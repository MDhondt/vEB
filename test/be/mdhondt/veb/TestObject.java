package be.mdhondt.veb;

class TestObject {
    private String content;

    TestObject(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "TestObject: " + content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestObject that = (TestObject) o;
        return content != null ? content.equals(that.content) : that.content == null;
    }

    @Override
    public int hashCode() {
        return content != null ? content.hashCode() : 0;
    }
}