package co.edu.puj.rpg.modelo;

import co.edu.puj.rpg.modelo.enemigo.Enemigo;

public class Personaje {

    // Atributos encapsulados
    private String nombre;
    private int puntosVida;
    private int vidaMaxima;
    private int puntosAtaque;
    private int nivel;
    private double oro;
    private boolean estaVivo;
    private int experiencia; // Reto 2

    // Constructor completo
    public Personaje(String nombre, int vidaMaxima, int puntosAtaque, double oro) {
        this.nombre = nombre;
        this.vidaMaxima = vidaMaxima;      // primero el tope, porque el setter lo usa
        this.puntosAtaque = puntosAtaque;
        this.experiencia = 0;
        setNivel(1);
        setPuntosVida(vidaMaxima);         // CORRECCION Caso B: pasa por el setter validado
        setOro(oro);
    }

    // Constructor secundario con valores por defecto
    public Personaje(String nombre) {
        this(nombre, 100, 15, 50.0);
    }

    // ---------- API de comportamiento ----------
    public void atacar(Enemigo enemigo) {
        int dano = this.puntosAtaque;
        // Reto 2: golpe critico con 15% de probabilidad
        if (Math.random() < 0.15) {
            dano *= 2;
            System.out.println("¡GOLPE CRITICO!");
        }
        enemigo.recibirDano(dano);
    }

    public void usarPocion(int cantidadRestaurada) {
        if (cantidadRestaurada > 0 && this.estaVivo) {
            setPuntosVida(this.puntosVida + cantidadRestaurada);
        }
    }

    public void recibirDano(int cantidadDano) {
        if (cantidadDano > 0) {
            setPuntosVida(this.puntosVida - cantidadDano);
        }
    }

    // Reto 2: experiencia y subida de nivel
    public void ganarExperiencia(int puntos) {
        this.experiencia += puntos;
        int umbral = this.nivel * 50;
        while (this.experiencia >= umbral) {
            this.experiencia -= umbral;
            setNivel(this.nivel + 1);
            System.out.printf("¡%s sube al nivel %d!%n", this.nombre, this.nivel);
            umbral = this.nivel * 50;
        }
    }

    // ---------- Setters validados ----------
    public void setPuntosVida(int vida) {
        if (vida <= 0) {
            this.puntosVida = 0;
            this.estaVivo = false;
        } else if (vida > this.vidaMaxima) {
            this.puntosVida = this.vidaMaxima;
            this.estaVivo = true;
        } else {
            this.puntosVida = vida;
            this.estaVivo = true;
        }
    }

    public void setOro(double oro) {
        if (oro < 0.0) {
            System.out.println("Saldo insuficiente: operacion rechazada.");
            return;
        }
        this.oro = oro;
    }

    protected void setNivel(int nivel) {
        if (nivel >= 1) {
            this.nivel = nivel;
        }
    }

    // ---------- Getters ----------
    public String getNombre() { return nombre; }
    public int getPuntosVida() { return puntosVida; }
    public int getVidaMaxima() { return vidaMaxima; }
    public int getPuntosAtaque() { return puntosAtaque; }
    public int getNivel() { return nivel; }
    public double getOro() { return oro; }
    public boolean isEstaVivo() { return estaVivo; }
}