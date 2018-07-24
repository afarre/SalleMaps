import Network.HttpRequest;
import Network.WSGoogleMaps;
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
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


    //Constructor
    public SalleMap(){}

    //Procedimientos y Funciones

    /**
     * Inicializa el programa.
     */
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
                        importMap();
                        avl = new AVL(graph);
                        //Creamos las otras estructuras de datos con el contenido del graph.
                        createHashList();
                        jsonIntroduced = true;
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
                        System.out.print("To: ");
                        String to = sc.nextLine();
                        switch (chooseStructure()){
                            case 1:
                                AVL.AVLNode fromNode = avl.existsCity(from);
                                AVL.AVLNode toNode = avl.existsCity(to);
                                boolean T_NotD = false;
                                if (type == 2) T_NotD = true;
                                if (fromNode != null && toNode != null){
                                    System.out.println(calculateRouteAVL(fromNode, toNode, T_NotD));
                                }else {
                                    System.out.println("Either origin or destiny city doesn't exist!");
                                }
                                break;
                            case 2:
                                //Lamamos al buscador de rutas del hash
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
        for (int i = 0; i < fromNode.getElement().getConnections().size(); i++){
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
        //Limpiamos si han sido visitados o no (es un nuevo paramentro que he añadido)
        hash.cleanVisited();
        //Comprobamos si exiten los nodos
        if (hash.get(from) == null || hash.get(to) == null){
            System.out.println("Error! Either origin city or destination city where not found. Please check if spelling is correct.");
            return;
        }
        boolean T_NotD = false;
        if (type == 2) T_NotD = true;
        //Cogemos los nodos de salida y llegada
        Node nFrom = hash.get(from);
        Node nTo = hash.get(to);
        //Calculamos la ruta
        System.out.println(calculateRouteHash(nFrom, nTo, T_NotD));


    }

    private int calculateRouteHash (Node nFrom, Node nTo, boolean T_NotD){
        //Valor mas pequeño
        int lessvalue = 2000000000;
        //Le ponemos que se visita ese nodo
        nFrom.setVisited(true);
        for (int i = 0; i < nFrom.getConnections().size(); i++){
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
        if ((node = hash.get(city)) == null){
            addCityToModel(city);
        } else {
            System.out.println(node.toString());
        }
    }

    private void createHashList() {
        hash = new HashList();
        for (int i = 0; i < graph.size(); i++){
            hash.add(graph.get(i).getCity().getName(), graph.get(i));
        }
    }

    private void searchCityAVL(String city) {
        AVL.AVLNode cityNode = avl.existsCity(city);
        if (cityNode != null){
            System.out.println("City found. Showing city data:");
            System.out.println("    " + cityNode.getElement());
        }else {
            System.out.println("Not found. Adding new city.");
            addCityToModel(city);
        }
       //Buscas la ciudad en el arbol, sino existe la añadimos al modelo con la funcion de addCityToModel();
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
        int size = graph.size();
        boolean flag = false;
        for (int i = 0; i < size; i++){
            Node n = graph.get(i);
            CityModel cityModel = n.getCity();
            if (city.toLowerCase().equals(cityModel.getName().toLowerCase())) {
                flag = true;
                break;
            }
        }
        if (!flag){
            addCityToModel(city);
        }
    }

    private void addCityToModel(final String city) {
        WSGoogleMaps.getInstance().setApiKey(API_KEY);
        HttpRequest.HttpReply httpReply = new HttpRequest.HttpReply() {
            @Override
            public void onSuccess(String s) {
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
            }

            @Override
            public void onError(String s) {
                System.out.println("We couldn't found the city.");
            }
        };

        WSGoogleMaps.getInstance().geolocate(city, httpReply);
        httpReply = new HttpRequest.HttpReply() {
            @Override
            public void onSuccess(String s) {
                JsonElement jelement = new JsonParser().parse(s);
                JsonObject jobject = jelement.getAsJsonObject();
                int mindistance = EARTH_LONG;
                int minvalue = 0;
                boolean exists = false;
                for (int i = 0; i < jobject.get("rows").getAsJsonArray().get(0).getAsJsonObject().get("elements").getAsJsonArray().size()-1; i++){
                    int distance = jobject.get("rows").getAsJsonArray().get(0).getAsJsonObject().get("elements").getAsJsonArray().get(i).getAsJsonObject().get("distance").getAsJsonObject().get("value").getAsInt();
                    if (MIN_DISTANCE > distance){
                        graph.getLastOne().getConnections().add(new ConnectionModel (
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
                    } else if (mindistance > distance){
                        mindistance = distance;
                        minvalue = i;
                    }
                }
                if (!exists){
                    //System.out.println("aux: " + graph.getLastOne());
                    graph.getLastOne().getConnections().add(new ConnectionModel (
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
                //Actualizamos las demás estructuras.
                createHashList();
                avl = new AVL(graph);
            }

            @Override
            public void onError(String s) {
                System.out.println("We couldn't found the city.");
            }
        };

        WSGoogleMaps.getInstance().distance(graph.getLastOne().getCity().getLatitude(), graph.getLastOne().getCity().getLongitude(), graph.getLats(),graph.getLongs(), httpReply);

        System.out.println(graph.toString());

    }

    private void importMap() {
        System.out.println("Introduce JSON file name with the path ([path]/[jsonFileName].json).");
        Scanner sc = new Scanner(System.in);
        String fileEntry = sc.nextLine();
        JsonObject jsonObject = null;
        if (fileEntry.endsWith("json")){
            try {
                jsonObject = new Gson().fromJson(new FileReader(fileEntry), JsonObject.class);
                readJson(jsonObject);
                System.out.println("JSON file imported correctly!");
            } catch (FileNotFoundException e) {
                System.out.println("Please introduce a valid file.\n");
            }
        } else {
            System.out.println("Invalid file type. The file must be a JSON file.");
        }
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
            //TODO: COMPROBAR SI HACE BIEN. AÑADIR PARIS Y HACER 1 Y 2 CON LUGO
            graph.dijkstra(from, to, T_NotD);
        }else {
            System.out.println("Error! Either origin city or destination city where not found. Please check if spelling is correct.");
        }

    }


    /**
     * Menu pricipal del programa.
     * @return      Opción donde el usuario quiere aceder.
     */
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
