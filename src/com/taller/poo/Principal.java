package com.taller.poo;

public class Principal {
    public static void main(String[] args) {
        Vehiculo v1 = new Vehiculo("Toyota", "Corolla", 15000.50, 180, true);
        Vehiculo v2 = new Vehiculo();
        Vehiculo v3 = new Vehiculo("Mazda", "3", 32000.00, 200, false);
        Vehiculo[] vehiculos = {v1, v2, v3};

        Estudiante e1 = new Estudiante("E001", "Laura Díaz", 4.35, true);
        Estudiante e2 = new Estudiante("E002", "Juan Rojas", 3.80, true);
        Estudiante e3 = new Estudiante("E003", "María Soto", 2.90, false);
        Estudiante[] estudiantes = {e1, e2, e3};

        CuentaBancaria c1 = new CuentaBancaria("1001", "Carlos Pérez", 1000.00, 500.0);
        CuentaBancaria c2 = new CuentaBancaria("1002", "Ana Gómez");
        CuentaBancaria c3 = new CuentaBancaria();

        c1.depositar(300.00);
        c1.retirar(49.25);
        c2.depositar(150.00);
        c2.retirar(100.00);
        c3.depositar(-5);
        c3.retirar(50);   // falla: sin saldo ni crédito

        System.out.printf("=== REPORTE DE VEHÍCULOS ===%n");
        for (int i = 0; i < vehiculos.length; i++) {
            Vehiculo v = vehiculos[i];
            System.out.printf("[%d] Marca: %s | Modelo: %s | KM: %.2f | VelMax: %d km/h | Encendido: %b%n",
                    i + 1, v.getMarca(), v.getModelo(), v.getKilometraje(), v.getVelocidadMaxima(), v.isEncendido());
        }

        System.out.printf("%n=== REPORTE DE ESTUDIANTES ===%n");
        for (Estudiante e : estudiantes) {
            System.out.printf("%s%n", e.obtenerReporteAcademicos());
        }

        System.out.printf("%n=== REPORTE DE CUENTAS BANCARIAS ===%n");
        CuentaBancaria[] cuentas = {c1, c2, c3};
        for (CuentaBancaria c : cuentas) {
            System.out.printf("Cuenta: %s | Titular: %s | Saldo Final: %s | Crédito: %s%n",
                    c.getNumeroCuenta(), c.getTitular(),
                    ConversorMoneda.formatearMoneda(c.getSaldo()),
                    ConversorMoneda.formatearMoneda(c.getLimiteCredito()));
        }

        double euros = ConversorMoneda.convertirDolaresAEuros(100, 0.92);
        System.out.printf("%n100 USD = %.2f EUR (%s)%n", euros, ConversorMoneda.formatearMoneda(euros));
    }
}