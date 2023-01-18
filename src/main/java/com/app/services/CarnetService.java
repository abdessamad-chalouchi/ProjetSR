package com.app.services;

import com.app.entities.*;
import com.app.repository.CarnetRepository;
import com.app.repository.ClientRepository;
import com.app.repository.SportRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarnetService {
    private final CarnetRepository carnetRepository;
    private final ClientRepository clientRepository;
    private final SportRepository sportRepository;
    @Data
    public static class CarnetCreate implements Serializable{
        private int client;
        private int sport;
        private int nombreEntrees;
    }
    public Carnet createCarnet(CarnetCreate requestMap) {
        Optional<Client> client = clientRepository.findById(requestMap.getClient());
        Optional<Sport> sport = sportRepository.findById(requestMap.getSport());
        Carnet carnet = new Carnet();
        if(client.isPresent() && sport.isPresent()){
            carnet.setId(new CarnetId(client.get().getId(),sport.get().getId()));
            carnet.setIdClient(client.get());
            carnet.setIdSport(sport.get());
            carnet.setNombreEntrees(requestMap.getNombreEntrees());
        }else
            throw new IllegalStateException("Please Enter a valid Sport and client!!");
        return carnetRepository.save(carnet);
    }
    public Optional<Carnet> getCarnetById(CarnetId id) {
        return carnetRepository.findById(id);
    }
    public List<Carnet> getAllCarnets() {
        return carnetRepository.findAll();
    }


    public Carnet updateCarnet(CarnetUpdateRequest requestMap) {
        Optional<Client> client = clientRepository.findById(requestMap.getClient());
        Optional<Sport> sport = sportRepository.findById(requestMap.getSport());
        Carnet carnet = new Carnet();
        if(client.isPresent() && sport.isPresent()){
            carnet.setId(new CarnetId(client.get().getId(),sport.get().getId()));
            carnet.setIdClient(client.get());
            carnet.setIdSport(sport.get());
            carnet.setNombreEntrees(Integer.valueOf(requestMap.getNombreEntrees()));
        }else
            throw new IllegalStateException("Please Enter a valid Sport and client!!");
        return carnetRepository.saveAndFlush(carnet);
    }

    public void deleteCarnetById(CarnetId id) {
        carnetRepository.deleteById(id);
    }
    public void deleteCarnet(Carnet carnet) {
        carnetRepository.delete(carnet);
    }
    public void deleteAllCarnets() {
        carnetRepository.deleteAll();
    }


    public Carnet buyTicket(Integer idClient, Integer idSport, Integer nombres) {
        CarnetId id = new CarnetId(idClient, idSport);
        Optional<Carnet> carnet = carnetRepository.findById(id);
        if (carnet.isPresent()) {
            Carnet c = carnet.get();
            if (c.getNombreEntrees() >= nombres) {
                // Il y a assez de tickets dans le carnet, on peut effectuer l'achat
                c.setNombreEntrees(c.getNombreEntrees() - nombres);
                carnetRepository.save(c);
                return c;
            } else {
                // Il n'y a pas assez de tickets dans le carnet
                return null;
            }
        } else {
            // Carnet non trouvé
            return null;
        }
    }

}
