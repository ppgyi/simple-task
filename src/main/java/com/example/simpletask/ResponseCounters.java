package com.example.simpletask;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Getter
public class ResponseCounters {
    private int processedJsonFiles;
    private int numberOfRows;
    private int numberOfCalls;
    private int numberOfMessages;
    private int diffOrigin;
    private int diffDestination;

    public ResponseCounters(List<Messages> messages) {
        ArrayList<String> jsonFiles = new ArrayList<>();
        ArrayList<Messages.MessageType> callsCall = new ArrayList<>();
        ArrayList<Messages.MessageType> messagesMsg = new ArrayList<>();
        ArrayList<String> originList = new ArrayList<>();
        ArrayList<String> destinationList = new ArrayList<>();

        for (Messages message : messages){
            jsonFiles.add(message.getJson_file_id());
            originList.add(message.getOrigin());
            destinationList.add(message.getDestination());
            if (message.getMessage_type() == Messages.MessageType.MSG){
                callsCall.add(message.getMessage_type());
            } else if (message.getMessage_type() == Messages.MessageType.CALL){
                messagesMsg.add(message.getMessage_type());
            }
        }

        HashSet<String> uniqueJsonFiles = new HashSet<>();
        uniqueJsonFiles.addAll(jsonFiles);

        HashSet<String> uniqueOriginList = new HashSet<>();
        uniqueOriginList.addAll(originList);

        HashSet<String> uniqueDestinationList = new HashSet<>();
        uniqueDestinationList.addAll(destinationList);

        this.processedJsonFiles = uniqueJsonFiles.size();
        this.numberOfRows = messages.size();
        this.numberOfCalls = callsCall.size();
        this.numberOfMessages = messagesMsg.size();
        this.diffOrigin = uniqueOriginList.size();
        this.diffDestination = uniqueDestinationList.size();
    }
}
