CREATE TABLE tarefa(
   tarefa_id BIGINT AUTO_INCREMENT PRIMARY KEY,
   titulo VARCHAR(100) NOT NULL,
   descricao VARCHAR(255) NOT NULL,
   status VARCHAR(20) NOT NULL,
   cliente_id BIGINT NOT NULL,

                       CONSTRAINT fk_tarefa_cliente
                           FOREIGN KEY (cliente_id) REFERENCES cliente(cliente_id)
);