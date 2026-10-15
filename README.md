# Sistema de Vendas — Projeto 1

## O que foi implementado
Spring Security com login HTML e logout por POST, usuários persistidos no PostgreSQL com senhas BCrypt, roles ADMIN/PADRAO, controle de URLs e navegação via Thymeleaf Security. Proteção CSRF mantida.

- **ADMIN**: produtos, clientes e pedidos (cadastro, alteração, exclusão e consulta).
- **PADRAO**: somente pedidos (consulta, criação, edição e cancelamento). Seleciona produtos e clientes no formulário do pedido, mas não acessa o cadastro deles.

## Rodar localmente com Docker
1. Copie `.env.example` para `.env` e **troque as duas senhas por senhas exclusivas com 12+ caracteres**.
2. Execute `docker compose up --build` na pasta raiz.
3. Acesse `http://localhost:8080/login` e faça login com os e-mails/senhas do `.env`.
4. O compose sobe tanto PostgreSQL quanto a aplicação. Para parar, `docker compose down` (sem `-v` para preservar os dados).

Alternativa: com PostgreSQL iniciado via `docker compose up -d db`, configure `DB_URL`, `DB_USER`, `DB_PASSWORD`, `ADMIN_EMAIL`, `ADMIN_PASSWORD`, `PADRAO_EMAIL`, `PADRAO_PASSWORD` no terminal e execute `./mvnw spring-boot:run` (Windows: `mvnw.cmd spring-boot:run`). Java 21.

**Importante:** o bootstrap cria cada usuário apenas quando o e-mail ainda não existir; mudar uma variável `*_PASSWORD` após a criação **não altera a senha existente**. Para alterar senhas em ambiente já provisionado, faça atualização com BCrypt no banco usando um procedimento administrativo seguro; não apague o banco de produção.

## Render
1. Publique este código em um repositório GitHub, excluindo `.env` e dados particulares.
2. No Render crie PostgreSQL e Docker Web Service **na mesma região** e conecte o repositório. O Dockerfile fica na raiz e o runtime usa Java 21.
3. Defina as variáveis no Web Service: `DB_URL` = URL JDBC (`jdbc:postgresql://HOST:5432/NOME_DO_BANCO`) obtida da **conexão interna** do Postgres Render; `DB_USER`; `DB_PASSWORD`; `ADMIN_EMAIL`; `ADMIN_PASSWORD`; `PADRAO_EMAIL`; `PADRAO_PASSWORD`. Não use a URL `postgresql://` sem convertê-la para JDBC.
4. Deixe a porta 8080 (ou configure `SERVER_PORT` conforme o serviço). Caminho de health check: `/actuator/health`.
5. Faça o deploy, abra o endereço do Web Service e teste login de ambos os perfis. **Nunca publique credenciais reais no GitHub.** Entregue as credenciais de teste ao professor por canal apropriado.
6. Entrega: link HTTPS do serviço Render, URL do GitHub e credenciais de ADMIN e PADRAO, conforme solicitação da faculdade.

## Segurança
- BCrypt com senhas não reversíveis; cadastro inicial via variáveis de ambiente; sem API pública de cadastro de usuários.
- A visualização usa `sec:authorize` e o backend restringe o acesso direto a `/produto/**` e `/cliente/**` a ADMIN.
- Requisições POST levam token CSRF. Toda rota não reconhecida é negada.
- Variáveis de exemplo não devem ser utilizadas como senhas reais de produção.
