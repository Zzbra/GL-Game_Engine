package amu.gl.equipe200.core.Component;

import amu.gl.equipe200.entity.BaseEntity;
import amu.gl.equipe200.gameworld.Settings;

public class PlayerInputComponent extends InputComponent{

    public PlayerInputComponent(BaseEntity entity) {
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
