package net.zelythia.List;

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
}
