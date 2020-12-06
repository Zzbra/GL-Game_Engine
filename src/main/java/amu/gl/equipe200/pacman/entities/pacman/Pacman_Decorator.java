package amu.gl.equipe200.pacman.entities.pacman;

public class Pacman_Decorator
    extends Pacman {

    private Pacman pacman;

    public void setPacman(Pacman pacman) { this.pacman = pacman; }
    public Pacman getPacman() { return pacman; }
}
