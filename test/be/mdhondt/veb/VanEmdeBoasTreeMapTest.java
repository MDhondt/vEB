package be.mdhondt.veb;

import org.junit.Test;

import static org.junit.Assert.*;

public class VanEmdeBoasTreeMapTest {

    @Test
    public void size_whenCreated_thenEmpty() {
        VanEmdeBoasTreeMap<TestObject> vEBTree1 = new VanEmdeBoasTreeMap<>(8);
        VanEmdeBoasTreeMap<TestObject> vEBTree2 = new VanEmdeBoasTreeMap<>(16);

        assertEquals(vEBTree1.size(), 0);
        assertEquals(vEBTree2.size(), 0);
    }

    @Test
    public void size_whenContainsElements() throws Exception {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);

        vEBTree.put(1, new TestObject("one"));
        vEBTree.put(2, new TestObject("two"));

        assertEquals(vEBTree.size(), 2);

        vEBTree.put(3, new TestObject("three"));

        assertEquals(vEBTree.size(), 3);

        vEBTree.put(4, new TestObject("four"));
        vEBTree.remove(3);

        assertEquals(vEBTree.size(), 3);

        vEBTree.put(3, new TestObject("three'"));
        vEBTree.put(4, new TestObject("four'"));
        vEBTree.put(5, new TestObject("five"));

        assertEquals(vEBTree.size(), 5);

        vEBTree.remove(3);

        assertEquals(vEBTree.size(), 4);;
    }

    @Test
    public void isEmpty_whenEmpty() throws Exception {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);

        assertTrue(vEBTree.isEmpty());
    }

    @Test
    public void isEmpty_whenNonEmpty() throws Exception {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        vEBTree.put(5, new TestObject("five"));

        assertFalse(vEBTree.isEmpty());
    }

    @Test
    public void isEmpty_whenEmptyAfterRemovals() throws Exception {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        vEBTree.put(5, new TestObject("five"));

        assertFalse(vEBTree.isEmpty());

        vEBTree.remove(5);

        assertTrue(vEBTree.isEmpty());
    }

    @Test
    public void isEmpty_whenNotEverythingRemoved() throws Exception {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        vEBTree.put(5, new TestObject("five"));
        vEBTree.put(6, new TestObject("six"));

        assertFalse(vEBTree.isEmpty());

        vEBTree.remove(5);

        assertFalse(vEBTree.isEmpty());
    }

    @Test
    public void containsKey_whenEmpty() throws Exception {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);

        assertFalse(vEBTree.containsKey(0));
        assertFalse(vEBTree.containsKey(3));
        assertFalse(vEBTree.containsKey(5));
        assertFalse(vEBTree.containsKey(6));
    }

    @Test
    public void containsKey_whenContainsElements() throws Exception {
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
    public void containsKey_whenElementNoLongerContained() throws Exception {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        vEBTree.put(1, new TestObject("one"));

        assertTrue(vEBTree.containsKey(1));

        vEBTree.remove(1);

        assertFalse(vEBTree.containsKey(1));

        vEBTree.put(1, new TestObject("one'"));

        assertTrue(vEBTree.containsKey(1));
    }

    @Test(expected = ClassCastException.class)
    public void containsKey_whenInvalidKeyObject() throws Exception {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);

        vEBTree.containsKey(new TestObject("one"));
    }

    @Test(expected = NullPointerException.class)
    public void containsKey_whenNullKey() throws Exception {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);

        vEBTree.containsKey(null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void containsKey_whenKeyIsNotInUniverse1() throws Exception {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);

        vEBTree.containsKey(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void containsKey_whenKeyIsNotInUniverse2() throws Exception {
        int universeSize = 8;
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(universeSize);

        vEBTree.containsKey(universeSize);
    }

    @Test
    public void containsValue_whenEmpty() throws Exception {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);

        assertFalse(vEBTree.containsValue(new TestObject("one")));
        assertFalse(vEBTree.containsValue(new TestObject("two")));
        assertFalse(vEBTree.containsValue(new TestObject("five")));
        assertFalse(vEBTree.containsValue(new TestObject("six")));
    }

    @Test
    public void containsValue_whenContainsElements() throws Exception {
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
    public void containsValue_whenElementNoLongerContained() throws Exception {
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
    public void containsValue_whenInvalidValueObject() throws Exception {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        TestObject to = new TestObject("one");
        vEBTree.put(1, to);

        assertTrue(vEBTree.containsValue(to));
        assertFalse(vEBTree.containsValue(new Long("12")));
    }

    @Test(expected = NullPointerException.class)
    public void containsValue_whenNullValue() throws Exception {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        TestObject to = new TestObject("one");
        vEBTree.put(1, to);

        assertTrue(vEBTree.containsValue(to));
        vEBTree.containsValue(null);
    }

    @Test
    public void get_whenEmpty() throws Exception {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);

        assertNull(vEBTree.get(0));
        assertNull(vEBTree.get(3));
        assertNull(vEBTree.get(5));
        assertNull(vEBTree.get(6));
    }

    @Test
    public void get_whenContainsElements() throws Exception {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        vEBTree.put(1, new TestObject("one"));
        vEBTree.put(2, new TestObject("two"));
        vEBTree.put(3, new TestObject("three"));

        assertEquals(vEBTree.get(1), new TestObject("one"));
        assertEquals(vEBTree.get(2), new TestObject("two"));
        assertEquals(vEBTree.get(3), new TestObject("three"));
        assertNull(vEBTree.get(4));
        assertNull(vEBTree.get(5));
        assertNull(vEBTree.get(6));
    }

    @Test
    public void get_whenElementNoLongerContained() throws Exception {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        vEBTree.put(1, new TestObject("one"));

        assertEquals(vEBTree.get(1), new TestObject("one"));

        vEBTree.remove(1);

        assertNull(vEBTree.get(1));

        vEBTree.put(1, new TestObject("one"));

        assertEquals(vEBTree.get(1), new TestObject("one"));
    }

    @Test(expected = ClassCastException.class)
    public void get_whenInvalidKeyObject() throws Exception {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);

        vEBTree.get(new TestObject("one"));
    }

    @Test(expected = NullPointerException.class)
    public void get_whenNullKey() throws Exception {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);

        vEBTree.get(null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void get_whenKeyIsNotInUniverse1() throws Exception {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);

        vEBTree.get(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void get_whenKeyIsNotInUniverse2() throws Exception {
        int universeSize = 8;
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(universeSize);

        vEBTree.get(universeSize);
    }

    @Test
    public void put_whenEmpty() throws Exception {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);

        assertNull(vEBTree.put(2, new TestObject("two")));
        assertTrue(vEBTree.containsKey(2));
        assertEquals(vEBTree.get(2), new TestObject("two"));
    }

    @Test
    public void put_whenContainsElements() throws Exception {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        vEBTree.put(2, new TestObject("two"));
        vEBTree.put(3, new TestObject("three"));
        vEBTree.put(4, new TestObject("four"));

        assertTrue(vEBTree.containsKey(2));
        assertEquals(vEBTree.get(2), new TestObject("two"));
        assertTrue(vEBTree.containsKey(3));
        assertEquals(vEBTree.get(3), new TestObject("three"));
        assertTrue(vEBTree.containsKey(4));
        assertEquals(vEBTree.get(4), new TestObject("four"));

        assertNull(vEBTree.put(5, new TestObject("five")));
        assertTrue(vEBTree.containsKey(5));
        assertEquals(vEBTree.get(5), new TestObject("five"));
    }

    @Test
    public void put_whenKeyAlreadyExists() throws Exception {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        vEBTree.put(6, new TestObject("six"));

        assertTrue(vEBTree.containsKey(6));
        assertEquals(vEBTree.put(6, new TestObject("six'")), new TestObject("six"));
    }

    @Test(expected = NullPointerException.class)
    public void put_whenKeyIsNull() throws Exception {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        vEBTree.put(null, new TestObject("null"));
    }

    @Test(expected = NullPointerException.class)
    public void put_whenValueIsNull() throws Exception {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        vEBTree.put(1, null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void put_whenKeyIsNotInUniverse1() throws Exception {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);

        vEBTree.put(-1, new TestObject("minusOne"));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void put_whenKeyIsNotInUniverse2() throws Exception {
        int universeSize = 8;
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(universeSize);

        vEBTree.put(universeSize, new TestObject("tooBig"));
    }
}