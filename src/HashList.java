public class HashList {
    private MyList<CityModel>[] list;

    public HashList (){
        list = new MyList[27];
    }

    public HashList (MyList<Node> data){
        list = new MyList[27];
        for (int i = 0; i < list.length; i++){
            list[i] = new MyList<>();
        }
        for (int i = 0; i < data.size(); i++){
            add(data.get(i).getCity().getName(), data.get(i).getCity());
        }
    }

    public void add (String clave, CityModel valor){
        list[hash(clave)].add(valor);
    }

    private int hash (String clave){
        clave = clave.toLowerCase();
        return (int)(clave.charAt(0)) - (int) ('a');
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

    public boolean searchRoute(String from, String to, int type) {
        int hashedFrom = hash(from);
        int hashedTo = hash(to);
        if (type == 1){
            //search short route

        }else {
            //show fast route

        }
        return false;
    }
}
