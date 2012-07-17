/*
 *  CPAchecker is a tool for configurable software verification.
 *  This file is part of CPAchecker.
 *
 *  Copyright (C) 2007-2012  Dirk Beyer
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
package org.sosy_lab.cpachecker.cpa.bdd;

import org.sosy_lab.common.configuration.Configuration;
import org.sosy_lab.common.configuration.InvalidConfigurationException;
import org.sosy_lab.common.configuration.Option;
import org.sosy_lab.common.configuration.Options;
import org.sosy_lab.cpachecker.core.interfaces.Precision;
import org.sosy_lab.cpachecker.util.VariableClassification;

import com.google.common.base.Optional;

@Options(prefix = "cpa.bdd.vector")
public class BDDVectorPrecision implements Precision {

  @Option(description = "track boolean variables from cfa as bitvectors,"
      + " this option limits the whitelist")
  private boolean trackBooleanFromCFA = false;

  @Option(description = "track simple numeral variables from cfa as bitvectors,"
      + " this option limits the whitelist")
  private boolean trackSimpleNumbersFromCFA = true;

  private final Optional<VariableClassification> varClass;

  public BDDVectorPrecision(Configuration config, Optional<VariableClassification> vc)
      throws InvalidConfigurationException {
    config.inject(this);
    this.varClass = vc;
  }

  public boolean isDisabled() {
    boolean trackSomeBoolean = trackBooleanFromCFA &&
        varClass.isPresent() && !varClass.get().getBooleanVars().isEmpty();
    boolean trackSomeSimpleNumber = trackSimpleNumbersFromCFA &&
        varClass.isPresent() && !varClass.get().getSimpleNumberVars().isEmpty();
    return !(trackSomeBoolean || trackSomeSimpleNumber);
  }

  /**
   * This method tells if the precision demands the given variable to be tracked.
   *
   * @param variable the scoped name of the variable to check
   * @return true, if the variable has to be tracked, else false
   */
  public boolean isTracking(String function, String var) {
    boolean isTrackedBoolean = trackBooleanFromCFA &&
        varClass.isPresent() && varClass.get().getBooleanVars().containsEntry(function, var);
    boolean isTrackedSimpleNumber = trackSimpleNumbersFromCFA &&
        varClass.isPresent() && varClass.get().getSimpleNumberVars().containsEntry(function, var);
    System.out.println(function + var + isTrackedBoolean + isTrackedSimpleNumber);
    return isTrackedBoolean || isTrackedSimpleNumber;
  }
}
