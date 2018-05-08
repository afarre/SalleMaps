public class Graph {
    private MyList graph;

    public Graph(MyList cities, MyList connections){
        this.graph = new MyList();
        for (int i = 0; i < cities.size();i++){
            Node n = new Node((CityModel) cities.getListElement(i));
            for (Object o: connections.getList()){
                ConnectionModel cm = (ConnectionModel) o;
                if (cm != null && cm.getFrom().equals(n.getCity().getName())){
                    n.addConection(cm);
                }
            }
            graph.add(n);
        }
    }

    public static class Node {
        private CityModel city;
        private MyList connections;

        public Node (CityModel city) {
            this.city = city;
            connections = new MyList();
        }

        public void addConection (ConnectionModel connection){
            connections.add(connection);
        }

        public CityModel getCity() {
            return city;
        }
    }

    public MyList getGraph(){
        return graph;
    }
}
