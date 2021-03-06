// This file is part of CPAchecker,
// a tool for configurable software verification:
// https://cpachecker.sosy-lab.org
//
// SPDX-FileCopyrightText: 2007-2020 Dirk Beyer <https://www.sosy-lab.org>
//
// SPDX-License-Identifier: Apache-2.0

public class IntegerOverflow_true_assert {

  public static void main(String[] args0) {
    int a = 2147483647;

    a = a + 1;
    assert a == -2147483648;

    a = a - 1;
    assert a == 2147483647;
  }
}
