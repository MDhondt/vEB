package be.mdhondt.veb;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;

import static java.lang.Integer.numberOfTrailingZeros;
import static java.lang.Math.*;

public class VanEmdeBoasTreeMap<E> implements Map<Integer, E> {

    private static final int MIN_UNIVERSE_SIZE = 2;
    private static final int NIL = -1;

    private VEBTree<E> root;

    public VanEmdeBoasTreeMap(int universeSize) {
        root = new VEBTree<>(universeSize);
    }

    @Override
    public int size() {
        return keySet().size();
    }

    @Override
    public boolean isEmpty() {
        return root.getMin() == NIL;
    }

    @Override
    public boolean containsKey(Object key) {
        if (key instanceof Integer)
            return root.contains((Integer) key);
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return values().contains(value);
    }

    @Override
    public E get(Object key) {
        if (key instanceof Integer)
            return root.getValue((Integer) key);
        throw new IllegalArgumentException("Key needs to be an Integer");
    }

    @Override
    public E put(Integer key, E value) {
        E oldValue = root.contains(key) ? root.getValue(key) : null;
        if (oldValue != null) {
            root.remove(key);
        }
        root.insert(key, value);
        return oldValue;
    }

    @Override
    public E remove(Object key) {
        if (key instanceof Integer) {
            E value = root.contains((Integer) key) ? root.getValue((Integer) key) : null;
            if (value != null) {
                root.remove((Integer) key);
            }
            return value;
        }
        throw new IllegalArgumentException("Key needs to be an Integer");
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends E> m) {
        for (Integer key : m.keySet()) {
            root.insert(key, m.get(key));
        }
    }

    @Override
    public void clear() {
        root = new VEBTree<>(root.universeSize);
    }

    @Override
    public Set<Integer> keySet() {
        Set<Integer> keys = new LinkedHashSet<>();
        if (root.getMin() == NIL) return keys;
        int key = root.getMin();
        while (key != NIL) {
            keys.add(key);
            key = root.successor(key);
        }
        return keys;
    }

    @Override
    public Collection<E> values() {
        List<E> values = new LinkedList<>();
        if (root.getMin() == NIL) return values;
        for (Integer key : keySet()) {
            values.add(root.getValue(key));
        }
        return values;
    }

    @Override
    public Set<Entry<Integer, E>> entrySet() {
        Set<Entry<Integer, E>> entries = new HashSet<>();
        if (root.getMin() == NIL) return entries;
        for (Integer key : keySet()) {
            entries.add(new SimpleEntry<>(key, root.getValue(key)));
        }
        return entries;
    }

    private static final class VEBTree<E> {

        private final int universeSize;
        private final int shift, mask;
        private final VEBTree<E> summary;
        private final VEBTree<E>[] clusters;
        private int min;
        private int max;
        private E minValue;
        private E maxValue;

        VEBTree(int universeSize) {
            this.universeSize = universeSize;
            min = NIL;
            max = NIL;

            int universeSizeLowerSquare = lowerSquare(universeSize);
            mask = universeSizeLowerSquare - 1;
            shift = numberOfTrailingZeros(universeSizeLowerSquare);

            if (universeSize == MIN_UNIVERSE_SIZE) {
                summary = null;
                clusters = null;
            } else {
                int universeSizeUpperSquare = upperSquare(universeSize);
                summary = new VEBTree<>(universeSizeUpperSquare);
                clusters = new VEBTree[universeSizeUpperSquare];
                for (int i = 0; i < universeSizeUpperSquare; i++) {
                    clusters[i] = new VEBTree<>(universeSizeLowerSquare);
                }
            }
        }

        private int getMin() {
            return min;
        }

        private int getMax() {
            return max;
        }

        private E getValue(int key) {
            if (key == min) return minValue;
            if (key == max) return maxValue;
            if (universeSize == 2) return null;
            return clusters[high(key)].getValue(low(key));
        }

        private boolean contains(int key) {
            if (key == min || key == max) return true;
            if (universeSize == 2) return false;
            return clusters[high(key)].contains(low(key));
        }

        private int successor(int key) {
            if (universeSize == 2)
                if (key == 0 && max == 1) return 1;
                else return NIL;
            if (min != NIL && key < min) return min;
            int maxLow = clusters[high(key)].getMax();
            if (maxLow != NIL && low(key) < maxLow) return index(high(key), clusters[high(key)].successor(low(key)));
            int successorCluster = summary.successor(high(key));
            if (successorCluster == NIL) return NIL;
            return index(successorCluster, clusters[successorCluster].getMin());
        }

        private int predecessor(int key) {
            if (universeSize == 2)
                if (key == 1 && min == 0) return 0;
                else return NIL;
            if (max != NIL && key > max) return max;
            int minLow = clusters[high(key)].getMin();
            if (minLow != NIL && low(key) > minLow) return index(high(key), clusters[high(key)].predecessor(low(key)));
            int predecessorCluster = summary.predecessor(high(key));
            if (predecessorCluster == NIL)
                if (min != NIL && key > min) return min;
                else return NIL;
            return index(predecessorCluster, clusters[predecessorCluster].getMax());
        }

        private void emptyInsert(int key, E value) {
            min = key;
            max = key;
            minValue = value;
            maxValue = value;
        }

        private void insert(int key, E value) {
            if (min == NIL)
                emptyInsert(key, value);
            else {
                if (key < min) {
                    int tmpK = key;
                    E tmpV = value;
                    key = min;
                    min = tmpK;
                    value = minValue;
                    minValue = tmpV;
                }
                if (universeSize > 2)
                    if (clusters[high(key)].getMin() == NIL) {
                        summary.insert(high(key), value);
                        clusters[high(key)].emptyInsert(low(key), value);
                    } else
                        clusters[high(key)].insert(low(key), value);
                if (key > max) {
                    max = key;
                    maxValue = value;
                }
            }
        }

        private void remove(int key) {
            if (min == max) {
                min = NIL;
                max = NIL;
                minValue = null;
                maxValue = null;
            } else if (universeSize == 2) {
                if (key == 0) {
                    min = 1;
                    minValue = maxValue;
                } else
                    min = 0;
                max = min;
                maxValue = minValue;
            } else {
                if (key == min) {
                    int firstCluster = summary.getMin();
                    key = index(firstCluster, clusters[firstCluster].getMin());
                    min = key;
                    minValue = getValue(key);
                }
                clusters[high(key)].remove(low(key));
                if (clusters[high(key)].getMin() == NIL) {
                    summary.remove(high(key));
                    if (key == max) {
                        int summaryMax = summary.getMax();
                        if (summaryMax == NIL) {
                            max = min;
                            maxValue = minValue;
                        } else {
                            int m = index(summaryMax, clusters[summaryMax].getMax());
                            max = m;
                            maxValue = getValue(m);
                        }
                    }
                } else if (key == max) {
                    int m = index(high(key), clusters[high(key)].getMax());
                    max = m;
                    maxValue = getValue(m);
                }
            }
        }

        /**
         * high(x) = floor( x / lowerSquare(universe) )
         * <p>
         * shift is a power of 2 (Math.pow(2,shift) = universeSizeLowerSquare)
         * x / u  =  x >>> shift   (if 2^shift=u)
         */
        private int high(int x) {
            return x >>> shift;
        }

        /**
         * low(x) = x % lowerSquare(universe)
         * <p>
         * x % y = (x & (y − 1))
         */
        private int low(int x) {
            return x & mask;
        }

        /**
         * index(x, y) = x times lowerSquare(universe) + y
         * <p>
         * x times u  =  x << shift    (if 2^shift=u)
         * x + y  =  x | y    (if x&y=0) ** die if conditie geld altijd voor (x << shift)   en    (y & mask), dus in orde **
         */
        private int index(int x, int y) {
            return (x << shift) | (y & mask);
        }
    }

    // i should be power of 2?
    private static int lowerSquare(int i) {
        return (int) pow(2.0, floor((log(i) / log(2.0)) / 2.0));
    }

    // i should be power of 2?
    private static int upperSquare(int i) {
        return (int) pow(2.0, ceil((log(i) / log(2.0)) / 2.0));
    }
}