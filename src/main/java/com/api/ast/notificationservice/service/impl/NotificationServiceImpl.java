package com.api.ast.notificationservice.service.impl;

import com.api.ast.notificationservice.dto.NotificationDto;
import com.api.ast.notificationservice.dto.NotificationEvent;
import com.api.ast.notificationservice.dto.NotificationMessage;
import com.api.ast.notificationservice.mapper.NotificationMapper;
import com.api.ast.notificationservice.openfeign.AuthServiceClient;
import com.api.ast.notificationservice.service.NotificationPushService;
import com.api.ast.notificationservice.service.NotificationService;
import com.api.ast.notificationservice.vo.UserResponse;
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
    private final AuthServiceClient authServiceClient;

    @Override
    public List<NotificationDto> getNotifications(String userUuid) {
        return mapper.selectNotificationsByUserUuid(userUuid);
    }

    @Override
    @Transactional
    public void createNotification(NotificationEvent event) {
        NotificationDto notification = NotificationDto.builder()
                .userUuid(event.getUserUuid())
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
        NotificationMessage message = new NotificationMessage();
        message.setNotificationId(notification.getId());
        message.setUserUuid(notification.getUserUuid());
        message.setType(notification.getType());
        message.setTitle(notification.getTitle());
        message.setContent(notification.getContent());
        message.setLinkUrl(notification.getLinkUrl());

        pushService.push(message);
    }

    @Override
    @Transactional
    public void createBroadcastNotification(NotificationEvent event, String excludeUserUuid) {
        List<UserResponse> users = authServiceClient.getUsers();
        
        for (UserResponse user : users) {
            if (excludeUserUuid != null && excludeUserUuid.equals(user.getUserUuid())) {
                continue;
            }
            
            NotificationEvent individualEvent = new NotificationEvent();
            individualEvent.setUserUuid(user.getUserUuid());
            individualEvent.setType(event.getType());
            individualEvent.setTitle(event.getTitle());
            individualEvent.setContent(event.getContent());
            individualEvent.setLinkUrl(event.getLinkUrl());
            
            this.createNotification(individualEvent);
        }
    }

    @Override
    @Transactional
    public void markAsRead(Long notificationId) {
        mapper.updateReadStatus(notificationId, true);
    }
}
