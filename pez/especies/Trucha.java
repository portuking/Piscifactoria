package pez.especies;

import pez.IPez;
import pez.Pez;

/**
 * Clase que representa una Trucha
 * @author Adri�n
 */
public class Trucha extends Pez {

	public Trucha()
	{
		this.name = "Trucha";
		this.scientificName = "Salmo Trutta";
	}
	
	@Override
	public void info() {
		super.info();
		System.out.println("Madura tras 3 d�as");
		System.out.println("Se reproduce tras 3 d�as");
		System.out.println("Crecimiento m�ximo: 10 d�as");
	}

	@Override
	public void grow(int food) {
		if(!this.dead)
		{
			this.age++;
			this.mature = (this.age > 2);
			this.fertile = (this.age > 2);
			this.dead = (this.age > 9 || food <= 0);
		}
	}
	
	public IPez populate()
	{
		return new Trucha();
	}

}
