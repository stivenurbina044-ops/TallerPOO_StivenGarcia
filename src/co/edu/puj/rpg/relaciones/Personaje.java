package co.edu.puj.rpg.relaciones;

public class Personaje {

    private String nombre;
    private int nivel;
    private Arma armaEquipada;          // Asociacion unidireccional (0..1)
    private Gremio gremio;              // Asociacion bidireccional (0..*) a 1
    private final CorazonRPG corazon;   // Composicion (1..1)

    public Personaje(String nombre, int nivel) {
        this.nombre = nombre;
        this.nivel = (nivel < 1) ? 1 : nivel;
        this.armaEquipada = null;       // inicialmente desarmado
        this.gremio = null;
        // Composicion: el corazon se crea DENTRO del constructor
        this.corazon = new CorazonRPG(100 + this.nivel * 20, 50 + this.nivel * 10);
    }

    // ---------- Asociacion unidireccional ----------
    public void equiparArma(Arma nuevaArma) {
        this.armaEquipada = nuevaArma;
        if (nuevaArma != null) {
            System.out.printf("%s equipo %s.%n", nombre, nuevaArma.getNombre());
        }
    }

    public void desequiparArma() {
        if (this.armaEquipada != null) {
            System.out.printf("%s guardo %s.%n", nombre, armaEquipada.getNombre());
            this.armaEquipada = null;
        } else {
            System.out.printf("%s no tiene arma para retirar.%n", nombre);
        }
    }

    public int calcularAtaqueTotal() {
        int ataqueBase = this.nivel * 5;
        if (this.armaEquipada != null) {
            ataqueBase += this.armaEquipada.getDanioBase();
        }
        return ataqueBase;
    }

    // ---------- Asociacion bidireccional ----------
    public void unirseAGremio(Gremio nuevoGremio) {
        if (nuevoGremio == null || nuevoGremio == this.gremio) {
            return;
        }
        salirDeGremio();                    // desvincula del gremio previo, si existe
        this.gremio = nuevoGremio;
        nuevoGremio.agregarMiembro(this);   // sincronizacion cruzada
        System.out.printf("%s se unio al gremio %s.%n", nombre, nuevoGremio.getNombre());
    }

    public void salirDeGremio() {
        if (this.gremio != null) {
            Gremio anterior = this.gremio;
            this.gremio = null;
            anterior.removerMiembro(this);
        }
    }

    // ---------- Getters (sin getCorazon: la composicion protege la referencia) ----------
    public String getNombre() { return nombre; }
    public int getNivel() { return nivel; }
    public Arma getArmaEquipada() { return armaEquipada; }
    public Gremio getGremio() { return gremio; }
    public int getSaludBase() { return corazon.getSaludBase(); }
    public int getManaBase() { return corazon.getManaBase(); }

    // Solo el nombre del gremio: evita StackOverflowError
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Personaje[").append(nombre)
                .append(" | Nivel ").append(nivel)
                .append(" | ").append(corazon)
                .append(" | Arma: ").append(armaEquipada == null ? "Desarmado" : armaEquipada.toString())
                .append(" | Gremio: ").append(gremio == null ? "Ninguno" : gremio.getNombre())
                .append("]");
        return sb.toString();
    }
}