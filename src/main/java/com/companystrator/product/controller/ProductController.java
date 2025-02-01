package com.companystrator.product.controller;

import com.companystrator.product.dto.req.CreateProductCategoryDTO;
import com.companystrator.product.dto.req.CreateProductDTO;
import com.companystrator.product.dto.req.UpdateProductDTO;
import com.companystrator.product.dto.res.ProductCategoryDTO;
import com.companystrator.product.dto.res.ProductDTO;
import com.companystrator.product.dto.res.SuccessResponseDTO;
import com.companystrator.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create-product/{nit}")
    public ResponseEntity<SuccessResponseDTO> createProduct(@PathVariable("nit") String nit, @RequestBody @Valid CreateProductDTO request) {
        this.productService.createProduct(nit, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponseDTO("Product " + request.name() + " created successfully"));
    }

    @PostMapping("/create-product-category")
    public ResponseEntity<SuccessResponseDTO> createProductCategory(@RequestBody @Valid CreateProductCategoryDTO request) {
        this.productService.createProductCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponseDTO("Product category " + request.category() + " created successfully"));
    }

    @GetMapping("/get-product-categories")
    public ResponseEntity<List<ProductCategoryDTO>> getProductCategoryList() {
        List<ProductCategoryDTO> response = this.productService.getProductCategoryList();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get-product-by-code/{code}")
    public ResponseEntity<ProductDTO> getProductByCode(@PathVariable("code") Long code) {
        ProductDTO response = this.productService.getProductByCode(code);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get-products-by-nit/{nit}")
    public ResponseEntity<List<ProductDTO>> getProductsCompanysList(@PathVariable("nit") String nit) {
        List<ProductDTO> response = this.productService.getProductsCompanysList(nit);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update-product/{code}")
    public ResponseEntity<SuccessResponseDTO> updateProductByCode(@PathVariable("code") Long code, @RequestBody @Valid UpdateProductDTO request) {
        this.productService.updateProductByCode(code, request);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponseDTO("Product " + code + " updated successfully"));
    }

    @DeleteMapping("/delete-product/{code}")
    public ResponseEntity<SuccessResponseDTO> deleteProductByCode(@PathVariable("code") Long code) {
        this.productService.deleteProduct(code);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponseDTO("Product " + code + " deleted successfully"));
    }

}
