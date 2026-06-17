package com.vk.dto;

import java.time.LocalDateTime;

public record UserResponse(Long id, String userName, String email, LocalDateTime createdAt) {

}
