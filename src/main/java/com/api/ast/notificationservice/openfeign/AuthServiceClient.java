package com.api.ast.notificationservice.openfeign;

import com.api.ast.notificationservice.vo.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "auth-service")
public interface AuthServiceClient {

    @GetMapping("/api/users")
    List<UserResponse> getUsers();
}
