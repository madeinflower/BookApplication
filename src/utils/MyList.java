package utils;

public interface MyList<T> extends Iterable<T> {

    void add(T value);

    void addAll(T... values);

    int size();

    int indexOf(T value);

    int lastIndexOf(T value);

    boolean contains(T value);

    T[] toArray();

    boolean remove(T value);

    T remove(int index);

    boolean isEmpty();

    T get(int index);
o
    void set (int index, T value);


}
