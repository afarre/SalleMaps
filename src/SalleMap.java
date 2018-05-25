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
    private HashList hash;
    private RedBlackTree rbt;
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
        while (option != 4){
            try {
                option = menu();
                switch (option){
                    case 1: //Importar mapa. Lectura json.
                        importMap();
                        //hash = new HashList(graph.getList());
                        //rbt = new RedBlackTree(graph.getList());
                        jsonIntroduced = true;
                        break;
                    case 2: //Buscar ciudad. Sino existe, añadir ciudad que no existe.
                        if (!jsonIntroduced){
                            System.out.println("You must complete option 1 before selecting this option.");
                            break;
                        }
                        int structure = 0;
                        while (structure != 1 && structure != 2 && structure != 3){
                            structure = chooseStructure();
                        }
                        System.out.println("Insert city name:");
                        Scanner sc = new Scanner(System.in);
                        switch (structure){
                            case 1:
                                searchCityRBT(sc.nextLine());
                                break;
                            case 2:
                                searchCityHash(sc.nextLine());
                                break;
                            case 3:
                                searchCity(sc.nextLine());
                                break;
                        }
                        break;
                    case 3: //Calcular ruta. Utiliza el método necesario en cada caso.
                        if (!jsonIntroduced){
                            System.out.println("You must complete option 1 before selecting this option.");
                            break;
                        }
                        structure = 0;
                        while (structure != 1 && structure != 2 && structure != 3){
                            structure = chooseStructure();
                        }
                        sc = new Scanner (System.in);
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
                        switch (structure){
                            case 1:
                                calculateRouteRBT(from, to, type);
                                break;
                            case 2:
                                calculateRouteHash(from, to ,type);
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

    private void calculateRouteHash(String from, String to, int type) {
        if (!rbt.searchRoute(from, to, type)){
            System.out.println("No route found");
        }else {
            //TODO: SHOW ROUTE INFO
        }
    }

    private void calculateRouteRBT(String from, String to, int type) {
        if (!rbt.searchRoute(from, to, type)){
            System.out.println("No route found");
        }else {
            //TODO: SHOW ROUTE INFO
        }
    }

    private void searchCityHash(String city) {
        if (!hash.searchCity(city)){
            addCityToModel(city);
            hash.add(city, graph.getLastOne().getCity());
            //TODO: INFORM CITY ADDED
        }else {
            //TODO: RETURN CITY INFO
        }
    }

    private void searchCityRBT(String city) {
        if (!rbt.searchCity(city)){
            addCityToModel(city);
            rbt.add(graph.getLastOne().getCity());
            //TODO: INFORM CITY ADDED
        }else {
            //TODO: RETURN CITY INFO
        }
    }

    private int chooseStructure() throws InputMismatchException{
        System.out.println("\nChoose structure:");
        System.out.println("\t1.Red-Black-Tree(RBT)");
        System.out.println("\t2.Hash table");
        System.out.println("\t3. No optimization");
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
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

    private void addCityToModel(String city) {
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
                        jobject.get("results").getAsJsonArray().get(0).getAsJsonObject().getAsJsonObject().get("geometry").getAsJsonObject().get("location").getAsJsonObject().get("lat").getAsLong(),
                        jobject.get("results").getAsJsonArray().get(0).getAsJsonObject().getAsJsonObject().get("geometry").getAsJsonObject().get("location").getAsJsonObject().get("lng").getAsLong());
                Node node = new Node(cityModel);
                graph.add(node);
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
