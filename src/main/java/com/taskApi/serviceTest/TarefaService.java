package com.taskApi.serviceTest;

import com.taskApi.dtos.request.AtualizarTarefaParcial;
import com.taskApi.dtos.request.ClienteRequest;
import com.taskApi.dtos.request.TarefaRequest;
import com.taskApi.dtos.response.TarefaResponse;
import com.taskApi.entity.ClienteEntity;
import com.taskApi.entity.TarefaEntity;
import com.taskApi.enums.StatusTarefaEnum;
import com.taskApi.exception.ClienteException;
import com.taskApi.exception.TarefaException;
import com.taskApi.repository.ClienteRepository;
import com.taskApi.repository.TarefaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TarefaService {
    private final TarefaRepository tarefaRepository;
    private final ClienteRepository clienteRepository;

    public TarefaService(TarefaRepository tarefaRepository, ClienteRepository clienteRepository) {
        this.tarefaRepository = tarefaRepository;
        this.clienteRepository = clienteRepository;
    }

    private TarefaResponse transformaResponse(TarefaEntity tarefaEntity) {
        return new TarefaResponse(
                tarefaEntity.getId(),
                tarefaEntity.getTitulo(),
                tarefaEntity.getDescricao(),
                tarefaEntity.getStatus(),
                tarefaEntity.getCliente()
        );
    }

    @Transactional
    public TarefaResponse cadastrar(TarefaRequest tarefaRequest, ClienteRequest clienteRequest) {
        ClienteEntity cliente = clienteRepository.findByEmail(clienteRequest.email())
                .orElseThrow(() -> new ClienteException("Não foi possível criar a tarefa, cliente não cadastrado"));

        if (tarefaRepository.existsByTituloAndClienteEmail(tarefaRequest.titulo(), clienteRequest.email())) {
            throw new TarefaException("Tarefa já existe nesse cliente");
        }

        TarefaEntity tarefaCadastrada = new TarefaEntity();
        tarefaCadastrada.setTitulo(tarefaRequest.titulo());
        tarefaCadastrada.setDescricao(tarefaRequest.descricao());
        tarefaCadastrada.setStatus(StatusTarefaEnum.PENDENTE);
        tarefaCadastrada.setCliente(cliente);


        TarefaEntity tarefa = tarefaRepository.save(tarefaCadastrada);
        return transformaResponse(tarefa);
    }

    public Page<TarefaResponse> listarTodas(ClienteRequest clienteRequest, Pageable pageable) {
        Page<TarefaEntity> listaTarefa = tarefaRepository.findAllByClienteEmail(clienteRequest.email(), pageable);

        if (listaTarefa.isEmpty()) {
            throw new TarefaException("O cliente não tem tarefas cadastradas");
        }
        return listaTarefa.map(this::transformaResponse);
    }

    @Transactional
    public TarefaResponse atualizarTarefa(Long id, TarefaRequest tarefaRequest) {
        TarefaEntity tarefaEncontrada = tarefaRepository.findById(id)
                .orElseThrow(() -> new TarefaException("Tarefa não encontrada"));

        tarefaEncontrada.setTitulo(tarefaRequest.titulo());
        tarefaEncontrada.setDescricao(tarefaRequest.descricao());
        tarefaEncontrada.setStatus(tarefaRequest.status());

        TarefaEntity tarefa = tarefaRepository.save(tarefaEncontrada);
        return transformaResponse(tarefa);
    }

    @Transactional
    public TarefaResponse atualizarParcial(Long id, AtualizarTarefaParcial atualizarTarefaParcial) {
        TarefaEntity tarefaEncontrada = tarefaRepository.findById(id)
                .orElseThrow(() -> new TarefaException("Tarefa não encontrada"));

        if (atualizarTarefaParcial.titulo() != null) {
            tarefaEncontrada.setTitulo(atualizarTarefaParcial.titulo());
        }

        if (atualizarTarefaParcial.descricao() != null) {
            tarefaEncontrada.setDescricao(atualizarTarefaParcial.descricao());
        }

        if (atualizarTarefaParcial.status() != null) {
            tarefaEncontrada.setStatus(atualizarTarefaParcial.status());
        }

        TarefaEntity tarefaAtualizada = tarefaRepository.save(tarefaEncontrada);
        return transformaResponse(tarefaAtualizada);
    }

    @Transactional
    public void deletar(Long id) {
        TarefaEntity tarefaEncontrada = tarefaRepository.findById(id)
                .orElseThrow(() -> new TarefaException("Tarefa não encontrada"));

        tarefaRepository.delete(tarefaEncontrada);
    }


}
