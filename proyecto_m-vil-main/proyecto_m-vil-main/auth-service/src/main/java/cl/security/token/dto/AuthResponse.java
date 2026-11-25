package cl.security.token.dto;

public record AuthResponse(String token, String rol, String correo) {}