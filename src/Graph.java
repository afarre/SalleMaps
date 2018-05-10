public class Graph {

    private MyList<Node> graph;

    public Graph(MyList<CityModel> cities, MyList<ConnectionModel> connections){
        this.graph = new MyList<>();
        for (int i = 0; i < cities.size();i++){
            Node n = new Node(cities.get(i));
            for (int j = 0; j < connections.size(); j++){
                ConnectionModel cm = connections.get(j);
                if (cm != null && cm.getFrom().equals(n.getCity().getName())){
                    n.addConection(cm);
                }
            }
            graph.add(n);
        }
    }

    public void add (Node n){
        graph.add(n);
    }


    public int size (){
        return graph.size();
    }

    public Node get (int i){
        return graph.get(i);
    }

    public Node getLastOne (){
        return graph.get(graph.size()-1);
    }

    public double[] getLats (){
        double[] lats = new double[graph.size()];
        for (int i = 0; i < graph.size(); i++){
            lats[i] = graph.get(i).getCity().getLatitude();
        }
        return lats;
    }

    public double[] getLongs(){
        double[] longs = new double[graph.size()];
        for (int i = 0; i < graph.size(); i++){
            longs[i] = graph.get(i).getCity().getLongitude();
        }
        return longs;

    }

    public String toString (){
        return graph.toString();
    }
}
