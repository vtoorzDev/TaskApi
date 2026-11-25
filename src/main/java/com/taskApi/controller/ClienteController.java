package com.taskApi.controller;

import com.taskApi.dtos.request.ClienteAtualizacaoRequest;
import com.taskApi.dtos.request.ClienteLoginRequest;
import com.taskApi.dtos.request.ClienteRequest;
import com.taskApi.dtos.response.ClienteResponse;
import com.taskApi.dtos.response.ClienteTokenResponse;
import com.taskApi.serviceTest.ClienteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<ClienteResponse> cadastrar(@Valid @RequestBody ClienteRequest clienteRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.cadastrar(clienteRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<ClienteTokenResponse> login(@Valid @RequestBody ClienteLoginRequest clienteLoginRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.login(clienteLoginRequest));
    }

    @GetMapping("/listar-todos")
    public ResponseEntity<Page<ClienteResponse>>listarTodos(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.listarTodosClientes(pageable));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ClienteResponse> atualizar(@Valid @RequestBody ClienteRequest clienteRequest, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.atualizar(clienteRequest, id));
    }

    @PatchMapping("/atualizar-parcial/{id}")
    public ResponseEntity<ClienteResponse> atualizarParcial(@Valid @RequestBody ClienteAtualizacaoRequest clienteAtualizacaoRequest, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.atualizarParcial(clienteAtualizacaoRequest, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
