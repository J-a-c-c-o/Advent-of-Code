package nl.jtepoel.AOC.utils;

import java.util.Collection;
import java.util.HashMap;

public class FrequencySetLong<A> extends HashMap<A, Long> {

    /**
     * Constructs an empty FrequencySet.
     */
    public FrequencySetLong() {
        super();
    }

    /**
     * Constructs a FrequencySet from an array.
     *
     * @param arr the array to be added to the FrequencySet.
     */
    public FrequencySetLong(A[] arr) {
        super();
        for (A a : arr) {
            add(a);
        }
    }

    /**
     * Constructs a FrequencySet from a set.
     *
     * @param items the collection to be added to the FrequencySet.
     */
    public FrequencySetLong(Collection<A> items) {
        super();
        items.forEach(this::add);
    }

    /**
     * Constructs a FrequencySet from a char array.
     *
     * @param charArray the char array to be added to the FrequencySet.
     */
    public FrequencySetLong(char[] charArray) {
        super();
        for (char c : charArray) {
            add((A) Character.valueOf(c));
        }
    }

    /**
     * Constructs a FrequencySet from a string.
     *
     * @param s the string to be added to the FrequencySet.
     */
    public FrequencySetLong(String s) {
        super();
        for (char c : s.toCharArray()) {
            add((A) Character.valueOf(c));
        }
    }

    /**
     * Constructs a FrequencySet from an int array.
     *
     * @param arr the int array to be added to the FrequencySet.
     */
    public FrequencySetLong(int[] arr) {
        super();
        for (int a : arr) {
            add((A) Integer.valueOf(a));
        }
    }

    /**
     * Adds an object to the FrequencySet.
     *
     * @param a the object to be added.
     */
    public void add(A a) {
        super.put(a, get(a) + 1);
    }

    /**
     * Adds an object to the FrequencySet a specified number of times.
     * @param freq the frequency of the object to be added.
     */
    public void addAll(FrequencySetLong<A> freq) {
        freq.forEach((key, value) -> put(key, super.getOrDefault(key, 0L) + value));
    }

    /**
     * Decrements the frequency of an object in the FrequencySet.
     * @param a the object to be decremented.
     */
    public void decrement(A a) {
        if (get(a) > 0) {
            super.put(a, get(a) - 1);
        }
    }

    /**
     * Returns the frequency of an object in the FrequencySet.
     * @param a the key whose associated value is to be returned
     * @return the frequency of the object in the FrequencySet.
     */
    @Override
    public Long get(Object a) {
        return super.getOrDefault(a, 0L);
    }

    /**
     * Returns the object with the highest frequency in the FrequencySet.
     * @return the object with the highest frequency in the FrequencySet.
     */
    public A getLargest() {
        return super.entrySet().stream().max((e1, e2) -> e1.getValue() > e2.getValue() ? 1 : -1).get().getKey();
    }

    /**
     * Returns the object with the lowest frequency in the FrequencySet.
     * @return the object with the lowest frequency in the FrequencySet.
     */
    public A getSmallest() {
        return super.entrySet().stream().min((e1, e2) -> e1.getValue() > e2.getValue() ? 1 : -1).get().getKey();
    }
}
