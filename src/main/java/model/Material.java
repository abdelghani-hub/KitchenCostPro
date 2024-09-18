package main.java.model;

import utils.ConsoleUI;

public class Material extends Component {
    private Double transportCost;
    private Double qualityCoefficient;
    private Double unitCost;
    private Integer quantity;

    public Material() {
    }

    public Material(String name, Double unitCost, Integer quantity, String componentType, Double transportCost, Double qualityCoefficient) { //, Double TVARate) {
        super(name, componentType); //, TVARate);
        this.unitCost = unitCost;
        this.quantity = quantity;
        this.transportCost = transportCost;
        this.qualityCoefficient = qualityCoefficient;
    }

    public Double getUnitCost() {
        return unitCost;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getTransportCost() {
        return transportCost;
    }

    public Double getQualityCoefficient() {
        return qualityCoefficient;
    }

    // Setters
    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setTransportCost(Double transportCost) {
        this.transportCost = transportCost;
    }

    public void setQualityCoefficient(Double qualityCoefficient) {
        this.qualityCoefficient = qualityCoefficient;
    }

    public String toString() {
        return "\t\t >>" + getName() + " : " + calculateCost() +"\n" +
                "\t\t\tQuantity       : " + getQuantity() + " m²\n" +
                "\t\t\tUnit Cost      : " + getUnitCost() + " €\n" +
                "\t\t\tQuality        : " + getQualityCoefficient() + "\n" +
                "\t\t\tTransport Cost : " + getTransportCost() + " €\n";
    }

    @Override
    public Double calculateCost() {
        return (unitCost * quantity * qualityCoefficient) + transportCost;
    }
}