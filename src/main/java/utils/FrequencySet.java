package utils;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class FrequencySet<A> {
    private final HashMap<A, Integer> map = new HashMap<>();

    public FrequencySet() {
    }

    public FrequencySet(A[] arr) {
        for (A a : arr) {
            add(a);
        }
    }

    public FrequencySet(Set<A> set) {
        for (A a : set) {
            add(a);
        }
    }

    public FrequencySet(List<A> list) {
        for (A a : list) {
            add(a);
        }
    }

    public FrequencySet(char[] charArray) {
        for (char c : charArray) {
            add((A) Character.valueOf(c));
        }
    }


    public void add(A a) {
        map.put(a, map.getOrDefault(a, 0) + 1);
    }

    public int get(A a) {
        return map.getOrDefault(a, 0);
    }

    public Set<A> getKeys() {
        return map.keySet();
    }

    public int size() {
        return map.size();
    }

    public void remove(A a) {
        map.remove(a);
    }

    public A getLargest() {
        int max = 0;
        A maxKey = null;
        for (A key : map.keySet()) {
            if (map.get(key) > max) {
                max = map.get(key);
                maxKey = key;
            }
        }
        return maxKey;
    }

    public A getSmallest() {
        int min = Integer.MAX_VALUE;
        A minKey = null;
        for (A key : map.keySet()) {
            if (map.get(key) < min) {
                min = map.get(key);
                minKey = key;
            }
        }
        return minKey;
    }


    public void clear() {
        map.clear();
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
