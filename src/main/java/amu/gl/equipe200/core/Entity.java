package amu.gl.equipe200.core;

import java.util.HashMap;
import java.util.UUID;

public abstract class Entity {

    private String uuid;
    private HashMap<Class<? extends Component>, Component> components;
    private HashMap<String, Object> properties;

    public Entity () {
        this.uuid = UUID.randomUUID().toString();
    }

    /*****  Getter and setter for the components *****/
    public void addComponent(Component component) {
        // add the component to the entity and notify it
        this.components.put(component.getClass(), component);
        component.onAttach(this);
    }
    public boolean hasComponent(Class<? extends Component> type) { return this.components.containsKey(type); }
    public Component getComponent(Class<? extends Component> type) { return this.components.get(type); }
    public void removeComponent(Class<? extends Component> type) { this.components.remove(type).onDetach(); }
    /*****  Getter and setter for the properties *****/
    public void addProperty(String name) {
        // we don't want to have multiple time the same properties
        this.properties.putIfAbsent(name, null);
    }
    public Object getProperty(String name) { return this.properties.get(name); }
    public void setProperty(String name, Object value) {
        this.properties.put(name, value);
    }
    public boolean hasProperty(String name) {return this.properties.containsKey(name); }
    // can't remove properties as it can be used by multiple component

}
