package co.edu.puj.rpg.arena;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static final int TAM = 5;
    static final int COFRE_X = 2;
    static final int COFRE_Y = 2;

    public static void mostrarTablero(char[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print("[" + matriz[i][j] + "]");
            }
            System.out.println();
        }
    }

    // Reconstruye la matriz desde las posiciones reales y la imprime
    public static void dibujarTablero(char[][] tablero, List<Personaje> heroes, List<Personaje> enemigos) {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j] = '.';
            }
        }
        tablero[COFRE_Y][COFRE_X] = 'C';
        for (Personaje enemigo : enemigos) {
            tablero[enemigo.getPosicionY()][enemigo.getPosicionX()] = 'E';
        }
        for (Personaje heroe : heroes) {
            char simbolo = (heroe instanceof Guerrero) ? 'G' : 'M';
            tablero[heroe.getPosicionY()][heroe.getPosicionX()] = simbolo;
        }
        mostrarTablero(tablero);
    }

    public static void mostrarEstado(String titulo, List<Personaje> lista) {
        System.out.println(titulo);
        for (Personaje p : lista) {
            String mana = "-";
            if (p instanceof Mago mago) {
                mana = String.valueOf(mago.getPuntosMana());
            }
            System.out.printf("  %s | Vida: %d/%d | Mana: %s%n",
                    p.getNombre(), p.getVida(), p.getVidaMaxima(), mana);
        }
    }

    public static boolean hayUnidad(int x, int y, List<Personaje> heroes, List<Personaje> enemigos) {
        for (Personaje heroe : heroes) {
            if (heroe.getPosicionX() == x && heroe.getPosicionY() == y) {
                return true;
            }
        }
        for (Personaje enemigo : enemigos) {
            if (enemigo.getPosicionX() == x && enemigo.getPosicionY() == y) {
                return true;
            }
        }
        return false;
    }

    public static int leerEntero(Scanner sc, String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Entrada invalida. " + mensaje);
        }
        return sc.nextInt();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Inicializacion
        char[][] tablero = new char[TAM][TAM];
        List<Personaje> heroes = new ArrayList<>();
        List<Personaje> enemigos = new ArrayList<>();
        Inventario<Pocion> inventario = new Inventario<>();

        Guerrero heroePrincipal = new Guerrero("Thorin", 120, 15, 0, 0, 10);
        heroes.add(heroePrincipal);
        heroes.add(new Mago("Gandalf", 80, 25, 0, 1, 50));

        enemigos.add(new Guerrero("Orco 1", 40, 10, 4, 0, 4));
        enemigos.add(new Guerrero("Orco 2", 40, 10, 0, 4, 4));
        enemigos.add(new Guerrero("Orco 3", 40, 10, 4, 4, 4));

        inventario.agregar(new Pocion("Pocion Pequena", 30));
        inventario.agregar(new Pocion("Pocion Grande", 50));

        // Error de compilacion (clase abstracta): descomenta para verlo en IntelliJ
        // Personaje p = new Personaje("X", 10, 1, 0, 0);

        boolean cofreAlcanzado = false;
        int ronda = 0;

        do {
            ronda++;
            System.out.printf("%n===== RONDA %d =====%n", ronda);
            dibujarTablero(tablero, heroes, enemigos);
            mostrarEstado("Heroes:", heroes);
            mostrarEstado("Enemigos:", enemigos);

            // Turno del jugador
            for (Personaje heroe : heroes) {
                if (enemigos.isEmpty() || cofreAlcanzado) {
                    break;
                }
                boolean turnoUsado = false;
                do {
                    System.out.printf("%n-- Turno de %s --%n", heroe.getNombre());
                    System.out.println("1. Atacar");
                    System.out.println("2. Usar Inventario");
                    System.out.println("3. Mover en Tablero");
                    int opcion = leerEntero(sc, "Opcion: ");

                    switch (opcion) {
                        case 1:
                            for (int i = 0; i < enemigos.size(); i++) {
                                Personaje e = enemigos.get(i);
                                System.out.printf("  %d. %s (vida: %d)%n", i + 1, e.getNombre(), e.getVida());
                            }
                            int idx = leerEntero(sc, "Objetivo: ");
                            if (idx >= 1 && idx <= enemigos.size()) {
                                heroe.realizarAtaqueEspecial(enemigos.get(idx - 1));
                                turnoUsado = true;
                            } else {
                                System.out.println("Objetivo invalido.");
                            }
                            break;
                        case 2:
                            if (inventario.getItems().isEmpty()) {
                                System.out.println("El inventario esta vacio.");
                            } else if (heroe.getVida() == heroe.getVidaMaxima()) {
                                System.out.println("Tu vida ya esta completa.");
                            } else {
                                Pocion pocion = inventario.getItems().remove(0);
                                heroe.curar(pocion.getCuracion());
                                System.out.printf("%s usa %s. Vida: %d%n",
                                        heroe.getNombre(), pocion.getNombre(), heroe.getVida());
                                turnoUsado = true;
                            }
                            break;
                        case 3:
                            System.out.println("1. Arriba  2. Abajo  3. Izquierda  4. Derecha");
                            int dir = leerEntero(sc, "Direccion: ");
                            int nx = heroe.getPosicionX();
                            int ny = heroe.getPosicionY();
                            if (dir == 1) {
                                ny--;
                            } else if (dir == 2) {
                                ny++;
                            } else if (dir == 3) {
                                nx--;
                            } else if (dir == 4) {
                                nx++;
                            } else {
                                System.out.println("Direccion invalida.");
                                break;
                            }
                            if (nx < 0 || nx >= TAM || ny < 0 || ny >= TAM) {
                                System.out.println("Movimiento fuera del tablero.");
                            } else if (hayUnidad(nx, ny, heroes, enemigos)) {
                                System.out.println("Esa celda esta ocupada.");
                            } else {
                                heroe.setPosicion(nx, ny);
                                turnoUsado = true;
                                if (heroe == heroePrincipal && nx == COFRE_X && ny == COFRE_Y) {
                                    cofreAlcanzado = true;
                                }
                                dibujarTablero(tablero, heroes, enemigos);
                            }
                            break;
                        default:
                            System.out.println("Opcion invalida.");
                    }
                } while (!turnoUsado);

                enemigos.removeIf(unidad -> !unidad.estaVivo());
            }

            // Turno enemigo
            if (!enemigos.isEmpty() && !cofreAlcanzado) {
                System.out.println("\n-- Turno enemigo --");
                for (Personaje enemigo : enemigos) {
                    if (heroes.isEmpty()) {
                        break;
                    }
                    Personaje objetivo = heroes.get((int) (Math.random() * heroes.size()));
                    enemigo.atacar(objetivo, Math.random() < 0.3);
                    heroes.removeIf(unidad -> !unidad.estaVivo());
                }
            }

            // Limpieza de bajas
            heroes.removeIf(unidad -> !unidad.estaVivo());
            enemigos.removeIf(unidad -> !unidad.estaVivo());

        } while (!heroes.isEmpty() && !enemigos.isEmpty() && !cofreAlcanzado);

        System.out.println();
        dibujarTablero(tablero, heroes, enemigos);
        if (heroes.isEmpty()) {
            System.out.println("DERROTA: tu escuadron ha caido.");
        } else if (enemigos.isEmpty()) {
            System.out.println("VICTORIA: todos los enemigos fueron eliminados.");
        } else {
            System.out.println("VICTORIA: el heroe principal llego al cofre.");
        }
        sc.close();
    }
}