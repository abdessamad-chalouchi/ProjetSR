package com.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.entities.Client;
import com.app.entities.Message;
import com.app.repository.ClientRepository;
import com.app.repository.MessageRepository;

import org.springframework.data.domain.Example;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final ClientRepository clientRepository;
    private final MessageRepository messageRepository;
    public List<Message> getMessages(Optional<Integer> id) {
        if(id.isPresent()){
            Optional<Client> c = clientRepository.findById(id.get());
            if(c.isEmpty()) return new ArrayList<Message>();
            Message msg = new Message();
            msg.setIdClient(c.get());
                return messageRepository.findAll(Example.of(msg));
        }
        return messageRepository.findAll();
    }
    public Optional<Message> getMessage(int id) {
        return messageRepository.findById(id);
    }
    public Message sendMessage(Message message) {
        return messageRepository.save(message);
    }
    public boolean deleteMessage(Client c,int id) {
        Optional<Message> message = messageRepository.findById(id);
        if(message.isPresent() && (message.get().getIdClient().isAdmin() || message.get().getIdClient().getId() == c.getId())){
            messageRepository.delete(message.get());
            return true;
        }
        return false;
    }
}
