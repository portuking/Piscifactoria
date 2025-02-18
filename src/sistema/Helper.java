package sistema;

public class Helper {

    /**
     * Muestra un menú con las opciones pasadas como parámetro y devuelve la opción
     * seleccionada por el usuario.
     * La última opción es para salir.
     * 
     * @param titulo   El título del menú.
     * @param opciones Un array con las opciones del menú.
     * @param extraOps Un array con opciones adicionales que no están en el menú.
     * @return La opción seleccionada (si es igual a opciones.length + 1, se
     *         entiende que el usuario eligió salir).
     */
    public int mostrarMenu(String titulo, String[] opciones, int[] extraOps) {
        System.out.println("\n===== " + titulo + " =====");
        for (int i = 0; i < opciones.length; i++) {
            System.out.println((i + 1) + ". " + opciones[i]);
        }
        // La última opción será para salir
        System.out.println((opciones.length + 1) + ". Salir");

        return leerOpcion(opciones.length + 1, extraOps);
    }

    /**
     * Método para leer y validar la entrada del usuario.
     * Se aceptan números entre 1 y maxOpcion (incluido).
     * 
     * @param maxOpcion El número máximo permitido (incluye la opción de salir).
     * @param extraOps  Un array con opciones adicionales que no están en el menú.
     * @return La opción seleccionada.
     */
    public int leerOpcion(int maxOpcion, int[] extraOps) {
        int opcion = -1;
        while (true) {
            System.out.print("Seleccione una opción: ");
            String input = System.console().readLine();
            try {
                opcion = Integer.parseInt(input);
                if ((opcion >= 1 && opcion <= maxOpcion) || isExtraOp(opcion, extraOps)) {
                    return opcion;
                } else {
                    System.out.println("Opción fuera de rango o no válida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Ingrese un número.");
            }
        }
    }

    /**
     * Verifica si la opción seleccionada por el usuario es una opción adicional.
     * @param opcion La opción seleccionada por el usuario.
     * @param extraOps Un array con opciones adicionales que no están en el menú.
     * @return
     */
    private boolean isExtraOp(int opcion, int[] extraOps) {
        for (int op : extraOps) {
            if (op == opcion) {
                return true;
            }
        }
        return false;
    }
}