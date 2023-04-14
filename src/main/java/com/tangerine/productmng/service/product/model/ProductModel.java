package com.tangerine.productmng.service.product.model;

import java.io.Serializable;

public record ProductModel(Long id, String name) implements Serializable {
}
