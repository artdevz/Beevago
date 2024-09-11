# BeevagoApp

**Descrição:** Beevago é um site de hospedagem de hotéis e reservas de quartos.

**Autores:** Arthur Dantas, Bruno Costa, Levi Mena, Rafael Andrade

## Tabela de Conteúdos:
1. [Ideia proposta para solução](#Ideia-proposta-para-solução)
2. [Passos de instalação](#Passos-de-instalação)
   - [Clonagem do repositório da aplicação](#Clonagem-do-repositório-da-aplicação)
   - [Instalação de dependências](#Instalação-de-dependências)
3. [Execução do projeto](#Execução-do-projeto)
   - [Credenciais para login](#Credenciais-para-login)
   - [Regras de negócio implementadas](#Regras-de-negócio-implementadas)
4. [Resultados](#Resultados)
5. [Estrutura de diretório e arquivos](#Estrutura-de-diretório-e-arquivos)
6. [Detalhes de implementação](#Detalhes-de-implementação)
7. [Insights](#Insights)
8. [Desafios & Dificuldades](#Desafios--Dificuldades)

---

## Ideia proposta para solução:
**Objetivo:** Criar um sistema de hospedagem de hotéis e reservas de quartos com interface amigável para os usuários, utilizando o framework Spring Boot para a camada backend e MySQL como banco de dados. 

- **Cadastro de Usuários:** Visitantes podem se registrar como hóspedes.
- **Gerenciamento de Quartos:** Administradores de hotéis podem adicionar, editar e remover informações sobre quartos disponíveis.
- **Sistema de Reservas:** Usuários podem buscar por hotéis, selecionar quartos e efetuar reservas.

---

## Passos de instalação:

### Clonagem do repositório da aplicação:
```git clone https://github.com/seu-usuario/BeevagoApp.git cd BeevagoApp```

### Instalação de dependências:
É necessário ter o **Java 17** e o **MySQL 8.0.33** instalados previamente.

Para instalar as dependências do projeto:
```./mvnw install```

---

## Execução do projeto:

Para rodar o servidor:

```./mvnw spring-boot```

Quando o servidor estiver ativo, acesse o site através de [localhost](http://localhost:8080):


### Credenciais para login:
- **Administrador:**
   - Usuário: admin
   - Senha: admin123
- **Usuário padrão:** Após o registro, o login estará disponível com o e-mail e senha cadastrados.

---

## Regras de negócio implementadas:
- **Reservas:** O sistema verifica a disponibilidade dos quartos em tempo real.
- **Cadastro de Hotéis:** Administradores podem gerenciar hotéis e seus quartos.
- **Cancelamento de Reservas:** Cancelamento de reservas permitido até 24 horas antes do check-in.

---

## Resultados:

O sistema está preparado para receber reservas e gerenciar hotéis, proporcionando uma experiência simplificada para usuários e administradores.


---

## Detalhes de implementação:

O sistema foi desenvolvido com:
- **Spring Boot 3.3.0:** Gerenciamento de rotas e regras de negócio.
- **MySQL 8.0.33:** Armazenamento de dados.
- **Maven 3.6.3:** Gerenciamento de dependências.

---

## Insights:
A utilização do Spring Boot facilitou o desenvolvimento de APIs RESTful. A integração com o banco de dados MySQL permitiu um gerenciamento eficiente das transações de reserva e cadastro de hotéis.

---

## Desafios & Dificuldades:
- Implementar a lógica de cancelamento de reservas foi um dos principais desafios, exigindo tratamentos de exceção robustos.
- A sincronização entre diferentes perfis de usuários (administrador e hóspedes) apresentou desafios de segurança e autorização.


