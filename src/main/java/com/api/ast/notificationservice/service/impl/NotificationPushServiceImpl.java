package com.api.ast.notificationservice.service.impl;

import com.api.ast.notificationservice.dto.NotificationMessage;
import com.api.ast.notificationservice.redis.RedisPublisher;
import com.api.ast.notificationservice.service.NotificationPushService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationPushServiceImpl implements NotificationPushService {

    private final RedisPublisher redisPublisher;

    @Override
    public void push(NotificationMessage message) {
        redisPublisher.publish(message);
    }
}
