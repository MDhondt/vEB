package be.mdhondt.veb;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static java.lang.Integer.numberOfTrailingZeros;
import static java.lang.Math.*;

public class VanEmdeBoasTreeMap<E> implements Map<Integer, E> {

    private static final int MIN_UNIVERSE_SIZE = 2;
    private static final int NIL = -1;

    @Override
    public int size() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsKey(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E get(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E put(Integer key, E value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends E> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Integer> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<E> values() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Entry<Integer, E>> entrySet() {
        throw new UnsupportedOperationException();
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
                int universeSizeUpperSquare = upperSqure(universeSize);
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

        private boolean contains(int k) {
            if (k == min || k == max) return true;
            if (universeSize == 2) return false;
            return clusters[high(k)].contains(low(k));
        }

        private int successor(int k) {
            if (universeSize == 2)
                if (k == 0 && max == 1) return 1;
                else return NIL;
            if (min != NIL && k < min) return min;
            int maxLow = clusters[high(k)].getMax();
            if (maxLow != NIL && low(k) < maxLow) return index(high(k), clusters[high(k)].successor(low(k)));
            int successorCluster = summary.successor(high(k));
            if (successorCluster == NIL) return NIL;
            return index(successorCluster, clusters[successorCluster].getMin());
        }

        private int predecessor(int k) {
            if (universeSize == 2)
                if (min == NIL) return NIL;
                else if (k == 1 && min == 0) return 0;
                else return NIL;
            if (max != NIL && k > max) return max;
            int minLow = clusters[high(k)].getMin();
            if (minLow != NIL && low(k) > minLow) return index(high(k), clusters[high(k)].predecessor(low(k)));
            int predecessorCluster = summary.predecessor(high(k));
            if (predecessorCluster == NIL) {
                if (min != NIL && k > min) return min;
                return NIL;
            }
            return index(predecessorCluster, clusters[predecessorCluster].getMax());
        }

        private void emptyInsert(int k) {
            min = k;
            max = k;
        }

        private void insert(int k) {
            if (min == NIL)
                emptyInsert(k);
            else {
                if (k < min) {
                    int tmp = k;
                    k = min;
                    min = tmp;
                }
                if (universeSize > 2) {
                    if (clusters[high(k)].getMin() == NIL) {
                        summary.insert(high(k));
                        clusters[high(k)].emptyInsert(low(k));
                    } else
                        clusters[high(k)].insert(low(k));
                }
                if (k > max) max = k;
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
    private static int upperSqure(int i) {
        return (int) pow(2.0, ceil((log(i) / log(2.0)) / 2.0));
    }
}