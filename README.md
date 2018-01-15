# Van Emde Boas Tree Java implementation

A Java implementation of a vEB tree data structure.

This library contains a VanEmdeBoasMap class which implements the NavigableMap<Integer, E> interface. The Integer keys are the vEB tree keys. 

## Usage


### Prerequisites

This VanEmdeBoasMap class requires a Java 8 compiler. You'll also need jUnit 5 if you want to run the tests.

### Example

To create a vEB Map, call its constructor with an Integer value representing the universe size for this vEB Map.

```
VanEmdeBoasMap<String> vEBMap = new VanEmdeBoasMap<>(8);
```


## Authors

* **Maarten Dhondt**

## Acknowledgments

* Cormen, Leiserson, Rivest, Stein for their algorithms in 'Introduction to Algorithms'
* Josh Bloch and Doug Lea for their implementation of java.util.TreeMap

