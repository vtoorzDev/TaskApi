package com.taskApi.entity;

import com.taskApi.enums.StatusTarefaEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tarefa")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tarefa_id")
    Long id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    StatusTarefaEnum status;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    ClienteEntity cliente;
}
