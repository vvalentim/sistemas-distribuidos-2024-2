# sistemas-distribuidos

**Aluno: João Victor Valentim - 2099284**

Os pacotes *server* e *client* possuem as classes principais para inicialização, *Server* e *Client*.

Verifique se todas as dependências estão instaladas antes de executar o projeto.

O pacote *gui* já está funcional, portanto utilize a classe *ClientLauncher* e *ServerLauncher* para execução do projeto.

# Como funciona

De acordo com o que foi discutido em sala, na primeira entrega o *Cliente* enviará uma mensagem em formato JSON para o *Servidor*, que deverá ser deserializada para o tipo de requisição correspondente, de acordo com o *Protocolo de Mensagens*.

O *Servidor* deve detectar possíveis falhas em tempo de execução ou se o conteúdo da requisição não está de acordo com as regras do *Protocolo de Mensagens*. Por fim, deve responder com a mensagem apropriada (que estão documentadas no protocolo).

O envio de mensagens será feito diretamente pelo terminal quando o *Cliente* estiver em execução e a resposta  não precisa ser deserializada, apenas impressa no próprio terminal.

O envio de mensagens na terceira (última) entrega deverá ser feito por uma interface gráfica.

# Protocolo e tipos de requisição

O protocolo deverá ser desenvolvido contando com a colaboração de todos os alunos em sala de aula, e as modificações necessárias para cada etapa devem ser decididas em conjunto e pela maioria.

As regras de validação e detalhes específicos de cada operação devem constar no documento oficial do protocolo.

Segue abaixo o exemplo com o formato em JSON para os diferentes tipos de requisições disponíveis.

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

### Adicionar ou editar categoria (APENAS ADMIN)
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

### Adicionar ou editar aviso (APENAS ADMIN)
```
{"operacao": "salvarAviso", "token": "2099284", "aviso": {"id": 0, "categoria": 1, "titulo": "TITULO", "descricao": "Descricao do aviso"}}
```

### Listagem de avisos
```
{"operacao": "listarAvisos", "token": "2099284", "categoria": 0}
```

### Localizar aviso
```
{"operacao": "localizarAviso", "token": "2099284", "id": 1}
```

### Excluir aviso
```
{"operacao": "excluirAviso", "token": "2099284", "id": 1}
```

### Cadastrar usuário em categoria de avisos
```
{"operacao": "cadastrarUsuarioCategoria", "token": "2099284", "ra": "2099284", "categoria": 1}
```

### Descadastrar usuário em categoria de avisos
```
{"operacao": "descadastrarUsuarioCategoria", "token": "2099284", "ra": "2099284", "categoria": 1}
```

### Listar categorias que o usuário está cadastrado
```
{"operacao": "listarUsuarioCategorias", "token": "2099284", "ra": "2099284"}
```

### Listar os avisos do usuário
```
{"operacao": "listarUsuarioAvisos", "token": "2099284", "ra": "2099284"}
```

## Configuração e ambiente de desenvolvimento

- Sistema operacional: Windows 11 23H2
- IDE: IntelliJ Idea 2024.2.3 (Community Edition)
- JDK: Oracle OpenJDK 21.0.5