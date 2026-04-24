package com.DM.dairyManagement.mapper;

import com.DM.dairyManagement.dto.ProductRequest;
import com.DM.dairyManagement.dto.ProductResponse;
import com.DM.dairyManagement.model.Product;
import com.DM.dairyManagement.model.Stock;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {
    
    public Product toEntity(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCustomerPrice(request.getPrice());
        product.setRetailerPrice(request.getPrice() * 0.9);
        product.setWholesalerPrice(request.getPrice() * 0.8);
        product.setCompanyPrice(request.getCostPrice() != null ? request.getCostPrice() : request.getPrice() * 0.7);
        // Note: unit and company would need to be fetched from DB in service layer
        return product;
    }
    
    public ProductResponse toResponse(Product product, Stock stock) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getCustomerPrice());
        response.setCostPrice(product.getCompanyPrice());
        response.setCategory(product.getHsnCode());
        response.setUnit(product.getUnit() != null ? product.getUnit().getName() : null);
        response.setActive(true); // Product doesn't have active field
        response.setCreatedAt(null); // Product doesn't have timestamps
        
        if (stock != null) {
            response.setCurrentStock(stock.getQuantity());
            response.setLowStock(stock.getQuantity() < 50); // Default threshold
        }
        
        return response;
    }
    
    public ProductResponse toResponse(Product product) {
        return toResponse(product, null);
    }
    
    public List<ProductResponse> toResponseList(List<Product> products) {
        return products.stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }
}
