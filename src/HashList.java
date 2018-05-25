public class HashList {
    private MyList<CityModel>[] list;

    public HashList (){
        list = new MyList[27];
    }

    public HashList (MyList<Node> data){
        list = new MyList[27];
        for (int i = 0; i < data.size(); i++){
            add(data.get(i).getCity().getName(), data.get(i).getCity());
        }
    }

    public void add (String clave, CityModel valor){
        list[hash(clave)].add(valor);
    }

    private int hash (String clave){
        clave.toLowerCase();
        return Integer.parseInt(String.valueOf(clave.charAt(0))) - Integer.parseInt("a");
    }

    public boolean searchCity(String city) {
        int hash = hash(city);
        int size = list[hash].size();
        for (int i = 0;  i < size; i++){
            if (list[hash].get(i).getName().equals(city)){
                return true;
            }
        }
        return false;
    }
}
