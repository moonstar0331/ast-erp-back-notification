package com.api.ast.notificationservice.dto;

import lombok.Data;

@Data
public class NotificationMessage {
    private Long notificationId;
    private Long userId;
    private String type;
    private String title;
    private String content;
    private String linkUrl;
}
