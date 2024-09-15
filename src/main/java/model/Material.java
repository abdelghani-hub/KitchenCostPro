package main.java.model;

public class Material extends Component{
    private Double transportCost;
    private Double qualityCoefficient;

    public Material(String name, Double unitCost, Double quantity, String componentType, Double TVARate, Double transportCost, Double qualityCoefficient) {
        super(name, unitCost, quantity, componentType, TVARate);
        this.transportCost = transportCost;
        this.qualityCoefficient = qualityCoefficient;
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