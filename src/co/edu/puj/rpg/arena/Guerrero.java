package co.edu.puj.rpg.arena;

public class Guerrero extends Personaje {

    protected int fuerzaEscudo;

    public Guerrero(String nombre, int vida, int ataqueBase, int posX, int posY, int fuerzaEscudo) {
        super(nombre, vida, ataqueBase, posX, posY);
        this.fuerzaEscudo = fuerzaEscudo;
    }

    @Override
    public void realizarAtaqueEspecial(Personaje objetivo) {
        int dano = ataqueBase + fuerzaEscudo;
        System.out.printf("%s lanza un GOLPE DE ESCUDO sobre %s%n", nombre, objetivo.getNombre());
        objetivo.defender(dano);
    }

    // El escudo mitiga parte del dano recibido
    @Override
    public void defender(int danoRecibido) {
        int reducido = Math.max(1, danoRecibido - fuerzaEscudo / 2);
        recibirDano(reducido);
    }
}