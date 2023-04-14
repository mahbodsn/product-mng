package com.tangerine.productmng.service.product.model;

import java.io.Serializable;

public record ProductUpdateModel(Long id, String name) implements Serializable {
}
