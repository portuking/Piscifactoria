package piscifactoria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Clase Main del sistema
 * @author Adri�n
 */
public class Main {
    
	/**
	 * Muestra el men� por pantalla
	 */
	private static void showMenu()
	{
		System.out.println("-----------------------------------");
		System.out.println("0. Estado.");
		System.out.println("1. Avanzar el d�a.");
		System.out.println("2. A�adir un pez.");
		System.out.println("3. Vender peces");
		System.out.println("4. A�adir 5 de comida");
		System.out.println("5. Vaciar el tanque");
		System.out.println("6. Terminar");
		System.out.println("Operaci�n a realizar: ");
	}

	public static void main(String[] args)
	{
		boolean run = true;
		Piscifactoria tanque = new Piscifactoria(10);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String op = "";
        
        int day = 0;
		
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ D�a " + day + " ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		tanque.status();
		while(run)
		{
			showMenu();
			try {
				op = reader.readLine();
			} catch (IOException e) {
				System.err.println("Fallo de lectura.");
				e.printStackTrace();
				op = "6"; //Finalizo
			}
			
			switch(op)
			{
				case "0":
					System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ D�a " + day + " ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					tanque.status();
				break;
				case "1":
					day++;
					tanque.nextDay();
				break;
				case "2":
					tanque.addFish();
				break;
				case "3":
					System.out.println("Vendido: " + tanque.sell());
				break;
				case "4":
					tanque.addFood(5);
				break;
				case "5":
					tanque.empty();
					System.out.println("Piscifactor�a vaciada");
				break;
				default:
					run = false;
					System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Terminado ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			}
		}
		
		try {
			reader.close()
		}catch (IOException e){
			
		}
	}
}
