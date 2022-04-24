package com.example.simpletask;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name="messages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Messages {

    public enum MessageType {
        MSG,
        CALL
    }

    public enum MessageStatus {
        DELIVERED,
        SEEN,
        NOTMSG
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private MessageType message_type;
    private Long timestamp;
    private String origin;
    private String destination;
    private String duration;
    private String status_code;
    private String status_description;
    private String message_content;
    @Enumerated(EnumType.STRING)
    private MessageStatus message_status;
    private String json_file_id;
    private LocalDateTime date;
}
