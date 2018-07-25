import Network.HttpRequest;
import Network.WSGoogleMaps;
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase principal del proyecto SalleMap.
 * Desde aquí se llaman y se utilizan todos los recursos necesarios para hacer funcionar este proyecto.
 * @author Javier Ortiz & Angel Farre
 */

public class SalleMap {

    private Graph graph;
    private HashList<Node> hash;
    private AVL avl;
    private final static String API_KEY = "AIzaSyCMG_IEevGb9kFUfR_DVgQIT0Gfqrz3S_I";
    private final static Integer MIN_DISTANCE = 300000;
    private final static Integer EARTH_LONG = 400750000;
    private boolean found = true;


    //Constructor
    public SalleMap(){}

    //Procedimientos y Funciones

    public void init (){
        System.out.println("Welcome to SalleMap.");
        boolean jsonIntroduced = false;
        int option = 0;
        Scanner sc = new Scanner(System.in);
        while (option != 4){
            try {
                option = menu();
                switch (option){
                    case 1: //Importar mapa. Lectura json.
                        if (importMap()){
                            System.out.print("Creating AVL tree...");
                            long olddate = new Date().getTime();
                            avl = new AVL(graph);
                            long actualdate = new Date().getTime();
                            System.out.println("(" + (actualdate-olddate) + "ms)");

                            System.out.print("Creating Hash list...");
                            olddate = new Date().getTime();
                            createHashList();
                            actualdate = new Date().getTime();
                            System.out.println("(" + (actualdate-olddate) + "ms)");
                            jsonIntroduced = true;
                        }
                        break;
                    case 2: //Buscar ciudad. Sino existe, añadir ciudad que no existe.
                        if (!jsonIntroduced){
                            System.out.println("You must complete option 1 before selecting this option.");
                            break;
                        }
                        System.out.println("Insert city name:");
                        String city = sc.nextLine();
                        switch (chooseStructure()){
                            case 1:
                                searchCityAVL(city);
                                break;
                            case 2:
                                searchCityHash(city);
                                break;
                            case 3:
                                searchCity(city);
                                break;
                        }
                        break;
                    case 3: //Calcular ruta. Utiliza el método necesario en cada caso.
                        if (!jsonIntroduced){
                            System.out.println("You must complete option 1 before selecting this option.");
                            break;
                        }
                        System.out.println("1. Shorter route");
                        System.out.println("2. Fastest route");
                        System.out.println("Select one:");
                        int type = sc.nextInt();
                        sc.nextLine();
                        System.out.println("Insert new route:");
                        System.out.println("From: ");
                        String from = sc.nextLine();
                        System.out.println("To: ");
                        String to = sc.nextLine();
                        switch (chooseStructure()){
                            case 1:
                                AVL.AVLNode fromNode = avl.existsCity(from);
                                AVL.AVLNode toNode = avl.existsCity(to);
                                boolean T_NotD = false;
                                if (type == 2) T_NotD = true;
                                if (fromNode != null && toNode != null){
                                    System.out.print("\tLooking for route from  " + fromNode.getElement().getCity()
                                            .getName() + " to " + toNode.getElement().getCity().getName() +
                                            " in AVL tree...");
                                    long olddate = new Date().getTime();
                                    int response = calculateRouteAVL(fromNode, toNode, T_NotD);
                                    long actualdate = new Date().getTime();
                                    System.out.println("(" + (actualdate-olddate) + "ms)");
                                    if (T_NotD){
                                        System.out.println("\tRoute duration = " + response + " seconds.");
                                    }else {
                                        System.out.println("\tRoute distance = " + response + " meters.");
                                    }
                                }else {
                                    System.out.println("Either origin or destiny city doesn't exist!");
                                }
                                break;
                            case 2:
                                searchRouteHash(from, to, type);
                                break;
                            case 3:
                                route(from, to, type);
                                break;
                        }
                        break;
                    case 4: //Salir del programa
                        System.out.println("\nGood bye!");
                        System.out.println("SalleMap by Javier Ortiz & Angel Farre");
                        break;
                    default:
                        System.out.println("This option doesn't exist.\nSelect a valid option.");
                        break;
                }
            }catch (InputMismatchException e){
                System.out.println("Input mismatch");
            }
        }
    }

    private int calculateRouteAVL(AVL.AVLNode fromNode, AVL.AVLNode toNode, boolean T_NotD) {
        int lessvalue = 2000000000;
        //Le ponemos que se visita ese nodo
        fromNode.getElement().setVisited(true);
        int connectionsSize = fromNode.getElement().getConnections().size();
        for (int i = 0; i < connectionsSize; i++){
            //Cogemos el nodo de la conexion que estamos mirando
            AVL.AVLNode node = avl.existsCity(fromNode.getElement().getConnections().get(i).getTo());
            if (node == null){
                continue;
            }
            int prevalue;
            //Si el nodo no ha sido visitado, así evitamos el stackoverflowerror
            if (!node.getElement().isVisited()) {
                //Si el to es igual que la conexion que estamos mirando
                if (fromNode.getElement().getConnections().get(i).getTo().equals(toNode.getElement().getCity().getName())) {
                    //Le añadimos el valor de la conexion al prevalue (valor de ahora mismo)
                    if (T_NotD) prevalue = fromNode.getElement().getConnections().get(i).getDuration();
                    else prevalue = fromNode.getElement().getConnections().get(i).getDistance();
                } else {
                    //Si no es el mismo, calculamos la ruta desde la conexion hasta el final
                    if (T_NotD) prevalue = fromNode.getElement().getConnections().get(i).getDuration() +  calculateRouteAVL(node, toNode, T_NotD);
                    else prevalue = fromNode.getElement().getConnections().get(i).getDistance() +  calculateRouteAVL(node, toNode, T_NotD);
                }
                //sustituimos si el valor calculado es mejor que el que teniamos antes.
                if (lessvalue > prevalue){
                    lessvalue = prevalue;
                }
            }
        }
        //Ponemos que el nodo no ha sido visitado
        fromNode.getElement().setVisited(false);
        //Devolvemos el valor calculado.
        return lessvalue;
    }

    private void searchRouteHash(String from, String to, int type) {
        hash.cleanVisited();
        if (hash.get(from) == null || hash.get(to) == null){
            System.out.println("Error! Either origin city or destination city where not found. Please check if spelling is correct.");
            return;
        }
        boolean T_NotD = false;
        if (type == 2) T_NotD = true;
        Node nFrom = hash.get(from);
        Node nTo = hash.get(to);
        System.out.print("\tLooking for route from  " + nFrom.getCity().getName() +
                " to " + nTo.getCity().getName() +
                " in Hash list...");
        long olddate = new Date().getTime();
        int response = calculateRouteHash(nFrom, nTo, T_NotD);
        long actualdate = new Date().getTime();
        System.out.println("(" + (actualdate-olddate) + "ms)");
        if (T_NotD){
            System.out.println("\tRoute duration = " + response + " seconds.");
        }else {
            System.out.println("\tRoute distance = " + response + " meters.");
        }


    }

    private int calculateRouteHash (Node nFrom, Node nTo, boolean T_NotD){
        //Valor mas pequeño
        int lessvalue = 2000000000;
        //Le ponemos que se visita ese nodo
        nFrom.setVisited(true);
        int connectionsSize = nFrom.getConnections().size();
        for (int i = 0; i < connectionsSize; i++){
            //Cogemos el nodo de la conexion que estamos mirando
            Node node = hash.get(nFrom.getConnections().get(i).getTo());
            int prevalue;
            //Si el nodo no ha sido visitado, así evitamos el stackoverflowerror
            if (!node.isVisited()) {
                //Si el to es igual que la conexion que estamos mirando
                if (nFrom.getConnections().get(i).getTo().equals(nTo.getCity().getName())) {
                    //Le añadimos el valor de la conexion al prevalue (valor de ahora mismo)
                    if (T_NotD) prevalue = nFrom.getConnections().get(i).getDuration();
                    else prevalue = nFrom.getConnections().get(i).getDistance();
                } else {
                    //Si no es el mismo, calculamos la ruta desde la conexion hasta el final
                    if (T_NotD) prevalue = nFrom.getConnections().get(i).getDuration() +  calculateRouteHash(node, nTo, T_NotD);
                    else prevalue = nFrom.getConnections().get(i).getDistance() +  calculateRouteHash(node, nTo, T_NotD);
                }
                //sustituimos si el valor calculado es mejor que el que teniamos antes.
                if (lessvalue > prevalue){
                    lessvalue = prevalue;
                }
            }
        }
        //Ponemos que el nodo no ha sido visitado
        nFrom.setVisited(false);
        //Devolvemos el valor calculado.
        return lessvalue;
    }

    private void searchCityHash(final String city) {
        Node node;
        System.out.print("\tLooking for " + city + " in Hash list...");
        long olddate = new Date().getTime();
        node = hash.get(city);
        long actualdate = new Date().getTime();
        System.out.println("(" + (actualdate-olddate) + "ms)");
        if (node == null){
            addCityToModel(city);
        } else {
            System.out.println("\tCity found. Showing city data:");
            System.out.println("\t\t" + node.toString());
        }
    }

    private void createHashList() {
        hash = new HashList();
        int graphSize = graph.size();
        for (int i = 0; i < graphSize; i++){
            hash.add(graph.get(i).getCity().getName(), graph.get(i));
        }
    }

    private void searchCityAVL(String city) {
        System.out.print("\tLooking for " + city + " in AVL tree...");
        long olddate = new Date().getTime();
        AVL.AVLNode cityNode = avl.existsCity(city);
        long actualdate = new Date().getTime();
        System.out.println("(" + (actualdate-olddate) + "ms)");
        if (cityNode != null){
            System.out.println("\tCity found. Showing city data:");
            System.out.println("\t\t" + cityNode.getElement());
        }else {
            addCityToModel(city);
        }
    }

    private int chooseStructure() throws InputMismatchException{
        int structure = 0;

        while (structure != 1 && structure != 2 && structure != 3){
            System.out.println("\nChoose structure:");
            System.out.println("\t1.AVL tree");
            System.out.println("\t2.Hash table");
            System.out.println("\t3.No optimization");
            Scanner sc = new Scanner(System.in);
            structure = sc.nextInt();
        }

        return structure;
    }

    private void searchCity(String city) {
        System.out.print("\tLooking for " + city + " in Graph...");
        long olddate = new Date().getTime();
        int size = graph.size();
        boolean flag = false;
        Node n = null;
        for (int i = 0; i < size; i++){
            n = graph.get(i);
            CityModel cityModel = n.getCity();
            if (city.toLowerCase().equals(cityModel.getName().toLowerCase())) {
                flag = true;
                break;
            }
        }
        long actualdate = new Date().getTime();
        System.out.println("(" + (actualdate-olddate) + "ms)");
        if (!flag){
            addCityToModel(city);
        } else {
            System.out.println("\tCity found. Showing city data:");
            System.out.println("\t\t" + n.toString());
        }
    }

    private void addCityToModel(final String city) {
        System.out.println("\tNot found. Adding new city.");
        System.out.print("\tAdding " + city + " in structures...");
        long olddate = new Date().getTime();
        WSGoogleMaps.getInstance().setApiKey(API_KEY);
        HttpRequest.HttpReply httpReply = new HttpRequest.HttpReply() {
            @Override
            public void onSuccess(String s) {
                try {
                    JsonElement jelement = new JsonParser().parse(s);
                    JsonObject jobject = jelement.getAsJsonObject();
                    CityModel cityModel = new CityModel(
                            jobject.get("results").getAsJsonArray().get(0).getAsJsonObject().getAsJsonObject().get("address_components").getAsJsonArray().get(0).getAsJsonObject().get("long_name").getAsString(),
                            jobject.get("results").getAsJsonArray().get(0).getAsJsonObject().getAsJsonObject().get("formatted_address").getAsString(),
                            jobject.get("results").getAsJsonArray().get(0).getAsJsonObject().getAsJsonObject().get("address_components").getAsJsonArray().get(3).getAsJsonObject().get("long_name").getAsString(),
                            jobject.get("results").getAsJsonArray().get(0).getAsJsonObject().getAsJsonObject().get("geometry").getAsJsonObject().get("location").getAsJsonObject().get("lat").getAsDouble(),
                            jobject.get("results").getAsJsonArray().get(0).getAsJsonObject().getAsJsonObject().get("geometry").getAsJsonObject().get("location").getAsJsonObject().get("lng").getAsDouble());
                    Node node = new Node(cityModel);
                    //System.out.println("lat: " + cityModel.getLatitude());
                    //System.out.println("lon: " + cityModel.getLongitude());
                    graph.add(node);
                    hash.add(city, node);
                } catch (IndexOutOfBoundsException ioobe) {
                    found = false;
                    System.out.println("\n\tWe couldn't found the city.");
                }
            }

            @Override
            public void onError(String s) {
                found = false;
                System.out.println("\n\tWe couldn't found the city.");
            }
        };

        WSGoogleMaps.getInstance().geolocate(city, httpReply);
        httpReply = new HttpRequest.HttpReply() {
            @Override
            public void onSuccess(String s) {
                try {
                    JsonElement jelement = new JsonParser().parse(s);
                    JsonObject jobject = jelement.getAsJsonObject();
                    int mindistance = EARTH_LONG;
                    int minvalue = 0;
                    boolean exists = false;
                    int size = jobject.get("rows").getAsJsonArray().get(0).getAsJsonObject().get("elements").getAsJsonArray().size() - 1;
                    for (int i = 0; i < size; i++) {
                        int distance = jobject.get("rows").getAsJsonArray().get(0).getAsJsonObject().get("elements").getAsJsonArray().get(i).getAsJsonObject().get("distance").getAsJsonObject().get("value").getAsInt();
                        if (MIN_DISTANCE > distance) {
                            graph.getLastOne().getConnections().add(new ConnectionModel(
                                    graph.getLastOne().getCity().getName(),
                                    graph.get(i).getCity().getName(),
                                    distance,
                                    jobject.get("rows").getAsJsonArray().get(0).getAsJsonObject().get("elements").getAsJsonArray().get(i).getAsJsonObject().get("duration").getAsJsonObject().get("value").getAsInt()
                            ));
                            graph.get(i).getConnections().add(new ConnectionModel(
                                    graph.get(i).getCity().getName(),
                                    graph.getLastOne().getCity().getName(),
                                    distance,
                                    jobject.get("rows").getAsJsonArray().get(0).getAsJsonObject().get("elements").getAsJsonArray().get(i).getAsJsonObject().get("duration").getAsJsonObject().get("value").getAsInt()
                            ));
                            exists = true;
                        } else if (mindistance > distance) {
                            mindistance = distance;
                            minvalue = i;
                        }
                    }
                    if (!exists) {
                        //System.out.println("aux: " + graph.getLastOne());
                        graph.getLastOne().getConnections().add(new ConnectionModel(
                                graph.getLastOne().getCity().getName(),
                                graph.get(minvalue).getCity().getName(),
                                mindistance,
                                jobject.get("rows").getAsJsonArray().get(0).getAsJsonObject().get("elements").getAsJsonArray().get(minvalue).getAsJsonObject().get("duration").getAsJsonObject().get("value").getAsInt()
                        ));
                        graph.get(minvalue).getConnections().add(new ConnectionModel(
                                graph.get(minvalue).getCity().getName(),
                                graph.getLastOne().getCity().getName(),
                                mindistance,
                                jobject.get("rows").getAsJsonArray().get(0).getAsJsonObject().get("elements").getAsJsonArray().get(minvalue).getAsJsonObject().get("duration").getAsJsonObject().get("value").getAsInt()
                        ));
                    }
                } catch (Exception e){
                    found = false;
                    System.out.println("\n\tWe couldn't found the city.");
                }
            }

            @Override
            public void onError(String s) {
                System.out.println("\n\tWe couldn't found the city.");
            }
        };

        WSGoogleMaps.getInstance().distance(graph.getLastOne().getCity().getLatitude(), graph.getLastOne().getCity().getLongitude(), graph.getLats(),graph.getLongs(), httpReply);
        //Actualizamos las demás estructuras.
        createHashList();
        avl = new AVL(graph);

        long actualdate = new Date().getTime();
        System.out.println("(" + (actualdate-olddate) + "ms)");
        if (found) System.out.println("\tAdded:\n\t\t" + graph.getLastOne().toString());
    }

    private boolean importMap() {
        System.out.println("Introduce JSON file name with the path ([path]/[jsonFileName].json).");
        Scanner sc = new Scanner(System.in);
        String fileEntry = sc.nextLine();
        JsonObject jsonObject = null;
        if (fileEntry.endsWith("json")){
            try {
                System.out.println("JSON file imported correctly!");
                System.out.print("Creating Graph...");
                long olddate = new Date().getTime();
                jsonObject = new Gson().fromJson(new FileReader(fileEntry), JsonObject.class);
                readJson(jsonObject);
                long actualdate = new Date().getTime();
                System.out.println("(" + (actualdate-olddate) + "ms)");
                return true;
            } catch (FileNotFoundException e) {
                System.out.println("\nPlease introduce a valid file.\n");
            }
        } else {
            System.out.println("Invalid file type. The file must be a JSON file.");
            return false;
        }
        return false;
    }

    private void readJson(JsonObject jsonObject) {
        MyList<CityModel> cities = new MyList<>();
        MyList<ConnectionModel> connections = new MyList<>();

        int sizecities = jsonObject.get("cities").getAsJsonArray().size();
        for (int i = 0; i < sizecities; i++){
            CityModel citiesModel = new CityModel(jsonObject.get("cities").getAsJsonArray().get(i).getAsJsonObject());
            cities.add(citiesModel);
        }

        int sizeconn = jsonObject.get("connections").getAsJsonArray().size();
        for (int i = 0; i < sizeconn; i++){
            ConnectionModel connectionModel= new ConnectionModel(jsonObject.get("connections").getAsJsonArray().get(i).getAsJsonObject());
            connections.add(connectionModel);
        }
        graph = new Graph(cities, connections);
    }

    private void route(String from, String to, int type){
        boolean T_NotD = false;
        switch (type){
            case 1:
                T_NotD = false;
                break;
            case 2:
                T_NotD = true;
                break;
        }
        if (graph.checkCities(from, to)){
            graph.dijkstra(from, to, T_NotD);
        }else {
            System.out.println("Error! Either origin city or destination city where not found. Please check if spelling is correct.");
        }

    }

    private int menu () throws InputMismatchException{
        Scanner sc = new Scanner (System.in);
        int value;
        System.out.println("\n\t1-. Import map.");
        System.out.println("\t2-. Search city.");
        System.out.println("\t3-. Calculate route.");
        System.out.println("\t4-. Shut down.");
        System.out.println("\nSelect an option:");

        value = sc.nextInt();
        sc.nextLine();

        return value;
    }
}
