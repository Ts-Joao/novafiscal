package com.novafiscal.backend.common.domain.model;

public enum DocumentType {
    CPF,
    CNPJ;

    public int expectedLength() {
        return this == CPF ? 11 : 14;
    }
}
