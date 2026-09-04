package com.novafiscal.backend.common.domain.model;

import com.novafiscal.backend.customer.domain.exception.InvalidDocumentException;

public record Document(String number, DocumentType type) {

    public Document {
        if (type == null) {
            throw new InvalidDocumentException("Document type must not be null");
        }

        number = sanitize(number);

        if (number.length() != type.expectedLength()) {
            throw new InvalidDocumentException("Document number must be " + type.expectedLength() + " digits");
        }

        if (isAllDigitsEquals(number)) {
            throw new InvalidDocumentException("Document number is not a valid document");
        }
    }

    private static String sanitize(String rawNumber) {
        if (rawNumber == null) {
            throw new InvalidDocumentException("Document number must not be null");
        }
        return rawNumber.replaceAll("[^0-9]", "");
    }

    private static boolean isAllDigitsEquals(String sanitized) {
        return sanitized.chars().distinct().count() == 1;
    }
}
