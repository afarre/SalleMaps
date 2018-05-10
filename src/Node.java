public class Node {
    private CityModel city;
    private MyList<ConnectionModel> connections;

    public Node (CityModel city) {
        this.city = city;
        connections = new MyList<>();
    }

    public void addConection (ConnectionModel connection){
        connections.add(connection);
    }

    public CityModel getCity() {
        return city;
    }

    public MyList<ConnectionModel> getConnections (){
        return connections;
    }

    public String toString (){
        return "{city: " + city.toString() + "," +
                "connections: " + connections.toString() +
                "}";
    }
}
