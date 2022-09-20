package com.example.messageuserapp.MqConfig;

import java.util.concurrent.CountDownLatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class Receiver {
    private final CountDownLatch latch = new CountDownLatch(1);

    @RabbitListener
    public void receiveMessage(String message) {
        log.info(message);
        System.out.println(message);
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}