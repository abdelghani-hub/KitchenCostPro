package main.java.service;

import main.java.model.Client;
import main.java.repository.ClientRepository;
import utils.ConsoleUI;

import java.util.Optional;

public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService() {
        this.clientRepository = new ClientRepository();
    }

    public Optional<Client> findById(String id) {
        return clientRepository.findById(id);
    }

    public Optional<Client> findByName(String clientName) {
        return clientRepository.findByColumn("name", clientName);
    }

    public Optional<Client> createNewClient() {
        String clientName = ConsoleUI.read("Enter Client Name : ", true);
        String clientAddress = ConsoleUI.read("Enter Client Address : ", true);
        String clientPhone = ConsoleUI.read("Enter Client Phone : ", true);
        Boolean isProfessional = ConsoleUI.readBoolean("Is Professional Client (y/n) : ");
        Client client = new Client(clientName, clientAddress, clientPhone, isProfessional);
        if (isProfessional)
            client.setDiscountRate(ConsoleUI.readDouble(">> Enter Discount Rate (%) : "));
        else
            client.setDiscountRate(0.0);
        Optional<Client> savedClient = clientRepository.save(client);
        if (savedClient.isPresent()) {
            ConsoleUI.print(savedClient.get().toString());
            return savedClient;
        }
        return Optional.empty();
    }
}