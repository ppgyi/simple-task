package com.example.simpletask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

@RestController
public class MessageController {

    @Autowired
    private MessageServiceImpl messageService;

    @GetMapping("/metrics")
    public ResponseEntity getCounters(){
        return new ResponseEntity<>(new ResponseCounters(messageService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/metrics/{date}")
    public ResponseEntity getCounters(@PathVariable("date") String mydate) throws ParseException {
        SimpleDateFormat sd = new SimpleDateFormat("yyMMdd");
        Date date = sd.parse(mydate);
        LocalDateTime dateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        List<Messages> messagesByDate = new ArrayList<>();
        for (Messages message : messageService.findAll()){
            if (message.getDate().equals(dateTime)){
                messagesByDate.add(message);
            }
        }
        return new ResponseEntity<>(new ResponseCounters(messagesByDate), HttpStatus.OK);
    }

    @PostMapping("/messages")
    public ResponseEntity saveMessages(@RequestBody List<Messages> messages){
        try {
            String json_file_id = UUID.randomUUID().toString();
            for (Messages message : messages){
                Instant instant = Instant.ofEpochSecond( message.getTimestamp() );
                LocalDateTime date = LocalDateTime.ofInstant( instant, ZoneOffset.UTC );
                message.setDate(date);
                message.setJson_file_id(json_file_id);
                if (message.getMessage_type().equals(Messages.MessageType.CALL)){
                    message.setMessage_content("NULL");
                    message.setMessage_status(Messages.MessageStatus.NOTMSG);
                } else if (message.getMessage_type().equals(Messages.MessageType.MSG)){
                    message.setDuration("NULL");
                    message.setStatus_code("NULL");
                    message.setStatus_description("NULL");
                    if (message.getMessage_status().equals(Messages.MessageStatus.NOTMSG)){
                        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);                }
                }
            }
            messageService.saveAll(messages);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException|NullPointerException e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleNoSuchElementFoundException(HttpMessageNotReadableException exception){
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
