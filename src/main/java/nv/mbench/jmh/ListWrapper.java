package nv.mbench.jmh;

import java.util.List;

/**
 * Created by nviorres on 24/8/2016.
 */
public class ListWrapper<T> {
    private List<T> list;
    private int index = 0;

    public ListWrapper(List<T> list) {
        this.list = list;
    }

    public T set(int index, T element) {
        return list.set(index, element);
    }

    public T get(int index) {
        return list.get(index);
    }

    public T get() {
        return list.get(index);
    }

    public void add(int index, T element) {
        list.add(index, element);
        this.index++;
    }

    public void add(T element) {
        list.add(element);
        index++;
    }

    public int size() {
        return list.size();
    }

    public void resetIndex() {
        index = 0;
    }

    public int index() {
        return index;
    }

    public int incrementIndex() {
        return ++index;
    }

    public List<T> underlying() {
        return list;
    }
}
