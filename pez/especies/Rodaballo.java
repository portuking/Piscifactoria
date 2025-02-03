package pez.especies;

import pez.IPez;
import pez.Pez;

/**
 * Clase que representa un rodaballo
 * @author Adrián
 */
public class Rodaballo extends Pez {

	public Rodaballo()
	{
		this.name = "Rodaballo";
		this.scientificName = "Scophthalmus maxima";
	}
	
	@Override
	public void info() {
		super.info();
		System.out.println("Madura tras 6 días");
		System.out.println("Se reproduce tras 5 días");
		System.out.println("Crecimiento máximo: 20 días");
	}

	@Override
	public void grow(int food) {
		if(!this.dead)
		{
			this.age++;
			this.mature = (this.age > 5);
			this.fertile = (this.age > 4);
			this.dead = (this.age > 19 || food <= 0);
		}
	}
	
	public IPez populate()
	{
		return new Rodaballo();
	}

}
