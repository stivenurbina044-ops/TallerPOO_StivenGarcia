package co.edu.puj.rpg.relaciones;

import java.util.ArrayList;
import java.util.List;

public class Gremio {

    private String nombre;
    private List<Personaje> miembros;

    public Gremio(String nombre) {
        this.nombre = nombre;
        this.miembros = new ArrayList<>();
    }

    // Sincronizacion cruzada (lado Gremio)
    public void agregarMiembro(Personaje p) {
        if (p == null) {
            return;
        }
        if (!miembros.contains(p)) {
            miembros.add(p);
            if (p.getGremio() != this) {
                p.unirseAGremio(this);
            }
        }
    }

    public void removerMiembro(Personaje p) {
        if (p != null && miembros.remove(p) && p.getGremio() == this) {
            p.salirDeGremio();
        }
    }

    public String getNombre() { return nombre; }

    public List<Personaje> getMiembros() {
        return new ArrayList<>(miembros); // copia: protege la lista interna
    }

    // Solo nombres de los miembros: evita StackOverflowError
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gremio: ").append(nombre)
                .append(" | Miembros (").append(miembros.size()).append("): ");
        if (miembros.isEmpty()) {
            sb.append("ninguno");
        }
        for (int i = 0; i < miembros.size(); i++) {
            sb.append(miembros.get(i).getNombre());
            if (i < miembros.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}