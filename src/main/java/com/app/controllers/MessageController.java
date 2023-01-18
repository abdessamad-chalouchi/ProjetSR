package com.app.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.entities.Client;
import com.app.entities.Message;
import com.app.services.MessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/api/v1/client/messages"},
        produces = {"application/json", "application/xml", MediaType.ALL_VALUE},
        consumes = {"application/json", "application/xml", MediaType.ALL_VALUE})
public class MessageController {
    private final MessageService messageService;
    @GetMapping()
    public ResponseEntity<List<Message>> getMessages(@RequestParam Optional<Integer> id) {
        return ResponseEntity.ok(messageService.getMessages(id));
    }
    @GetMapping(path="/my",produces = { "application/json", "application/xml" }, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<List<Message>> getMyMessages(Authentication authentication) {
        Client client = (Client)authentication.getPrincipal();
        if(client == null)
            return ResponseEntity.ok(new ArrayList<Message>());
        return ResponseEntity.ok(messageService.getMessages(Optional.of(client.getId())));
    }
    @GetMapping(path={"/my/{id}","/{id}"},produces = { "application/json", "application/xml" }, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Optional<Message>> getMessage(@PathVariable int id) {
        return ResponseEntity.ok(messageService.getMessage(id));
    }
    @PostMapping(path={"","/my"},consumes = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<Message> postMessage(Authentication authentication,@RequestBody String message) {
        Client client = (Client)authentication.getPrincipal();
        if(client == null){
            return ResponseEntity.badRequest().build();
        }
        Message msg = new Message(message, client);
        return ResponseEntity.ok(messageService.sendMessage(msg));
    }
    @DeleteMapping(path={"/my/{id}","/{id}"})
    public ResponseEntity<Boolean> deleteMessage(Authentication authentication,@PathVariable int id) {
        return ResponseEntity.ok(messageService.deleteMessage((Client)authentication.getPrincipal(), id));
    }
}
