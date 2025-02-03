package pez;

/**
 * Clase base para peces avanzados
 * @author Adrián
 *
 */
public abstract class Pez implements IPez {

	/** El nombre del pez **/
	protected String name;
	/** El nombre científico del pez **/
	protected String scientificName;
	/** La edad dedel pez **/
	protected int age = 0;
	/** Si el pez es adulto **/
	protected boolean mature = false;
	/** Si el pez es fértil **/
	protected boolean fertile = false;
	/** Si el pez está muerto **/
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
		System.out.println("Nombre científico: " + this.scientificName);
	}

	@Override
	public void status() {
		System.out.println("--------------- " + this.name + " ---------------");
		System.out.println("Vivo: " + (!this.dead ? "Si" : "No"));
		System.out.println("Edad: " + this.age + " días");
		System.out.println("Adulto: " + (this.mature ? "Si" : "No"));
		System.out.println("Fértil: " + (this.fertile ? "Si" : "No"));
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
		return this.name + "(" + this.scientificName + ") con " + this.age + " días";
	}
	
	@Override
	public abstract IPez populate();

}
