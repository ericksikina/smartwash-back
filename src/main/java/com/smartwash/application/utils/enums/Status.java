package com.smartwash.application.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    ATIVO("A"),
    INATIVO("I");

    private final String value;
}
