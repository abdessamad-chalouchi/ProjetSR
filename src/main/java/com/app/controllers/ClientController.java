package com.app.controllers;

import com.app.entities.Client;
import com.app.securityConfig.JwtService;
import com.app.services.ClientServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/api/v1/profile"},
        produces = {"application/json", "application/xml"},
        consumes = {"application/json", "application/xml", MediaType.ALL_VALUE})
public class ClientController {
    private final PasswordEncoder passwordEncoder;
    private final ClientServices clientServices;
    @GetMapping()
    public ResponseEntity<Client> getClient(Authentication authentication) {
        return ResponseEntity.ok((Client) authentication.getPrincipal());
    }
    @PostMapping(value="update")
    public ResponseEntity<Client> saveClient(Authentication authentication,@RequestBody Client requestClient) {
        Client client = (Client) authentication.getPrincipal();
        client.setPrenom(requestClient.getPrenom());
        client.setNom(requestClient.getNom());
        client.setRue(requestClient.getRue());
        client.setVille(requestClient.getVille());
        client.setCodePostal(requestClient.getCodePostal());

        if(!client.getEmail().equals(requestClient.getEmail())){
            Optional<Client> client1 = clientServices.getClient(requestClient.getEmail());
            if(client1.isPresent()){
                throw new IllegalStateException("This Email is taken!");
            }
            client.setEmail(requestClient.getEmail());
        }
        if(!requestClient.getPassword().equals("") && !requestClient.getPassword().isEmpty()){
            client.setPassword(passwordEncoder.encode(requestClient.getPassword()));
        }
        return ResponseEntity.ok(clientServices.saveClient(client));
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Client> deleteClient(@PathVariable Integer id){
        return ResponseEntity.ok(clientServices.delete(id));
    }
}
