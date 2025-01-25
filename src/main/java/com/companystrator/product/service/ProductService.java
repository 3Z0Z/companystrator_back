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

    void createProduct(CreateProductDTO request);

    ProductDTO getProductByCode(int code);

    List<ProductDTO> getProductsCompanysList(String nit);

    void updateProductByCode(int code, UpdateProductDTO request);

    void deleteProduct(int code);

}
