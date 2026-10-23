package co.edu.puj.rpg.integrador.model;

import co.edu.puj.rpg.integrador.exception.CapacidadExcedidaException;
import co.edu.puj.rpg.integrador.exception.SinManaException;
import co.edu.puj.rpg.integrador.exception.VidaInsuficienteException;
import co.edu.puj.rpg.integrador.model.enums.EstadoPersonaje;
import co.edu.puj.rpg.integrador.model.enums.TipoPersonaje;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Personaje {

    public static final int MAX_HABILIDADES = 5;

    private String nombre;
    private TipoPersonaje tipoPersonaje;
    private EstadoPersonaje estado;
    private int vida;
    private int vidaMaxima;
    private int mana;
    private int manaMaximo;
    private int ataqueBase;
    private int defensaBase;
    private Set<String> habilidades;

    public Personaje(String nombre, TipoPersonaje tipoPersonaje) {
        if (tipoPersonaje == null) {
            throw new IllegalArgumentException("El tipo de personaje es obligatorio.");
        }
        this.nombre = nombre;
        this.tipoPersonaje = tipoPersonaje;
        this.estado = EstadoPersonaje.VIVO;
        this.vidaMaxima = 100;
        this.vida = vidaMaxima;
        this.manaMaximo = 50;
        this.mana = manaMaximo;
        this.ataqueBase = 15;
        this.defensaBase = 5;
        this.habilidades = new HashSet<>();
    }

    // Calculos que consumen los bonificadores del enum
    public int calcularAtaque() {
        return (int) (ataqueBase * (1 + tipoPersonaje.getBonificadorAtaqueBase()));
    }

    public int calcularDefensa() {
        return (int) (defensaBase * (1 + tipoPersonaje.getBonificadorDefensaBase()));
    }

    // ---------- Set de habilidades (HashSet) ----------
    public boolean aprenderHabilidad(String habilidad) throws CapacidadExcedidaException {
        if (habilidad == null || habilidad.isBlank()) {
            return false;
        }
        String clave = habilidad.trim();
        if (habilidades.contains(clave)) {
            return false; // duplicado rechazado
        }
        if (habilidades.size() >= MAX_HABILIDADES) {
            throw new CapacidadExcedidaException(
                    "El libro de " + nombre + " esta lleno (maximo " + MAX_HABILIDADES + ").");
        }
        return habilidades.add(clave);
    }

    public Set<String> obtenerHabilidades() {
        return Collections.unmodifiableSet(habilidades);
    }

    // ---------- Reglas de excepcion ----------
    public int lanzarHechizo(int costoMana) throws SinManaException {
        if (costoMana > mana) {
            throw new SinManaException("Mana insuficiente: se requieren " + costoMana
                    + " y solo hay " + mana + ".");
        }
        mana -= costoMana;
        return calcularAtaque() + costoMana;
    }

    public void consumirVida(int costoVida) {
        if (costoVida >= vida) {
            throw new VidaInsuficienteException("No se pueden gastar " + costoVida
                    + " de vida: solo quedan " + vida + ".");
        }
        vida -= costoVida;
    }

    // ---------- Estados ----------
    public boolean puedeActuar() {
        return estado != EstadoPersonaje.ATURDIDO && estado != EstadoPersonaje.DERROTADO;
    }

    public void aplicarEfectoTurno() {
        if (estado == EstadoPersonaje.ENVENENADO) {
            vida = Math.max(0, vida - 5);
            if (vida == 0) {
                estado = EstadoPersonaje.DERROTADO;
            }
        }
    }

    public void setEstado(EstadoPersonaje estado) {
        if (estado != null) {
            this.estado = estado;
        }
    }

    public String getNombre() { return nombre; }
    public TipoPersonaje getTipoPersonaje() { return tipoPersonaje; }
    public EstadoPersonaje getEstado() { return estado; }
    public int getVida() { return vida; }
    public int getVidaMaxima() { return vidaMaxima; }
    public int getMana() { return mana; }
    public int getManaMaximo() { return manaMaximo; }
}