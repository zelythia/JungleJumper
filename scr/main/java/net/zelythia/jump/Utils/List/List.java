package net.zelythia.jump.Utils.List;

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

    public void remove(T d){
        node = node.remove(d);
        size--;
    }

    /**
     *
     * @param index Index of the desired object
     */
    public T get(int index){
        if (index < size) {
            ListElement<T> x = node;
            for (int i = 0; i < index; i++)
                x = x.getNext();
            return x.getData();
        }
        throw new IndexOutOfBoundsException();
    }

    public void clear(){
        size = 0;
        node = new End<>();
    }
}
