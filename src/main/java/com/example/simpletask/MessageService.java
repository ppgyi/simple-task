package com.example.simpletask;

import java.util.List;

public interface MessageService {

    void saveAll(List<Messages> messages);
    List<Messages> findAll();
}
