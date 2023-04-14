package com.tangerine.productmng.service.product;

import com.tangerine.productmng.exception.BusinessException;
import com.tangerine.productmng.service.product.model.ProductCreateModel;
import com.tangerine.productmng.service.product.model.ProductModel;
import com.tangerine.productmng.service.product.model.ProductUpdateModel;

import java.util.List;

public interface ProductService {

    List<ProductModel> getProducts();

    ProductModel getProductById(Long id) throws BusinessException;

    ProductModel createProduct(ProductCreateModel model);

    ProductModel updateProduct(ProductUpdateModel productUpdateModel) throws BusinessException;

    void deleteProductById(Long id) throws BusinessException;
}
