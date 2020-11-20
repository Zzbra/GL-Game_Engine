package amu.gl.equipe200.core.Component;

import amu.gl.equipe200.entity.BaseEntity;
import amu.gl.equipe200.system.InputEngine;

public abstract class InputComponent extends Component {

    public InputComponent(BaseEntity entity) {
        super(entity);
        entity.addComponent(InputComponent.class, this);
    }

    public abstract void reactToInput(String input);


    public void updateBy(InputEngine inputEngine){ inputEngine.update(this); }


}
