package com.api.ast.notificationservice.dto;

import lombok.Data;

@Data
public class NotificationEvent {
    private Long userId;

    private String type;

    private String title;

    private String content;

    private String linkUrl;
}
