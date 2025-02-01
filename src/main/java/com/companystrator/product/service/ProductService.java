package com.companystrator.product.service;

import com.companystrator.product.dto.req.CreateProductCategoryDTO;
import com.companystrator.product.dto.req.CreateProductDTO;
import com.companystrator.product.dto.req.UpdateProductDTO;
import com.companystrator.product.dto.res.ProductCategoryDTO;
import com.companystrator.product.dto.res.ProductDTO;

import java.util.List;

public interface ProductService {

    void createProductCategory(CreateProductCategoryDTO request);

    List<ProductCategoryDTO> getProductCategoryList();

    void createProduct(String nit, CreateProductDTO request);

    ProductDTO getProductByCode(Long code);

    List<ProductDTO> getProductsCompanysList(String nit);

    void updateProductByCode(Long code, UpdateProductDTO request);

    void deleteProduct(Long code);

}
