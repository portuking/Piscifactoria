package sistema;

public class Helper {

    /**
     * Muestra un menú con las opciones pasadas como parámetro y devuelve la opción seleccionada por el usuario.
     * La última opción es para salir.
     * @param titulo El título del menú.
     * @param opciones Un arreglo con las opciones del menú.
     * @return La opción seleccionada (si es igual a opciones.length + 1, se entiende que el usuario eligió salir).
     */
    public int mostrarMenu(String titulo, String[] opciones) {
        System.out.println("\n===== " + titulo + " =====");
        for (int i = 0; i < opciones.length; i++) {
            System.out.println((i + 1) + ". " + opciones[i]);
        }
        // La última opción será para salir
        System.out.println((opciones.length + 1) + ". Salir");
 
        return leerOpcion(opciones.length + 1);
    }

    /**
     * Método para leer y validar la entrada del usuario.
     * Se aceptan números entre 1 y maxOpcion (incluido).
     * @param maxOpcion El número máximo permitido (incluye la opción de salir).
     * @return La opción seleccionada.
     */
    public int leerOpcion(int maxOpcion) {
        int opcion = -1;
        while (true) {
            System.out.print("Seleccione una opción: ");
            String input = System.console().readLine();
            try {
                opcion = Integer.parseInt(input);
                if (opcion >= 1 && opcion <= maxOpcion) {
                    return opcion;
                } else {
                    System.out.println("Opción fuera de rango. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Ingrese un número.");
            }
        }
    }
}