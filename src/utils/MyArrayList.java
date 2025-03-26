package utils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class MyArrayList<T> implements MyList<T> {
    private T[] array;
    private int cursor;


    public MyArrayList() {
        this.array = (T[]) new Object[10];
    }

    public MyArrayList(T[] array) {

        if (array == null || array.length == 0) {
            this.array = (T[]) new Object[10];
        } else {
            this.array = (T[]) new Object[array.length * 2];
            addAll(array);
        }
    }

    @Override
    public void add(T value) {
        if (cursor == array.length) {
            expandArray();
        }
        array[cursor] = value;
        cursor++;
    }

    private void expandArray() {
        T[] newArray = (T[]) new Object[array.length * 2];

        for (int i = 0; i < cursor; i++) {
            newArray[i] = array[i];
        }

        array = newArray;
    }

    @Override

    public void addAll(T... numbers) {
        for (int i = 0; i < numbers.length; i++) {
            add(numbers[i]);
        }
    }

    @Override
    public String toString() {
        if (cursor == 0) return "[]";

        String result = "[";
        for (int i = 0; i < cursor; i++) {
            result += array[i] + (i < cursor - 1 ? ", " : "]");
        }
        return result;
    }

    @Override
    public int size() {
        return cursor;
    }

    public T get(int index) {
        if (index >= 0 && index < cursor) {
            return array[index];
        }
        return null;
    }

    public T remove(int index) {
        if (index >= 0 && index < cursor) {
            T value = array[index];

            for (int i = index; i < cursor - 1; i++) {
                array[i] = array[i + 1];
            }
            cursor--;
            array[cursor] = null;

            return value;
        } else {
            return null;
        }
    }

    @Override
    public boolean isEmpty() {
        return cursor == 0;
    }

    @Override
    public boolean contains(T value) {
        return indexOf(value) >= 0;
    }

    @Override
    public void set(int index, T value) {
        if (index >= 0 && index < cursor) {
            array[index] = value;
        }
    }


    public int indexOf(T value) {
        for (int i = 0; i < cursor; i++) {
            if (Objects.equals(array[i], value)) {
                return i;
            }
        }
        return -1;
    }

    public int lastIndexOf(T value) {
        for (int i = cursor - 1; i >= 0; i--) {
            if (Objects.equals(array[i], value)) return i;
        }
        return -1;
    }

    @Override
    public boolean remove(T value) {
        int index = indexOf(value);
        if (index < 0) return false;
        remove(index);
        return true;
    }

    @Override
    public T[] toArray() {
        if (cursor == 0) return (T[]) new Object[0];
        Class<T> clazz = (Class<T>) array[0].getClass();

        T[] result = (T[]) Array.newInstance(clazz, cursor);
        for (int i = 0; i < cursor; i++) {
            result[i] = array[i];
        }
        return result;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<T> {
        int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < cursor;
        }

        @Override
        public T next() {
            return array[currentIndex++];
        }
    }
    public void test() {
        System.out.println(Arrays.toString(array));
    }
}