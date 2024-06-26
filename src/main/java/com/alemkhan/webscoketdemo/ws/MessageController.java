package com.alemkhan.webscoketdemo.ws;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageRepository messageRepository;


    @MessageMapping("/chat")
    public void chat(@Payload Message message) {
        log.info("Message received: {}", message);

        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setToUser(message.to());
        messageEntity.setMessage(message.message());
        messageEntity.setFromUser(message.from());
        messageRepository.save(messageEntity);

        simpMessagingTemplate.convertAndSendToUser(message.to(), "/topic", message);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<MessageEntity>> getAllMessages(Authentication authentication) {
        List<MessageEntity> messages = messageRepository.findByToUser(authentication.getName());
        return ResponseEntity.ok(messages);
    }

}
