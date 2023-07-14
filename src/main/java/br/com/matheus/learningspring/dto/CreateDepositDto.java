package br.com.matheus.learningspring.dto;

import jakarta.validation.constraints.NotNull;

public class CreateDepositDto {
    @NotNull

    public float value;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
