package com.tangerine.productmng.service.product.impl;

import com.tangerine.productmng.exception.BusinessException;
import com.tangerine.productmng.exception.ErrorCode;
import com.tangerine.productmng.model.product.ProductEntity;
import com.tangerine.productmng.model.product.dao.ProductDao;
import com.tangerine.productmng.service.product.mapper.ProductServiceMapper;
import com.tangerine.productmng.service.product.model.ProductCreateModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ContextConfiguration
@ExtendWith({SpringExtension.class})
class ProductServiceImplTest {

    @Autowired
    ProductServiceImpl productService;

    @MockBean
    ProductDao productDao;

    @Test
    void getProductById_productNotExists_throwException() {

        when(productDao.findById(any())).thenReturn(Optional.empty());

        var exception = Assertions.assertThrows(BusinessException.class, () -> productService.getProductById((new Random()).nextLong()));

        assertThat(exception).isNotNull();
        assertThat(exception.getCode()).isEqualTo(ErrorCode.PRODUCT_NOT_FOUND.getCode());
        assertThat(exception.getMessage()).isEqualTo(ErrorCode.PRODUCT_NOT_FOUND.getMessage());
    }

    @Test
    void createProduct_validData_success() {
        var model = new ProductCreateModel(UUID.randomUUID().toString());

        var productCreationArgumentCaptor = ArgumentCaptor.forClass(ProductEntity.class);

        var result = productService.createProduct(model);

        verify(productDao, times(1)).save(productCreationArgumentCaptor.capture());

        var productEntity = productCreationArgumentCaptor.getValue();
        assertThat(productEntity.getName()).isNotBlank();
    }

    @TestConfiguration
    @ComponentScan(
            basePackageClasses = {ProductServiceMapper.class, ProductServiceImpl.class},
            useDefaultFilters = false,
            includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                    classes = {ProductServiceMapper.class, ProductServiceImpl.class}))

    static class Configuration {

        @Bean
        public CacheManager getCacheManager() {
            return new NoOpCacheManager();
        }

    }


}