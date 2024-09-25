package com.smartwash.application.utils.request;

import java.time.LocalDate;

public record DataRequest(LocalDate dataInicial, LocalDate dataFinal) {
}