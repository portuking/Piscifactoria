package piscifactoria;

import pez.especies.Pececillo;

/**
 * Clase que efect�a la l�gica de la piscifactor�a.
 * @author Adri�n
 */
public class Piscifactoria {

	/** Nombre de la piscifactor�a **/
	private String name;
	/** Las referencias a los peces **/
	private Pececillo[] tank;
	/** El n�mero de peces de la piscifactor�a **/
	private int occuped = 0;
	/** El n�mero de peces vendidos **/
	private int selled = 0;
	/** La comida en la piscifactor�a **/
	private int food = 0;
	/** El m�ximo de comida n�mero m�ximo de peces*2 **/
	private final int maxFood;

	/**
	 * Constructor parametrizado
	 *
	 * @param num El numero de peces permitidos.
	 * @param name El nombre del invernadero
	 */
	public Piscifactoria(int num, String name) {
		this.name = name;
		this.tank = new Pececillo[num];
		this.maxFood = num * 2;
	}

	/**
	 * Constructor parametrizado con nombre gen�rico
	 *
	 * @param num El n�mero de peces permitidos.
	 */
	public Piscifactoria(int num) {
		this(num, "Piscifactoria");
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * A�ade un nuevo pez si hay espacio e informa de ello.
	 */
	public void addFish() {
		if (this.occuped < this.tank.length) {
			this.tank[this.occuped] = new Pececillo();
			this.occuped++;
			System.out.println("Nuevo pez a�adido a la piscifactor�a [" + this.name + "]");
		} else {
			System.out.println("La piscifactor�a [" + this.name + "] est� llena");
		}
	}

	/**
	 * A�ade comida si hay espacio para almacenarla.
	 *
	 * @param cant La cantidad de comida a a�adir.
	 */
	public void addFood(int cant) {
		if (this.food < this.maxFood) {
			if (this.food + cant > this.maxFood) {
				this.food = this.maxFood;
			} else {
				this.food += cant;
			}
			System.out.println("Comida a�adida a la piscifactor�a [" + this.name + "]");
		} else {
			System.out.println("No hay espacio para m�s comida en la piscifactor�a [" + this.name + "]");
		}
	}

	/**
	 * @return El n�mero de peces adultos.
	 */
	private int mature() {
		int mat = 0;
		for (int i = 0; i < this.occuped; i++) {
			mat += (this.tank[i].isMature() ? 1 : 0);
		}
		return mat;
	}

	/**
	 * @return El n�mero de peces f�rtiles.
	 */
	private int fertile() {
		int fer = 0;
		for (int i = 0; i < this.occuped; i++) {
			fer += (this.tank[i].isFertile() ? 1 : 0);
		}
		return fer;
	}

	/**
	 * Muestra los datos de la comida en la piscifactor�a.
	 */
	public void getFoodData() {
		System.out.println("Reservas de comida: " + this.food + "/" + this.maxFood);
	}

	/**
	 * Muestra el estado de la piscifactor�a
	 */
	public void status() {
		System.out.println("=============== " + this.name + " ===============");
		System.out.println("Ocupaci�n: " + this.occuped + "/" + tank.length);
		this.getFoodData();
		System.out.println("Peces maduros: " + this.mature() + "/" + this.occuped);
		System.out.println("Peces f�rtiles: " + this.fertile() + "/" + this.occuped);
		System.out.println("Producido: " + this.selled);
		for (int i = 0; i < this.occuped; i++) {
			this.tank[i].status();
		}
		System.out.println("===========================================");
	}

	/**
	 * Hace crecer los peces un d�a. E indica si ha muerto alguno, elimin�ndolos del tanque.
	 */
	public void nextDay() {
		int dead = 0;
		int pop = 0;
		System.out.println("De los " + this.occuped + " peces que hab�a originalmente:");
		for (int i = 0; i < this.occuped; i++) {
			this.tank[i].grow(this.food--);
			if (this.tank[i].isDead()) {
				this.tank[i] = null;
				dead++;
			}
			if(this.tank[i].isFertile() && this.occuped + pop < this.tank.length)
			{
				this.tank[this.occuped + pop] = (Pececillo) this.tank[i].populate();
				pop++;
			}
		}
		if(this.food < 0)
		{
			this.food = 0;
		}
		this.occuped += pop; //Fuera para no modificar el bucle.
		this.recreate();
		System.out.println("Han muerto " + dead + " peces");
		System.out.println("Se han reproducido " + pop + " peces");
		System.out.println("Quedando un total de " + this.occuped + " peces.");
	}

	/**
	 * Vende los peces maduros, quit�ndolos de la piscifactor�a.
	 *
	 * @return El n�mero de peces vendidos
	 */
	public int sell() {
		int sell = 0;
		for (int i = 0; i < this.occuped; i++) {
			if (this.tank[i].isMature()) {
				sell++;
				this.tank[i] = null;
			}
		}
		this.selled += sell;
		this.recreate(); //Ya se encarga de reducir los ocupados.
		return sell;
	}

	/**
	 * Reinicia el orden del tanque sin nulos.
	 */
	private void recreate() {
		Pececillo[] postTank = new Pececillo[this.tank.length];
		int newOccup = 0;
		for (int i = 0; i < this.occuped; i++) {
			if (this.tank[i] != null) {
				postTank[newOccup] = this.tank[i];
				newOccup++;
			}
		}
		this.tank = postTank;
		this.occuped = newOccup;
	}

	/**
	 * Vac�a la piscifactor�a sin vender los peces.
	 */
	public void empty() {
		for (int i = 0; i < this.occuped; i++) {
			this.tank[i] = null;
		}
		this.occuped = 0;
	}

	@Override
	public String toString() {
		return "Piscifactor�a [" + this.name + "] con " + this.occuped + "/" + this.tank.length + " peces";
	}
}
