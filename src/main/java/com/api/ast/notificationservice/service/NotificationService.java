package com.api.ast.notificationservice.service;

import com.api.ast.notificationservice.dto.NotificationDto;
import com.api.ast.notificationservice.dto.NotificationEvent;

import java.util.List;

public interface NotificationService {
    List<NotificationDto> getNotifications(Long userId);
    void createNotification(NotificationEvent event);
    void createBroadcastNotification(NotificationEvent event, Long excludeUserId);
    void markAsRead(Long notificationId);
}
