package com.example.messageuserapp.Controller;

import com.example.messageuserapp.Model.CustomMessage;
import com.example.messageuserapp.repository.MessageRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ReceiveMessage {
    private final MessageRepository messageRepository;

    public ReceiveMessage(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/getMessage")
    public List<CustomMessage> message() {
        return new ArrayList<>(messageRepository.findAll());
    }
}
