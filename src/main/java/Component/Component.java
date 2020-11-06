package Component;

import Entity.Entity;

public abstract class Component {

    private Entity entity;  // Entity to which the component is linked

    private boolean enable; // Is the componant enbale, won't update if false

    public Entity getEntity() { return this.entity; }
    public boolean isEnable() { return this.enable; }

    // overwrite to describe the component beheviour on update
    public void update(long lastFrameTime) { }
}
