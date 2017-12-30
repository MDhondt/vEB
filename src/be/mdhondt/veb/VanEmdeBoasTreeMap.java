package be.mdhondt.veb;

import java.util.AbstractMap.SimpleEntry;
import java.util.*;

import static java.lang.Integer.numberOfTrailingZeros;
import static java.lang.Math.*;
import static java.util.Optional.empty;
import static java.util.Optional.of;

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
        if (key == null)
            throw new NullPointerException();
        if (!(key instanceof Integer))
            throw new ClassCastException();
        Integer integerKey = (Integer) key;
        if (integerKey < 0 || integerKey > (root.universeSize - 1))
            throw new IndexOutOfBoundsException(integerKey + " is not an element in the universe [0," + root.universeSize + "[");
        return root.contains(integerKey);
    }

    @Override
    public boolean containsValue(Object value) {
        if (value == null)
            throw new NullPointerException();
        return values().contains(value);
    }

    @Override
    public E get(Object key) {
        if (key == null)
            throw new NullPointerException();
        if (!(key instanceof Integer))
            throw new ClassCastException();
        Integer integerKey = (Integer) key;
        if (integerKey < 0 || integerKey > (root.universeSize - 1))
            throw new IndexOutOfBoundsException(integerKey + " is not an element in the universe [0," + root.universeSize + "[");
        return root.getValue(integerKey);
    }

    @Override
    public E put(Integer key, E value) {
        if (key == null || value == null)
            throw new NullPointerException();
        if (key < 0 || key > (root.universeSize - 1))
            throw new IndexOutOfBoundsException(key + " is not an element in the universe [0," + root.universeSize + "[");
        E oldValue = root.contains(key) ? root.getValue(key) : null;
        if (oldValue != null)
            root.remove(key);
        root.insert(key, value);
        return oldValue;
    }

    @Override
    public E remove(Object key) {
        if (key == null)
            throw new NullPointerException();
        if (!(key instanceof Integer))
            throw new ClassCastException();
        Integer integerKey = (Integer) key;
        if (integerKey < 0 || integerKey > (root.universeSize - 1))
            throw new IndexOutOfBoundsException(integerKey + " is not an element in the universe [0," + root.universeSize + "[");
        E value = root.contains(integerKey) ? root.getValue(integerKey) : null;
        if (value != null)
            root.remove(integerKey);
        return value;
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
    
    public Optional<Integer> getMinimum() {
        int min = root.getMin();
        return min != NIL ? of(min) : empty();
    }
    
    public Optional<E> getMinimumValue() {
        int min = root.getMin();
        return min != NIL ? of(get(min)) : empty();
    }
    
    public Optional<Integer> getMaximum() {
        int max = root.getMax();
        return max != NIL ? of(max) : empty();
    }
    
    public Optional<E> getMaximumValue() {
        int max = root.getMax();
        return max != NIL ? of(get(max)) : empty();
    }
    
    public Optional<Integer> successor(Integer key) {
        if (key == null)
            throw new NullPointerException();
        if (key < 0 || key > (root.universeSize - 1))
            throw new IndexOutOfBoundsException(key + " is not an element in the universe [0," + root.universeSize + "[");
        int successor = root.successor(key);
        return successor != NIL ? of(successor) : empty();
    }
    
    public Optional<E> successorValue(Integer key) {
        if (key == null)
            throw new NullPointerException();
        if (key < 0 || key > (root.universeSize - 1))
            throw new IndexOutOfBoundsException(key + " is not an element in the universe [0," + root.universeSize + "[");
        int successor = root.successor(key);
        return successor != NIL ? of(get(successor)) : empty();
    }
    
    public Optional<Integer> predecessor(Integer key) {
        if (key == null)
            throw new NullPointerException();
        if (key < 0 || key > (root.universeSize - 1))
            throw new IndexOutOfBoundsException(key + " is not an element in the universe [0," + root.universeSize + "[");
        int predecessor = root.predecessor(key);
        return predecessor != NIL ? of(predecessor) : empty();
    }
    
    public Optional<E> predecessorValue(Integer key) {
        if (key == null)
            throw new NullPointerException();
        if (key < 0 || key > (root.universeSize - 1))
            throw new IndexOutOfBoundsException(key + " is not an element in the universe [0," + root.universeSize + "[");
        int predecessor = root.predecessor(key);
        return predecessor != NIL ? of(get(predecessor)) : empty();
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

        private int getUniverseSize() {
            return universeSize;
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
                    minValue = maxValue;
                    min = 1;
                } else
                    min = 0;
                maxValue = minValue;
                max = min;
            } else {
                if (key == min) {
                    int firstCluster = summary.getMin();
                    key = index(firstCluster, clusters[firstCluster].getMin());
                    minValue = getValue(key);
                    min = key;
                }
                clusters[high(key)].remove(low(key));
                if (clusters[high(key)].getMin() == NIL) {
                    summary.remove(high(key));
                    if (key == max) {
                        int summaryMax = summary.getMax();
                        if (summaryMax == NIL) {
                            maxValue = minValue;
                            max = min;
                        } else {
                            int m = index(summaryMax, clusters[summaryMax].getMax());
                            maxValue = getValue(m);
                            max = m;
                        }
                    }
                } else if (key == max) {
                    int m = index(high(key), clusters[high(key)].getMax());
                    maxValue = getValue(m);
                    max = m;
                }
            }
        }

        /**
         * high(x) = floor( x / lowerSquare(universe) )
         *
         * shift is a power of 2 (Math.pow(2,shift) = universeSizeLowerSquare)
         * x / u  =  x >>> shift   (if 2^shift=u)
         */
        private int high(int x) {
            return x >>> shift;
        }

        /**
         * low(x) = x % lowerSquare(universe)
         *
         * x % y = (x & (y − 1))
         */
        private int low(int x) {
            return x & mask;
        }

        /**
         * index(x, y) = x times lowerSquare(universe) + y
         *
         * x times u  =  x << shift    (if 2^shift=u)
         * x + y  =  x | y    (only true if x&y=0  (which is always the case if you have (x << shift) as left operand and (y a mask) as right operand))
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