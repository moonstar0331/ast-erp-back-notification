package com.api.ast.notificationservice.service;

import com.api.ast.notificationservice.dto.NotificationMessage;

public interface NotificationPushService {

    void push(NotificationMessage message);
}
