package com.tangerine.productmng.service.product.mapper;

import com.tangerine.productmng.model.product.ProductEntity;
import com.tangerine.productmng.service.product.model.ProductCreateModel;
import com.tangerine.productmng.service.product.model.ProductModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductServiceMapper {

    ProductModel toProductModel(ProductEntity product);

    List<ProductModel> toProductModels(List<ProductEntity> products);

    ProductEntity toProductEntity(ProductCreateModel model);
}
