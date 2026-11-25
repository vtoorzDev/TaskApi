package com.taskApi.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AdminLoginRequest(
        @NotNull
        @Size(min = 11, max = 11)
        String cpf,

        @NotNull
        String senha
) {
}
