package com.jbk.api.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {

	
	private int productId;
	private String productName;
	private double productPrice;
	private int productQty;
	private String productType;
	
	
	public Product() {
		
	}
	
	public Product(int productId, String productName, double productPrice,int productQty, String productType) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productQty = productQty;
		this.productType = productType;
	}

	@Id
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	
	public int getProductQty() {
		return productQty;
	}

	public void setProductQty(int productQty) {
		this.productQty = productQty;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", productPrice=" + productPrice
				+ ", productQty=" + productQty + ", productType=" + productType + "]";
	}

	
	
	
	
	
}
