package pez.especies;

import pez.IPez;
import pez.Pez;

/**
 * Clase que representa un rodaballo
 * @author Adri�n
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
		System.out.println("Madura tras 6 d�as");
		System.out.println("Se reproduce tras 5 d�as");
		System.out.println("Crecimiento m�ximo: 20 d�as");
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
