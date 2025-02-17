package com.vvalentim.protocol.response.errors;

public enum ErrorType {
    UNPROCESSABLE_CONTENT("Não foi possível ler o conteúdo da requisição (JSON)."),
    INVALID_OPERATION("Operação não encontrada."),
    FAILED_VALIDATION("Os campos recebidos não são válidos."),
    UNAUTHORIZED("Acesso não autorizado."),
    USER_ALREADY_EXISTS("Não foi possível cadastrar pois o usuário informado já existe."),
    USER_ALREADY_LOGGED_IN("O usuário já está logado."),
    USER_NOT_FOUND("Usuário não encontrado."),
    INCORRECT_CREDENTIALS("Credenciais incorretas."),
    NOTIFICATION_CATEGORY_NOT_FOUND("Categoria não encontrada."),
    NOTIFICATION_CATEGORY_HAS_ASSOCIATION("Não foi possível excluir, a categoria já está alocada a um ou mais avisos.");

    public final String message;

    ErrorType(String message) {
        this.message = message;
    }
}
