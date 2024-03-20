package br.com.ibmec.cloud.spotifyclone.errorHandler;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ValidationErrorResponse {
    public String mensage = "Existem erros na sua requisição";
    public List<Validation> validationsErrors = new ArrayList<>();
}
