package sistema.helpers;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Clase para manejar las entradas por teclado del Usuario
 * @author Manuel Abalo Rietz
 * @author Adrián Ces López
 * @author Pablo Dopazo Suárez
 */
public class InputHelper {
    /**Vaeiable scanner para manejar entradas*/
    private static Scanner sc = new Scanner(System.in);

    /**
     * Método que pide un número al usuario para elegir una opción
     * @param min número de opción miníma
     * @param max número de opción máxmia
     * @return la elección del usuario
     */
    public static int inNum(int min, int max) {
        int selectedOption =  0;
        System.out.println("Introduzca un número entre: " + min + " y " + max);
        try{
            int input = sc.nextInt();
            if(input < min || input > max){
                System.out.println("se ha introducido un número no válido");
            }else{
                selectedOption = input;
            }
        }catch(InputMismatchException e ){
            System.out.println("No se ha introducido un número");
            sc.next();
        }catch(NoSuchElementException e){
            System.out.println("No hay más elementos disponibles");
        }catch(IllegalStateException e) {
            System.out.println("Error: El Scanner ha sido cerrado");
        }
        return selectedOption;
    }
}
