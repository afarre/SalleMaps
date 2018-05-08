import Model.City;
import Network.HttpRequest;
import Network.WSGoogleMaps;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase principal del proyecto SalleMap.
 * Desde aquí se llaman y se utilizan todos los recursos necesarios para hacer funcionar este proyecto.
 * @author Javier Ortiz & Angel
 */

public class SalleMap {

    private Graph graph;

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
                        jsonIntroduced = true;
                        break;
                    case 2: //Buscar ciudad. Sino existe, añadir ciudad que no existe.
                        if (!jsonIntroduced){
                            System.out.println("You must complete option 1 before selecting this option.");
                            break;
                        }else {
                            Scanner sc = new Scanner(System.in);
                            searchCity(sc.nextLine());
                        }
                        System.out.println("BUSCAR CIUDAD");
                        break;
                    case 3: //Calcular ruta. Utiliza el método necesario en cada caso.
                        if (!jsonIntroduced){
                            System.out.println("You must complete option 1 before selecting this option.");
                            break;
                        }
                        System.out.println("CALCULAR RUTA");
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

    private void searchCity(String city) {
        int size = graph.getGraph().size();
        boolean flag = false;
        for (int i = 0; i < size; i++){
            Graph.Node n = (Graph.Node) graph.getGraph().getListElement(i);
            CityModel cityModel = n.getCity();
            if (city.equals(cityModel.getName())) {
                flag = true;
                cityModel.toString();
                break;
            }
        }
        if (!flag){
            addCityToModel(city);
        }
    }

    private void addCityToModel(String city) {
        WSGoogleMaps.getInstance().setApiKey("AIzaSyCMG_IEevGb9kFUfR_DVgQIT0Gfqrz3S_I");
        HttpRequest.HttpReply httpReply = new HttpRequest.HttpReply() {
            @Override
            public void onSuccess(String s) {
                //ToDo: Añadir Ciudad al sistema
                //String s = información de las ciudades en formato JSON.
            }

            @Override
            public void onError(String s) {
                System.out.println("We couldnt found the city.");
            }
        };
        WSGoogleMaps.getInstance().geolocate(city, httpReply);

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
        MyList cities = new MyList();
        MyList connections = new MyList();

        int sizecities = jsonObject.get("cities").getAsJsonArray().size();
        System.out.println("Tamaño JSON:" + sizecities);
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


    /**
     * Menu pricipal del programa.
     * @return      Opción donde el usuario quiere aceder.
     */
    private int menu () throws InputMismatchException{
        Scanner sc = new Scanner (System.in);
        int value;
        System.out.println("\n\t1-. Import map.");
        System.out.println("\t2-. Search city.");
        System.out.println("\t3-. Calculate rout.");
        System.out.println("\t4-. Shut down.");
        System.out.println("\nSelect an option:");

        value = sc.nextInt();
        sc.nextLine();

        return value;
    }
}
