package com.dametdamet.app.model;

import java.util.*;

public class MultiMap<K, V> {

    private Map<K, Set<V>> associations;

    public MultiMap(){
        associations = new HashMap<>();
    }

    public void put(K key, V value){
        if (!associations.containsKey(key)){
            HashSet<V> set = new HashSet<>();
            set.add(value);
            associations.put(key, set);
        }else{
            associations.get(key).add(value);
        }
    }

    public Iterator<V> getIteratorOf(K key){
        if(associations.get(key) == null){
            return Collections.emptyIterator();
        }
        return associations.get(key).iterator();
    }
}