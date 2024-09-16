package main.java.model;

public class Labor extends Component{
    private Double hoursWorked;
    private Double hourlyRate;
    private Double productivity;

    public Labor(String name, String componentType, Double TVARate, Double hoursWorked, Double hourlyRate, Double productivity) {
        super(name, componentType, TVARate);
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
        this.productivity = productivity;
    }

    public Double getHoursWorked() {
        return hoursWorked;
    }

    public Double getHourlyRate() {
        return hourlyRate;
    }

    public Double getProductivity() {
        return productivity;
    }

    @Override
    public Double calculateCost() {
        return hoursWorked * hourlyRate * productivity * (1 + getTVARate()/100);
    }
}
