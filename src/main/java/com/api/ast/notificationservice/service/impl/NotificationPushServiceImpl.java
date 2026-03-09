package com.api.ast.notificationservice.service.impl;

import com.api.ast.notificationservice.dto.NotificationEvent;
import com.api.ast.notificationservice.service.NotificationPushService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationPushServiceImpl implements NotificationPushService {

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void push(NotificationEvent event) {
        messagingTemplate.convertAndSendToUser(
                event.getUserId().toString(),
                "/queue/notification",
                event
        );
    }
}
