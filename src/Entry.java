public class Entry<T> {
    private T value;
    private String key;

    public Entry (String key, T value){
        this.key = key;
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString (){
        return new StringBuilder().append("K=").append(key).append(" - VALUE=").append(value).toString();
    }
}
