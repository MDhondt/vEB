package be.mdhondt.veb;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.AbstractMap.SimpleEntry;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

public class VanEmdeBoasTreeMapTest {

    @Test
    public void size_whenCreated_thenEmpty() {
        VanEmdeBoasMap<TestObject> vEBTree1 = new VanEmdeBoasMap<>(8);
        VanEmdeBoasMap<TestObject> vEBTree2 = new VanEmdeBoasMap<>(16);

        assertEquals(0, vEBTree1.size());
        assertEquals(0, vEBTree2.size());
    }

    @Test
    public void size_whenContainsElements() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);

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
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);

        assertTrue(vEBTree.isEmpty());
    }

    @Test
    public void isEmpty_whenNonEmpty() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);
        vEBTree.put(5, new TestObject("five"));

        assertFalse(vEBTree.isEmpty());
    }

    @Test
    public void isEmpty_whenEmptyAfterRemovals() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);
        vEBTree.put(5, new TestObject("five"));

        assertFalse(vEBTree.isEmpty());

        vEBTree.remove(5);

        assertTrue(vEBTree.isEmpty());
    }

    @Test
    public void isEmpty_whenNotEverythingRemoved() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);
        vEBTree.put(5, new TestObject("five"));
        vEBTree.put(6, new TestObject("six"));

        assertFalse(vEBTree.isEmpty());

        vEBTree.remove(5);

        assertFalse(vEBTree.isEmpty());
    }

    @Test
    public void containsKey_whenEmpty() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);

        assertFalse(vEBTree.containsKey(0));
        assertFalse(vEBTree.containsKey(3));
        assertFalse(vEBTree.containsKey(5));
        assertFalse(vEBTree.containsKey(6));
    }

    @Test
    public void containsKey_whenContainsElements() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);
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
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);
        vEBTree.put(1, new TestObject("one"));

        assertTrue(vEBTree.containsKey(1));

        vEBTree.remove(1);

        assertFalse(vEBTree.containsKey(1));

        vEBTree.put(1, new TestObject("one'"));

        assertTrue(vEBTree.containsKey(1));
    }

    @Test
    public void containsKey_whenInvalidKeyObject() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);

        Executable codeToTest = () -> vEBTree.containsKey(new TestObject("one"));

        assertThrows(ClassCastException.class, codeToTest);
    }

    @Test
    public void containsKey_whenNullKey() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);

        Executable codeToTest = () -> vEBTree.containsKey(null);

        assertThrows(NullPointerException.class, codeToTest);
    }

    @Test
    public void containsKey_whenKeyIsNotInUniverse1() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);

        Executable codeToTest = () -> vEBTree.containsKey(-1);

        assertThrows(IndexOutOfBoundsException.class, codeToTest);
    }

    @Test
    public void containsKey_whenKeyIsNotInUniverse2() {
        int universeSize = 8;
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(universeSize);

        Executable codeToTest = () -> vEBTree.containsKey(universeSize);

        assertThrows(IndexOutOfBoundsException.class, codeToTest);
    }

    @Test
    public void containsValue_whenEmpty() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);

        assertFalse(vEBTree.containsValue(new TestObject("one")));
        assertFalse(vEBTree.containsValue(new TestObject("two")));
        assertFalse(vEBTree.containsValue(new TestObject("five")));
        assertFalse(vEBTree.containsValue(new TestObject("six")));
    }

    @Test
    public void containsValue_whenContainsElements() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);
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
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);
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
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);
        TestObject to = new TestObject("one");
        vEBTree.put(1, to);

        assertTrue(vEBTree.containsValue(to));
        assertFalse(vEBTree.containsValue(new Long("12")));
    }

    @Test
    public void containsValue_whenNullValue() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);
        TestObject to = new TestObject("one");
        vEBTree.put(1, to);

        assertTrue(vEBTree.containsValue(to));
        Executable codeToTest = () -> vEBTree.containsValue(null);
        assertThrows(NullPointerException.class, codeToTest);
    }

    @Test
    public void get_whenEmpty() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);

        assertNull(vEBTree.get(0));
        assertNull(vEBTree.get(3));
        assertNull(vEBTree.get(5));
        assertNull(vEBTree.get(6));
    }

    @Test
    public void get_whenContainsElements() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);
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
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);
        vEBTree.put(1, new TestObject("one"));

        assertEquals(new TestObject("one"), vEBTree.get(1));

        vEBTree.remove(1);

        assertNull(vEBTree.get(1));

        vEBTree.put(1, new TestObject("one"));

        assertEquals(new TestObject("one"), vEBTree.get(1));
    }

    @Test
    public void get_whenInvalidKeyObject() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);

        Executable codeToTest = () -> vEBTree.get(new TestObject("one"));

        assertThrows(ClassCastException.class, codeToTest);
    }

    @Test
    public void get_whenNullKey() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);

        Executable codeToTest = () -> vEBTree.get(null);

        assertThrows(NullPointerException.class, codeToTest);
    }

    @Test
    public void get_whenKeyIsNotInUniverse1() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);

        Executable codeToTest = () -> vEBTree.get(-1);

        assertThrows(IndexOutOfBoundsException.class, codeToTest);
    }

    @Test
    public void get_whenKeyIsNotInUniverse2() {
        int universeSize = 8;
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(universeSize);

        Executable codeToTest = () -> vEBTree.get(universeSize);

        assertThrows(IndexOutOfBoundsException.class, codeToTest);
    }

    @Test
    public void put_whenEmpty() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);

        assertNull(vEBTree.put(2, new TestObject("two")));
        assertTrue(vEBTree.containsKey(2));
        assertEquals(new TestObject("two"), vEBTree.get(2));
    }

    @Test
    public void put_whenContainsElements() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);
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
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);
        vEBTree.put(6, new TestObject("six"));

        assertTrue(vEBTree.containsKey(6));
        assertEquals(new TestObject("six"), vEBTree.put(6, new TestObject("six'")));
    }

    @Test
    public void put_whenKeyIsNull() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);

        Executable codeToTest = () -> vEBTree.put(null, new TestObject("null"));

        assertThrows(NullPointerException.class, codeToTest);
    }

    @Test
    public void put_whenValueIsNull() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);

        Executable codeToTest = () -> vEBTree.put(1, null);

        assertThrows(NullPointerException.class, codeToTest);
    }

    @Test
    public void put_whenKeyIsNotInUniverse1() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);

        Executable codeToTest = () -> vEBTree.put(-1, new TestObject("minusOne"));

        assertThrows(IndexOutOfBoundsException.class, codeToTest);
    }

    @Test
    public void put_whenKeyIsNotInUniverse2() {
        int universeSize = 8;
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(universeSize);

        Executable codeToTest = () -> vEBTree.put(universeSize, new TestObject("tooBig"));

        assertThrows(IndexOutOfBoundsException.class, codeToTest);
    }

    @Test
    public void remove_whenEmpty() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);

        assertNull(vEBTree.remove(0));
        assertNull(vEBTree.remove(3));
        assertNull(vEBTree.remove(5));
        assertNull(vEBTree.remove(6));
    }

    @Test
    public void remove_whenContainsElements() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(16);
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
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);
        vEBTree.put(1, new TestObject("one"));

        assertTrue(vEBTree.containsKey(1));
        assertTrue(vEBTree.containsValue(new TestObject("one")));

        vEBTree.remove(1);

        assertFalse(vEBTree.containsKey(1));
        assertFalse(vEBTree.containsValue(new TestObject("one")));
        assertNull(vEBTree.get(1));
    }

    @Test
    public void remove_whenInvalidKeyObject() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);
        vEBTree.put(1, new TestObject("one"));

        Executable codeToTest = () -> vEBTree.remove(new TestObject("one"));

        assertThrows(ClassCastException.class, codeToTest);
    }

    @Test
    public void remove_whenNullKey() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);

        Executable codeToTest = () -> vEBTree.remove(null);

        assertThrows(NullPointerException.class, codeToTest);
    }

    @Test
    public void remove_whenKeyIsNotInUniverse1() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);

        Executable codeToTest = () -> vEBTree.remove(-1);

        assertThrows(IndexOutOfBoundsException.class, codeToTest);
    }

    @Test
    public void remove_whenKeyIsNotInUniverse2() {
        int universeSize = 8;
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(universeSize);

        Executable codeToTest = () -> vEBTree.remove(universeSize);

        assertThrows(IndexOutOfBoundsException.class, codeToTest);
    }

    @Test
    public void clear_whenEmpty() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);

        assertEquals(0, vEBTree.size());

        vEBTree.clear();

        assertEquals(0, vEBTree.size());
    }

    @Test
    public void clear_whenContainsElements() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);
        vEBTree.put(1, new TestObject("one"));
        vEBTree.put(2, new TestObject("two"));
        vEBTree.put(3, new TestObject("three"));

        assertEquals(3, vEBTree.size());

        vEBTree.clear();

        assertEquals(0, vEBTree.size());
    }

    @Test
    public void clear_whenFull() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(4);
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
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);

        assertEquals(0, vEBTree.size());
        assertEquals(0, vEBTree.keySet().size());
        assertEquals(0, vEBTree.values().size());
        assertEquals(0, vEBTree.entrySet().size());
    }

    @Test
    public void keysValuesAndEntries_whenContainsElements() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);
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
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(4);
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