package com.taskApi.controller;

import com.taskApi.dtos.request.AtualizarTarefaParcial;
import com.taskApi.dtos.request.ClienteRequest;
import com.taskApi.dtos.request.TarefaRequest;
import com.taskApi.dtos.response.TarefaResponse;
import com.taskApi.serviceTest.TarefaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {
    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<TarefaResponse> cadastrar(@Valid @RequestBody TarefaRequest tarefaRequest, ClienteRequest clienteRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaService.cadastrar(tarefaRequest, clienteRequest));
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<TarefaResponse>> listar(@PageableDefault(size = 10)ClienteRequest clienteRequest, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(tarefaService.listarTodas(clienteRequest, pageable));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<TarefaResponse> atualizar(@Valid @RequestBody TarefaRequest tarefaRequest, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(tarefaService.atualizarTarefa(id, tarefaRequest));
    }

    @PatchMapping("/atualizar-parcial/{id}")
    public ResponseEntity<TarefaResponse> atualizarParcial(@Valid @RequestBody AtualizarTarefaParcial atualizarTarefaParcial, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(tarefaService.atualizarParcial(id, atualizarTarefaParcial));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void>deletar(@PathVariable Long id) {
        tarefaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
