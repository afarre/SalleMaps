import java.util.AbstractList;
import java.util.Arrays;

public class MyList<T> extends AbstractList<T> {
    private T[] list;
    private int numElements;
    private final static int INITIALCAPACITY = 10;

    public MyList(){
        list = (T[]) new Object[INITIALCAPACITY];
        numElements = 0;
    }

    @Override
    public boolean add (T e){
        if (list.length == numElements){
            T[] aux = (T[]) new Object[list.length * 2];
            System.arraycopy(list, 0, aux, 0, list.length);
            list = aux;
        }
        list[numElements] = e;
        numElements++;
        return true;
    }

    @Override
    public T get (int i){
        return list[i];
    }

    public T[] getAll(){
        return list;
    }

    @Override
    public int size (){
        return numElements;
    }

    @Override
    public String toString(){
        return "MyList{" +
                "list=" + Arrays.toString(list) +
                ", numElements =" + numElements +
                "}";
    }
}
