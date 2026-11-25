package com.taskApi.repository;

import com.taskApi.entity.TarefaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TarefaRepository extends JpaRepository<TarefaEntity, Long> {
    boolean existsByTituloAndClienteEmail(String titulo, String email);
    Page<TarefaEntity> findAllByClienteEmail(String email, Pageable pageable);
}
