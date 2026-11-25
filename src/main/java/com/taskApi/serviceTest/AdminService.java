package com.taskApi.serviceTest;

import com.taskApi.dtos.request.AdminAtualizacaoRequest;
import com.taskApi.dtos.request.AdminLoginRequest;
import com.taskApi.dtos.request.AdminRequest;
import com.taskApi.dtos.response.AdminResponse;
import com.taskApi.dtos.response.AdminTokenResponse;
import com.taskApi.entity.AdminEntity;
import com.taskApi.enums.RoleUsuarios;
import com.taskApi.exception.AdminException;
import com.taskApi.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder, @Qualifier("adminAuthenticationManager") AuthenticationManager authenticationManager, JwtService jwtService) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;

    }

    private AdminResponse transformaResponse(AdminEntity adminEntity) {
        return new AdminResponse(

                adminEntity.getId(),
                adminEntity.getNome(),
                adminEntity.getCpf(),
                adminEntity.getRole()
        );
    }


    @Transactional
    public AdminResponse registrar(AdminRequest adminRequest) {
        boolean adminEncontrado = adminRepository.existsByCpf(adminRequest.cpf());

        if (adminEncontrado) {
            throw new AdminException("Admin já cadastrado no sistema");
        }

        AdminEntity adminCadastrado = new AdminEntity();
        adminCadastrado.setNome(adminRequest.nome());
        adminCadastrado.setCpf(adminRequest.cpf());
        adminCadastrado.setSenha(passwordEncoder.encode(adminRequest.senha()));
        adminCadastrado.setRole(RoleUsuarios.ADMIN);

        AdminEntity admin = adminRepository.save(adminCadastrado);
        return transformaResponse(admin);
    }

    public AdminTokenResponse login(AdminLoginRequest adminLoginRequest) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(adminLoginRequest.cpf(), adminLoginRequest.senha());
        var authentication = authenticationManager.authenticate(authenticationToken);
        var admin = (AdminEntity) authentication.getPrincipal();
        String token = jwtService.gerarTokenAdmin(admin.getCpf(), admin.getRole().name());

        return new AdminTokenResponse(token);
    }

    public Page<AdminResponse>listarTodos(Pageable pageable) {
        Page<AdminEntity> listaAdmin = adminRepository.findAll(pageable);

        if (listaAdmin.isEmpty()) {
            throw new AdminException("Nenhum admin cadastrado no sistema");
        }
        return listaAdmin.map(this::transformaResponse);
    }

    @Transactional
    public AdminResponse atualizar(AdminRequest adminRequest, Long id) {
        AdminEntity admin = adminRepository.findById(id)
                .orElseThrow(() -> new AdminException("Admin não encontrado no sistema"));

        admin.setNome(adminRequest.nome());
        admin.setSenha(passwordEncoder.encode(adminRequest.senha()));
        admin.setCpf(adminRequest.cpf());

        AdminEntity adminAtualizado = adminRepository.save(admin);
        return transformaResponse(adminAtualizado);
    }

    @Transactional
    public AdminResponse atualizarParcial(AdminAtualizacaoRequest adminAtualizacaoRequest, Long id) {
        AdminEntity adminEncontrado = adminRepository.findById(id)
                .orElseThrow(() -> new AdminException("Admin não encontrado no sistema"));

        if (adminAtualizacaoRequest.nome() != null) {
            adminEncontrado.setNome(adminAtualizacaoRequest.nome());
        }

        if (adminAtualizacaoRequest.cpf() != null) {
            adminEncontrado.setCpf(adminAtualizacaoRequest.cpf());
        }

        if (adminAtualizacaoRequest.senha() != null) {
            adminEncontrado.setSenha(passwordEncoder.encode(adminAtualizacaoRequest.senha()));
        }

        AdminEntity admin = adminRepository.save(adminEncontrado);
        return transformaResponse(admin);
    }

    public void deletar(Long id) {
        AdminEntity adminEncontrado = adminRepository.findById(id)
                .orElseThrow(() -> new AdminException("Admin não encontrado no sistema"));

         adminRepository.delete(adminEncontrado);
    }
}
