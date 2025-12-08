package nl.jtepoel.AOC.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static java.util.Collections.swap;

/**
 * Utility class for generating permutations of a list.
 */
public class Permutation {

    /**
     * Generate all permutations of a list using Heap's algorithm.
     * @param list list to generate permutations for
     * @return list of permutations
     * @param <E> type of the list
     */
    public static <E> List<List<E>> generatePermutations(List<E> list) {
        return generatePermutations(list, list.size());
    }

    /**
     * Generate all permutations of a list of size n using Heap's algorithm.
     * @param list list to generate permutations for
     * @param n number of elements to generate permutations for
     * @return list of permutations
     * @param <E> type of the list
     */
    private static <E> List<List<E>> generatePermutations(List<E> list, int n) {
        List<List<E>> result = new ArrayList<>();
        if (n == 1) {
            result.add(new ArrayList<>(list));
            return result;
        } else {
            for (int i = 0; i < n - 1; i++) {
                result.addAll(generatePermutations(list, n - 1));
                if (n % 2 == 0) {
                    swap(list, i, n - 1);
                } else {
                    swap(list, 0, n - 1);
                }
            }
            result.addAll(generatePermutations(list, n - 1));
        }

        return result;
    }

    /**
     * Generate all permutations of a list in lexicographical order.
     * @param list list to generate permutations for
     * @return list of permutations
     * @param <E> type of the list
     */
    public static <E extends Comparable<E>> List<List<E>> generatePermutationsOrdered(List<E> list) {
        list.sort(E::compareTo);
        List<List<E>> result = new ArrayList<>();

        boolean hasNext = true;

        while (hasNext) {
            result.add(new ArrayList<>(list));
            int k = -1;
            for (int i = list.size() - 2; i >= 0; i--) {
                if (list.get(i).compareTo(list.get(i + 1)) < 0) {
                    k = i;
                    break;
                }
            }
            if (k == -1) {
                hasNext = false;
            } else {
                int l = -1;
                for (int i = list.size() - 1; i > k; i--) {
                    if (list.get(i).compareTo(list.get(k)) > 0) {
                        l = i;
                        break;
                    }
                }
                swap(list, k, l);
                int left = k + 1;
                int right = list.size() - 1;
                while (left < right) {
                    swap(list, left++, right--);
                }
            }
        }

        return result;
    }

    /**
     * Generate an Iterable of all permutations of a list.
     * @param list list to generate permutations for
     * @return Iterable of permutations
     * @param <E> type of the list
     */
    public static <E> Iterable<List<E>> asIterable(List<E> list) {
        return () -> new PermutationIterator<>(list);
    }

    /**
     * Generate an Iterable of all permutations of a list.
     * using a non non-recursive version of Heap's algorithm.
     * @param <E>
     */
    public static class PermutationIterator<E> implements Iterator<List<E>> {
        private final List<E> current;
        private final int n;
        private final int[] indexes;
        private int i;

        /**
         * Create a new PermutationIterator.
         * @param list list to generate permutations for
         * @param n number of elements to generate permutations for
         */
        private PermutationIterator(List<E> list, int n) {
            this.current = new ArrayList<>(list);
            this.n = n;
            this.i = 1;
            this.indexes = new int[n];
            for (int j = 0; j < n; j++) {
                indexes[j] = 0;
            }
        }

        /**
         * Create a new PermutationIterator.
         * @param list list to generate permutations for
         */
        public PermutationIterator(List<E> list) {
            this(list, list.size());
        }

        @Override
        public boolean hasNext() {
            return i < n;
        }

        @Override
        public List<E> next() {

            if (!hasNext()) {
                throw new NoSuchElementException("No more permutations available.");
            }


            List<E> result = new ArrayList<>(current);
            if (indexes[i] < i) {
                swap(current, i % 2 == 0 ? 0 : indexes[i], i);
                indexes[i]++;
                i = 1;
            } else {
                indexes[i] = 0;
                i++;
                if (hasNext()) {
                    next();
                }
            }

            return result;
        }
    }
}
