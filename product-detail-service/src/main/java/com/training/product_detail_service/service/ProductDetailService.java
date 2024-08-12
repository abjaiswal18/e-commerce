package com.training.product_detail_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.training.product_detail_service.exception.ResourceNotFoundException;
import com.training.product_detail_service.model.ProductDetail;
import com.training.product_detail_service.repository.ProductDetailRepository;

@Service
public class ProductDetailService {

	@Autowired
	private ProductDetailRepository productDetailRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String productServiceUrl = "http://product-service/products/";
	
	public ProductDetail getProductDetail(Long productId, boolean fromProductService) {
		if(!fromProductService) {
			String url = productServiceUrl + productId;
			restTemplate.getForObject(url, String.class);
		}
		ProductDetail detail = productDetailRepository.findByProductId(productId);
		if(detail == null) {
			throw new ResourceNotFoundException("Product details not found for productId: "+ productId);
		}
		return detail;
	}
	
	public ProductDetail setProductDetail(Long productId, String size, String design) {
		ProductDetail detail = productDetailRepository.findByProductId(productId);
		if(detail == null) {
			detail = new ProductDetail();
			detail.setProductId(productId);
		}
		detail.setSize(size);
		detail.setDesign(design);
		return productDetailRepository.save(detail);
	}
	
	@Transactional
	public void deleteProductDetails(Long productId) {
		productDetailRepository.deleteByProductId(productId);
	}
}
