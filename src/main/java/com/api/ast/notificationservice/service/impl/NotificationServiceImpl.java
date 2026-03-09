package com.api.ast.notificationservice.service.impl;

import com.api.ast.notificationservice.dto.NotificationDto;
import com.api.ast.notificationservice.dto.NotificationEvent;
import com.api.ast.notificationservice.mapper.NotificationMapper;
import com.api.ast.notificationservice.service.NotificationPushService;
import com.api.ast.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationMapper mapper;
    private final NotificationPushService pushService;

    @Override
    public List<NotificationDto> getNotifications(Long userId) {
        return mapper.selectNotificationsByUserId(userId);
    }

    @Override
    @Transactional
    public void createNotification(NotificationEvent event) {
        NotificationDto notification = NotificationDto.builder()
                .userId(event.getUserId())
                .type(event.getType())
                .title(event.getTitle())
                .content(event.getContent())
                .linkUrl(event.getLinkUrl())
                .isRead(false)
                .build();

        // 1. PostgreSQL에 알림 저장
        mapper.insertNotification(notification);
        log.info("Notification saved: {}", notification.getId());

        // 2. WebSocket을 통해 클라이언트에 푸시
        pushService.push(event);
    }

    @Override
    @Transactional
    public void markAsRead(Long notificationId) {
        mapper.updateReadStatus(notificationId, true);
    }
}
