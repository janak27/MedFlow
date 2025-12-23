package com.pharma.inventory_service.product.controller;
import com.pharma.inventory_service.product.entity.ProductStatus;
import com.pharma.inventory_service.response.ApiResponse;
import com.pharma.inventory_service.product.dto.*;
import com.pharma.inventory_service.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ApiResponse<ProductResponse> create(@RequestBody CreateProductRequest request) {
        return new ApiResponse<>(true, "Product created", productService.createProduct(request));
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getById(@PathVariable Long id) {
        return new ApiResponse<>(true, "Product fetched", productService.getById(id));
    }

    @GetMapping("/sku/{sku}")
    public ApiResponse<ProductResponse> getBySku(@PathVariable String sku) {
        return new ApiResponse<>(true, "Product fetched", productService.getBySku(sku));
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> update(
            @PathVariable Long id,
            @RequestBody UpdateProductRequest request
    ) {
        return new ApiResponse<>(true, "Product updated", productService.updateProduct(id, request));
    }

    @PatchMapping("/{id}/status/{status}")
    public ApiResponse<ProductResponse> changeStatus(
            @PathVariable Long id,
            @PathVariable ProductStatus status
    ) {
        productService.changeStatus(id, status);
        ProductResponse updatedProduct = productService.getById(id);
        return new ApiResponse<>(true, "Status updated", updatedProduct);
    }
}
