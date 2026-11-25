package com.taskApi.dtos.request;

import jakarta.validation.constraints.Size;

public record AdminAtualizacaoRequest(
        @Size(min = 3)
        String nome,

        @Size(min = 11, max = 11)
        String cpf,

        @Size(min = 3)
        String senha
) {
}
