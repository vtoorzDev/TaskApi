package com.taskApi.controller;

import com.taskApi.dtos.request.AdminAtualizacaoRequest;
import com.taskApi.dtos.request.AdminLoginRequest;
import com.taskApi.dtos.request.AdminRequest;
import com.taskApi.dtos.response.AdminResponse;
import com.taskApi.dtos.response.AdminTokenResponse;
import com.taskApi.serviceTest.AdminService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<AdminResponse> registrar(@Valid @RequestBody AdminRequest adminRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.registrar(adminRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AdminTokenResponse> login(@Valid @RequestBody AdminLoginRequest adminLoginRequest ) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.login(adminLoginRequest));
    }

    @GetMapping("/listar-todos")
    public ResponseEntity<Page<AdminResponse>> listarTodos(@PageableDefault(size = 10)Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.listarTodos(pageable));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<AdminResponse> atualizar(@Valid @RequestBody AdminRequest adminRequest, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.atualizar(adminRequest, id));
    }

    @PatchMapping("/atualizar-parcial")
    public ResponseEntity<AdminResponse> atualizarParcial(@Valid @RequestBody AdminAtualizacaoRequest adminAtualizacaoRequest, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.atualizarParcial(adminAtualizacaoRequest, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        adminService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
