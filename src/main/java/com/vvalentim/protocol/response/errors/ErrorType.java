package com.vvalentim.protocol.response.errors;

public enum ErrorType {
    UNPROCESSABLE_CONTENT("Não foi possível ler o conteúdo da requisição (JSON)."),
    INVALID_OPERATION("Operação não encontrada."),
    FAILED_VALIDATION("Os campos recebidos não são válidos."),
    USER_ALREADY_EXISTS("Não foi possível cadastrar pois o usuário informado já existe."),
    INCORRECT_CREDENTIALS("Credenciais incorretas.");

    public final String message;

    ErrorType(String message) {
        this.message = message;
    }
}
