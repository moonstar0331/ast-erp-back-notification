package com.api.ast.notificationservice.dto;

import lombok.Data;

@Data
public class NotificationMessage {
    private Long notificationId;
    private String userUuid;
    private String type;
    private String title;
    private String content;
    private String linkUrl;
}
