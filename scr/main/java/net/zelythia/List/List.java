package net.zelythia.List;

import java.util.Iterator;
import java.util.LinkedList;

public class List<T> {

    private ListElement<T> node;
    public int size;

    public List(){
        node = new End<T>();
        size = 0;
    }

    public void add(T d){
        node = node.add(d);
        size++;
    }

    /**
     *
     * @param i Index of the desired object
     */
    public T get(int i){
        if (i < size) {
            ListElement<T> x = node;
            for (int j = 0; j < i; j++)
                x = x.getNext();
            return x.getData();
        }
        throw new IndexOutOfBoundsException();
    }
}
