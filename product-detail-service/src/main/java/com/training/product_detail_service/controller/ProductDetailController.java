package com.training.product_detail_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.training.product_detail_service.dto.ProductDetailDTO;
import com.training.product_detail_service.model.ProductDetail;
import com.training.product_detail_service.service.ProductDetailService;

@RestController
@RequestMapping("/products")
public class ProductDetailController {

	@Autowired
	private ProductDetailService productDetailService;

	@PostMapping("/{productId}/details")
	public ResponseEntity<ProductDetail> setProductDetail(@PathVariable Long productId,
			@RequestBody ProductDetailDTO productDetailDTO) {
		ProductDetail detail = productDetailService.setProductDetail(productId, productDetailDTO.getSize(),
				productDetailDTO.getDesign());
		return ResponseEntity.ok(detail);

	}

	@GetMapping("/{productId}/details")
	public ResponseEntity<ProductDetail> getProductDetail(@PathVariable Long productId, @RequestParam(required = false) boolean fromProductService) {
		ProductDetail detail = productDetailService.getProductDetail(productId, fromProductService);
		return ResponseEntity.ok(detail);
	}
	
	@DeleteMapping("/{productId}/details")
	public ResponseEntity<Void> deleteProductDetails(@PathVariable Long productId){
		productDetailService.deleteProductDetails(productId);
		return ResponseEntity.noContent().build();
	}
}
