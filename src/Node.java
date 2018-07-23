public class Node {
    private CityModel city;
    private MyList<ConnectionModel> connections;
    private boolean visited;

    public Node (CityModel city) {
        this.city = city;
        connections = new MyList<>();
        visited = false;
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

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
