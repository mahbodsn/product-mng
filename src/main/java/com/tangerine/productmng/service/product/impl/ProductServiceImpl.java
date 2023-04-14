package com.tangerine.productmng.service.product.impl;

import com.tangerine.productmng.exception.BusinessException;
import com.tangerine.productmng.exception.ErrorCode;
import com.tangerine.productmng.model.product.dao.ProductDao;
import com.tangerine.productmng.service.product.ProductService;
import com.tangerine.productmng.service.product.mapper.ProductServiceMapper;
import com.tangerine.productmng.service.product.model.ProductCreateModel;
import com.tangerine.productmng.service.product.model.ProductModel;
import com.tangerine.productmng.service.product.model.ProductUpdateModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    private final ProductServiceMapper mapper;

    @Override
    public List<ProductModel> getProducts() {
        logger.debug("going to fetch product list");
        return mapper.toProductModels(productDao.findAll());
    }

    @Cacheable(cacheNames = "productById")
    @Override
    public ProductModel getProductById(Long id) throws BusinessException {
        logger.debug("going to fetch product -> {} from db", id);
        return productDao.findById(id)
                .map(mapper::toProductModel)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    @Override
    public ProductModel createProduct(ProductCreateModel model) {
        var entity = productDao.save(mapper.toProductEntity(model));
        return mapper.toProductModel(entity);
    }

    @CacheEvict(cacheNames = {"productById"}, key = "#model.id()")
    @Override
    public ProductModel updateProduct(ProductUpdateModel model) throws BusinessException {
        var product = productDao.findById(model.id())
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
        product.setName(model.name());
        var updatedProduct = productDao.save(product);
        return mapper.toProductModel(updatedProduct);
    }

    @CacheEvict(cacheNames = {"productById"}, key = "#id")
    @Override
    public void deleteProductById(Long id) throws BusinessException {
        var product = productDao.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
        logger.debug("product to delete -> {}", product);
        productDao.delete(product);
        logger.info("product deleted");
    }

}
