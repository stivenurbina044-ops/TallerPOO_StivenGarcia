package co.edu.puj.rpg.arena;

public class Mago extends Personaje {

    private static final int COSTO_MANA = 10;
    protected int puntosMana;

    public Mago(String nombre, int vida, int ataqueBase, int posX, int posY, int puntosMana) {
        super(nombre, vida, ataqueBase, posX, posY);
        this.puntosMana = puntosMana;
    }

    @Override
    public void realizarAtaqueEspecial(Personaje objetivo) {
        if (puntosMana >= COSTO_MANA) {
            puntosMana -= COSTO_MANA;
            int dano = ataqueBase * 2;
            System.out.printf("%s lanza una BOLA DE FUEGO sobre %s%n", nombre, objetivo.getNombre());
            objetivo.defender(dano);
        } else {
            System.out.printf("%s no tiene mana suficiente. Ataque normal.%n", nombre);
            atacar(objetivo);
        }
    }

    public int getPuntosMana() { return puntosMana; }
}