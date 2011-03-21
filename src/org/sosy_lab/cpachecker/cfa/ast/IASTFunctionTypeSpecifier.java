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
package org.sosy_lab.cpachecker.cfa.ast;

import static com.google.common.base.Preconditions.checkState;

import java.util.List;

import com.google.common.collect.ImmutableList;

public class IASTFunctionTypeSpecifier extends IASTDeclSpecifier {

  private final IASTDeclSpecifier              returnType;
  private       IASTName                       name = null;
  private final List<IASTSimpleDeclaration>    parameters;
  private final boolean                        takesVarArgs;
  
  public IASTFunctionTypeSpecifier(
      boolean pConst,
      boolean pVolatile,
      IASTDeclSpecifier pReturnType,
      List<IASTSimpleDeclaration> pParameters,
      boolean pTakesVarArgs) {
    super(pConst, pVolatile);
    returnType = pReturnType;
    parameters = ImmutableList.copyOf(pParameters);
    takesVarArgs = pTakesVarArgs;
  }
  
  public IASTDeclSpecifier getReturnType() {
    return returnType;
  }
  
  public IASTName getName() {
    return name;
  }
  
  public void setName(IASTName pName) {
    checkState(name == null);
    name = pName;
  }
  
  public List<IASTSimpleDeclaration> getParameters() {
    return parameters;
  }
  
  public boolean takesVarArgs() {
    return takesVarArgs;
  }
}
