package be.mdhondt.veb;

import org.junit.Test;

import java.util.AbstractMap.SimpleEntry;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

public class VanEmdeBoasTreeMapTest {

    @Test
    public void size_whenCreated_thenEmpty() {
        VanEmdeBoasTreeMap<TestObject> vEBTree1 = new VanEmdeBoasTreeMap<>(8);
        VanEmdeBoasTreeMap<TestObject> vEBTree2 = new VanEmdeBoasTreeMap<>(16);

        assertEquals(0, vEBTree1.size());
        assertEquals(0, vEBTree2.size());
    }

    @Test
    public void size_whenContainsElements() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);

        vEBTree.put(1, new TestObject("one"));
        vEBTree.put(2, new TestObject("two"));

        assertEquals(2, vEBTree.size());

        vEBTree.put(3, new TestObject("three"));

        assertEquals(3, vEBTree.size());

        vEBTree.put(4, new TestObject("four"));
        vEBTree.remove(3);

        assertEquals(3, vEBTree.size());

        vEBTree.put(3, new TestObject("three'"));
        vEBTree.put(4, new TestObject("four'"));
        vEBTree.put(5, new TestObject("five"));

        assertEquals(5, vEBTree.size());

        vEBTree.remove(3);

        assertEquals(4, vEBTree.size());
    }

    @Test
    public void isEmpty_whenEmpty() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);

        assertTrue(vEBTree.isEmpty());
    }

    @Test
    public void isEmpty_whenNonEmpty() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        vEBTree.put(5, new TestObject("five"));

        assertFalse(vEBTree.isEmpty());
    }

    @Test
    public void isEmpty_whenEmptyAfterRemovals() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        vEBTree.put(5, new TestObject("five"));

        assertFalse(vEBTree.isEmpty());

        vEBTree.remove(5);

        assertTrue(vEBTree.isEmpty());
    }

    @Test
    public void isEmpty_whenNotEverythingRemoved() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        vEBTree.put(5, new TestObject("five"));
        vEBTree.put(6, new TestObject("six"));

        assertFalse(vEBTree.isEmpty());

        vEBTree.remove(5);

        assertFalse(vEBTree.isEmpty());
    }

    @Test
    public void containsKey_whenEmpty() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);

        assertFalse(vEBTree.containsKey(0));
        assertFalse(vEBTree.containsKey(3));
        assertFalse(vEBTree.containsKey(5));
        assertFalse(vEBTree.containsKey(6));
    }

    @Test
    public void containsKey_whenContainsElements() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        vEBTree.put(1, new TestObject("one"));
        vEBTree.put(2, new TestObject("two"));
        vEBTree.put(3, new TestObject("three"));

        assertTrue(vEBTree.containsKey(1));
        assertTrue(vEBTree.containsKey(2));
        assertTrue(vEBTree.containsKey(3));
        assertFalse(vEBTree.containsKey(4));
        assertFalse(vEBTree.containsKey(5));
        assertFalse(vEBTree.containsKey(6));
    }

    @Test
    public void containsKey_whenElementNoLongerContained() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        vEBTree.put(1, new TestObject("one"));

        assertTrue(vEBTree.containsKey(1));

        vEBTree.remove(1);

        assertFalse(vEBTree.containsKey(1));

        vEBTree.put(1, new TestObject("one'"));

        assertTrue(vEBTree.containsKey(1));
    }

    @Test(expected = ClassCastException.class)
    public void containsKey_whenInvalidKeyObject() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);

        vEBTree.containsKey(new TestObject("one"));
    }

    @Test(expected = NullPointerException.class)
    public void containsKey_whenNullKey() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);

        vEBTree.containsKey(null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void containsKey_whenKeyIsNotInUniverse1() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);

        vEBTree.containsKey(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void containsKey_whenKeyIsNotInUniverse2() {
        int universeSize = 8;
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(universeSize);

        vEBTree.containsKey(universeSize);
    }

    @Test
    public void containsValue_whenEmpty() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);

        assertFalse(vEBTree.containsValue(new TestObject("one")));
        assertFalse(vEBTree.containsValue(new TestObject("two")));
        assertFalse(vEBTree.containsValue(new TestObject("five")));
        assertFalse(vEBTree.containsValue(new TestObject("six")));
    }

    @Test
    public void containsValue_whenContainsElements() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        vEBTree.put(1, new TestObject("one"));
        vEBTree.put(2, new TestObject("two"));
        vEBTree.put(3, new TestObject("three"));

        assertTrue(vEBTree.containsValue(new TestObject("one")));
        assertTrue(vEBTree.containsValue(new TestObject("two")));
        assertTrue(vEBTree.containsValue(new TestObject("three")));
        assertFalse(vEBTree.containsValue(new TestObject("four")));
        assertFalse(vEBTree.containsValue(new TestObject("five")));
        assertFalse(vEBTree.containsValue(new TestObject("six")));
    }

    @Test
    public void containsValue_whenElementNoLongerContained() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        TestObject to = new TestObject("one");
        vEBTree.put(1, to);

        assertTrue(vEBTree.containsValue(to));

        vEBTree.remove(1);

        assertFalse(vEBTree.containsValue(to));

        vEBTree.put(1, to);

        assertTrue(vEBTree.containsValue(to));
    }

    @Test
    public void containsValue_whenInvalidValueObject() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        TestObject to = new TestObject("one");
        vEBTree.put(1, to);

        assertTrue(vEBTree.containsValue(to));
        assertFalse(vEBTree.containsValue(new Long("12")));
    }

    @Test(expected = NullPointerException.class)
    public void containsValue_whenNullValue() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        TestObject to = new TestObject("one");
        vEBTree.put(1, to);

        assertTrue(vEBTree.containsValue(to));
        vEBTree.containsValue(null);
    }

    @Test
    public void get_whenEmpty() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);

        assertNull(vEBTree.get(0));
        assertNull(vEBTree.get(3));
        assertNull(vEBTree.get(5));
        assertNull(vEBTree.get(6));
    }

    @Test
    public void get_whenContainsElements() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        vEBTree.put(1, new TestObject("one"));
        vEBTree.put(2, new TestObject("two"));
        vEBTree.put(3, new TestObject("three"));

        assertEquals(new TestObject("one"), vEBTree.get(1));
        assertEquals(new TestObject("two"), vEBTree.get(2));
        assertEquals(new TestObject("three"), vEBTree.get(3));
        assertNull(vEBTree.get(4));
        assertNull(vEBTree.get(5));
        assertNull(vEBTree.get(6));
    }

    @Test
    public void get_whenElementNoLongerContained() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        vEBTree.put(1, new TestObject("one"));

        assertEquals(new TestObject("one"), vEBTree.get(1));

        vEBTree.remove(1);

        assertNull(vEBTree.get(1));

        vEBTree.put(1, new TestObject("one"));

        assertEquals(new TestObject("one"), vEBTree.get(1));
    }

    @Test(expected = ClassCastException.class)
    public void get_whenInvalidKeyObject() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);

        vEBTree.get(new TestObject("one"));
    }

    @Test(expected = NullPointerException.class)
    public void get_whenNullKey() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);

        vEBTree.get(null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void get_whenKeyIsNotInUniverse1() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);

        vEBTree.get(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void get_whenKeyIsNotInUniverse2() {
        int universeSize = 8;
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(universeSize);

        vEBTree.get(universeSize);
    }

    @Test
    public void put_whenEmpty() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);

        assertNull(vEBTree.put(2, new TestObject("two")));
        assertTrue(vEBTree.containsKey(2));
        assertEquals(new TestObject("two"), vEBTree.get(2));
    }

    @Test
    public void put_whenContainsElements() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        vEBTree.put(2, new TestObject("two"));
        vEBTree.put(3, new TestObject("three"));
        vEBTree.put(4, new TestObject("four"));

        assertTrue(vEBTree.containsKey(2));
        assertEquals(new TestObject("two"), vEBTree.get(2));
        assertTrue(vEBTree.containsKey(3));
        assertEquals(new TestObject("three"), vEBTree.get(3));
        assertTrue(vEBTree.containsKey(4));
        assertEquals(new TestObject("four"), vEBTree.get(4));

        assertNull(vEBTree.put(5, new TestObject("five")));
        assertTrue(vEBTree.containsKey(5));
        assertEquals(new TestObject("five"), vEBTree.get(5));
    }

    @Test
    public void put_whenKeyAlreadyExists() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        vEBTree.put(6, new TestObject("six"));

        assertTrue(vEBTree.containsKey(6));
        assertEquals(new TestObject("six"), vEBTree.put(6, new TestObject("six'")));
    }

    @Test(expected = NullPointerException.class)
    public void put_whenKeyIsNull() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        vEBTree.put(null, new TestObject("null"));
    }

    @Test(expected = NullPointerException.class)
    public void put_whenValueIsNull() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        vEBTree.put(1, null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void put_whenKeyIsNotInUniverse1() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);

        vEBTree.put(-1, new TestObject("minusOne"));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void put_whenKeyIsNotInUniverse2() {
        int universeSize = 8;
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(universeSize);

        vEBTree.put(universeSize, new TestObject("tooBig"));
    }

    @Test
    public void remove_whenEmpty() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);

        assertNull(vEBTree.remove(0));
        assertNull(vEBTree.remove(3));
        assertNull(vEBTree.remove(5));
        assertNull(vEBTree.remove(6));
    }

    @Test
    public void remove_whenContainsElements() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(16);
        vEBTree.put(1, new TestObject("one"));
        vEBTree.put(2, new TestObject("two"));
        vEBTree.put(3, new TestObject("three"));

        assertEquals(new TestObject("one"), vEBTree.remove(1));
        assertEquals(new TestObject("two"), vEBTree.remove(2));
        assertEquals(new TestObject("three"), vEBTree.remove(3));
        assertNull(vEBTree.get(4));
        assertNull(vEBTree.get(5));
        assertNull(vEBTree.get(6));
    }

    @Test
    public void remove_whenElementNoLongerContained() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        vEBTree.put(1, new TestObject("one"));

        assertTrue(vEBTree.containsKey(1));
        assertTrue(vEBTree.containsValue(new TestObject("one")));

        vEBTree.remove(1);

        assertFalse(vEBTree.containsKey(1));
        assertFalse(vEBTree.containsValue(new TestObject("one")));
        assertNull(vEBTree.get(1));
    }

    @Test(expected = ClassCastException.class)
    public void remove_whenInvalidKeyObject() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        vEBTree.put(1, new TestObject("one"));

        vEBTree.remove(new TestObject("one"));
    }

    @Test(expected = NullPointerException.class)
    public void remove_whenNullKey() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);

        vEBTree.remove(null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void remove_whenKeyIsNotInUniverse1() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);

        vEBTree.remove(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void remove_whenKeyIsNotInUniverse2() {
        int universeSize = 8;
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(universeSize);

        vEBTree.remove(universeSize);
    }

    @Test
    public void clear_whenEmpty() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);

        assertEquals(0, vEBTree.size());

        vEBTree.clear();

        assertEquals(0, vEBTree.size());
    }

    @Test
    public void clear_whenContainsElements() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        vEBTree.put(1, new TestObject("one"));
        vEBTree.put(2, new TestObject("two"));
        vEBTree.put(3, new TestObject("three"));

        assertEquals(3, vEBTree.size());

        vEBTree.clear();

        assertEquals(0, vEBTree.size());
    }

    @Test
    public void clear_whenFull() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(4);
        vEBTree.put(0, new TestObject("zero"));
        vEBTree.put(1, new TestObject("one"));
        vEBTree.put(2, new TestObject("two"));
        vEBTree.put(3, new TestObject("three"));

        assertEquals(4, vEBTree.size());

        vEBTree.clear();

        assertEquals(0, vEBTree.size());
    }

    @Test
    public void keysValuesAndEntries_whenEmpty() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);

        assertEquals(0, vEBTree.size());
        assertEquals(0, vEBTree.keySet().size());
        assertEquals(0, vEBTree.values().size());
        assertEquals(0, vEBTree.entrySet().size());
    }

    @Test
    public void keysValuesAndEntries_whenContainsElements() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        vEBTree.put(1, new TestObject("one"));
        vEBTree.put(2, new TestObject("two"));
        vEBTree.put(3, new TestObject("three"));

        assertEquals(3, vEBTree.size());
        assertEquals(3, vEBTree.keySet().size());
        assertTrue(vEBTree.keySet().containsAll(asList(1, 2, 3)));
        assertEquals(3, vEBTree.values().size());
        assertTrue(vEBTree.values().containsAll(asList(new TestObject("one"), new TestObject("two"), new TestObject("three"))));
        assertEquals(3, vEBTree.entrySet().size());
        assertTrue(vEBTree.entrySet().containsAll(asList(new SimpleEntry<>(1, new TestObject("one")), new SimpleEntry<>(2, new TestObject("two")), new SimpleEntry<>(3, new TestObject("three")))));
    }

    @Test
    public void keysValuesAndEntries_whenFull() {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(4);
        vEBTree.put(0, new TestObject("zero"));
        vEBTree.put(1, new TestObject("one"));
        vEBTree.put(2, new TestObject("two"));
        vEBTree.put(3, new TestObject("three"));

        assertEquals(4, vEBTree.size());
        assertEquals(4, vEBTree.keySet().size());
        assertTrue(vEBTree.keySet().containsAll(asList(0, 1, 2, 3)));
        assertEquals(4, vEBTree.values().size());
        assertTrue(vEBTree.values().containsAll(asList(new TestObject("zero"), new TestObject("one"), new TestObject("two"), new TestObject("three"))));
        assertEquals(4, vEBTree.entrySet().size());
        assertTrue(vEBTree.entrySet().containsAll(asList(new SimpleEntry<>(0, new TestObject("zero")), new SimpleEntry<>(1, new TestObject("one")), new SimpleEntry<>(2, new TestObject("two")), new SimpleEntry<>(3, new TestObject("three")))));
    }
}