package com.veiculos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResourcesNotFoundException extends RuntimeException{

    public ResourcesNotFoundException(String mensagem) {
        super(mensagem);
    }
}
