package com.taskApi.dtos.response;

import com.taskApi.entity.ClienteEntity;
import com.taskApi.enums.StatusTarefaEnum;

public record TarefaResponse(
        Long id,
        String titulo,
        String descricao,
        StatusTarefaEnum status,
        ClienteEntity cliente
) {
}
