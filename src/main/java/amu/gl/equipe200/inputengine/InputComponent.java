package amu.gl.equipe200.inputengine;

import amu.gl.equipe200.core.Component;
import amu.gl.equipe200.entity.Entity;

public abstract class InputComponent extends Component {

    public InputComponent(Entity entity) {
        super(entity);
        entity.addComponent(InputComponent.class, this);
    }

    public abstract void reactToInput(String input);


//    public void updateBy(InputEngine inputEngine){ inputEngine.update(this); }


}
