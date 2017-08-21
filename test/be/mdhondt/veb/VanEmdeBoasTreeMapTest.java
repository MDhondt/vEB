package be.mdhondt.veb;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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

    private class TestObject {

        private String content;

        TestObject(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "TestObject: " + content;
        }
    }
}