package main.java.model;



public class Project {
    private Integer id;
    private String name;
    private Double profitMargin;
    private Double totalCost;
    private String status;
    private Double area;
    private Double TVA;
    private Integer client_id;

    public Project() {
    }

    public Project(java.lang.String name, Double profitMargin, Double totalCost, String status, Double area, Double TVA, Integer client_id) {
        this.name = name;
        this.profitMargin = profitMargin;
        this.totalCost = totalCost;
        this.status = status;
        this.area = area;
        this.TVA = TVA;
        this.client_id = client_id;
    }

    public Integer getId() {
        return id;
    }

    public java.lang.String getName() {
        return name;
    }

    public Double getProfitMargin() {
        return profitMargin;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public String getStatus() {
        return status;
    }

    public Double getArea() {
        return area;
    }

    public Double getTVA() {
        return TVA;
    }

    public Integer getClient_id() {
        return client_id;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public void setProfitMargin(Double profitMargin) {
        this.profitMargin = profitMargin;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public void setTVA(Double value) {
        this.TVA = value;
    }

    public void setClient_id(Integer client_id) {
        this.client_id = client_id;
    }

    public Double getTotalCostWithMargin() {
        return totalCost * (1 + (profitMargin / 100));
    }

    public String toString() {
        return
                "Name :" + name +
                "ProfitMargin :" + profitMargin +
                "TotalCost :" + totalCost +
                "Status :" + status +
                "Area :" + area +
                "TVA :" + TVA +
                "client_id :" + client_id +
                "TotalCostWithMargin :" + getTotalCostWithMargin();
    }
}
