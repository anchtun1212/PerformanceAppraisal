package com.cha.product.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cha.product.model.Product;
import com.cha.product.model.StringResponse;
import com.cha.product.model.User;
import com.cha.product.service.ProductService;
import com.cha.product.service.TransactionService;
import com.cha.product.service.UserService;

@RestController
public class AdminController {

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	@Autowired
	private TransactionService transactionService;

	@PutMapping("/api/admin/user-update")
	public ResponseEntity<?> updateUser(@RequestBody User user) {
		User existUser = userService.findUserByUserName(user.getUserName());
		if (Objects.nonNull(existUser) && !existUser.getId().equals(user.getId())) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(userService.updateUser(user), HttpStatus.NO_CONTENT);
	}

	// can be also @DeleteMapping
	@PostMapping("/api/admin/user-delete")
	public ResponseEntity<?> deleteUser(@RequestBody User user) {
		userService.deleteUserById(user.getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/api/admin/user-all")
	public ResponseEntity<?> findAllUsers() {
		return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
	}

	@GetMapping("/api/admin/user-count")
	public ResponseEntity<?> nbOfUsers() {
		Long nbUsers = userService.nbOfUsers();
		StringResponse response = new StringResponse(nbUsers.toString());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/api/admin/product-create")
	public ResponseEntity<?> createProduct(Product product) {
		return new ResponseEntity<>(productService.saveProduct(product), HttpStatus.CREATED);
	}
	
	@PutMapping("/api/admin/product-update")
	public ResponseEntity<?> updateProduct(Product product) {
		return new ResponseEntity<>(productService.updateProduct(product), HttpStatus.NO_CONTENT);
	}

	@PostMapping("/api/admin/product-delete")
	public ResponseEntity<?> deleteProduct(@RequestBody Product product) {
		productService.deleteProductById(product.getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/api/admin/product-all")
	public ResponseEntity<?> findAllProducts() {
		return new ResponseEntity<>(productService.findAllProducts(), HttpStatus.OK);
	}
	
	@GetMapping("/api/admin/product-count")
	public ResponseEntity<?> nbOfProducts() {
		Long nbProducts = productService.nbOfProduct();
		StringResponse response = new StringResponse(nbProducts.toString());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/api/admin/transaction-all")
	public ResponseEntity<?> findAllTransactions() {
		return new ResponseEntity<>(transactionService.findAllTransaction(), HttpStatus.OK);
	}
	
	@GetMapping("/api/admin/transaction-count")
	public ResponseEntity<?> nbOfTransactions() {
		Long nbTransactions = transactionService.nbOfTransactions();
		StringResponse response = new StringResponse(nbTransactions.toString());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
