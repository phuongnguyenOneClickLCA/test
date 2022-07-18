package com.bionova.optimi.util

import groovy.transform.CompileStatic

@CompileStatic
class OptimiCollectionUtils {

    static <T> List<T> getNewItemsBetweenTwoLists(List<T> currentList, List<T> newList) {
        List<T> newItems = newList ? new ArrayList<T>(newList) : new ArrayList<T>()
        if (currentList) {
            newItems?.removeAll(currentList)
        }
        return newItems
    }

    static <T> List<T> getRemovedItemsBetweenTwoLists(List<T> currentList, List<T> newList) {
        List<T> removedItems = currentList ? new ArrayList<T>(currentList) : new ArrayList<T>()
        if (removedItems && newList) {
            removedItems?.removeAll(newList)
        }
        return removedItems
    }
}
