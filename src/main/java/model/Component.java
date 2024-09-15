package main.java.model;

abstract class Component {
    private String name;
    private Double unitCost;
    private Double quantity;
    private String componentType;
    private Double TVARate;

    public Component(String name, Double unitCost, Double quantity, String componentType, Double TVARate) {
        this.name = name;
        this.unitCost = unitCost;
        this.quantity = quantity;
        this.componentType = componentType;
        this.TVARate = TVARate;
    }

    public String getName() {
        return name;
    }

    public Double getUnitCost() {
        return unitCost;
    }

    public Double getQuantity() {
        return quantity;
    }

    public String getComponentType() {
        return componentType;
    }

    public Double getTVARate() {
        return TVARate;
    }

    abstract Double calculateCost();
}
