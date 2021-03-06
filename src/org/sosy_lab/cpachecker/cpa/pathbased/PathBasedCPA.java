/*
 *  CPAchecker is a tool for configurable software verification.
 *  This file is part of CPAchecker.
 *
 *  Copyright (C) 2007-2015  Dirk Beyer
 *  All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 *  CPAchecker web page:
 *    http://cpachecker.sosy-lab.org
 */
package org.sosy_lab.cpachecker.cpa.pathbased;

import java.util.Collection;

import org.sosy_lab.cpachecker.cfa.model.CFAEdge;
import org.sosy_lab.cpachecker.cfa.model.CFANode;
import org.sosy_lab.cpachecker.core.interfaces.AbstractDomain;
import org.sosy_lab.cpachecker.core.interfaces.AbstractState;
import org.sosy_lab.cpachecker.core.interfaces.ConfigurableProgramAnalysis;
import org.sosy_lab.cpachecker.core.interfaces.ConfigurableProgramAnalysisWithBAM;
import org.sosy_lab.cpachecker.core.interfaces.MergeOperator;
import org.sosy_lab.cpachecker.core.interfaces.Precision;
import org.sosy_lab.cpachecker.core.interfaces.PrecisionAdjustment;
import org.sosy_lab.cpachecker.core.interfaces.Reducer;
import org.sosy_lab.cpachecker.core.interfaces.StateSpacePartition;
import org.sosy_lab.cpachecker.core.interfaces.StopOperator;
import org.sosy_lab.cpachecker.core.interfaces.TransferRelation;
import org.sosy_lab.cpachecker.core.interfaces.pcc.ProofChecker;
import org.sosy_lab.cpachecker.exceptions.CPAException;
import org.sosy_lab.cpachecker.exceptions.CPATransferException;


public class PathBasedCPA implements ConfigurableProgramAnalysis, ConfigurableProgramAnalysisWithBAM, ProofChecker {

  @Override
  public boolean areAbstractSuccessors(AbstractState pState, CFAEdge pCfaEdge,
      Collection<? extends AbstractState> pSuccessors) throws CPATransferException, InterruptedException {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isCoveredBy(AbstractState pState, AbstractState pOtherState)
      throws CPAException, InterruptedException {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Reducer getReducer() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public AbstractDomain getAbstractDomain() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public TransferRelation getTransferRelation() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public MergeOperator getMergeOperator() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public StopOperator getStopOperator() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public PrecisionAdjustment getPrecisionAdjustment() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public AbstractState getInitialState(CFANode pNode, StateSpacePartition pPartition) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Precision getInitialPrecision(CFANode pNode, StateSpacePartition pPartition) {
    // TODO Auto-generated method stub
    return null;
  }

}
