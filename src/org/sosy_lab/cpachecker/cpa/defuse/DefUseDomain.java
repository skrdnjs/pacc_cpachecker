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
package org.sosy_lab.cpachecker.cpa.defuse;

import java.util.HashSet;
import java.util.Set;

import org.sosy_lab.cpachecker.core.interfaces.AbstractDomain;
import org.sosy_lab.cpachecker.core.interfaces.AbstractElement;
import org.sosy_lab.cpachecker.core.interfaces.JoinOperator;
import org.sosy_lab.cpachecker.core.interfaces.PartialOrder;
import org.sosy_lab.cpachecker.cpa.defuse.DefUseDefinition;
import org.sosy_lab.cpachecker.cpa.defuse.DefUseElement;
import org.sosy_lab.cpachecker.exceptions.CPAException;

public class DefUseDomain implements AbstractDomain
{
    private static class DefUsePartialOrder implements PartialOrder
    {
        @Override
        public boolean satisfiesPartialOrder (AbstractElement element1, AbstractElement element2)
        {
            DefUseElement defUseElement1 = (DefUseElement) element1;
            DefUseElement defUseElement2 = (DefUseElement) element2;
            
            return defUseElement2.containsAllOf(defUseElement1);
        }
    }

    private static class DefUseJoinOperator implements JoinOperator
    {
        @Override
        public AbstractElement join (AbstractElement element1, AbstractElement element2)
        {
            // Useless code, but helps to catch bugs by causing cast exceptions
            DefUseElement defUseElement1 = (DefUseElement) element1;
            DefUseElement defUseElement2 = (DefUseElement) element2;

            Set<DefUseDefinition> joined = new HashSet<DefUseDefinition> ();
            for (DefUseDefinition definition : defUseElement1)
                joined.add(definition);

            for (DefUseDefinition definition : defUseElement2)
            {
                if (!joined.contains(definition))
                    joined.add (definition);
            }

            return new DefUseElement (joined);
        }
    }

    private final static PartialOrder partialOrder = new DefUsePartialOrder ();
    private final static JoinOperator joinOperator = new DefUseJoinOperator ();

    @Override
    public JoinOperator getJoinOperator ()
    {
        return joinOperator;
    }

    @Override
    public PartialOrder getPartialOrder ()
    {
        return partialOrder;
    }
    
    @Override
    public AbstractElement join(AbstractElement pElement1,
        AbstractElement pElement2) throws CPAException {
      return getJoinOperator().join(pElement1, pElement2);
    }

    @Override
    public boolean satisfiesPartialOrder(AbstractElement pElement1,
        AbstractElement pElement2) throws CPAException {
      return getPartialOrder().satisfiesPartialOrder(pElement1, pElement2);
    }
}
