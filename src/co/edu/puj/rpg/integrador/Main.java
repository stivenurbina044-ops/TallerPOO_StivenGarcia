package co.edu.puj.rpg.integrador;

import co.edu.puj.rpg.integrador.engine.Bestiario;
import co.edu.puj.rpg.integrador.engine.CatalogoTienda;
import co.edu.puj.rpg.integrador.exception.CapacidadExcedidaException;
import co.edu.puj.rpg.integrador.exception.SinManaException;
import co.edu.puj.rpg.integrador.model.Item;
import co.edu.puj.rpg.integrador.model.PerfilJugador;
import co.edu.puj.rpg.integrador.model.Personaje;
import co.edu.puj.rpg.integrador.model.enums.RaridadItem;
import co.edu.puj.rpg.integrador.model.enums.TipoPersonaje;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    // Lectura numerica blindada con try-catch-finally
    static int leerEntero(Scanner sc, String mensaje) {
        int valor = 0;
        boolean valido = false;
        while (!valido) {
            System.out.print(mensaje);
            try {
                valor = sc.nextInt();
                valido = true;
            } catch (InputMismatchException e) {
                sc.nextLine(); // limpia el bufer
                System.err.println("Entrada invalida: ingresa un numero entero.");
            } finally {
                if (valido) {
                    sc.nextLine(); // consume el salto de linea pendiente
                }
            }
        }
        return valor;
    }

    static String leerTexto(Scanner sc, String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    static Personaje seleccionarPersonaje(Scanner sc, List<Personaje> personajes) {
        if (personajes.isEmpty()) {
            System.out.println("Primero crea un personaje.");
            return null;
        }
        for (int i = 0; i < personajes.size(); i++) {
            Personaje p = personajes.get(i);
            System.out.printf("  %d. %s (%s)%n", i + 1, p.getNombre(), p.getTipoPersonaje());
        }
        int n = leerEntero(sc, "Personaje: ");
        if (n < 1 || n > personajes.size()) {
            System.out.println("Seleccion invalida.");
            return null;
        }
        return personajes.get(n - 1);
    }

    static void mostrarPersonaje(Personaje p) {
        System.out.printf("%s | %s | %s | Vida: %d/%d | Mana: %d/%d | Ataque: %d | Defensa: %d%n",
                p.getNombre(), p.getTipoPersonaje(), p.getEstado(),
                p.getVida(), p.getVidaMaxima(), p.getMana(), p.getManaMaximo(),
                p.calcularAtaque(), p.calcularDefensa());
        System.out.println("  Habilidades: " + p.obtenerHabilidades());
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Personaje> personajes = new ArrayList<>();
        Bestiario bestiario = new Bestiario();
        PerfilJugador perfil = new PerfilJugador();
        CatalogoTienda tienda = new CatalogoTienda();

        tienda.agregarItem(new Item("Pocion de vida", 30.0, RaridadItem.COMUN));
        tienda.agregarItem(new Item("Espada runica", 100.0, RaridadItem.RARO));
        tienda.agregarItem(new Item("Amuleto del dragon", 200.0, RaridadItem.LEGENDARIO));

        int opcion;
        do {
            System.out.println("\n=== ARENA DE HEROES RPG ===");
            System.out.println("1. Crear Personaje");
            System.out.println("2. Asignar Habilidad Unica");
            System.out.println("3. Registrar Victoria en Bestiario");
            System.out.println("4. Ejecutar Ataque Especial");
            System.out.println("5. Consultar Bestiario y Logros");
            System.out.println("6. Salir");
            opcion = leerEntero(sc, "Seleccione una opcion: ");

            switch (opcion) {
                case 1: {
                    String nombre = leerTexto(sc, "Nombre del personaje: ");
                    if (nombre.isEmpty()) {
                        System.out.println("El nombre no puede estar vacio.");
                        break;
                    }
                    TipoPersonaje[] tipos = TipoPersonaje.values();
                    for (int i = 0; i < tipos.length; i++) {
                        System.out.printf("  %d. %s%n", i + 1, tipos[i]);
                    }
                    int t = leerEntero(sc, "Tipo: ");
                    if (t < 1 || t > tipos.length) {
                        System.out.println("Tipo invalido.");
                        break;
                    }
                    Personaje p = new Personaje(nombre, tipos[t - 1]);
                    personajes.add(p);
                    System.out.println("Personaje creado:");
                    mostrarPersonaje(p);
                    break;
                }
                case 2: {
                    Personaje p = seleccionarPersonaje(sc, personajes);
                    if (p == null) {
                        break;
                    }
                    String habilidad = leerTexto(sc, "Nombre de la habilidad: ");
                    if (habilidad.isEmpty()) {
                        System.out.println("La habilidad no puede estar vacia.");
                        break;
                    }
                    try {
                        if (p.aprenderHabilidad(habilidad)) {
                            System.out.printf("%s aprendio %s.%n", p.getNombre(), habilidad);
                        } else {
                            System.out.println("Advertencia: la habilidad ya esta aprendida.");
                        }
                    } catch (CapacidadExcedidaException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }
                case 3: {
                    String enemigo = leerTexto(sc, "Nombre del enemigo derrotado: ");
                    if (enemigo.isEmpty()) {
                        System.out.println("El nombre no puede estar vacio.");
                        break;
                    }
                    boolean primera = bestiario.registrarVictoria(enemigo);
                    System.out.printf("Victoria registrada contra %s (total: %d).%n",
                            enemigo, bestiario.getCantidad(enemigo));
                    if (primera && perfil.registrarLogro("Primera victoria contra " + enemigo)) {
                        System.out.println("¡Logro desbloqueado: Primera victoria contra " + enemigo + "!");
                    }
                    break;
                }
                case 4: {
                    Personaje p = seleccionarPersonaje(sc, personajes);
                    if (p == null) {
                        break;
                    }
                    if (!p.puedeActuar()) {
                        System.out.printf("%s no puede actuar (estado: %s).%n", p.getNombre(), p.getEstado());
                        break;
                    }
                    int costo = leerEntero(sc, "Costo de mana del ataque: ");
                    if (costo <= 0) {
                        System.out.println("El costo debe ser mayor que 0.");
                        break;
                    }
                    try {
                        int dano = p.lanzarHechizo(costo);
                        System.out.printf("%s lanza su ataque especial e inflige %d de dano. Mana restante: %d%n",
                                p.getNombre(), dano, p.getMana());
                    } catch (SinManaException e) {
                        System.out.println("No se pudo lanzar: " + e.getMessage());
                    }
                    break;
                }
                case 5: {
                    System.out.println("--- Bestiario ---");
                    if (bestiario.getRegistro().isEmpty()) {
                        System.out.println("  (sin victorias registradas)");
                    }
                    for (Map.Entry<String, Integer> entry : bestiario.getRegistro().entrySet()) {
                        System.out.printf("  %-20s %d victoria(s)%n", entry.getKey(), entry.getValue());
                    }
                    System.out.println("--- Logros (orden alfabetico) ---");
                    if (perfil.listarLogrosOrdenados().isEmpty()) {
                        System.out.println("  (sin logros)");
                    }
                    for (String logro : perfil.listarLogrosOrdenados()) {
                        System.out.println("  * " + logro);
                    }
                    System.out.println("--- Catalogo de la tienda (TreeMap) ---");
                    for (Map.Entry<String, Double> entry : tienda.getPrecios().entrySet()) {
                        System.out.printf("  %-20s %8.2f oro%n", entry.getKey(), entry.getValue());
                    }
                    break;
                }
                case 6:
                    System.out.println("Cerrando la Arena de Heroes...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while (opcion != 6);

        sc.close();
    }
}