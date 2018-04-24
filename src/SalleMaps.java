import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase principal del proyecto SalleMaps.
 * Desde aquí se llaman y se utilizan todos los recursos necesarios para hacer funcionar este proyecto.
 * @author Javier Ortiz & Angel
 */

public class SalleMaps {


    //Constructor
    public SalleMaps (){}

    //Procedimientos y Funciones

    /**
     * Inicializa el programa.
     */
    public void init (){
        System.out.println("Welcome to SalleMaps.");
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
                        System.out.println("SalleMaps by Javier Ortiz & Angel Farre");
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

    private void importMap() {
        System.out.println("Introduce JSON file name.");
        Scanner sc = new Scanner(System.in);
        String fileEntry = sc.nextLine();
        GraphModel graphModel = null;
        if (fileEntry.endsWith("json")){
            try {
                graphModel = new Gson().fromJson(new FileReader("graphs/" + fileEntry), GraphModel.class);
            } catch (FileNotFoundException e) {
                System.out.println("Please introduce a valid file.\n");
                importMap();
            }
        } else {
            System.out.println("Invalid file type. The file must be a JSON file.");
            importMap();
        }

/*
        for (int  i = 0; i < graphModel.getCities().size(); i++){
            System.out.println(graphModel.getCities().get(i).getName());
            System.out.println(graphModel.getCities().get(i).getAddress());
            System.out.println(graphModel.getCities().get(i).getCountry());
            System.out.println(graphModel.getCities().get(i).getLatitude());
            System.out.println(graphModel.getCities().get(i).getLongitude());

            System.out.println(graphModel.getConnections().get(i).getFrom());
            System.out.println(graphModel.getConnections().get(i).getTo());
            System.out.println(graphModel.getConnections().get(i).getDuration());
            System.out.println(graphModel.getConnections().get(i).getDistance());
            System.out.println("_____________________________");
        }
*/

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
