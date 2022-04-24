package com.example.simpletask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void saveAll(List<Messages> messages) {
        messageRepository.saveAll(messages);
    }

    @Override
    public List<Messages> findAll() {
        return messageRepository.findAll();
    }
}
