package com.taskApi.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AdminRequest(
        @Size(min = 3)
        @NotNull
        String nome,

        @Size(min = 11, max = 11)
        @NotNull
        String cpf,

        @Size(min = 3)
        @NotNull
        String senha
) {
}
