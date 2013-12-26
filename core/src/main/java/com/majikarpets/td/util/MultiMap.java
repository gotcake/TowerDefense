package com.majikarpets.td.util;

import java.util.*;

/**
 * A class which allows you to map multiple values to a single key.
 * @author Aaron Cake
 */
public class MultiMap<K, V> {

    private final HashMap<K, Set<V>> map;

    public MultiMap() {
        map = new HashMap<K, Set<V>>();
    }

    public Set<K> getKeys() {
        return map.keySet();
    }

    public Set<V> get(K key) {
        Set<V> set = map.get(key);
        if (set == null)
            return Collections.emptySet();
        return set;
    }

    public void put(K key, V value) {
        Set<V> set = map.get(key);
        if (set == null) {
            set = new HashSet<V>();
            map.put(key, set);
        }
        set.add(value);
    }

    public void putAll(K key, Collection<V> values) {
        Set<V> set = map.get(key);
        if (set == null) {
            set = new HashSet<V>();
            map.put(key, set);
        }
        set.addAll(values);
    }

    public void putAll(K key, V... values) {
        Set<V> set = map.get(key);
        if (set == null) {
            set = new HashSet<V>();
            map.put(key, set);
        }
        set.addAll(Arrays.asList(values));
    }

    public void removeValue(K key, V value) {
        Set<V> set = map.get(key);
        if (set != null)
            set.remove(value);
    }

    public void removeValue(V value) {
        for (Set<V> set: map.values()) {
            set.remove(value);
        }
    }

    public void removeKey(K key) {
        map.remove(key);
    }

    public void clear() {
        map.clear();
    }

}
