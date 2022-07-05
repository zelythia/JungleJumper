package net.zelythia.Utils.List;

public class End<T> extends ListElement<T>{
    @Override
    public T getData() {
        return null;
    }

    @Override
    public ListElement<T> getNext() {
        return this;
    }

    @Override
    public ListElement<T> add(T d) {
        return new Node<T>(this, d);
    }

    @Override
    public ListElement<T> remove(T d) {
        return this;
    }
}
