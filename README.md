# 🧪 Suíte de Testes - CMS For QA's (API de Gestão de Conteúdo)

Esta suíte cobre testes automatizados de uma API REST voltada para gerenciamento de conteúdo, incluindo usuários, categorias e autenticação.

---

## 📌 Endpoints Cobertos

- 🔐 Autenticação
- 👤 Usuários
- 🗂️ Categorias

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

### 🔐 Autenticação

#### ✅ POST - Login do usuário
- **Objetivo:** Autenticar usuário e obter token JWT.
- **Pré-condição:** Usuário cadastrado.
- **Dados:** E-mail e senha.
- **Resposta esperada:** `200 OK` com token.

---

### 👤 Usuários

#### ✅ POST - Criar novo usuário
- **Objetivo:** Criar novo usuário com dados válidos.
- **Resposta esperada:** `201 Created` com dados e ID.

#### ❌ POST - Criar usuário com campos inválidos ou ausentes
- Sem nome completo → `400 Bad Request` + mensagem obrigatória.
- Sem nome de usuário → `400 Bad Request` + mensagem obrigatória.
- Sem e-mail → `400 Bad Request` + e-mail inválido.
- E-mail mal formatado → `400 Bad Request` + e-mail inválido.
- Sem senha → `400 Bad Request` + múltiplas mensagens:
  - Senha deve ter no mínimo 6 caracteres  
  - Senha deve conter pelo menos um número  
  - Senha deve conter pelo menos uma letra maiúscula  
- Senha curta (ex: `Teste`) → `400 Bad Request` + "Senha deve ter no mínimo 6 caracteres".
- Senha sem letra maiúscula (ex: `teste1`) → `400 Bad Request` + "Senha deve conter pelo menos uma letra maiúscula".
- Senha sem número (ex: `Testee`) → `400 Bad Request` + "Senha deve conter pelo menos um número".

---

#### ✅ GET - Listar usuários
- Todos os usuários → `200 OK`
- Por nome de usuário → `200 OK` com resultado ou lista vazia
- Por e-mail → `200 OK` com resultado ou lista vazia
- Por ID → `200 OK` ou `404 Not Found` + "Usuário não encontrado"

#### ❌ GET - Usuário sem token
- **Resposta esperada:** `401 Unauthorized` + "Token inválido"

---

#### ✅ PUT - Atualizar usuário
- Atualização válida → `200 OK` com dados atualizados

#### ❌ PUT - Atualizar com erro
- Sem token → `401 Unauthorized` + "Token não fornecido"
- Nome de usuário duplicado → `400 Bad Request` + "Nome de usuário já está em uso"

---

#### ✅ DELETE - Excluir usuário
- Criar → Excluir → Buscar → Confirmar `204 No Content` e `404 Not Found`

#### ❌ DELETE - Usuário inexistente
- **Resposta esperada:** `404 Not Found` + "Usuário não encontrado"

---

### 🗂️ Categorias

#### ✅ POST - Criar nova categoria
- **Resposta esperada:** `201 Created` com dados

#### ❌ POST - Criar categoria inválida
- Nome duplicado → `400 Bad Request` + "Nome de categoria já existe"
- Sem token → `401 Unauthorized` + "Token não fornecido"

---

#### ✅ GET - Listar categorias
- Todas → `200 OK`
- Por ID → `200 OK` com dados

#### ❌ GET - Categoria inválida
- ID inexistente → `404 Not Found` + "Categoria não encontrada"
- Sem token → `401 Unauthorized` + "Token não fornecido"

---

📌 _Os testes desabilitados com `enabled = false` foram ignorados neste relatório para manter a consistência com a execução atual da suíte._

---

🎯 **Total de testes documentados:** 30+  
📅 **Atualizado em:** 13 de maio de 2025
