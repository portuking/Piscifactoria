package pez.especies;

import pez.IPez;
import pez.Pez;

/**
 * Clase que representa una Trucha
 * @author Adrián
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
		System.out.println("Madura tras 3 días");
		System.out.println("Se reproduce tras 3 días");
		System.out.println("Crecimiento máximo: 10 días");
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
