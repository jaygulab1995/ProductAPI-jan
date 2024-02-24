package com.jbk.api.dao;

import java.util.List;

import com.jbk.api.entity.Product;

public interface ProductDao {

	public boolean saveProduct(Product product);
	
	public List<Product> getAllProduct();
	
	public boolean updateProduct(Product product);
	
	public boolean deleteProduct(int productId);
	
	public Product getProductById(int productId);
	
	public List<Product> getMaxPriceProducts();
	
	public int excelToDb(List<Product> list);
}
