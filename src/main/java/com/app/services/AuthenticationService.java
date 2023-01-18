package com.app.services;

import com.app.entities.Client;
import com.app.entities.Role;
import com.app.repository.ClientRepository;
import com.app.securityConfig.JwtService;
import com.app.entities.auth.AuthenticationRequest;
import com.app.entities.auth.AuthenticationResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final ClientRepository clientRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(Client client, boolean isClient) {
    if(clientRepository.findByEmail(client.getEmail()).isPresent()){
      throw new IllegalStateException("Email already taken");
    }
    client.setPassword(passwordEncoder.encode(client.getPassword()));
    if(!isClient){
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      System.out.println("\n\n\n\n\n\n"+auth.getPrincipal()+"\n\n\n\n\n\n");
      if(auth.getPrincipal().toString().equals("anonymousUser")){
        throw new IllegalStateException("You need to connect as an admin to register an admin user!");
      }
      Client c = (Client)auth.getPrincipal();
      if((c != null) && c.isAdmin()){
        client.setRole(Role.ADMIN);
      }else{
        throw new IllegalStateException("You don't have authority to register an admin user!");
      }
    }else{
      client.setRole(Role.CLIENT);
    }

    clientRepository.save(client);
    var jwtToken = jwtService.generateToken(client);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var client = clientRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new IllegalStateException("User not found!!"));
    var jwtToken = jwtService.generateToken(client);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

  public List<Client> getUsers(){
    return clientRepository.findAll();
  }
  public Optional<Client> getUser(int id){
    return clientRepository.findById(id);
  }

}
