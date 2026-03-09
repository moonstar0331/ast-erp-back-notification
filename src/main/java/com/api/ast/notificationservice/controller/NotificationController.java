package com.api.ast.notificationservice.controller;

import com.api.ast.notificationservice.dto.NotificationDto;
import com.api.ast.notificationservice.dto.NotificationEvent;
import com.api.ast.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    // 현재 사용자의 알림 목록 조회
    @GetMapping
    public List<NotificationDto> getNotifications(@AuthenticationPrincipal Jwt jwt) {
        // user_uuid는 JWT의 sub 필드나 커스텀 클레임에서 추출
        String userUuid = jwt.getSubject(); 
        return notificationService.getNotifications(userUuid);
    }

    // 타 서비스에서 알림 생성 요청 (POST /api/notifications/send)
    @PostMapping("/send")
    public ResponseEntity<Void> sendNotification(@RequestBody NotificationEvent event) {
        notificationService.createNotification(event);
        return ResponseEntity.ok().build();
    }

    // 타 서비스에서 전체 알림 생성 요청 (POST /api/notifications/broadcast)
    @PostMapping("/broadcast")
    public ResponseEntity<Void> broadcastNotification(
            @RequestBody NotificationEvent event,
            @RequestParam(required = false) String excludeUserUuid) {
        notificationService.createBroadcastNotification(event, excludeUserUuid);
        return ResponseEntity.ok().build();
    }

    // 알림 읽음 처리
    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok().build();
    }
}
