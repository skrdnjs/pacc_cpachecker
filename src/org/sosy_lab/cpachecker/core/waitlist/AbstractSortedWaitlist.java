/*
 *  CPAchecker is a tool for configurable software verification.
 *  This file is part of CPAchecker.
 *
 *  Copyright (C) 2007-2014  Dirk Beyer
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
package org.sosy_lab.cpachecker.core.waitlist;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.sosy_lab.cpachecker.core.interfaces.AbstractState;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.errorprone.annotations.ForOverride;

/**
 * Default implementation of a sorted waitlist.
 * The key that is used for sorting is defined by sub-classes (it's type is
 * the type parameter of this class).
 *
 * There may be several abstract states with the same key, so this class
 * delegates the decision which of those should be chosen to a second waitlist
 * implementation. A factory for this implementation needs to be given to the
 * constructor.
 *
 * The iterators created by this class are unmodifiable.
 */
public abstract class AbstractSortedWaitlist<K extends Comparable<K>> implements Waitlist {

  //DEBUG
  private final WaitlistFactory wrappedWaitlist;
  //protected final WaitlistFactory wrappedWaitlist;
  //GUBED

  // invariant: all entries in this map are non-empty
  private final NavigableMap<K, Waitlist> waitlist;

  private int size = 0;

  /**
   * Constructor that needs a factory for the waitlist implementation that
   * should be used to store states with the same sorting key.
   */

  protected AbstractSortedWaitlist(WaitlistFactory pSecondaryStrategy) {
    wrappedWaitlist = Preconditions.checkNotNull(pSecondaryStrategy);
    waitlist = new TreeMap<>();
  }

  /**
   * Method that generates the sorting key for any abstract state.
   * This method may not return null.
   * If this method throws an exception, no guarantees about the state of the
   * current instance of this class are made.
   */
  @ForOverride
  protected abstract K getSortKey(AbstractState pState);

  @Override
  public void add(AbstractState pState) {
    /*
    //DEBUG
    ARGState tARGState = AbstractStates.extractStateByType(pState, ARGState.class);
    assert tARGState != null : "extractStateByType is failed! (ARGState)";
    if (tARGState.getBlkDepth()==0){
      System.out.println("What?!");
    }
    //GUBED
     *
     */
    K key = getSortKey(pState);
    Waitlist localWaitlist = waitlist.get(key);
    if (localWaitlist == null) {
      localWaitlist = wrappedWaitlist.createWaitlistInstance();
      waitlist.put(key, localWaitlist);
    } else {
      assert !localWaitlist.isEmpty();
    }
    localWaitlist.add(pState);
    size++;
  }

  @Override
  public boolean contains(AbstractState pState) {
    K key = getSortKey(pState);
    Waitlist localWaitlist = waitlist.get(key);
    if (localWaitlist == null) {
      return false;
    }
    assert !localWaitlist.isEmpty();
    return localWaitlist.contains(pState);
  }

  @Override
  public void clear() {
    waitlist.clear();
    size = 0;
  }

  @Override
  public boolean isEmpty() {
    assert waitlist.isEmpty() == (size == 0);
    return waitlist.isEmpty();
  }

  @Override
  public Iterator<AbstractState> iterator() {
    return Iterables.concat(waitlist.values()).iterator();
  }

  @Override
  //DEBUG
  //originally final method but I modify it as non-final
  //GUBED
  public AbstractState pop() {
    Entry<K, Waitlist> highestEntry = null;
    /*
    //DEBUG

    if (this instanceof CallstackSortedWaitlist){
      if (waitlist.size() > 0){
        for (Entry<K, Waitlist> entry : waitlist.entrySet()){
          System.out.print(entry.getValue().getClass().getName());
          System.out.println(" "+entry.getValue().size()+"  key: "+entry.getKey());
          }
        }
    }

    boolean check = true;

    if (this instanceof DynamicSortedWaitlist){
      if (waitlist.size() > 0){
        for (Entry<K, Waitlist> entry : waitlist.entrySet()){
          K key = entry.getKey();
          if (key instanceof SimpleSearchInfo){
            SimpleSearchInfo skey = (SimpleSearchInfo) key;
            if (skey.getInfos().get("isAbsSt")==0){
              check = false;
            }
          }
        }
      }
    }

    if (check){
      System.out.println("good! only AbsSts");
    }

    //GUBED
     * * */


    highestEntry = waitlist.lastEntry();
    Waitlist localWaitlist = highestEntry.getValue();
    assert !localWaitlist.isEmpty();
    AbstractState result = localWaitlist.pop();
    if (localWaitlist.isEmpty()) {
      waitlist.remove(highestEntry.getKey());
    }
    size--;
    return result;
  }

  @Override
  public boolean remove(AbstractState pState) {
    K key = getSortKey(pState);
    Waitlist localWaitlist = waitlist.get(key);
    if (localWaitlist == null) {
      return false;
    }
    assert !localWaitlist.isEmpty();
    boolean result = localWaitlist.remove(pState);
    if (result) {
      if (localWaitlist.isEmpty()) {
        waitlist.remove(key);
      }
      size--;
    }
    return result;
  }

  public WaitlistFactory getWLF(){
    return wrappedWaitlist;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public String toString() {
    return waitlist.toString();
  }
}
