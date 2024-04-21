package com.alemkhan.webscoketdemo.auth;

import lombok.Builder;

@Builder
public record AuthRespDto(String token, String username, String message) { }
