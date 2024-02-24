package com.jbk.api.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.jbk.api.entity.Product;

public interface ProductService {

	public boolean saveProduct(Product product);
	
	public List<Product> getAllProduct();
	
	public boolean updateProduct(Product product);
	
	public boolean deleteProduct(int productId);
	
	public Product getProductById(int productId);
	
	public List<Product> getMaxPriceProducts();
	
	public String uploadSheet(MultipartFile file, HttpSession session);
}
