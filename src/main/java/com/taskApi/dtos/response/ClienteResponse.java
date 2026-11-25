package com.taskApi.dtos.response;

import com.taskApi.enums.RoleUsuarios;

public record ClienteResponse(
        Long id,
        String nome,
        String email,
        RoleUsuarios role
) {
}
