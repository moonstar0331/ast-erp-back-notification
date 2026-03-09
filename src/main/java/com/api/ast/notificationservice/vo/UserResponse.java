package com.api.ast.notificationservice.vo;

import lombok.Data;

@Data
public class UserResponse {
    private Long userId;
    private String loginId;
    private String name;
    private String userUuid;
}
