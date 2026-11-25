package com.taskApi.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ClienteRequest(
        @NotNull(message = "Campo de nome precisa ser preenchido")
        @Size(min = 3, message = "o nome precisa ter mais de 3 letras")
        String nome,

        @Email(message = "O email precisa estar no formato certo")
        @NotNull(message = "O campo de email precisa ser preenchido")
        String email,

        @NotNull(message = "O campo de senha precisa ser preenchido")
        @Size(min = 3, message = "A senha deve conter pelo menos 3 caracteres")
        String senha
) {
}
