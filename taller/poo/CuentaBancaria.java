package com.taller.poo;

public class CuentaBancaria {
    private String numeroCuenta;
    private String titular;
    private double saldo;
    private Double limiteCredito;

    public CuentaBancaria() {
        this.numeroCuenta = "000000";
        this.titular = "Sin Nombre";
        this.saldo = 0.0;
        this.limiteCredito = 0.0;
    }

    public CuentaBancaria(String numeroCuenta, String titular) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = 0.0;
        this.limiteCredito = 100.0;
    }

    public CuentaBancaria(String numeroCuenta, String titular, double saldo, Double limiteCredito) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = saldo;
        this.limiteCredito = limiteCredito;
    }

    public boolean depositar(double monto) {
        if (monto > 0) {
            saldo += monto;
            return true;
        } else {
            return false;
        }
    }

    public boolean retirar(double monto) {
        double disponible = saldo + (limiteCredito != null ? limiteCredito : 0.0);
        if (monto > 0 && disponible >= monto) {
            saldo -= monto;
            return true;
        } else {
            return false;
        }
    }

    public String getNumeroCuenta() { return numeroCuenta; }
    public String getTitular() { return titular; }
    public double getSaldo() { return saldo; }
    public Double getLimiteCredito() { return limiteCredito; }

    /*
     * PREGUNTAS DE ANÁLISIS:
     * 1. 'saldo' (double primitivo) guarda el valor directamente (8 bytes) y por defecto
     *    vale 0.0. 'limiteCredito' (Double) es una referencia a un objeto en el heap
     *    (más memoria) y por defecto vale null, es decir, no apunta a ningún objeto.
     * 2. Con un double primitivo la operación se hace sobre 0.0. Con un Double null,
     *    Java intenta unboxing (null.doubleValue()) para operar, y como no hay objeto
     *    se lanza NullPointerException.
     */
}