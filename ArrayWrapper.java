import java.util.*;

class ArrayWrapper {
    private int[] array;

    public ArrayWrapper(int[] array) {
        this.array = array;
    }

    protected int[] getArray() {
        return array;
    }

    protected void setArray(int[] array) {
        this.array = array;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ArrayWrapper other = (ArrayWrapper) obj;
        return Arrays.equals(array, other.array);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }
}