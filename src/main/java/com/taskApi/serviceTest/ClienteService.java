package com.taskApi.serviceTest;

import com.taskApi.dtos.request.ClienteAtualizacaoRequest;
import com.taskApi.dtos.request.ClienteLoginRequest;
import com.taskApi.dtos.request.ClienteRequest;
import com.taskApi.dtos.response.ClienteResponse;
import com.taskApi.dtos.response.ClienteTokenResponse;
import com.taskApi.entity.ClienteEntity;
import com.taskApi.enums.RoleUsuarios;
import com.taskApi.exception.ClienteException;
import com.taskApi.repository.ClienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ClienteService(ClienteRepository clienteRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    private ClienteResponse transformaResponse(ClienteEntity clienteEntity) {
        return new ClienteResponse(
                clienteEntity.getId(),
                clienteEntity.getNome(),
                clienteEntity.getEmail(),
                clienteEntity.getRole()
        );
    }

    @Transactional
    public ClienteResponse cadastrar(ClienteRequest clienteRequest) {
        boolean clienteEncontrado = clienteRepository.existsByEmail(clienteRequest.email());

        if (clienteEncontrado) {
            throw new ClienteException("Cliente já existe no banco de dados, não foi possivel cadastrar");
        }

        ClienteEntity clienteCadastrado = new ClienteEntity();
        clienteCadastrado.setNome(clienteRequest.nome());
        clienteCadastrado.setEmail(clienteRequest.email());
        clienteCadastrado.setSenha(passwordEncoder.encode(clienteRequest.senha()));
        clienteCadastrado.setRole(RoleUsuarios.CLIENTE);

        ClienteEntity cliente = clienteRepository.save(clienteCadastrado);
        return transformaResponse(cliente);
    }

    public ClienteTokenResponse login(ClienteLoginRequest clienteLoginRequest) {

        try{
            var authenticationToken = new UsernamePasswordAuthenticationToken(clienteLoginRequest.email(), clienteLoginRequest.senha());
            var authentication = authenticationManager.authenticate(authenticationToken);
            var cliente = (ClienteEntity) authentication.getPrincipal();

            String token = jwtService.gerarToken(
                    cliente.getEmail(),
                    cliente.getRole().name()
            );

            return new ClienteTokenResponse(token);

        } catch (BadCredentialsException e) {
            throw new ClienteException("Email ou senha invalidos");
        }
    }

    public Page<ClienteResponse> listarTodosClientes(Pageable pageable) {
        Page<ClienteEntity> cliente = clienteRepository.findAll(pageable);

        if(cliente.isEmpty()) {
            throw new ClienteException("Nenhum clienta cadastrado no sistema");
        }
        return cliente.map(this::transformaResponse);
    }

    @Transactional
    public ClienteResponse atualizar(ClienteRequest clienteRequest, Long id) {
        ClienteEntity clienteEncontrado = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteException("Cliente não cadastrado no sistema"));

        clienteEncontrado.setEmail(clienteRequest.email());
        clienteEncontrado.setNome(clienteRequest.nome());
        clienteEncontrado.setSenha(passwordEncoder.encode(clienteRequest.senha()));

        ClienteEntity clienteAtualizado = clienteRepository.save(clienteEncontrado);
        return transformaResponse(clienteAtualizado);
    }

    @Transactional
    public ClienteResponse atualizarParcial(ClienteAtualizacaoRequest clienteAtualizacaoRequest, Long id) {
        ClienteEntity clienteEncontrado = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteException("Cliente não cadastrado no sistema"));

        if (clienteAtualizacaoRequest.nome() != null) {
            clienteEncontrado.setNome(clienteAtualizacaoRequest.nome());
        }

        if (clienteAtualizacaoRequest.senha() != null) {
            clienteEncontrado.setSenha(passwordEncoder.encode(clienteAtualizacaoRequest.senha()));
        }

        if (clienteAtualizacaoRequest.email() != null) {
            clienteEncontrado.setEmail(clienteAtualizacaoRequest.email());
        }

        ClienteEntity clienteAtualizado = clienteRepository.save(clienteEncontrado);
        return transformaResponse(clienteAtualizado);
    }

    @Transactional
    public void deletar(Long id) {
        ClienteEntity clienteEncontrado = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteException("Cliente não encontrado no sistema"));

        clienteRepository.delete(clienteEncontrado);
    }

}
