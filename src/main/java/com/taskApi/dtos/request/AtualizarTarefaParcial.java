package com.taskApi.dtos.request;

import com.taskApi.enums.StatusTarefaEnum;
import jakarta.validation.constraints.Size;

public record AtualizarTarefaParcial(
        @Size(min = 3, max = 100)
        String titulo,

        @Size(min = 3, max = 500)
        String descricao,

        StatusTarefaEnum status
) {
}
