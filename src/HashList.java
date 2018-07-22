import java.lang.reflect.Array;

public class HashList<T> {
    private Entry<T>[] list;
    private int current;
    private int maxcapacity;

    public HashList (int maxcapacity) {
        list = new Entry[maxcapacity];
        this.maxcapacity = maxcapacity;
        current = 0;
    }

    public synchronized void add (String clave, T valor){
        if (current == list.length){
            System.out.println("The HashList hasn't more capacity");
        } else {
            int value = hash(clave);
            while (list[value] != null){
                if (list[value].getKey().equals(clave)){
                    System.out.println("The key (" + clave +") is in the HashTable, you can't input again.");
                    return;
                }
                value = hash(value);
            }
            list[value] = new Entry(clave, valor);
        }
        current++;
        System.out.println(this.toString());
    }

    private int hash (String clave){
        clave = clave.toLowerCase();
        int value = (int)(clave.charAt(0)) - (int) ('a');
        value = hash(value);
        return value;
    }

    private int hash (int value){
        value = value % maxcapacity;
        value = value + 1;
        if (value >= maxcapacity) value = 0;
        return value;
    }

    private T get (String key){
        int value = hash(key);
        while (list[value] == null || !(list[value].getKey().equals(key))){
            value = hash(value);
        }
        System.out.println(list[value].getValue());
        return list[value].getValue();
    }

    public int capacity (){
        return maxcapacity;
    }

    @Override
    public String toString (){
        StringBuilder sb = new StringBuilder();
        sb.append("MAX:").append(maxcapacity).append("\n");
        for (int i = 0; i < maxcapacity; i++){
            sb.append(i).append("|");
            if (list[i] == null){
                sb.append("null").append("\n");
            } else {
                sb.append(list[i].toString()).append("\n");
            }
        }
        return sb.toString();
    }

    public static void main (String[] args){
        HashList<Integer> asdf = new HashList<>(5);
        asdf.add("a", 1);
        asdf.add("b", 2);
        asdf.add("c", 5);
        asdf.add("d", 4);
        asdf.add("z", 3);
    }
}
