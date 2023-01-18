package com.app.services;

import com.app.entities.Client;
import com.app.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientServices {
    private final ClientRepository clientRepository;

    public List<Client> getAllClient(){
        return clientRepository.findAll();
    }
    public Client saveClient(Client client){
        if(clientRepository.findByEmail(client.getEmail()).isPresent() && !Objects.equals(clientRepository.findByEmail(client.getEmail()).get().getId(), client.getId())) {
            throw new IllegalStateException("Email already taken");
        }
        return clientRepository.save(client);
    }

    public Client delete(Integer id) {
        if(!clientRepository.findById(id).isPresent()) {
            throw new IllegalStateException("No Client Exist with id "+id);
        }
        Client client = clientRepository.findById(id).get();
        clientRepository.delete(client);
        return client;
    }

    public Optional<Client> getClient(String email) {
        return clientRepository.findByEmail(email);
    }
}
