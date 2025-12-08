package nl.jtepoel.AOC.utils;

import java.util.*;

public class UnionFind<A> {
    HashMap<A, A> parent = new HashMap<>();

    public UnionFind() {}

    public A find(A key) {
        if (parent.computeIfAbsent(key, k -> key) == key) {
            return key;
        }

        return find(parent.get(key));
    }

    public void union(A a, A b) {
        A aRep = find(a);
        A bRep = find(b);

        parent.put(aRep, bRep);

    }


    public long count(A key) {
        long count = 0;
        A keyRep = find(key);
        for (A a : parent.values()) {
            A aRep = find(a);
            if (aRep.equals(keyRep)) {
                count++;
            }
        }

        return count;
    }

    public List<A> findComponent(A key) {
        List<A> result = new ArrayList<>();
        A keyRep = find(key);
        for (A a : parent.values()) {
            A aRep = find(a);
            if (aRep.equals(keyRep)) {
                result.add(a);
            }
        }

        return result;
    }

    public List<List<A>> findAllComponents() {
        Map<A, List<A>> result = new HashMap<>();
        for (A key : parent.keySet()) {
            A keyRep = find(key);
            List<A> aList = result.computeIfAbsent(keyRep, k -> new ArrayList<>());
            aList.add(key);
        }

        return new ArrayList<>(result.values());
    }
}
