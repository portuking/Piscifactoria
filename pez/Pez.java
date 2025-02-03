package pez;

/**
 * Clase base para peces avanzados
 * @author Adri�n
 *
 */
public abstract class Pez implements IPez {

	/** El nombre del pez **/
	protected String name;
	/** El nombre cient�fico del pez **/
	protected String scientificName;
	/** La edad dedel pez **/
	protected int age = 0;
	/** Si el pez es adulto **/
	protected boolean mature = false;
	/** Si el pez es f�rtil **/
	protected boolean fertile = false;
	/** Si el pez est� muerto **/
	protected boolean dead = false;
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getScientificName() {
		return this.scientificName;
	}

	@Override
	public void info() {
		System.out.println("--------------- " + this.name + " ---------------");
		System.out.println("Nombre cient�fico: " + this.scientificName);
	}

	@Override
	public void status() {
		System.out.println("--------------- " + this.name + " ---------------");
		System.out.println("Vivo: " + (!this.dead ? "Si" : "No"));
		System.out.println("Edad: " + this.age + " d�as");
		System.out.println("Adulto: " + (this.mature ? "Si" : "No"));
		System.out.println("F�rtil: " + (this.fertile ? "Si" : "No"));
	}

	@Override
	public abstract void grow(int food);

	@Override
	public boolean isMature() {
		return this.mature;
	}

	@Override
	public boolean isFertile() {
		return this.fertile;
	}

	@Override
	public boolean isDead() {
		return this.dead;
	}
	
	@Override
	public String toString() {
		return this.name + "(" + this.scientificName + ") con " + this.age + " d�as";
	}
	
	@Override
	public abstract IPez populate();

}
