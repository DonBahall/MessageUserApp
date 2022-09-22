package com.example.messageuserapp.Controller;


import com.example.messageuserapp.Model.CustomMessage;
import com.example.messageuserapp.Model.RoomModel;
import com.example.messageuserapp.MqConfig.MQConfig;
import com.example.messageuserapp.MqConfig.Receiver;
import com.example.messageuserapp.repository.MessageRepository;
import com.example.messageuserapp.repository.RoomRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Controller
public class RoomController {
    private final RoomRepository repository;
    private final MessageRepository messageRepository;
    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    public RoomController(RoomRepository repository, MessageRepository messageRepository, RabbitTemplate rabbitTemplate, Receiver receiver) {
        this.repository = repository;
        this.messageRepository = messageRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.receiver = receiver;
    }

    @GetMapping("/Chat")
    public String chat(Model model) {
        Iterable<RoomModel> models = repository.findAll();
        model.addAttribute("models", models);
        return "Chat";
    }

    @GetMapping("/new_room")
    public String newRoom(Model model) {
        model.addAttribute("room", new RoomModel());
        return "new_room";
    }

    @PostMapping("/new_room")
    public String save_room(@RequestParam String nameOfRoom) {
        RoomModel model = new RoomModel(nameOfRoom);
        repository.save(model);
        return "redirect:/Chat";
    }

    @GetMapping("/Chat/{id}")
    public String getRoom(@PathVariable(value = "id") long id, Model model) {
        if (!repository.existsById(id)) {
            return "redirect:/Chat";
        }
        Optional<RoomModel> post = repository.findById(id);
        ArrayList<RoomModel> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post",res);
        model.addAttribute("atr",id);
        return "room";
    }

    @PostMapping("/Chat/{id}")
    public String saveMessage(@PathVariable(value = "id")Long id, @RequestParam String message,Model model)
            throws InterruptedException {
        CustomMessage message1 = new CustomMessage(message, LocalDateTime.now(),id);
        messageRepository.save(message1);
        rabbitTemplate.convertAndSend(MQConfig.topicExchangeName, "foo.bar.baz", message);
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);

        System.out.println(id);
        return "redirect:/Chat/{id}";
    }

    @PostMapping("/Chat/{id}/delete")
    public String RoomDelete(@PathVariable(value = "id") long id) {
        RoomModel post = repository.findById(id).orElseThrow();
        repository.delete(post);
        return "redirect:/Chat";
    }

    @GetMapping("/Chat/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        if (!repository.existsById(id)) {
            return "redirect:/Chat";
        }
        Optional<RoomModel> post = repository.findById(id);
        ArrayList<RoomModel> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "room_edit";
    }

    @PostMapping("/Chat/{id}/edit")
    public String RoomUpdate(@PathVariable(value = "id") long id, @RequestParam String nameOfRoom) {
        RoomModel post = repository.findById(id).orElseThrow();
        post.setNameOfRoom(nameOfRoom);
        repository.save(post);
        return "redirect:/Chat";
    }
}
