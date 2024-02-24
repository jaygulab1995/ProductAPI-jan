package com.jbk.api.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jbk.api.entity.Product;
import com.jbk.api.exception.ProductAlreadyExistsException;
import com.jbk.api.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	private ProductService service;

	@PostMapping(value = "operations/saveproduct")
	public ResponseEntity<Boolean> saveProduct(@RequestBody Product product) {

		boolean isAdded = service.saveProduct(product);
		if (isAdded) {
			return new ResponseEntity<Boolean>(isAdded, HttpStatus.CREATED);
		} else {
			throw new ProductAlreadyExistsException("Product Already Exists ID>>"+product.getProductId());//customize exception
		}

	}

	@GetMapping(value = "operations/getallproduct")
	public ResponseEntity<List<Product>> getAllProduct() {

		List<Product> list = service.getAllProduct();
		if (!list.isEmpty()) {
			return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Product>>(HttpStatus.OK);
		}

	}

	@PutMapping(value = "operations/updateproduct")
	public ResponseEntity<Boolean> updateProduct(@RequestBody Product product) {

		boolean isUpdated = service.updateProduct(product);
		if (isUpdated) {
			return new ResponseEntity<Boolean>(isUpdated, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Boolean>(isUpdated, HttpStatus.OK);
		}

	}

	@DeleteMapping(value = "operations/deleteproduct")
	public ResponseEntity<Boolean> deleteProduct(@RequestParam int productId) {

		boolean isDeleted = service.deleteProduct(productId);
		if (isDeleted) {
			return new ResponseEntity<Boolean>(isDeleted, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(isDeleted, HttpStatus.OK);
		}

	}

	@GetMapping(value = "operations/getproductbyid/{productid}")
	public ResponseEntity<Product> getProductById(@PathVariable int productid) {
		Product product = service.getProductById(productid);
		if (product != null) {
			return new ResponseEntity<Product>(product, HttpStatus.OK);
		} else {
			return new ResponseEntity<Product>(HttpStatus.OK);
		}

	}

	@GetMapping(value = "operations/getmaxpriceproducts")
	public ResponseEntity<List<Product>> getMaxPriceProducts() {

		List<Product> maxPriceProducts = service.getMaxPriceProducts();
		return new ResponseEntity<List<Product>>(maxPriceProducts, HttpStatus.OK);

	}
	
	@PostMapping(value = "operations/uploadsheet")
	public ResponseEntity<String> uploadSheet(@RequestParam MultipartFile file, HttpSession session){
		
		String msg = service.uploadSheet(file, session);
		
		return new ResponseEntity<String>(msg, HttpStatus.OK);
		
	}
}
