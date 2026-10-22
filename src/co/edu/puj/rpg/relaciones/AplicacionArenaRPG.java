package co.edu.puj.rpg.relaciones;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AplicacionArenaRPG {

    static int leerEntero(Scanner sc, String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Entrada invalida. " + mensaje);
        }
        int valor = sc.nextInt();
        sc.nextLine(); // consume el resto de la linea
        return valor;
    }

    static String leerTexto(Scanner sc, String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    static int pedirIndice(Scanner sc, String mensaje, int total) {
        int n = leerEntero(sc, mensaje);
        if (n < 1 || n > total) {
            System.out.println("Seleccion invalida.");
            return -1;
        }
        return n - 1;
    }

    static void listarPersonajes(List<Personaje> lista) {
        for (int i = 0; i < lista.size(); i++) {
            System.out.printf("  %d. %s%n", i + 1, lista.get(i).getNombre());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        List<Personaje> personajes = new ArrayList<>();
        List<Gremio> gremios = new ArrayList<>();
        List<Escuadron> escuadrones = new ArrayList<>();

        do {
            System.out.println("\n=== MENÚ ARENA DE HÉROES RPG ===");
            System.out.println("1. Crear Personaje");
            System.out.println("2. Crear Gremio");
            System.out.println("3. Reclutar Personaje a Escuadrón");
            System.out.println("4. Equipar Arma a Personaje");
            System.out.println("5. Mostrar Estado Detallado (Personaje y Corazón RPG)");
            System.out.println("6. Salir");
            opcion = leerEntero(scanner, "Seleccione una opción: ");

            switch (opcion) {
                case 1: {
                    String nombre = leerTexto(scanner, "Nombre del personaje: ");
                    if (nombre.isEmpty()) {
                        System.out.println("El nombre no puede estar vacio.");
                        break;
                    }
                    int nivel = leerEntero(scanner, "Nivel inicial: ");
                    Personaje p = new Personaje(nombre, nivel);
                    personajes.add(p);
                    System.out.println("Creado: " + p);
                    break;
                }
                case 2: {
                    String nombre = leerTexto(scanner, "Nombre del gremio: ");
                    if (nombre.isEmpty()) {
                        System.out.println("El nombre no puede estar vacio.");
                        break;
                    }
                    Gremio g = new Gremio(nombre);
                    gremios.add(g);
                    System.out.println("Gremio creado: " + nombre);

                    int mas = personajes.isEmpty() ? 0
                            : leerEntero(scanner, "¿Inscribir un personaje ahora? (1=Si, 0=No): ");
                    while (mas == 1) {
                        listarPersonajes(personajes);
                        int idx = pedirIndice(scanner, "Personaje: ", personajes.size());
                        if (idx >= 0) {
                            personajes.get(idx).unirseAGremio(g);
                        }
                        mas = leerEntero(scanner, "¿Inscribir otro? (1=Si, 0=No): ");
                    }
                    break;
                }
                case 3: {
                    if (personajes.isEmpty()) {
                        System.out.println("Primero crea un personaje.");
                        break;
                    }
                    listarPersonajes(personajes);
                    int idx = pedirIndice(scanner, "Personaje a reclutar: ", personajes.size());
                    if (idx < 0) {
                        break;
                    }
                    String nombreEsc = leerTexto(scanner, "Nombre del escuadrón: ");
                    Escuadron escuadron = null;
                    for (Escuadron e : escuadrones) {
                        if (e.getNombre().equalsIgnoreCase(nombreEsc)) {
                            escuadron = e;
                        }
                    }
                    if (escuadron == null) {
                        escuadron = new Escuadron(nombreEsc);
                        escuadrones.add(escuadron);
                        System.out.println("Escuadrón nuevo creado: " + nombreEsc);
                    }
                    escuadron.reclutarPersonaje(personajes.get(idx));
                    break;
                }
                case 4: {
                    if (personajes.isEmpty()) {
                        System.out.println("Primero crea un personaje.");
                        break;
                    }
                    listarPersonajes(personajes);
                    int idx = pedirIndice(scanner, "Personaje: ", personajes.size());
                    if (idx < 0) {
                        break;
                    }
                    Personaje p = personajes.get(idx);
                    int accion = leerEntero(scanner, "1. Equipar nueva arma  2. Quitar arma: ");
                    if (accion == 1) {
                        String nombreArma = leerTexto(scanner, "Nombre del arma: ");
                        int danio = leerEntero(scanner, "Danio base: ");
                        p.equiparArma(new Arma(nombreArma, danio));
                    } else if (accion == 2) {
                        p.desequiparArma();
                    } else {
                        System.out.println("Accion invalida.");
                    }
                    break;
                }
                case 5: {
                    if (personajes.isEmpty()) {
                        System.out.println("No hay personajes registrados.");
                    } else {
                        System.out.printf("%-12s %5s %-18s %7s %7s %6s %-12s%n",
                                "Nombre", "Nivel", "Arma", "Ataque", "Salud", "Mana", "Gremio");
                        for (Personaje p : personajes) {
                            String arma = (p.getArmaEquipada() == null)
                                    ? "Desarmado" : p.getArmaEquipada().getNombre();
                            String gremio = (p.getGremio() == null)
                                    ? "Sin gremio" : p.getGremio().getNombre();
                            System.out.printf("%-12s %5d %-18s %7d %7d %6d %-12s%n",
                                    p.getNombre(), p.getNivel(), arma, p.calcularAtaqueTotal(),
                                    p.getSaludBase(), p.getManaBase(), gremio);
                        }
                    }
                    System.out.println();
                    for (Gremio g : gremios) {
                        System.out.println(g);
                    }
                    for (Escuadron e : escuadrones) {
                        System.out.println(e);
                    }
                    break;
                }
                case 6:
                    System.out.println("Cerrando la Arena de Héroes...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 6);

        scanner.close();
    }
}