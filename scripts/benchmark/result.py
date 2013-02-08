"""
CPAchecker is a tool for configurable software verification.
This file is part of CPAchecker.

Copyright (C) 2007-2012  Dirk Beyer
All rights reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


CPAchecker web page:
  http://cpachecker.sosy-lab.org
"""

from __future__ import absolute_import, unicode_literals

import benchmark.util as util

# These strings are searched in file names to determine correct or incorrect verification result.
# Do not change the values.
_SAFE_SUBSTRING_LIST   = ['_safe']
_UNSAFE_SUBSTRING_LIST = ['_unsafe']

# Score values taken from http://sv-comp.sosy-lab.org/
SCORE_CORRECT_SAFE = 2
SCORE_CORRECT_UNSAFE = 1
SCORE_UNKNOWN = 0
SCORE_WRONG_UNSAFE = -4
SCORE_WRONG_SAFE = -8


def fileIsUnsafe(filename):
    return util.containsAny(filename, _UNSAFE_SUBSTRING_LIST)

def fileIsSafe(filename):
    return util.containsAny(filename, _SAFE_SUBSTRING_LIST)

def getResultCategory(filename, status):
    '''
    This function return a string
    that shows the relation between status and file.
    '''
    status = status.lower()

    if status not in ['safe', 'unsafe', 'unknown']:
        return 'error'

    if fileIsSafe(filename):
        if status == 'safe':
            return 'correctSafe'
        elif status == 'unsafe':
            return 'wrongUnsafe'

    elif fileIsUnsafe(filename):
        if status == 'safe':
            return 'wrongSafe'
        elif status == 'unsafe':
            return 'correctUnsafe'

    else:
        return None

    return 'unknown'

def calculateScore(category):
    return {'correctSafe':   SCORE_CORRECT_SAFE,
            'wrongSafe':     SCORE_WRONG_SAFE,
            'correctUnsafe': SCORE_CORRECT_UNSAFE,
            'wrongUnsafe':   SCORE_WRONG_UNSAFE,
            }.get(category,  SCORE_UNKNOWN)