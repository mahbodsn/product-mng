package com.tangerine.productmng.api.web.product;

import com.tangerine.productmng.api.dto.EmptyResponse;
import com.tangerine.productmng.api.web.product.dto.ProductCreateRequest;
import com.tangerine.productmng.api.web.product.dto.ProductDto;
import com.tangerine.productmng.api.web.product.dto.ProductUpdateRequest;
import com.tangerine.productmng.api.web.product.mapper.ProductResourceMapper;
import com.tangerine.productmng.exception.BusinessException;
import com.tangerine.productmng.service.product.ProductService;
import com.tangerine.productmng.service.product.model.ProductUpdateModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
public class ProductResource {

    private final ProductService productService;

    private final ProductResourceMapper productResourceMapper;

    @GetMapping(path = "/prids", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductDto>> getProducts() {
        logger.debug("going to fetch product list");
        var products = productService.getProducts();
        logger.debug("product list result is -> {}", products);
        return ResponseEntity.ok(productResourceMapper.toProductDtos(products));
    }

    @GetMapping(path = "/prid/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) throws BusinessException {
        logger.debug("going to fetch product with id -> {}", id);
        var product = productService.getProductById(id);
        logger.debug("product id -> [{}] result is -> {}", id, product);
        return ResponseEntity.ok(productResourceMapper.toProductDto(product));
    }

    @PostMapping(path = "/prid", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductCreateRequest request) {
        logger.info("going to create product with request -> {}", request);
        var result = productService.createProduct(productResourceMapper.toProductCreateModel(request));
        logger.info("product created with result -> {}", result);
        return ResponseEntity.ok(productResourceMapper.toProductDto(result));
    }

    @PutMapping(path = "/prid/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequest request)
            throws BusinessException {
        logger.info("going to update product -> {} with request -> {}", id, request);
        var result = productService.updateProduct(new ProductUpdateModel(id, request.name()));
        logger.debug("product updated successfully with result -> {}", result);
        return ResponseEntity.ok(productResourceMapper.toProductDto(result));
    }

    @DeleteMapping(path = "/prid/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmptyResponse> deleteProduct(@PathVariable Long id) throws BusinessException {
        logger.info("going to delete product -> {}", id);
        productService.deleteProductById(id);
        return ResponseEntity.ok(new EmptyResponse());
    }

}
