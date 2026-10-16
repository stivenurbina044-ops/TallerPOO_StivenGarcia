package com.taller.poo;

import java.util.Scanner;

public class SistemaGestionCuentas {

    private static CuentaBancaria buscarCuenta(CuentaBancaria[] cuentas, String numero) {
        for (int i = 0; i < cuentas.length; i++) {
            if (cuentas[i].getNumeroCuenta().equals(numero)) {
                return cuentas[i];
            }
        }
        return null;
    }

    private static double leerMonto(Scanner sc) {
        try {
            return Double.parseDouble(sc.nextLine().trim());
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CuentaBancaria[] cuentas = {
                new CuentaBancaria("1001", "Carlos Pérez", 1000.00, 500.0),
                new CuentaBancaria("1002", "Ana Gómez"),
                new CuentaBancaria("1003", "Luis Mora", 250.50, 200.0)
        };
        int opcion = 0;

        do {
            System.out.printf("%n=== MENÚ ===%n1. Depositar%n2. Retirar%n3. Reporte consolidado%n4. Salir%nOpción: ");
            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException ex) {
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                case 2:
                    System.out.printf("Número de cuenta: ");
                    String num = sc.nextLine().trim();
                    System.out.printf("Monto: ");
                    double monto = leerMonto(sc);
                    CuentaBancaria cuenta = buscarCuenta(cuentas, num);
                    if (monto <= 0) {
                        System.out.printf("Error: el monto debe ser mayor a cero.%n");
                    } else if (cuenta == null) {
                        System.out.printf("Error: la cuenta %s no existe.%n", num);
                    } else {
                        boolean ok = (opcion == 1) ? cuenta.depositar(monto) : cuenta.retirar(monto);
                        if (ok) {
                            System.out.printf("Operación exitosa. Nuevo saldo: $%.2f%n", cuenta.getSaldo());
                        } else {
                            System.out.printf("Operación rechazada (fondos insuficientes).%n");
                        }
                    }
                    break;
                case 3:
                    StringBuilder sb = new StringBuilder();
                    double total = 0;
                    for (int i = 0; i < cuentas.length; i++) {
                        sb.append(String.format("Cuenta: %s | Titular: %s | Saldo: $%.2f%n",
                                cuentas[i].getNumeroCuenta(), cuentas[i].getTitular(), cuentas[i].getSaldo()));
                        total += cuentas[i].getSaldo();
                    }
                    System.out.printf("%n=== REPORTE CONSOLIDADO ===%n%s", sb);
                    System.out.printf("Saldo total acumulado: $%.2f%n", total);
                    break;
                case 4:
                    System.out.printf("Saliendo del sistema...%n");
                    break;
                default:
                    System.out.printf("Opción inválida: %d%n", opcion);
            }
        } while (opcion != 4);
        sc.close();
    }
}