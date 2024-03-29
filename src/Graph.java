import java.util.Date;

public class Graph {

    private final static Integer EARTH_LONG = 400750000;

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

    public boolean checkCities (String from, String to){
        boolean bfrom = false, bto = false;
        int grapSize = graph.size();
        for (int i = 0; i < grapSize; i++){
            if (from.toLowerCase().equals(graph.get(i).getCity().getName().toLowerCase())){
                bfrom = true;
            }
            if (to.toLowerCase().equals(graph.get(i).getCity().getName().toLowerCase())){
                bto = true;
            }
        }
        return bfrom && bto;
    }

    public void dijkstra (String from, String to, boolean T_NotD){
        //Inicialización

        Node nfrom = null, nto = null;
        int pfrom = 0, pto = 0;
        int grapSize = graph.size();
        for (int i = 0; i < grapSize; i++){
            if (from.toLowerCase().equals(graph.get(i).getCity().getName().toLowerCase())){
                nfrom = graph.get(i);
                pfrom = i;
            }
            if (to.toLowerCase().equals(graph.get(i).getCity().getName().toLowerCase())){
                nto = graph.get(i);
                pto = i;
            }
        }
        int[] value = new int[grapSize];
        for (int i = 0; i < value.length; i++){
            value[i] = EARTH_LONG;
        }
        value[pfrom] = 0;
        int[] way = new int [grapSize];
        way[pfrom] = -1;

        //Dijkstra
        System.out.print("\tLooking for route from  " + nfrom.getCity().getName() +
                " to " + nto.getCity().getName() +
                " in Graph with Dijkstra...");
        long olddate = new Date().getTime();
        while(!(pfrom == pto)){
            int vlow = EARTH_LONG;
            int plow = 0;
            for (int i = 0; i < grapSize; i++){
                if (value[i] != -1 && i != pfrom) {
                    int connectionsSize = nfrom.getConnections().size();
                    for (int j = 0; j < connectionsSize; j++) {
                        if (graph.get(i).getCity().getName().toLowerCase().equals(nfrom.getConnections().get(j).getTo().toLowerCase())) {
                            if (T_NotD) {
                                if (value[i] > nfrom.getConnections().get(j).getDuration() + value[pfrom]) {
                                    value[i] = nfrom.getConnections().get(j).getDuration() + value[pfrom];
                                    way[i] = pfrom;
                                }
                            } else {
                                if (value[i] > nfrom.getConnections().get(j).getDistance() + value[pfrom]) {
                                    value[i] = nfrom.getConnections().get(j).getDistance() + value[pfrom];
                                    way[i] = pfrom;
                                }
                            }
                        }
                        if (value[i] < vlow){
                            plow = i;
                        }
                    }
                }
            }
            value[pfrom] = -1;
            pfrom = plow;
            nfrom = graph.get(pfrom);
        }
        long actualdate = new Date().getTime();
        System.out.println("(" + (actualdate-olddate) + "ms)");

        System.out.print("Camino: ");
        boolean finish = false;
        String stringway = graph.get(pto).getCity().getName();
        while  (!finish){
            pto = way[pto];
            stringway = stringway + "," + graph.get(pto).getCity().getName();
            if (way[pto] == -1){
                finish = true;
            }
        }
        String [] yaw = stringway.split(",");
        for (int i = yaw.length; i > 1; i--){
            System.out.print(yaw[i-1] + ", ");
        }
        System.out.println(yaw[0] + ".");
        if (T_NotD) System.out.println("\tRoute duration = " + value[pfrom] + " seconds.");
        else System.out.println("\tRoute duration = " + value[pfrom] + " meters.");
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
        int graphSize = graph.size();
        double[] lats = new double[graphSize];
        for (int i = 0; i < graphSize; i++){
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

    public MyList<Node> getList(){
        return graph;
    }
}
