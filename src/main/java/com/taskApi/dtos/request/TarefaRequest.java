package com.taskApi.dtos.request;

import com.taskApi.enums.StatusTarefaEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TarefaRequest(
        @NotNull
        @Size(min = 3, max = 100)
        String titulo,

        @NotNull
        @Size(min = 3, max = 500)
        String descricao,

        @NotNull
        StatusTarefaEnum status
) {
}
