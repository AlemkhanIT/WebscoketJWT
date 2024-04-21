package com.alemkhan.webscoketdemo.ws;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Table(name = "messages")
@Data
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String toUser;
    private String message;
    private String fromUser;
}