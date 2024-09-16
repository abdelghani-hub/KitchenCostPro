package main.java.model;

abstract class Component {
    private String name;
    private String componentType;
    private Double TVARate;

    public Component(String name, String componentType, Double TVARate) {
        this.name = name;
        this.componentType = componentType;
        this.TVARate = TVARate;
    }

    public String getName() {
        return name;
    }

    public String getComponentType() {
        return componentType;
    }

    public Double getTVARate() {
        return TVARate;
    }

    abstract Double calculateCost();
}
