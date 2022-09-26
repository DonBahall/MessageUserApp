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
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @GetMapping("/lobby")
    public String lobby(Model model) {
        Iterable<RoomModel> models = repository.findAll();
        model.addAttribute("models", models);
        return "lobby";
    }

    @GetMapping("/new_room")
    public String new_room(Model model) {
        model.addAttribute("room", new RoomModel());
        return "new_room";
    }

    @PostMapping("/new_room")
    public String save_room(@RequestParam String nameOfRoom) {
        RoomModel model = new RoomModel(nameOfRoom);
        repository.save(model);
        return "redirect:/lobby";
    }

    @GetMapping("/lobby/{id}")
    public String get_room(@PathVariable(value = "id") long id, Model model) {
        if (!repository.existsById(id)) {
            return "redirect:/lobby";
        }
        Optional<RoomModel> post = repository.findById(id);
        ArrayList<RoomModel> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post",res);
        model.addAttribute("atr",id);
        return "room";
    }

    @PostMapping("/lobby/{id}")
    public String save_message(@PathVariable(value = "id")Long id, @RequestParam String message)
            throws InterruptedException {
        CustomMessage message1 = new CustomMessage(message, LocalDateTime.now(),id);
        messageRepository.save(message1);
        rabbitTemplate.convertAndSend(MQConfig.topicExchangeName, "foo.bar.baz", message);
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        return "redirect:/lobby/{id}";
    }

    @PostMapping("/lobby/{id}/delete")
    public String room_delete(@PathVariable(value = "id") long id) {
        RoomModel post = repository.findById(id).orElseThrow();
        repository.delete(post);
        return "redirect:/lobby";
    }

    @GetMapping("/lobby/{id}/edit")
    public String blog_edit(@PathVariable(value = "id") long id, Model model) {
        if (!repository.existsById(id)) {
            return "redirect:/lobby";
        }
        Optional<RoomModel> post = repository.findById(id);
        ArrayList<RoomModel> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "room_edit";
    }

    @PostMapping("/lobby/{id}/edit")
    public String room_update(@PathVariable(value = "id") long id, @RequestParam String nameOfRoom) {
        RoomModel post = repository.findById(id).orElseThrow();
        post.setNameOfRoom(nameOfRoom);
        repository.save(post);
        return "redirect:/lobby";
    }
}
