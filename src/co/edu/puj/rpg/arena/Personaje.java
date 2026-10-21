package co.edu.puj.rpg.arena;

public abstract class Personaje implements ICombatiente {

    protected String nombre;
    protected int vida;
    protected int ataqueBase;
    protected int posicionX;
    protected int posicionY;
    private final int vidaMaxima;

    public Personaje(String nombre, int vida, int ataqueBase, int posicionX, int posicionY) {
        this.nombre = nombre;
        this.vida = vida;
        this.vidaMaxima = vida;
        this.ataqueBase = ataqueBase;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
    }

    // Cada subclase define su ataque unico
    public abstract void realizarAtaqueEspecial(Personaje objetivo);

    @Override
    public void defender(int danoRecibido) {
        recibirDano(danoRecibido);
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    public void recibirDano(int cantidad) {
        if (cantidad < 0) {
            cantidad = 0;
        }
        this.vida = Math.max(0, this.vida - cantidad);
        System.out.printf("%s recibe %d de dano (vida: %d)%n", nombre, cantidad, vida);
        if (!estaVivo()) {
            System.out.printf("¡%s ha caido!%n", nombre);
        }
    }

    public void curar(int cantidad) {
        this.vida = Math.min(vidaMaxima, this.vida + cantidad);
    }

    // Sobrecarga de metodos
    public void atacar(Personaje objetivo) {
        atacar(objetivo, false);
    }

    public void atacar(Personaje objetivo, boolean modoFuria) {
        int dano = modoFuria ? ataqueBase * 2 : ataqueBase;
        System.out.printf("%s ataca a %s%s%n", nombre, objetivo.getNombre(),
                modoFuria ? " en MODO FURIA" : "");
        objetivo.defender(dano);
    }

    public void setPosicion(int x, int y) {
        this.posicionX = x;
        this.posicionY = y;
    }

    public String getNombre() { return nombre; }
    public int getVida() { return vida; }
    public int getVidaMaxima() { return vidaMaxima; }
    public int getPosicionX() { return posicionX; }
    public int getPosicionY() { return posicionY; }
}