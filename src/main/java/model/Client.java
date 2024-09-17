package main.java.model;

public class Client {
    private Integer id;
    private String name;
    private String address;
    private String phone;
    private Boolean isProfessional;

    public Client() {}
    public Client(String name, String address, String phone, Boolean isProfessional) {
        this.id = null;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.isProfessional = isProfessional;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public Boolean getIsProfessional() {
        return isProfessional;
    }

    @Override
    public String toString() {
        return
                "\n\tid             : " + id +
                "\n\tname           : " + name +
                "\n\taddress        : " + address +
                "\n\tphone          : " + phone +
                "\n\tisProfessional : " + isProfessional + "\n";
    }
}
