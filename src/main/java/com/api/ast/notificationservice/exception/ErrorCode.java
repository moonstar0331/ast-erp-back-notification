package com.api.ast.notificationservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"),
    BOARD_MASTER_SELECT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Board Master Select Error"),
    BOARD_MASTER_CREATE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Board Master Create Error"),
    BOARD_MASTER_UPDATE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Board Master Update Error"),
    BOARD_POST_CREATE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Board Post Create Error"),
    BOARD_POST_UPDATE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Board Post Update Error"),
    BOARD_POST_DELETE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Board Post Delete Error");

    private HttpStatus status;
    private String message;
}
