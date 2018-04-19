import java.sql.SQLOutput;
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
        int option = 0;
        while (option != 4){
            option = menu();
            switch (option){
                case 1: //Importar mapa. Lectura json.
                    System.out.println("IMPORTAR MAPA");
                    break;
                case 2: //Buscar ciudad. Sino existe, añadir ciudad que no existe.
                    System.out.println("BUSCAR CIUDAD");
                    break;
                case 3: //Calcular ruta. Utiliza el método necesario en cada caso.
                    System.out.println("CALCULAR RUTA");
                    break;
                case 4: //Salir del programa
                    System.out.println("\nGood bye!");
                    System.out.println("SalleMaps by Javier Ortiz & Angel SinApellido");
                    break;
                default:
                    System.out.println("This option doesn't exist.\nSelect a valid option.");
                    break;
            }
        }
    }


    /**
     * Menu pricipal del programa.
     * @return      Opción donde el usuario quiere aceder.
     */
    private int menu (){
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
