package br.com.tulio.letscodestore.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String entity) {
        super(entity + " not found");
    }

}
