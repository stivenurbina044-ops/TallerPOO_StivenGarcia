package co.edu.puj.rpg.relaciones;

public class CorazonRPG {

    private int saludBase;
    private int manaBase;

    public CorazonRPG(int saludBase, int manaBase) {
        this.saludBase = saludBase;
        this.manaBase = manaBase;
    }

    public int getSaludBase() { return saludBase; }
    public int getManaBase() { return manaBase; }

    @Override
    public String toString() {
        return "Corazon[salud=" + saludBase + ", mana=" + manaBase + "]";
    }
}