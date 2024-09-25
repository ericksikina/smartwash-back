package com.smartwash.application.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Resposta {
    SIM("S"),
    NAO("N");

    private final String value;
}
