package com.dametdamet.app.model;

import java.util.*;

public class MultiMap<K, V> {

    private Map<K, Set<V>> associations;

    public MultiMap(){
        associations = new HashMap<>();
    }

    public void put(K key, V value){
        if (!associations.containsKey(key)){
            associations.put(key, new HashSet<>());
        }else{
            associations.get(key).add(value);
        }
    }

    public Iterator<V> getIteratorOf(K key){
        return associations.get(key).iterator();
    }
}
