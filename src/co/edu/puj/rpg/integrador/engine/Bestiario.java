package co.edu.puj.rpg.integrador.engine;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Bestiario {

    private Map<String, Integer> contador = new HashMap<>();

    // Retorna true si es la primera victoria contra ese enemigo
    public boolean registrarVictoria(String enemigo) {
        boolean primera = !contador.containsKey(enemigo);
        contador.put(enemigo, contador.getOrDefault(enemigo, 0) + 1);
        return primera;
    }

    public int getCantidad(String enemigo) {
        return contador.getOrDefault(enemigo, 0);
    }

    public Map<String, Integer> getRegistro() {
        return Collections.unmodifiableMap(contador);
    }
}