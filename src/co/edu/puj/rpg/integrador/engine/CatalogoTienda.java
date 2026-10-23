package co.edu.puj.rpg.integrador.engine;

import co.edu.puj.rpg.integrador.model.Item;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class CatalogoTienda {

    private Map<String, Double> precios = new TreeMap<>();

    public void agregarItem(Item item) {
        precios.put(item.getNombre(), item.getPrecioFinal());
    }

    // Ordenado alfabeticamente por nombre del item
    public Map<String, Double> getPrecios() {
        return Collections.unmodifiableMap(precios);
    }
}