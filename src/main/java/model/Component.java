package main.java.model;

abstract class Component {
    private String name;
    private String componentType;
//    private Double TVARate;
    private Integer projectId;

    public Component() {
    }

    public Component(String name, String componentType) { //, Double TVARate) {
        this.name = name;
        this.componentType = componentType;
//        this.TVARate = TVARate;
    }

    public String getName() {
        return name;
    }

    public String getComponentType() {
        return componentType;
    }

    public Integer getProjectId() {
        return projectId;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    abstract Double calculateCost();
}
