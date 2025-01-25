package com.companystrator.product.service.impl;

import com.companystrator.db.model.Company;
import com.companystrator.db.model.Product;
import com.companystrator.db.model.ProductCategory;
import com.companystrator.db.repository.CompanyRepository;
import com.companystrator.db.repository.ProductCategoryRepository;
import com.companystrator.db.repository.ProductRepository;
import com.companystrator.exceptions.exception.*;
import com.companystrator.product.dto.req.CreateProductCategoryDTO;
import com.companystrator.product.dto.req.CreateProductDTO;
import com.companystrator.product.dto.req.UpdateProductDTO;
import com.companystrator.product.dto.res.ProductCategoryDTO;
import com.companystrator.product.dto.res.ProductDTO;
import com.companystrator.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final CompanyRepository companyRepository;

    @Override
    public void createProductCategory(CreateProductCategoryDTO request) {
        Optional<ProductCategory> verifyProductCategoryExist = this.productCategoryRepository.findByCategory(request.category());
        if (verifyProductCategoryExist.isPresent()) {
            log.error("Product category {} already exist", request.category());
            throw new CreateProductCategoryException("Product category " + request.category() + " already exist");
        }
        ProductCategory newProductCategory = ProductCategory.builder()
            .category(request.category().toUpperCase())
            .build();
        this.productCategoryRepository.save(newProductCategory);
        log.info("Product category {} created", request.category());
    }

    @Override
    public List<ProductCategoryDTO> getProductCategoryList() {
        return this.productCategoryRepository.findAll()
            .stream().map(p -> ProductCategoryDTO.builder()
                .id(p.getId())
                .category(p.getCategory())
                .build())
            .toList();
    }

    @Override
    public void createProduct(CreateProductDTO request) {
        Optional<Product> verifyProductExist = this.productRepository.findById(request.code());
        if (verifyProductExist.isPresent()) {
            log.error("Product code {} or name {} already exist", request.code(), request.name());
            throw new CreateProductException("Product code " + request.code() + " or name " + request.name() + " already exist");
        }
        Company company = this.companyRepository.findById(request.nit())
            .orElseThrow(() -> new CompanyNotFoundException("Company not found with NIT " + request.nit()));
        ProductCategory primaryCategory = this.findProductCategoryById(request.primaryCategory());
        ProductCategory secondaryCategory = request.secondaryCategory() != null
            ? this.findProductCategoryById(request.secondaryCategory())
            : null;
        Product newProduct = Product.builder()
            .code(request.code())
            .name(request.name())
            .description(request.description())
            .priceCop(request.priceCop())
            .priceUsd(request.priceUsd())
            .priceMxn(request.priceMxn())
            .primaryCategory(primaryCategory)
            .secondaryCategory(secondaryCategory)
            .company(company)
            .build();
        this.productRepository.save(newProduct);
        log.error("Product {} created", request.name());
    }

    @Override
    public ProductDTO getProductByCode(int code) {
        Product product = this.findProductByCode(code);
        return this.toProductDTO(product);
    }

    @Override
    public List<ProductDTO> getProductsCompanysList(String nit) {
        return this.productRepository.findByCompanyNit(nit)
            .stream().map(this::toProductDTO)
            .toList();
    }

    @Override
    public void updateProductByCode(int code, UpdateProductDTO request) {
        Product product = this.findProductByCode(code);
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPriceCop(request.priceCop());
        product.setPriceUsd(request.priceUsd());
        product.setPriceMxn(request.priceMxn());
        product.setPrimaryCategory(request.primaryCategory().equals(product.getPrimaryCategory().getId())
            ? product.getPrimaryCategory()
            : this.findProductCategoryById(request.primaryCategory())
        );
        product.setSecondaryCategory(request.secondaryCategory() == null
            ? null
            : this.findProductCategoryById(request.secondaryCategory())
        );
        this.productRepository.saveAndFlush(product);
        log.info("Product {} updated", request.name());
    }

    @Override
    public void deleteProduct(int code) {
        Product product = this.findProductByCode(code);
        this.productRepository.delete(product);
        log.info("Product {} deleted", code);
    }

    private Product findProductByCode(int code) {
        return this.productRepository.findById(code)
            .orElseThrow(() -> new ProductNotFoundException("Product not found with code " + code));
    }

    private ProductCategory findProductCategoryById(Long id) {
        return this.productCategoryRepository.findById(id)
            .orElseThrow(() -> new ProductCategoryNotFoundException("Product category not found with id " + id));
    }

    private ProductDTO toProductDTO(Product product) {
        return ProductDTO.builder()
            .code(product.getCode())
            .name(product.getName())
            .description(product.getDescription())
            .priceCop(product.getPriceCop())
            .priceUsd(product.getPriceUsd())
            .priceMxn(product.getPriceMxn())
            .primaryCategory(product.getPrimaryCategory().getCategory())
            .secondaryCategory(product.getSecondaryCategory() != null ? product.getSecondaryCategory().getCategory() : null)
            .build();
    }

}
