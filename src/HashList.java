public class HashList {
    private MyList<CityModel>[] list;

    public HashList (){
        list = new MyList[27];
    }

    public HashList (MyList<CityModel> data){
        list = new MyList[27];
        for (int i = 0; i < data.size(); i++){
            add(data.get(i).getName(), data.get(i));
        }
    }

    public void add (String clave, CityModel valor){
        list[hash(clave)].add(valor);
    }

    private int hash (String clave){
        clave.toLowerCase();
        return Integer.parseInt(String.valueOf(clave.charAt(0))) - Integer.parseInt("a");
    }
}
