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

    /** Método que muestra el estado del Pez */
    public void showStatus() {
        System.out.println("---------------Nombre---------------");
        System.out.println("Edad: " + this.age + " días");
        System.out.println(sex ? "Sexo: H" : "Sexo: M");
        System.out.println(hungry ? "Alimentado: Si" : "Alimentado: No");
        System.out.println(mature ? "Adulto: Si" : "Adulto: No");
        System.out.println(fertile ? "Fértil: Si" : "Fértil: No");
    }

    public void grow(){
        if(this.alive != false) {
            this.age+=1;
            
        }
    }
}
