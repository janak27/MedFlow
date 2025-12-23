package com.pharma.inventory_service.product.service;

import com.pharma.inventory_service.exception.BadRequestException;
import com.pharma.inventory_service.exception.ResourceNotFoundException;
import com.pharma.inventory_service.product.dto.*;
        import com.pharma.inventory_service.product.entity.Product;
import com.pharma.inventory_service.product.entity.ProductStatus;
import com.pharma.inventory_service.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(CreateProductRequest request) {

        if (productRepository.existsBySku(request.getSku())) {
            throw new BadRequestException("SKU already exists");
        }

        if (request.getCostPrice().compareTo(request.getSellingPrice()) > 0) {
            throw new BadRequestException("Cost price cannot be greater than selling price");
        }

        Product product = Product.builder()
                .name(request.getName())
                .sku(request.getSku())
                .manufacturer(request.getManufacturer())
                .unitOfMeasure(request.getUnitOfMeasure())
                .costPrice(request.getCostPrice())
                .sellingPrice(request.getSellingPrice())
                .description(request.getDescription())
                .status(ProductStatus.ACTIVE)
                .build();

        return toResponse(productRepository.save(product));
    }

    public ProductResponse getById(Long id) {
        return toResponse(findProduct(id));
    }

    public ProductResponse getBySku(String sku) {
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return toResponse(product);
    }

    public ProductResponse updateProduct(Long id, UpdateProductRequest request) {

        Product product = findProduct(id);

        if (request.getCostPrice().compareTo(request.getSellingPrice()) > 0) {
            throw new BadRequestException("Cost price cannot be greater than selling price");
        }

        product.setName(request.getName());
        product.setManufacturer(request.getManufacturer());
        product.setUnitOfMeasure(request.getUnitOfMeasure());
        product.setCostPrice(request.getCostPrice());
        product.setSellingPrice(request.getSellingPrice());
        product.setDescription(request.getDescription());

        return toResponse(productRepository.save(product));
    }

    public void changeStatus(Long id, ProductStatus status) {
        Product product = findProduct(id);
        product.setStatus(status);
        productRepository.save(product);
    }

    private Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    private ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .sku(product.getSku())
                .manufacturer(product.getManufacturer())
                .unitOfMeasure(product.getUnitOfMeasure())
                .costPrice(product.getCostPrice())
                .sellingPrice(product.getSellingPrice())
                .status(product.getStatus())
                .description(product.getDescription())
                .build();
    }
}
