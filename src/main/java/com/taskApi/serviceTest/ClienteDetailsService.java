package com.taskApi.serviceTest;

import com.taskApi.entity.ClienteEntity;
import com.taskApi.exception.ClienteException;
import com.taskApi.repository.ClienteRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClienteDetailsService implements UserDetailsService {
    private final ClienteRepository clienteRepository;

    public ClienteDetailsService(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ClienteEntity clienteEncontrado = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new ClienteException("Cliente não encontrado"));
        return clienteEncontrado;
    }
}
