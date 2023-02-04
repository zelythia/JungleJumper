package net.zelythia.jungleJumper.Utils.List;

public abstract class ListElement<T> {
    public abstract T getData();
    public abstract ListElement<T> getNext();
    public abstract ListElement<T> add(T d);
    public abstract ListElement<T> remove(T d);
}
