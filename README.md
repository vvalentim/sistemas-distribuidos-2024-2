# sistemas-distribuidos

**Aluno: João Victor Valentim - 2099284**

Os pacotes *server* e *client* possuem as classes principais para inicialização, *Server* e *Client*.

Verifique se todas as dependências estão instaladas antes de executar o projeto.

O pacote *gui* ainda não está funcional, portanto utilize a classe *Client* para execução do cliente na segunda entrega do projeto.

# Como funciona

De acordo com o que foi discutido em sala, na primeira entrega o *Cliente* enviará uma mensagem em formato JSON para o *Servidor*, que deverá ser deserializada para o tipo de requisição correspondente, de acordo com o *Protocolo de Mensagens*.

O *Servidor* deve detectar possíveis falhas em tempo de execução ou se o conteúdo da requisição não está de acordo com as regras do *Protocolo de Mensagens*. Por fim, deve responder com a mensagem apropriada (que estão documentadas no protocolo).

O envio de mensagens será feito diretamente pelo terminal quando *Cliente* estiver em execução e a resposta  não precisa ser deserializada, apenas impressa no próprio terminal.

# Protocolo e tipos de requisição

De acordo com o protocolo, segue o formato em JSON para os diferentes tipos de requisições disponíveis para a primeira entrega.

**Obs**: esse usuário já está cadastrado (*hardcoded*) em memória na inicialização do servidor.

### Cadastrar usuário

```
{"operacao": "cadastrarUsuario", "nome": "JOAO VICTOR VALENTIM", "ra": "2099284", "senha": "administrador"}
```

### Login
```
{"operacao": "login", "ra": "2099284", "senha": "administrador"}
```

### Logout
```
{"operacao": "logout", "token": "2099284"}
```

### Busca por usuário
```
{"operacao": "localizarUsuario", "token": "2099284", "ra": "2099284"}
```

### Edição de usuário
```
{"operacao": "editarUsuario", "token": "2099284", "usuario": {"ra": "2099284", "senha": "abcabcabc", "nome": "NOME TESTE"}}
```

### Listagem de usuários (APENAS ADMIN)
```
{"operacao": "listarUsuarios", "token": "2099284"}
```

### Exclusão de usuário
```
{"operacao": "excluirUsuario", "token": "2099284", "ra": "2099284"}
```

### Listagem de categorias
```
{"operacao": "listarCategorias", "token": "2099284"}
```

### Salvar categoria (APENAS ADMIN)
```
{"operacao": "salvarCategoria", "token": "2099284", "categoria": {"id": "0", "nome": "ECONOMIA"}}
```
### Localizar categoria 
```
{"operacao": "localizarCategoria", "token": "2099284", "id": 1}
```

### Exclusão de categoria (APENAS ADMIN)
```
{"operacao": "excluirCategoria", "token": "2099284", "id": 1}
```


## Configuração e ambiente de desenvolvimento

- Sistema operacional: Windows 11 23H2
- IDE: IntelliJ Idea 2024.2.3 (Community Edition)
- JDK: Oracle OpenJDK 21.0.5