package com.tangerine.productmng.exception;

import lombok.Getter;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Getter
public enum ErrorCode {

    FAILURE(0, "error-code.failure"),
    AUTH_BAD_TOKEN(1000, "error-code.auth.invalid-token"),
    AUTH_INVALID_CREDENTIAL(1001, "error-code.auth.invalid-credentials"),
    PRODUCT_NOT_FOUND(2000, "error-code.product.not-found");

    private final int code;

    private final String message;

    ErrorCode(int code, String message) {
        var properties = new Properties();
        try (var inputStreamReader = new InputStreamReader(new FileInputStream(new ClassPathResource("labels.properties").getFile()), StandardCharsets.UTF_8)) {
            properties.load(inputStreamReader);
            this.code = code;
            this.message = properties.getProperty(message);
        } catch (Exception e) {
            throw new RuntimeException("could4n't load labels file");
        }

    }
}
