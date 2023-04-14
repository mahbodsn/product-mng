package com.tangerine.productmng.api.web.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tangerine.productmng.AbstractIntegrationTest;
import com.tangerine.productmng.api.dto.EmptyResponse;
import com.tangerine.productmng.api.dto.ExceptionResponse;
import com.tangerine.productmng.api.web.product.dto.ProductCreateRequest;
import com.tangerine.productmng.api.web.product.dto.ProductDto;
import com.tangerine.productmng.api.web.product.dto.ProductUpdateRequest;
import com.tangerine.productmng.exception.ErrorCode;
import com.tangerine.productmng.model.product.ProductEntity;
import com.tangerine.productmng.model.product.dao.ProductDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ProductResourceIT extends AbstractIntegrationTest {

    @Autowired
    ProductDao productDao;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        var product1 = new ProductEntity();
        product1.setName(UUID.randomUUID().toString());
        var product2 = new ProductEntity();
        product2.setName(UUID.randomUUID().toString());
        productDao.saveAll(List.of(product1, product2));
    }

    @Test
    void getProducts() throws Exception {
        var response = restTemplate.exchange(getBaseUrl("/prids"),
                HttpMethod.GET, new HttpEntity<>(HttpHeaders.class), ProductDto[].class);

        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Arrays.stream(response.getBody())
                .forEach(productDto -> {
                    assertThat(productDto.id()).isNotNull();
                    assertThat(productDto.name()).isNotBlank();
                });
    }

    @Test
    void getProductById_productExists_returnDetail() {
        var response = restTemplate.exchange(getBaseUrl("/prid/" + 1),
                HttpMethod.GET, new HttpEntity<>(HttpHeaders.class), ProductDto.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().id()).isNotNull();
        assertThat(response.getBody().name()).isNotBlank();
    }

    @Test
    void getProductById_productNotExists_returnException() {
        var response = restTemplate.exchange(getBaseUrl("/prid/" + 1111111),
                HttpMethod.GET, new HttpEntity<>(HttpHeaders.class), ExceptionResponse.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo(ErrorCode.PRODUCT_NOT_FOUND.getCode());
        assertThat(response.getBody().message()).isEqualTo(ErrorCode.PRODUCT_NOT_FOUND.getMessage());
    }

    @Test
    void createProduct_validData_success() {
        var httpEntity = new HttpEntity<>(new ProductCreateRequest(UUID.randomUUID().toString()), new HttpHeaders());
        var response = restTemplate.exchange(getBaseUrl("/prid"), HttpMethod.POST, httpEntity, ProductDto.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().id()).isNotNull();
        assertThat(response.getBody().name()).isNotBlank();

        var createdProduct = productDao.findById(response.getBody().id());
        assertThat(createdProduct).isPresent();
    }

    @Test
    void updateProduct_validData_success() {
        var product = productDao.save(ProductEntity.of(UUID.randomUUID().toString()));
        var httpEntity = new HttpEntity<>(new ProductUpdateRequest(UUID.randomUUID().toString()), new HttpHeaders());
        var response = restTemplate.exchange(getBaseUrl("/prid/" + product.getId()), HttpMethod.PUT, httpEntity, ProductDto.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().id()).isEqualTo(product.getId());
        assertThat(response.getBody().name()).isNotBlank();
    }

    @Test
    void updateProduct_productNotExists_returnError() {
        var httpEntity = new HttpEntity<>(new ProductUpdateRequest(UUID.randomUUID().toString()), new HttpHeaders());
        var response = restTemplate.exchange(getBaseUrl("/prid/" + 111111), HttpMethod.PUT, httpEntity, ExceptionResponse.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo(ErrorCode.PRODUCT_NOT_FOUND.getCode());
        assertThat(response.getBody().message()).isEqualTo(ErrorCode.PRODUCT_NOT_FOUND.getMessage());
    }

    @Test
    void deleteProduct_productExists_success() {
        var product = productDao.save(ProductEntity.of(UUID.randomUUID().toString()));

        var response = restTemplate.exchange(getBaseUrl("/prid/" + product.getId()), HttpMethod.DELETE, new HttpEntity<>(new HttpHeaders()), EmptyResponse.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        assertThat(productDao.findById(product.getId())).isEmpty();
    }

    @Test
    void deleteProduct_productNotExists_returnError() {
        var response = restTemplate.exchange(getBaseUrl("/prid/" + 11111), HttpMethod.DELETE, new HttpEntity<>(new HttpHeaders()), ExceptionResponse.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo(ErrorCode.PRODUCT_NOT_FOUND.getCode());
        assertThat(response.getBody().message()).isEqualTo(ErrorCode.PRODUCT_NOT_FOUND.getMessage());
    }

}