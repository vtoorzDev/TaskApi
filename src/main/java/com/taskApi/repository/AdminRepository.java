package com.taskApi.repository;

import com.taskApi.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
    boolean existsByCpf(String cpf);
    Optional<AdminEntity> findByCpf(String cpf);
}
