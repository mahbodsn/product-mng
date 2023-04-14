package com.tangerine.productmng.api.web.product.mapper;

import com.tangerine.productmng.api.web.product.dto.ProductCreateRequest;
import com.tangerine.productmng.api.web.product.dto.ProductDto;
import com.tangerine.productmng.service.product.model.ProductCreateModel;
import com.tangerine.productmng.service.product.model.ProductModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductResourceMapper {

    ProductDto toProductDto(ProductModel model);

    List<ProductDto> toProductDtos(List<ProductModel> productModels);

    ProductCreateModel toProductCreateModel(ProductCreateRequest request);

}
