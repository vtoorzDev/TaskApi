package com.taskApi.dtos.response;

import com.taskApi.enums.RoleUsuarios;

public record AdminResponse(
        Long id,
        String nome,
        String cpf,
        RoleUsuarios role
) {
}
