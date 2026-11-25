package com.taskApi.serviceTest;

import com.taskApi.entity.AdminEntity;
import com.taskApi.exception.AdminException;
import com.taskApi.repository.AdminRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    public AdminDetailsService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        AdminEntity adminEncontrado = adminRepository.findByCpf(cpf)
                .orElseThrow(() -> new AdminException("Admin não encontrado no sistema"));
        return adminEncontrado;
    }
}
