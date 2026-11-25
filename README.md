# Task API

API REST desenvolvida com Java e Spring Boot para gerenciamento de tarefas e clientes, oferecendo autenticação e autorização com JWT, controle de acesso por usuário e operações de gerenciamento de tarefas.

## Tecnologias utilizadas

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- JWT (JSON Web Token)
- Hibernate
- MySQL
- Maven

## Funcionalidades

### Clientes
- Cadastro de clientes.
- Login com autenticação JWT.
- Criptografia de senhas.
- Listagem de clientes com paginação.

### Tarefas
- Cadastro de tarefas.
- Consulta de tarefas.
- Atualização de tarefas.
- Exclusão de tarefas.
- Listagem de tarefas com paginação.
- Associação de tarefas aos respectivos clientes.

### Segurança
- Autenticação utilizando JWT.
- Autorização baseada em roles.
- Proteção de endpoints.
- Controle de acesso às tarefas de acordo com o cliente autenticado.

## Arquitetura

O projeto utiliza uma estrutura em camadas para organizar as responsabilidades da aplicação:

- **Controller:** responsável pelos endpoints e requisições HTTP.
- **Service:** concentra as regras de negócio.
- **Repository:** responsável pelo acesso e persistência dos dados.
- **Entity:** representa as entidades do banco de dados.
- **DTO:** responsável pela transferência de dados entre as camadas e a API.
- **Config:** configurações de segurança e demais componentes da aplicação.
- **Exception:** tratamento de exceções personalizadas e globais.

## Banco de dados

O projeto utiliza MySQL para persistência dos dados.

Para executar a aplicação, configure as credenciais do banco de dados no arquivo `application.properties`.

## Como executar o projeto

**1. Clone o repositório**

```bash
git clone https://github.com/vtoorzDev/taskApi.git
```

**2. Acesse a pasta do projeto**

```bash
cd taskApi
```

**3. Configure o banco de dados**

Crie um banco de dados MySQL e configure as credenciais de acesso no `application.properties`.

**4. Execute a aplicação**

Utilizando Maven:

```bash
./mvnw spring-boot:run
```

No Windows:

```bash
mvnw.cmd spring-boot:run
```

## Objetivo do projeto

O projeto foi desenvolvido com o objetivo de aprofundar conhecimentos em desenvolvimento Backend com Java e Spring Boot, trabalhando com construção de APIs REST, persistência de dados, autenticação, autorização e organização de código em camadas.
