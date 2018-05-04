import java.util.Arrays;

public class MyList {
    private Object[] list;
    private int numElements;
    private final static int CAPACITY = 10;

    public MyList(Object[] content){
        if (content.length < CAPACITY){
            list = new Object[CAPACITY];
        }else {
            list = new Object[content.length];
        }

        for (Object o : content) {
            add(o);
        }
        numElements = content.length;
    }

    public MyList(Object content){
        list = new Object[CAPACITY];
        add(content);
        numElements = 1;
    }

    public MyList(){
        list = new Object[CAPACITY];
        numElements = 0;
    }

    public void add(Object element){
        if (list.length == numElements){
            Object[] aux = new Object[list.length * 2];
            System.arraycopy(list, 0, aux, 0, list.length);
            list = aux;
        }
        list[numElements] = element;
        numElements++;
    }

    public Object[] getList() {
        return list;
    }

    public Object getListElement(int index){
        return list[index];
    }

    public int size (){
        return numElements;
    }

    @Override
    public String toString() {
        return "MyList{" +
                "list=" + Arrays.toString(list) +
                ", numElements=" + numElements +
                '}';
    }
}
