package co.edu.puj.rpg.principal;

import co.edu.puj.rpg.modelo.Personaje;
import co.edu.puj.rpg.modelo.enemigo.Enemigo;

import java.util.Scanner;

public class Main {

    // Caso C (corregido): los limites se prueban sobre un personaje de prueba
    public static void probarLimites() {
        Personaje prueba = new Personaje("Prueba", 100, 10, 0.0);
        probarValor(prueba, -10);
        probarValor(prueba, 0);
        probarValor(prueba, 100000);
        System.out.println();
    }

    private static void probarValor(Personaje p, int valor) {
        p.setPuntosVida(valor);
        System.out.printf("setPuntosVida(%d) -> vida=%d | vivo=%s%n",
                valor, p.getPuntosVida(), p.isEstaVivo());
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Personaje heroe = new Personaje("Aragorn", 100, 15, 50.0);
        Enemigo enemigo = new Enemigo("Orco", 80, 12, 1, 40.0);

        // Caso A: esto NO compila ('puntosVida' has private access in 'Personaje')
        // heroe.puntosVida = 200;

        probarLimites();

        StringBuilder historial = new StringBuilder();
        int pocionesGrandes = 1;
        final double COSTO_POCION = 30.0;
        final int CURACION_POCION = 50;
        int opcion;

        combate:
        do {
            System.out.println("===== MENU =====");
            System.out.println("1. Atacar");
            System.out.println("2. Usar Pocion");
            System.out.println("3. Ver Estado");
            System.out.println("4. Huir");
            System.out.println("5. Tienda");
            System.out.print("Opcion: ");

            if (!sc.hasNextInt()) {
                System.out.println("Entrada invalida. Ingresa un numero.");
                sc.next();
                continue;
            }
            opcion = sc.nextInt();

            boolean consumioTurno = false;

            switch (opcion) {
                case 1:
                    int vidaAntes = enemigo.getPuntosVida();
                    heroe.atacar(enemigo);
                    int infligido = vidaAntes - enemigo.getPuntosVida();
                    historial.append(heroe.getNombre()).append(" inflige ")
                            .append(infligido).append(" de dano a ")
                            .append(enemigo.getNombre()).append("\n");
                    consumioTurno = true;
                    break;
                case 2:
                    if (pocionesGrandes > 0) {
                        pocionesGrandes--;
                        heroe.usarPocion(CURACION_POCION);
                        historial.append(heroe.getNombre()).append(" usa una pocion (+")
                                .append(CURACION_POCION).append(" PV)\n");
                        consumioTurno = true;
                    } else {
                        System.out.println("No tienes pociones.");
                    }
                    break;
                case 3:
                    System.out.printf("%s | Nivel: %d | Vida: %d/%d | Oro: %.2f | Pociones: %d%n",
                            heroe.getNombre(), heroe.getNivel(), heroe.getPuntosVida(),
                            heroe.getVidaMaxima(), heroe.getOro(), pocionesGrandes);
                    System.out.printf("%s | Vida: %d/%d%n", enemigo.getNombre(),
                            enemigo.getPuntosVida(), enemigo.getVidaMaxima());
                    break;
                case 4:
                    System.out.println("Huiste del combate.");
                    break combate;
                case 5:
                    System.out.printf("Oro actual: %.2f | Pocion Grande: %.2f. Comprar? (1=Si, 2=No): ",
                            heroe.getOro(), COSTO_POCION);
                    if (sc.hasNextInt() && sc.nextInt() == 1) {
                        double oroAntes = heroe.getOro();
                        heroe.setOro(oroAntes - COSTO_POCION);
                        if (heroe.getOro() != oroAntes) {
                            pocionesGrandes++;
                            historial.append("Compra de pocion grande\n");
                            System.out.printf("Compra exitosa. Oro restante: %.2f%n", heroe.getOro());
                        } else {
                            System.out.println("Oro insuficiente. No se entrego la pocion.");
                        }
                    }
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }

            // Turno del enemigo
            if (consumioTurno && enemigo.isEstaVivo()) {
                heroe.recibirDano(enemigo.getPuntosAtaque());
                historial.append(enemigo.getNombre()).append(" inflige ")
                        .append(enemigo.getPuntosAtaque()).append(" de dano a ")
                        .append(heroe.getNombre()).append("\n");
            }

            System.out.println("----- Historial de batalla -----");
            System.out.print(historial);
            System.out.printf("Vida %s: %d | Vida %s: %d%n%n", heroe.getNombre(),
                    heroe.getPuntosVida(), enemigo.getNombre(), enemigo.getPuntosVida());

        } while (heroe.isEstaVivo() && enemigo.isEstaVivo());

        if (!enemigo.isEstaVivo()) {
            System.out.printf("¡Victoria! Ganas %.2f de oro.%n", enemigo.getOro());
            heroe.setOro(heroe.getOro() + enemigo.getOro());
            heroe.ganarExperiencia(60);
        } else if (!heroe.isEstaVivo()) {
            System.out.println("Has sido derrotado...");
        }
        sc.close();
    }
}