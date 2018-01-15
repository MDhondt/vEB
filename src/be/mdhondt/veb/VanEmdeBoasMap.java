package be.mdhondt.veb;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;

import static java.lang.Integer.numberOfTrailingZeros;
import static java.lang.Math.*;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toSet;

public class VanEmdeBoasMap<E> implements NavigableMap<Integer, E> {

    private static final int MIN_UNIVERSE_SIZE = 2;
    private static final int NIL = -1;

    private VEBTree<E> root;

    public VanEmdeBoasMap(int universeSize) {
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

    @Override
    public SortedMap<Integer, E> subMap(Integer fromKey, Integer toKey) {
        return subMap(fromKey, true, toKey, false);
    }

    @Override
    public SortedMap<Integer, E> headMap(Integer toKey) {
        throw new RuntimeException("Not yet implemented!");
    }

    @Override
    public SortedMap<Integer, E> tailMap(Integer fromKey) {
        throw new RuntimeException("Not yet implemented!");
    }

    @Override
    public Comparator<? super Integer> comparator() {
        throw new RuntimeException("Not yet implemented!");
    }

    @Override
    public Integer firstKey() {
        throw new RuntimeException("Not yet implemented!");
    }

    @Override
    public Integer lastKey() {
        throw new RuntimeException("Not yet implemented!");
    }

    @Override
    public Entry<Integer, E> lowerEntry(Integer key) {
        throw new RuntimeException("Not yet implemented!");
    }

    @Override
    public Integer lowerKey(Integer key) {
        throw new RuntimeException("Not yet implemented!");
    }

    @Override
    public Entry<Integer, E> floorEntry(Integer key) {
        throw new RuntimeException("Not yet implemented!");
    }

    @Override
    public Integer floorKey(Integer key) {
        throw new RuntimeException("Not yet implemented!");
    }

    @Override
    public Entry<Integer, E> ceilingEntry(Integer key) {
        throw new RuntimeException("Not yet implemented!");
    }

    @Override
    public Integer ceilingKey(Integer key) {
        throw new RuntimeException("Not yet implemented!");
    }

    @Override
    public Entry<Integer, E> higherEntry(Integer key) {
        throw new RuntimeException("Not yet implemented!");
    }

    @Override
    public Integer higherKey(Integer key) {
        throw new RuntimeException("Not yet implemented!");
    }

    @Override
    public Entry<Integer, E> firstEntry() {
        throw new RuntimeException("Not yet implemented!");
    }

    @Override
    public Entry<Integer, E> lastEntry() {
        throw new RuntimeException("Not yet implemented!");
    }

    @Override
    public Entry<Integer, E> pollFirstEntry() {
        throw new RuntimeException("Not yet implemented!");
    }

    @Override
    public Entry<Integer, E> pollLastEntry() {
        throw new RuntimeException("Not yet implemented!");
    }

    @Override
    public NavigableMap<Integer, E> descendingMap() {
        throw new RuntimeException("Not yet implemented!");
    }

    @Override
    public NavigableSet<Integer> navigableKeySet() {
        throw new RuntimeException("Not yet implemented!");
    }

    @Override
    public NavigableSet<Integer> descendingKeySet() {
        throw new RuntimeException("Not yet implemented!");
    }

    @Override
    public NavigableMap<Integer, E> subMap(Integer fromKey, boolean fromInclusive, Integer toKey, boolean toInclusive) {
        return new AscendingSubMap<>(this, false, fromKey, fromInclusive, false, toKey, toInclusive);
    }

    @Override
    public NavigableMap<Integer, E> headMap(Integer toKey, boolean inclusive) {
        throw new RuntimeException("Not yet implemented!");
    }

    @Override
    public NavigableMap<Integer, E> tailMap(Integer fromKey, boolean inclusive) {
        throw new RuntimeException("Not yet implemented!");
    }

//    public Optional<Integer> getMinimum() {
//        int min = root.getMin();
//        return min != NIL ? of(min) : empty();
//    }
//
//    public Optional<E> getMinimumValue() {
//        int min = root.getMin();
//        return min != NIL ? of(get(min)) : empty();
//    }
//
//    public Optional<Integer> getMaximum() {
//        int max = root.getMax();
//        return max != NIL ? of(max) : empty();
//    }
//
//    public Optional<E> getMaximumValue() {
//        int max = root.getMax();
//        return max != NIL ? of(get(max)) : empty();
//    }
//
//    public Optional<Integer> successor(Integer key) {
//        if (key == null)
//            throw new NullPointerException();
//        if (key < 0 || key > (root.universeSize - 1))
//            throw new IndexOutOfBoundsException(key + " is not an element in the universe [0," + root.universeSize + "[");
//        int successor = root.successor(key);
//        return successor != NIL ? of(successor) : empty();
//    }
//
//    public Optional<E> successorValue(Integer key) {
//        if (key == null)
//            throw new NullPointerException();
//        if (key < 0 || key > (root.universeSize - 1))
//            throw new IndexOutOfBoundsException(key + " is not an element in the universe [0," + root.universeSize + "[");
//        int successor = root.successor(key);
//        return successor != NIL ? of(get(successor)) : empty();
//    }
//
//    public Optional<Integer> predecessor(Integer key) {
//        if (key == null)
//            throw new NullPointerException();
//        if (key < 0 || key > (root.universeSize - 1))
//            throw new IndexOutOfBoundsException(key + " is not an element in the universe [0," + root.universeSize + "[");
//        int predecessor = root.predecessor(key);
//        return predecessor != NIL ? of(predecessor) : empty();
//    }
//
//    public Optional<E> predecessorValue(Integer key) {
//        if (key == null)
//            throw new NullPointerException();
//        if (key < 0 || key > (root.universeSize - 1))
//            throw new IndexOutOfBoundsException(key + " is not an element in the universe [0," + root.universeSize + "[");
//        int predecessor = root.predecessor(key);
//        return predecessor != NIL ? of(get(predecessor)) : empty();
//    }

    static <E> Integer keyOrNull(Entry<Integer, E> e) {
        return (e == null) ? null : e.getKey();
    }

    /**
     * Returns the key corresponding to the specified Entry.
     *
     * @throws NoSuchElementException if the Entry is null
     */
    static <E> Integer key(Entry<Integer, E> e) {
        if (e == null)
            throw new NoSuchElementException();
        return e.getKey();
    }

    private abstract class NavigableSubMap<E> extends AbstractMap<Integer, E> implements NavigableMap<Integer, E> {

        final VanEmdeBoasMap<E> backingMap;
        final int low, high;
        final boolean fromStart, toEnd;
        final boolean lowInclusive, highInclusive;

        NavigableSubMap(VanEmdeBoasMap<E> backingMap, boolean fromStart, Integer low, boolean lowInclusive, boolean toEnd, Integer high, boolean highInclusive) {
            if (!fromStart && !toEnd) {
                if (low > high || low < 0 || high >= VanEmdeBoasMap.this.root.universeSize)
                    throw new IllegalArgumentException();
            }

            this.backingMap = backingMap;
            this.fromStart = fromStart;
            this.low = low;
            this.lowInclusive = lowInclusive;
            this.toEnd = toEnd;
            this.high = high;
            this.highInclusive = highInclusive;
        }

        boolean tooLow(Integer key) {
            if (!fromStart) {
                int c = key.compareTo(low);
                return c < 0 || (c == 0 && !lowInclusive);
            }
            return false;
        }

        boolean tooHigh(Integer key) {
            if (!toEnd) {
                int c = key.compareTo(high);
                return c > 0 || (c == 0 && !highInclusive);
            }
            return false;
        }

        boolean inRange(Integer key) {
            return !tooLow(key) && !tooHigh(key);
        }

        boolean inClosedRange(Integer key) {
            return (fromStart || key >= low) && (toEnd || high >= key);
        }

        boolean inRange(Integer key, boolean inclusive) {
            return inclusive ? inRange(key) : inClosedRange(key);
        }

        Entry<Integer, E> absLowest() {
            Entry<Integer, E> e = (fromStart ?
                                           backingMap.firstEntry() :
                                           (lowInclusive ?
                                                    backingMap.ceilingEntry(low) :
                                                    backingMap.higherEntry(low)));
            return (e == null || tooHigh(e.getKey())) ? null : e;
        }

        Entry<Integer, E> absHighest() {
            Entry<Integer, E> e = (toEnd ?
                                           backingMap.lastEntry() :
                                           (highInclusive ?
                                                    backingMap.floorEntry(high) :
                                                    backingMap.lowerEntry(high)));
            return (e == null || tooLow(e.getKey())) ? null : e;
        }

        Entry<Integer, E> absCeiling(Integer key) {
            if (tooLow(key))
                return absLowest();
            Entry<Integer, E> e = backingMap.ceilingEntry(key);
            return (e == null || tooHigh(e.getKey())) ? null : e;
        }

        Entry<Integer, E> absHigher(Integer key) {
            if (tooLow(key))
                return absLowest();
            Entry<Integer, E> e = backingMap.higherEntry(key);
            return (e == null || tooHigh(e.getKey())) ? null : e;
        }

        Entry<Integer, E> absFloor(Integer key) {
            if (tooHigh(key))
                return absHighest();
            Entry<Integer, E> e = backingMap.floorEntry(key);
            return (e == null || tooLow(e.getKey())) ? null : e;
        }

        Entry<Integer, E> absLower(Integer key) {
            if (tooHigh(key))
                return absHighest();
            Entry<Integer, E> e = backingMap.lowerEntry(key);
            return (e == null || tooLow(e.getKey())) ? null : e;
        }

        Entry<Integer, E> absHighFence() {
            return (toEnd ? null : (highInclusive ?
                                            backingMap.higherEntry(high) :
                                            backingMap.ceilingEntry(high)));
        }

        Entry<Integer, E> absLowFence() {
            return (fromStart ? null : (lowInclusive ?
                                                backingMap.lowerEntry(low) :
                                                backingMap.floorEntry(low)));
        }

        abstract Entry<Integer, E> subLowest();

        abstract Entry<Integer, E> subHighest();

        abstract Entry<Integer, E> subCeiling(Integer key);

        abstract Entry<Integer, E> subHigher(Integer key);

        abstract Entry<Integer, E> subFloor(Integer key);

        abstract Entry<Integer, E> subLower(Integer key);

        public boolean isEmpty() {
            return (fromStart && toEnd) ? backingMap.isEmpty() : entrySet().isEmpty();
        }

        public int size() {
            return (fromStart && toEnd) ? backingMap.size() : entrySet().size();
        }

        public boolean containsKey(Object key) {
            if (key == null)
                throw new NullPointerException();
            if (!(key instanceof Integer))
                throw new ClassCastException();
            Integer integerKey = (Integer) key;
            return inRange(integerKey) && backingMap.containsKey(key);
        }

        public E put(Integer key, E element) {
            if (!inRange(key))
                throw new IllegalArgumentException("key out of range");
            return backingMap.put(key, element);
        }

        public E get(Object key) {
            if (key == null)
                throw new NullPointerException();
            if (!(key instanceof Integer))
                throw new ClassCastException();
            Integer integerKey = (Integer) key;
            return !inRange(integerKey) ? null : backingMap.get(key);
        }

        public E remove(Object key) {
            if (key == null)
                throw new NullPointerException();
            if (!(key instanceof Integer))
                throw new ClassCastException();
            Integer integerKey = (Integer) key;
            return !inRange(integerKey) ? null : backingMap.remove(key);
        }

        @Override
        public Entry<Integer, E> ceilingEntry(Integer key) {
            return subCeiling(key);
        }

        @Override
        public Integer ceilingKey(Integer key) {
            return keyOrNull(subCeiling(key));
        }

        @Override
        public Entry<Integer, E> higherEntry(Integer key) {
            return subHigher(key);
        }

        @Override
        public Integer higherKey(Integer key) {
            return keyOrNull(subHigher(key));
        }

        @Override
        public Entry<Integer, E> floorEntry(Integer key) {
            return subFloor(key);
        }

        @Override
        public Integer floorKey(Integer key) {
            return keyOrNull(subFloor(key));
        }

        @Override
        public Entry<Integer, E> lowerEntry(Integer key) {
            return subLower(key);
        }

        @Override
        public Integer lowerKey(Integer key) {
            return keyOrNull(subLower(key));
        }

        @Override
        public Integer firstKey() {
            return key(subLowest());
        }

        @Override
        public Integer lastKey() {
            return key(subHighest());
        }

        @Override
        public Entry<Integer, E> firstEntry() {
            return subLowest();
        }

        @Override
        public Entry<Integer, E> lastEntry() {
            return subHighest();
        }

        @Override
        public Entry<Integer, E> pollFirstEntry() {
            Entry<Integer, E> e = subLowest();
            if (e != null)
                backingMap.remove(e.getKey());
            return e;
        }

        @Override
        public Entry<Integer, E> pollLastEntry() {
            Entry<Integer, E> e = subHighest();
            if (e != null)
                backingMap.remove(e.getKey());
            return e;
        }

        @Override
        public Set<Entry<Integer, E>> entrySet() {
            return backingMap.entrySet().stream().filter(entry -> inRange(entry.getKey())).collect(toSet());
        }

        @Override
        public NavigableSet<Integer> navigableKeySet() {
            return backingMap.keySet().stream().filter(this::inRange).collect(toCollection(TreeSet::new));
        }

        public final Set<Integer> keySet() {
            return navigableKeySet();
        }

        @Override
        public NavigableSet<Integer> descendingKeySet() {
            return descendingMap().navigableKeySet();
        }

        @Override
        public final SortedMap<Integer, E> subMap(Integer fromKey, Integer toKey) {
            return subMap(fromKey, true, toKey, false);
        }

        @Override
        public final SortedMap<Integer, E> headMap(Integer toKey) {
            return headMap(toKey, false);
        }

        @Override
        public final SortedMap<Integer, E> tailMap(Integer fromKey) {
            return tailMap(fromKey, true);
        }

        @Override
        public Comparator<? super Integer> comparator() {
            return null;
        }
    }

    private class AscendingSubMap<E> extends NavigableSubMap<E> {

        AscendingSubMap(VanEmdeBoasMap<E> backingMap, boolean fromStart, Integer low, boolean lowInclusive, boolean toEnd, Integer high, boolean highInclusive) {
            super(backingMap, fromStart, low, lowInclusive, toEnd, high, highInclusive);
        }

        @Override
        public NavigableMap<Integer, E> subMap(Integer fromKey, boolean fromInclusive, Integer toKey, boolean toInclusive) {
            if (!inRange(fromKey, fromInclusive))
                throw new IllegalArgumentException("fromKey out of range");
            if (!inRange(toKey, toInclusive))
                throw new IllegalArgumentException("toKey out of range");
            return new AscendingSubMap<>(backingMap, false, fromKey, fromInclusive, false, toKey, toInclusive);
        }

        @Override
        public NavigableMap<Integer, E> headMap(Integer toKey, boolean inclusive) {
            if (!inRange(toKey, inclusive))
                throw new IllegalArgumentException("toKey out of range");
            return new AscendingSubMap<>(backingMap, fromStart, low, lowInclusive, false, toKey, inclusive);
        }

        @Override
        public NavigableMap<Integer, E> tailMap(Integer fromKey, boolean inclusive) {
            if (!inRange(fromKey, inclusive))
                throw new IllegalArgumentException("fromKey out of range");
            return new AscendingSubMap<>(backingMap, false, fromKey, inclusive, toEnd, high, highInclusive);
        }

        @Override
        public NavigableMap<Integer, E> descendingMap() {
            return new DescendingSubMap<>(backingMap, fromStart, low, lowInclusive, toEnd, high, highInclusive);
        }

        @Override
        Entry<Integer, E> subLowest() {
            return absLowest();
        }

        @Override
        Entry<Integer, E> subHighest() {
            return absHighest();
        }

        @Override
        Entry<Integer, E> subCeiling(Integer key) {
            return absCeiling(key);
        }

        @Override
        Entry<Integer, E> subHigher(Integer key) {
            return absHigher(key);
        }

        @Override
        Entry<Integer, E> subFloor(Integer key) {
            return absFloor(key);
        }

        @Override
        Entry<Integer, E> subLower(Integer key) {
            return absLower(key);
        }
    }

    private class DescendingSubMap<E> extends NavigableSubMap<E> {

        DescendingSubMap(VanEmdeBoasMap<E> backingMap, boolean fromStart, Integer low, boolean lowInclusive, boolean toEnd, Integer high, boolean highInclusive) {
            super(backingMap, fromStart, low, lowInclusive, toEnd, high, highInclusive);
        }

        public NavigableMap<Integer, E> subMap(Integer fromKey, boolean fromInclusive, Integer toKey, boolean toInclusive) {
            if (!inRange(fromKey, fromInclusive))
                throw new IllegalArgumentException("fromKey out of range");
            if (!inRange(toKey, toInclusive))
                throw new IllegalArgumentException("toKey out of range");
            return new DescendingSubMap<>(backingMap, false, toKey, toInclusive, false, fromKey, fromInclusive);
        }

        public NavigableMap<Integer, E> headMap(Integer toKey, boolean inclusive) {
            if (!inRange(toKey, inclusive))
                throw new IllegalArgumentException("toKey out of range");
            return new DescendingSubMap<>(backingMap, false, toKey, inclusive, toEnd, high, highInclusive);
        }

        public NavigableMap<Integer, E> tailMap(Integer fromKey, boolean inclusive) {
            if (!inRange(fromKey, inclusive))
                throw new IllegalArgumentException("fromKey out of range");
            return new DescendingSubMap<>(backingMap, fromStart, low, lowInclusive, false, fromKey, inclusive);
        }

        public NavigableMap<Integer, E> descendingMap() {
            return new AscendingSubMap<>(backingMap, fromStart, low, lowInclusive, toEnd, high, highInclusive);
        }

        Entry<Integer, E> subLowest() {
            return absHighest();
        }

        Entry<Integer, E> subHighest() {
            return absLowest();
        }

        Entry<Integer, E> subCeiling(Integer key) {
            return absFloor(key);
        }

        Entry<Integer, E> subHigher(Integer key) {
            return absLower(key);
        }

        Entry<Integer, E> subFloor(Integer key) {
            return absCeiling(key);
        }

        Entry<Integer, E> subLower(Integer key) {
            return absHigher(key);
        }
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