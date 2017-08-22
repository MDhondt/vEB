package be.mdhondt.veb;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

        assertFalse(vEBTree.containsKey(1));
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

    @Test
    public void containsKey_whenInvalidKeyObject() throws Exception {
        VanEmdeBoasTreeMap<TestObject> vEBTree = new VanEmdeBoasTreeMap<>(8);
        vEBTree.put(1, new TestObject("one"));

        assertTrue(vEBTree.containsKey(1));
        assertFalse(vEBTree.containsKey(new Object()));
        assertFalse(vEBTree.containsKey(new TestObject("one")));
        assertFalse(vEBTree.containsKey(null));
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
        vEBTree.put(1, new TestObject("one"));

        assertTrue(vEBTree.containsValue(new TestObject("one")));
        assertFalse(vEBTree.containsValue(new Object()));
        assertFalse(vEBTree.containsValue(1));
        assertFalse(vEBTree.containsValue(new TestObject("one'")));
        assertFalse(vEBTree.containsValue(null));
    }
}