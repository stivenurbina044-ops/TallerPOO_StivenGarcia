package com.taller.poo;

/*
 * Wrappers vs primitivos:
 * - Los Wrappers (Double, Integer, Boolean) son objetos: consumen más memoria
 *   (cabecera del objeto + referencia) y requieren boxing/unboxing.
 * - Admiten null (útil para "dato desconocido"); los primitivos no.
 * - Aquí se usan primitivos porque kilometraje, velocidad y estado siempre
 *   tienen un valor válido (0.0, 120, false) y así se ahorra memoria.
 */
public class Vehiculo {
    private String marca;
    private String modelo;
    private double kilometraje;   // primitivo: siempre tiene valor, sin null
    private int velocidadMaxima;  // primitivo: siempre tiene valor
    private boolean encendido;    // primitivo: solo true/false

    public Vehiculo() {
        this.marca = "Genérico";
        this.modelo = "Sin Modelo";
        this.kilometraje = 0.0;
        this.velocidadMaxima = 120;
        this.encendido = false;
    }

    public Vehiculo(String marca, String modelo, double kilometraje, int velocidadMaxima, boolean encendido) {
        this.marca = marca;
        this.modelo = modelo;
        this.kilometraje = kilometraje;
        this.velocidadMaxima = velocidadMaxima;
        this.encendido = encendido;
    }

    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public double getKilometraje() { return kilometraje; }
    public int getVelocidadMaxima() { return velocidadMaxima; }
    public boolean isEncendido() { return encendido; }
}