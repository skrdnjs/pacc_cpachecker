/*
 *  CPAchecker is a tool for configurable software verification.
 *  This file is part of CPAchecker.
 *
 *  Copyright (C) 2007-2010  Dirk Beyer
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
package org.sosy_lab.cpachecker.util.assumptions;

import org.sosy_lab.cpachecker.util.symbpredabstraction.interfaces.Formula;
import org.sosy_lab.cpachecker.util.symbpredabstraction.interfaces.FormulaManager;
import org.sosy_lab.cpachecker.core.interfaces.AbstractElement;
import org.sosy_lab.cpachecker.core.interfaces.AbstractWrapperElement;

/**
 * Static methods used as helpers to manipulate elements implementing
 * FormulaReportingElement.
 *
 * @author g.theoduloz
 */
public class ReportingUtils {

  /** Cannot have instances */
  private ReportingUtils() {}

  /**
   * Returns a predicate representing states represented by
   * the given abstract element, according to reported
   * formulas
   */
  public static Formula extractReportedFormulas(FormulaManager manager, AbstractElement element)
  {
    Formula result = manager.makeTrue();

    // If it is a wrapper, add its sub-element's assertions
    if (element instanceof AbstractWrapperElement)
    {
      for (AbstractElement subel : ((AbstractWrapperElement) element).getWrappedElements())
        result = manager.makeAnd(result, extractReportedFormulas(manager, subel));
    }

    // If the element can be approximated by a formula, conjunct its approximation
    if (element instanceof FormulaReportingElement) {
      FormulaReportingElement repel = (FormulaReportingElement) element;
      Formula formula = repel.getFormulaApproximation();
      result = manager.makeAnd(result, formula);
    }

    return result;
  }

}
