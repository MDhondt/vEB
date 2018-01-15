package be.mdhondt.veb;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import static java.lang.reflect.Modifier.isPublic;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

public class VanEmdeBoasSortedMapTest {

    private static final TestObject to0 = new TestObject("zero");
    private static final TestObject to1 = new TestObject("one");
    private static final TestObject to2 = new TestObject("two");
    private static final TestObject to3 = new TestObject("three");
    private static final TestObject to4 = new TestObject("four");
    private static final TestObject to5 = new TestObject("five");
    private static final TestObject to6 = new TestObject("six");
    private static final TestObject to7 = new TestObject("seven");

    @Test
    public void subMap_whenFromToRangeEnclosesAllEntries() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);
        vEBTree.put(2, to2);
        vEBTree.put(3, to3);
        vEBTree.put(4, to4);
        vEBTree.put(5, to5);

        SortedMap<Integer, TestObject> actual = vEBTree.subMap(1, 7);
        assertFalse(actual.containsKey(0));
        assertFalse(actual.containsKey(1));
        assertTrue(actual.get(2).equals(to2));
        assertTrue(actual.get(3).equals(to3));
        assertTrue(actual.get(4).equals(to4));
        assertTrue(actual.get(5).equals(to5));
        assertFalse(actual.containsKey(6));
        assertFalse(actual.containsKey(7));
    }

    @Test
    public void subMap_whenFromKeyIsGreaterThanExistingMinimumKeyAndToKeyIsLessThanExistingMaximumKey() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);
        vEBTree.put(2, to2);
        vEBTree.put(3, to3);
        vEBTree.put(4, to4);
        vEBTree.put(5, to5);
        vEBTree.put(6, to6);
        vEBTree.put(7, to7);

        SortedMap<Integer, TestObject> actual = vEBTree.subMap(3, 6);
        assertFalse(actual.containsKey(0));
        assertFalse(actual.containsKey(1));
        assertFalse(actual.containsKey(2));
        assertTrue(actual.get(3).equals(to3));
        assertTrue(actual.get(4).equals(to4));
        assertTrue(actual.get(5).equals(to5));
        assertFalse(actual.containsKey(6));
        assertFalse(actual.containsKey(7));
    }

    @Test
    public void subMap_whenFromKeyIsGreaterThanExistingMinimumKeyAndToKeyIsGreaterThanExistingMaximumKey() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);
        vEBTree.put(2, to2);
        vEBTree.put(3, to3);
        vEBTree.put(5, to5);

        SortedMap<Integer, TestObject> actual = vEBTree.subMap(3, 7);
        assertFalse(actual.containsKey(0));
        assertFalse(actual.containsKey(1));
        assertFalse(actual.containsKey(2));
        assertTrue(actual.get(3).equals(to3));
        assertFalse(actual.containsKey(4));
        assertTrue(actual.get(5).equals(to5));
        assertFalse(actual.containsKey(6));
        assertFalse(actual.containsKey(7));
    }

    @Test
    public void subMap_whenFromKeyIsLessThanExistingMinimumKeyAndToKeyIsLessThanExistingMaximumKey() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);
        vEBTree.put(2, to2);
        vEBTree.put(3, to3);
        vEBTree.put(5, to5);

        SortedMap<Integer, TestObject> actual = vEBTree.subMap(1, 4);
        assertFalse(actual.containsKey(0));
        assertFalse(actual.containsKey(1));
        assertTrue(actual.get(2).equals(to2));
        assertTrue(actual.get(3).equals(to3));
        assertFalse(actual.containsKey(4));
        assertFalse(actual.containsKey(5));
        assertFalse(actual.containsKey(6));
        assertFalse(actual.containsKey(7));
    }

    @Test
    public void subMap_whenChangesAreAppliedOnThisMap_thenChangesAlsoVisibleOnReturnedMap() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);
        vEBTree.put(2, to2);
        vEBTree.put(3, to3);
        vEBTree.put(5, to5);

        SortedMap<Integer, TestObject> actual = vEBTree.subMap(2, 6);
        assertFalse(actual.containsKey(0));
        assertFalse(actual.containsKey(1));
        assertTrue(actual.get(2).equals(to2));
        assertTrue(actual.get(3).equals(to3));
        assertFalse(actual.containsKey(4));
        assertTrue(actual.get(5).equals(to5));
        assertFalse(actual.containsKey(6));
        assertFalse(actual.containsKey(7));

        vEBTree.put(4, to4);
        assertFalse(actual.containsKey(0));
        assertFalse(actual.containsKey(1));
        assertTrue(actual.get(2).equals(to2));
        assertTrue(actual.get(3).equals(to3));
        assertTrue(actual.get(4).equals(to4));
        assertTrue(actual.get(5).equals(to5));
        assertFalse(actual.containsKey(6));
        assertFalse(actual.containsKey(7));
    }

    @Test
    public void subMap_whenChangesAreAppliedOnReturnedMap_thenChangesAlsoVisibleOnThisMap() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);
        vEBTree.put(2, to2);
        vEBTree.put(3, to3);
        vEBTree.put(5, to5);

        SortedMap<Integer, TestObject> actual = vEBTree.subMap(2, 6);
        assertFalse(actual.containsKey(0));
        assertFalse(actual.containsKey(1));
        assertTrue(actual.get(2).equals(to2));
        assertTrue(actual.get(3).equals(to3));
        assertFalse(actual.containsKey(4));
        assertTrue(actual.get(5).equals(to5));
        assertFalse(actual.containsKey(6));
        assertFalse(actual.containsKey(7));

        actual.put(4, to4);
        assertFalse(vEBTree.containsKey(0));
        assertFalse(vEBTree.containsKey(1));
        assertTrue(vEBTree.get(2).equals(to2));
        assertTrue(vEBTree.get(3).equals(to3));
        assertTrue(vEBTree.get(4).equals(to4));
        assertTrue(vEBTree.get(5).equals(to5));
        assertFalse(vEBTree.containsKey(6));
        assertFalse(vEBTree.containsKey(7));
    }

    @Test
    public void subMap_returnedMapMustHaveSameOperationsAsThisMap() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);
        vEBTree.put(2, to2);
        vEBTree.put(3, to3);
        vEBTree.put(5, to5);

        SortedMap<Integer, TestObject> actual = vEBTree.subMap(2, 6);

        List<String> publicMethodNamesInThisMap = publicMethodsOf(vEBTree.getClass()).stream().map(this::signature).distinct().sorted().collect(toList());
        List<String> publicMethodNamesInActualSubThisMap = publicMethodsOf(actual.getClass()).stream().map(this::signature).distinct().sorted().collect(toList());

        assertEquals(publicMethodNamesInThisMap, publicMethodNamesInActualSubThisMap);
    }

    @Test
    public void subMap_whenFromKeyIsEqualToToKey_thenEmpty() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);
        vEBTree.put(2, to2);
        vEBTree.put(3, to3);
        vEBTree.put(5, to5);

        SortedMap<Integer, TestObject> actual = vEBTree.subMap(2, 2);

        assertTrue(actual.isEmpty());
    }

    @Test
    public void subMap_whenFromKeyIsNull_thenThrowException() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);
        vEBTree.put(2, to2);
        vEBTree.put(3, to3);
        vEBTree.put(5, to5);

        Executable codeToTest = () -> vEBTree.subMap(null, 5);

        assertThrows(NullPointerException.class, codeToTest);
    }

    @Test
    public void subMap_whenToKeyIsNull_thenThrowException() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);
        vEBTree.put(2, to2);
        vEBTree.put(3, to3);
        vEBTree.put(5, to5);

        Executable codeToTest = () -> vEBTree.subMap(2, null);

        assertThrows(NullPointerException.class, codeToTest);
    }

    @Test
    public void subMap_whenFromKeyIsGreaterThanToKey_thenThrowException() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);
        vEBTree.put(2, to2);
        vEBTree.put(3, to3);
        vEBTree.put(5, to5);

        Executable codeToTest = () -> vEBTree.subMap(3, 2);

        assertThrows(IllegalArgumentException.class, codeToTest);
    }

    @Test
    public void subMap_whenFromKeyIsOutsideUniverse_thenThrowException() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);
        vEBTree.put(2, to2);
        vEBTree.put(3, to3);
        vEBTree.put(5, to5);

        Executable codeToTest = () -> vEBTree.subMap(-1, 2);

        assertThrows(IllegalArgumentException.class, codeToTest);
    }

    @Test
    public void subMap_whenToKeyIsOutsideUniverse_thenThrowException() {
        VanEmdeBoasMap<TestObject> vEBTree = new VanEmdeBoasMap<>(8);
        vEBTree.put(2, to2);
        vEBTree.put(3, to3);
        vEBTree.put(5, to5);

        Executable codeToTest = () -> vEBTree.subMap(3, 8);

        assertThrows(IllegalArgumentException.class, codeToTest);
    }

    private List<Method> publicMethodsOf(Class clazz) {
        List<Method> result = new ArrayList<>();
        while (clazz != null) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (isPublic(method.getModifiers())) {
                    result.add(method);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return result;
    }

    private String signature(Method method) {
        String result = method.getReturnType().getSimpleName() + " " + method.getName() + "(";
        for (Class<?> clazz : method.getParameterTypes()) {
            result = result + clazz.getSimpleName() + ", ";
        }
        result = result + ")";
        result = result.replace(", )", ")");
        return result;
    }
}
