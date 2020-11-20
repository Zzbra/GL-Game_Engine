package amu.gl.equipe200.inputengine;

import amu.gl.equipe200.core.Entity;
import amu.gl.equipe200.gameworld.Settings;
import amu.gl.equipe200.inputengine.InputComponent;

public class PlayerInputComponent extends InputComponent {

    public PlayerInputComponent(Entity entity) {
        super(entity);
    }

    @Override
    public void reactToInput(String input) {
        input = input.toUpperCase();
        switch (input){
            case "Z":
                getEntity().setDx(0);
                getEntity().setDy(-Settings.PLAYER_SHIP_SPEED);
                getEntity().setR(270);
                break;
            case "S":
                getEntity().setDx(0);
                getEntity().setDy(Settings.PLAYER_SHIP_SPEED);
                getEntity().setR(90);
                break;
            case "Q":
                getEntity().setDx(-Settings.PLAYER_SHIP_SPEED);
                getEntity().setDy(0);
                getEntity().setR(180);
                break;
            case "D":
                getEntity().setDx(Settings.PLAYER_SHIP_SPEED);
                getEntity().setDy(0);
                getEntity().setR(0);
                break;
            default:
                break;
        }
    }

    @Override
    public void remove() {

    }
}
