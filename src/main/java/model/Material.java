package main.java.model;

public class Material extends Component{
    private Double transportCost;
    private Double qualityCoefficient;
    private Double unitCost;
    private Double quantity;

    public Material(String name, Double unitCost, Double quantity, String componentType, Double TVARate, Double transportCost, Double qualityCoefficient) {
        super(name, componentType, TVARate);
        this.unitCost = unitCost;
        this.quantity = quantity;
        this.transportCost = transportCost;
        this.qualityCoefficient = qualityCoefficient;
    }

    public Double getUnitCost() {
        return unitCost;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Double getTransportCost() {
        return transportCost;
    }

    public Double getQualityCoefficient() {
        return qualityCoefficient;
    }

    @Override
    public Double calculateCost() {
        return getUnitCost() * getQuantity() * qualityCoefficient * (1 + getTVARate()/100) + transportCost;
    }
}