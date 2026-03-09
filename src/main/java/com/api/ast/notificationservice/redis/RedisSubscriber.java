package com.api.ast.notificationservice.redis;

import com.api.ast.notificationservice.dto.NotificationMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String body = new String(message.getBody());
            NotificationMessage msg =
                    objectMapper.readValue(body, NotificationMessage.class);
            
            log.info("Redis message received for user UUID: {}", msg.getUserUuid());
            
            // STOMP /user/{userUuid}/queue/notification 으로 전송
            messagingTemplate.convertAndSendToUser(
                    msg.getUserUuid(),
                    "/queue/notification",
                    msg
            );
        } catch (Exception e) {
            log.error("Error processing Redis message: {}", e.getMessage());
        }
    }
}
