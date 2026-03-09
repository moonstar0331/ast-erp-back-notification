package com.api.ast.notificationservice.service;

import com.api.ast.notificationservice.dto.NotificationDto;
import com.api.ast.notificationservice.dto.NotificationEvent;

import java.util.List;

public interface NotificationService {
    List<NotificationDto> getNotifications(String userUuid);
    void createNotification(NotificationEvent event);
    void createBroadcastNotification(NotificationEvent event, String excludeUserUuid);
    void markAsRead(Long notificationId);
}
