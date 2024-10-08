package main.java.model;

import utils.ConsoleUI;

public class Labor extends Component {
    private Double hoursWorked;
    private Double hourlyRate;
    private Double productivity;

    public Labor() {
    }

    public Labor(String name, String componentType, Double hoursWorked, Double hourlyRate, Double productivity) {   //, Double TVARate) {
        super(name, componentType); //, TVARate);
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

    // Setters
    public void setHoursWorked(Double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public void setProductivity(Double productivity) {
        this.productivity = productivity;
    }

    public String toString() {
        return "\t\t >> " + ConsoleUI.BLUE + getName() + " : " +  calculateCost() +" €\n" + ConsoleUI.RESET +
                "\t\t\tHours Worked : " + getHoursWorked() + " h\n" +
                "\t\t\tHourly Rate  : " + getHourlyRate() + " €\n" +
                "\t\t\tProductivity : " + getProductivity() + "\n";
    }

    @Override
    public Double calculateCost() {
        return hoursWorked * hourlyRate * productivity;
    }
}
