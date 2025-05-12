# 🧪 Suíte de Testes - CMS For QA's (API de Gestão de Conteúdo)

Esta suíte cobre testes automatizados de uma API REST voltada para gerenciamento de conteúdo, incluindo usuários, categorias e artigos.

---

## 📌 Endpoints Cobertos

- Autenticação
- Usuários
- Categorias
- Artigos

---

## 🛠️ Tecnologias Utilizadas

- **Java** com **Rest Assured**
- **IntelliJ IDEA**
- **Bibliotecas**:
  - Faker (geração de dados dinâmicos)
  - Hamcrest (validações)
  - TestNG (estruturação dos testes)

---

## ✅ Cobertura de Testes

### 🔐 POST - Criar um novo usuário

- **Funcionalidade**: Criação de um novo usuário no banco de dados.
- **Dados de entrada**: Nome completo, nome de usuário, e-mail, senha.
- **Resposta esperada**:
  - Status HTTP `201 Created`
  - Corpo da resposta com os dados do usuário criado e um ID único.

---

### 🔐 POST - Login do usuário

- **Funcionalidade**: Autenticação do usuário para obtenção de token JWT.
- **Pré-condição**: Usuário previamente cadastrado no banco de dados.
- **Dados de entrada**: E-mail e senha.
- **Resposta esperada**:
  - Status HTTP `200 OK`
  - Token JWT no corpo da resposta.

---

### ❌ POST - Criar usuário sem nome completo

- **Objetivo**: Verificar a resposta da API ao enviar o nome completo em branco.
- **Resposta esperada**:
  - Status HTTP `400 Bad Request`
  - Mensagem de erro indicando que o campo é obrigatório.

---

### ❌ POST - Criar usuário sem nome de usuário

- **Objetivo**: Verificar a resposta da API ao enviar o nome de usuário em branco.
- **Resposta esperada**:
  - Status HTTP `400 Bad Request`
  - Mensagem de erro indicando que o campo é obrigatório.

---

### ❌ POST - Criar usuário sem e-mail

- **Objetivo**: Verificar a resposta da API ao enviar o e-mail em branco.
- **Resposta esperada**:
  - Status HTTP `400 Bad Request`
  - Mensagem de erro indicando que o e-mail é inválido.

---

### ❌ POST - Criar usuário com e-mail mal formatado

- **Objetivo**: Verificar a resposta da API ao enviar e-mail sem `@` ou com `@@`.
- **Resposta esperada**:
  - Status HTTP `400 Bad Request`
  - Mensagem de erro indicando e-mail inválido.

---

### ❌ POST - Criar usuário sem senha

- **Objetivo**: Verificar a resposta da API ao enviar senha em branco.
- **Resposta esperada**:
  - Status HTTP `400 Bad Request`
  - Mensagens de erro:
    - "Senha deve ter no mínimo 6 caracteres"
    - "Senha deve conter pelo menos um número"
    - "Senha deve conter pelo menos uma letra maiúscula"

---

### ❌ POST - Criar usuário com senha curta

- **Objetivo**: Verificar a resposta ao usar senha com menos de 6 caracteres.
- **Senha usada**: `Teste`
- **Resposta esperada**:
  - Status HTTP `400 Bad Request`
  - Mensagem: "Senha deve ter no mínimo 6 caracteres"

---

### ❌ POST - Criar usuário com senha sem letra maiúscula

- **Objetivo**: Verificar a resposta ao usar senha sem letras maiúsculas.
- **Senha usada**: `teste1`
- **Resposta esperada**:
  - Status HTTP `400 Bad Request`
  - Mensagem: "Senha deve conter pelo menos uma letra maiúscula"

---

### ❌ POST - Criar usuário com senha sem número

- **Objetivo**: Verificar a resposta ao usar senha sem números.
- **Senha usada**: `Testee`
- **Resposta esperada**:
  - Status HTTP `400 Bad Request`
  - Mensagem: "Senha deve conter pelo menos um número"
