package com.tangerine.productmng.api.web.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductDto(@JsonProperty("prid") Long id, @JsonProperty("prdname") String name) {
}
