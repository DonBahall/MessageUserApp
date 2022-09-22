package com.example.messageuserapp.Controller;

import com.example.messageuserapp.Model.CustomMessage;
import com.example.messageuserapp.repository.MessageRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ReceiveMessage {
    private final MessageRepository messageRepository;

    public ReceiveMessage(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/getMessage")
    public List<CustomMessage> message(@RequestParam Long idroom) {
        return messageRepository.findAllByNumOfRoom(idroom);
    }
}
