package pez.especies;

import pez.IPez;

/**
 * Clase que representa un pez gen�rico
 * @author Adri�n
 */
public class Pececillo implements IPez {

	private int age = 0;
	
	@Override
	public String getName()
	{
		return "Pececillo";
	}

	@Override
	public String getScientificName()
	{
		return "Pez gen�rico";
	}

	@Override
	public void info()
	{
		System.out.println("--------------- Pececillo ---------------");
		System.out.println("Nombre cient�fico: Pez gen�rico");
		System.out.println("Es adulto y f�rtil al segundo d�a");
		System.out.println("Crecimiento m�ximo: Infinitos d�as");
		System.out.println("Come si hay comida, pero le da igual");
	}

	@Override
	public void status()
	{
		System.out.println("--------------- Pececillo ---------------");
		System.out.println("Edad: " + this.age + " d�as");
		System.out.println("Maduro: " + (this.isMature() ? "Si" : "No"));
		System.out.println("F�rtil: " + (this.isFertile() ? "Si" : "No"));
	}

	@Override
	public void grow(int food)
	{
		this.age++;
	}

	@Override
	public boolean isMature()
	{
		return this.age > 1;
	}

	@Override
	public boolean isFertile()
	{
		return this.age > 1;
	}

	@Override
	public boolean isDead()
	{
		return false;
	}
	
	@Override
	public String toString()
	{
		return "Pececillo (Pez Gen�rico) con " + this.age + " d�as";
	}
    
	public IPez populate()
	{
		return new Pececillo();
	}
}
