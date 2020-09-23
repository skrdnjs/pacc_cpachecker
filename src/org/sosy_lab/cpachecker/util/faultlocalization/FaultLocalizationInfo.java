// This file is part of CPAchecker,
// a tool for configurable software verification:
// https://cpachecker.sosy-lab.org
//
// SPDX-FileCopyrightText: 2007-2020 Dirk Beyer <https://www.sosy-lab.org>
//
// SPDX-License-Identifier: Apache-2.0

package org.sosy_lab.cpachecker.util.faultlocalization;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.sosy_lab.common.JSON;
import org.sosy_lab.cpachecker.cfa.model.CFAEdge;
import org.sosy_lab.cpachecker.core.counterexample.CFAPathWithAdditionalInfo;
import org.sosy_lab.cpachecker.core.counterexample.CounterexampleInfo;

public class FaultLocalizationInfo extends CounterexampleInfo {

  private List<Fault> rankedList;
  private FaultReportWriter htmlWriter;

  /** Maps a CFA edge to the index of faults in {@link #rankedList} associated with that edge. **/
  private Multimap<CFAEdge, Integer> mapEdgeToRankedFaultIndex;
  private Map<CFAEdge, FaultContribution> mapEdgeToFaultContribution;

  /**
   * Fault localization algorithms will result in a set of sets of CFAEdges that are most likely to fix a bug.
   * Transforming it into a Set of Faults enables the possibility to attach reasons of why this edge is in this set.
   * After ranking the set of faults an instance of this class can be created.
   *
   * The class should be used to display information to the user.
   *
   * Note that there is no need to create multiple instances of this object if more than one
   * ranking should be applied. FaultRankingUtils provides a method that concatenates multiple rankings.
   *
   * To see the result of FaultLocalizationInfo replace the CounterexampleInfo of the target state by this
   * or simply call apply() on an instance of this class.
   *
   * @param pFaults Ranked list of faults obtained by a fault localization algorithm
   * @param pParent the counterexample info of the target state
   */
  public FaultLocalizationInfo(
      List<Fault> pFaults,
      CounterexampleInfo pParent) {
    super(
        pParent.isSpurious(),
        pParent.getTargetPath(),
        pParent.getCFAPathWithAssignments(),
        pParent.isPreciseCounterExample(),
        CFAPathWithAdditionalInfo.empty());
    initialize(pFaults);
  }

  /**
   *
   * Fault localization algorithms will result in a set of sets of CFAEdges that are most likely to fix a bug.
   * Transforming it into a Set of Faults enables the possibility to attach reasons of why this edge is in this set.
   * After ranking the set of faults an instance of this class can be created.
   *
   * The class should be used to display information to the user.
   *
   * Note that there is no need to create multiple instances of this object if more than one
   * ranking should be applied. FaultRankingUtils provides a method that concatenates multiple rankings.
   *
   * To see the result of FaultLocalizationInfo replace the CounterexampleInfo of the target state by this
   * or simply call apply() on an instance of this class.
   *
   * @param pFaults set of faults obtained by a fault localization algorithm
   * @param pRanking the ranking for pFaults
   * @param pParent the counterexample info of the target state
   */
  public FaultLocalizationInfo(Set<Fault> pFaults, FaultRanking pRanking, CounterexampleInfo pParent){
    super(
        pParent.isSpurious(),
        pParent.getTargetPath(),
        pParent.getCFAPathWithAssignments(),
        pParent.isPreciseCounterExample(),
        CFAPathWithAdditionalInfo.empty());
    List<Fault> rankedFault = pRanking.rank(pFaults);
    for (Fault fault : rankedFault) {
      FaultRankingUtils.assignScoreTo(fault);
      for (FaultContribution faultContribution : fault) {
        FaultRankingUtils.assignScoreTo(faultContribution);
      }
    }
    initialize(rankedFault);
  }

  private void initialize(List<Fault> pRankedFaults){
    mapEdgeToFaultContribution = new HashMap<>();
    mapEdgeToRankedFaultIndex = ArrayListMultimap.create();
    for(int i = 0; i < pRankedFaults.size(); i++){
      for (FaultContribution faultContribution : pRankedFaults.get(i)) {
        mapEdgeToRankedFaultIndex.put(faultContribution.correspondingEdge(), i);
        mapEdgeToFaultContribution.put(faultContribution.correspondingEdge(), faultContribution);
      }
    }

    rankedList = pRankedFaults;
    htmlWriter = new FaultReportWriter();
  }

  public int getRankOfSet(Fault set) {
    return rankedList.indexOf(set);
  }

  @Override
  public String toString() {
    StringBuilder toString = new StringBuilder();
    if(!rankedList.isEmpty()){
      toString.append(rankedList.stream().map(Fault::toString).collect(Collectors.joining("\n\n")));
    }
    return toString.toString();
  }

  /**
   * Transform a set of sets of CFAEdges to a set of Faults.
   *
   * @param pErrorIndicators possible candidates for the error
   * @return FaultLocalizationOutputs of the CFAEdges.
   */
  public static Set<Fault> transform(
      Set<Set<CFAEdge>> pErrorIndicators) {
    Set<Fault> transformed = new HashSet<>();
    for (Set<CFAEdge> errorIndicator : pErrorIndicators) {
      transformed.add(new Fault(
          errorIndicator.stream().map(FaultContribution::new).collect(Collectors.toSet())));
    }
    return transformed;
  }

  public void faultsToJSON(Writer pWriter) throws IOException {
    List<Map<String, Object>> faults = new ArrayList<>();
    for (int i = 0; i < rankedList.size(); i++) {
      Fault fault = rankedList.get(i);
      Map<String, Object> faultMap = new HashMap<>();
      faultMap.put("rank", (i+1));
      faultMap.put("score", (int) (100 * fault.getScore()));
      faultMap.put("reason", htmlWriter.toHtml(fault));
      faults.add(faultMap);
    }
    JSON.writeJSONString(faults ,pWriter);
  }

  /**
   * Append additional information to the CounterexampleInfo output
   * @param elem maps a property of edge to an object
   * @param edge the edge that is currently transformed into JSON format.
   */
  @Override
  protected void addAdditionalInfo(Map<String, Object> elem, CFAEdge edge) {
    elem.put("additional", "");
    FaultContribution fc = mapEdgeToFaultContribution.get(edge);
    if(fc != null){
      if(fc.hasReasons()){
        elem.put("additional", "<br><br><strong>Additional information provided:</strong><br>" + htmlWriter.toHtml(fc));
      }
    }
    if(mapEdgeToRankedFaultIndex.containsKey(edge)){
      elem.put("faults", mapEdgeToRankedFaultIndex.get(edge));
    }
    if (!elem.containsKey("faults")) {
      elem.put("faults", new ArrayList<>());
    }
  }

  public List<Fault> getRankedList() {
    return rankedList;
  }

  public FaultReportWriter getHtmlWriter() {
    return htmlWriter;
  }

  public void replaceHtmlWriter(FaultReportWriter pFaultToHtml){
    htmlWriter = pFaultToHtml;
  }

  /**
   * Replace default CounterexampleInfo with this extended version of a CounterexampleInfo.
   */
  public void apply(){
    super.getTargetPath().getLastState().replaceCounterexampleInformation(this);
  }
}
