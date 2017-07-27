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
        private int min;
        private int max;
        private E minValue;
        private E maxValue;
        private VEBTree<E> summary;
        private VEBTree<E>[] clusters;
        private int shift, mask;

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
                for (int i = 0; i < universeSizeUpperSquare; i++)
                    clusters[i] = new VEBTree<>(universeSizeLowerSquare);
            }
        }

        private int high(int x) {
            return x >>> shift;
        }

        private int low(int x) {
            return x & mask;
        }

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