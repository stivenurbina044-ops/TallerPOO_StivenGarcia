package co.edu.puj.rpg.integrador.model;

import co.edu.puj.rpg.integrador.model.enums.RaridadItem;

public class Item {

    private String nombre;
    private double precioBase;
    private RaridadItem rareza;

    public Item(String nombre, double precioBase, RaridadItem rareza) {
        this.nombre = nombre;
        this.precioBase = precioBase;
        this.rareza = rareza;
    }

    public double getPrecioFinal() {
        return precioBase * rareza.getMultiplicadorStat();
    }

    public String getNombre() { return nombre; }
    public RaridadItem getRareza() { return rareza; }
}