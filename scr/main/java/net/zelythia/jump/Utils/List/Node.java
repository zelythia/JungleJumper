package net.zelythia.jump.Utils.List;

public class Node<T> extends ListElement<T> {

    private ListElement<T> next;
    private T data;

    public Node(ListElement<T> n, T d){
        next = n;
        data = d;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public ListElement<T> getNext() {
        return next;
    }

    @Override
    public ListElement<T> add(T d) {
        next = next.add(d);
        return this;
    }

    @Override
    public ListElement<T> remove(T d) {
        if(this.data.equals(d)){
            return next;
        }
        next = next.remove(d);
        return this;
    }
}
