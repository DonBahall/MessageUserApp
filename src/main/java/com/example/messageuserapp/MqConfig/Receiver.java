package com.example.messageuserapp.MqConfig;

import java.util.concurrent.CountDownLatch;
import org.springframework.stereotype.Component;


@Component
public class Receiver {

    private final CountDownLatch latch = new CountDownLatch(1);

    public Receiver() {
    }

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}