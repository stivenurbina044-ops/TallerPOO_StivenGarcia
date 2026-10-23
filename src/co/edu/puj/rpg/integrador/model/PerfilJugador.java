package co.edu.puj.rpg.integrador.model;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class PerfilJugador {

    private Set<String> logros = new TreeSet<>();

    public boolean registrarLogro(String logro) {
        if (logro == null || logro.isBlank()) {
            return false;
        }
        return logros.add(logro.trim());
    }

    // Al ser TreeSet, la iteracion es alfabetica
    public Set<String> listarLogrosOrdenados() {
        return Collections.unmodifiableSet(logros);
    }
}