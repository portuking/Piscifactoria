package peces;

import java.net.Socket;

public class Pez {
    /** Nombre Común del Pez */
    private String name;
    /** Nombre Científico del Pez */
    private String scientificName;
    /** Edad del Pez */
    private int age;
    /** Sexo del Pez */
    private boolean sex;
    /** Si el Pez es fértil */
    private boolean fertile;
    /** Si el Pez está vivo */
    private boolean alive;
    /** Si el Pez tiene hambre o no */
    private boolean hungry;
    /** Si el Pez es adulto */
    private boolean mature;

    /**
     * Constructor de Pez
     * @param name Nombre del Pez
     * @param scientificName Nombre Científico del Pez
     * @param age Edad del Pez
     * @param sex Sexo del Pez
     * @param fertile Si el Pez es Fértil 
     * @param alive Si el pez está vivo
     * @param hungry Si el pez está hambriento
     * @param mature Si el Pez es maduro
     */
    public Pez(String name, String scientificName, int age, boolean sex, boolean fertile, boolean alive, boolean hungry,boolean mature) {
        this.name = name;
        this.scientificName = scientificName;
        this.age = 0;
        this.sex = sex;
        this.fertile = false;
        this.alive = true;
        this.hungry = true;
        this.mature = false;
    }

    /** Método que muestra el estado del Pez */
    public void showStatus() {
        System.out.println("---------------Nombre---------------");
        System.out.println("Edad: " + this.age + " días");
        System.out.println(sex ? "Sexo: H" : "Sexo: M");
        System.out.println(hungry ? "Alimentado: Si" : "Alimentado: No");
        System.out.println(mature ? "Adulto: Si" : "Adulto: No");
        System.out.println(fertile ? "Fértil: Si" : "Fértil: No");
    }

    /**
     * Método que hace crecer un Pez
     */
    public void grow(){
        if(this.alive != false) {
            this.age+=1;
            
        }
    }

    /**
     * Método que resetea un Pez
     */
    public void reset(){
        this.name = name;
        this.scientificName = scientificName;
        this.age = 0;
        this.sex = sex;
        this.fertile = false;
        this.alive = true;
        this.hungry = true;
        this.mature = false;
    }
}
