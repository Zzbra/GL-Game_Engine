package amu.gl.equipe200.core;

public abstract class Component {

    private Entity entity;  // the entity to which it is linked
    private Boolean enable;  // is the component enable

    public Component() {
        this.enable = true;
        this.entity = null;  // must be added when attach to the entity
        this.onCreate();     // do custom action for creation
    };

    /*****  Getter and Setter  *****/
    public void setEntity(Entity entity) {
        this.entity = entity;
        this.onAttach(entity);
    }
    public Entity getEntity() { return this.entity; }
    public Boolean isEnable() {return this.enable; }
    public void stop() { this.enable = false; }
    public void resume() {this.enable = true; }


    /**
     * This method are called when the component is created/deleted or attach/detach from the entity
     * Overwrite them to change what they are doing
     */
    public void onCreate() { }
    public void onAttach(Entity entity) { }
    public void onDetach() { }

    @Override
    public String toString() {
        return "Component{"
                + "type:" + this.getClass().getSimpleName()
                + "; enable=" + enable
                + "}";
    }
}
