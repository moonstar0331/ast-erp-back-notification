package com.api.ast.notificationservice.service;

import com.api.ast.notificationservice.dto.NotificationEvent;

public interface NotificationPushService {

    public void push(NotificationEvent event);
}
