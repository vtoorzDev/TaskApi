package com.taskApi.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record ClienteAtualizacaoRequest(
        @Size(min = 3, message = "o nome precisa ter mais de 3 letras")
        String nome,

        @Email(message = "O email precisa estar no formato certo")
        String email,

        @Size(min = 3, message = "A senha deve conter pelo menos 3 caracteres")
        String senha
) {
}
