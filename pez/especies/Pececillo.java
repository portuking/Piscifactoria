package pez.especies;

import pez.IPez;

/**
 * Clase que representa un pez genérico
 * @author Adrián
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
		return "Pez genérico";
	}

	@Override
	public void info()
	{
		System.out.println("--------------- Pececillo ---------------");
		System.out.println("Nombre científico: Pez genérico");
		System.out.println("Es adulto y fértil al segundo día");
		System.out.println("Crecimiento máximo: Infinitos días");
		System.out.println("Come si hay comida, pero le da igual");
	}

	@Override
	public void status()
	{
		System.out.println("--------------- Pececillo ---------------");
		System.out.println("Edad: " + this.age + " días");
		System.out.println("Maduro: " + (this.isMature() ? "Si" : "No"));
		System.out.println("Fértil: " + (this.isFertile() ? "Si" : "No"));
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
		return "Pececillo (Pez Genérico) con " + this.age + " días";
	}
    
	public IPez populate()
	{
		return new Pececillo();
	}
}
