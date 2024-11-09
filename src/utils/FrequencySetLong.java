package utils;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class FrequencySetLong<A> {
    private final HashMap<A, Long> map = new HashMap<>();

    public FrequencySetLong() {
    }

    public FrequencySetLong(A[] arr) {
        for (A a : arr) {
            add(a);
        }
    }

    public FrequencySetLong(Set<A> set) {
        for (A a : set) {
            add(a);
        }
    }

    public FrequencySetLong(List<A> list) {
        for (A a : list) {
            add(a);
        }
    }

    public FrequencySetLong(char[] charArray) {
        for (char c : charArray) {
            add((A) Character.valueOf(c));
        }
    }


    public void add(A a) {
        map.put(a, map.getOrDefault(a, 0L) + 1);
    }

    public long get(A a) {
        return map.getOrDefault(a, 0L);
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

    public void reduce(A a) {
        if (map.containsKey(a)) {
            map.put(a, map.get(a) - 1);
            if (map.get(a) == 0) {
                map.remove(a);
            }
        }
    }

    public A getLargest() {
        long max = 0;
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
        long min = Long.MAX_VALUE;
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

    public void addAll(FrequencySetLong<A> freq) {
        for (A key : freq.getKeys()) {
            map.put(key, map.getOrDefault(key, 0L) + freq.get(key));
        }
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
