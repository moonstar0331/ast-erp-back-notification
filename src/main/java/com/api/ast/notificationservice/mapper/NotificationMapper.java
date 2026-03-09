package com.api.ast.notificationservice.mapper;

import com.api.ast.notificationservice.dto.NotificationDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NotificationMapper {
    int insertNotification(NotificationDto notification);
    List<NotificationDto> selectNotificationsByUserUuid(@Param("userUuid") String userUuid);
    int updateReadStatus(@Param("id") Long id, @Param("isRead") boolean isRead);
}
