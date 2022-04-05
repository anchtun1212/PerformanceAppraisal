package com.cha.performanceappraisal.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cha.performanceappraisal.model.Role;
import com.cha.performanceappraisal.model.Transaction;
import com.cha.performanceappraisal.model.User;
import com.cha.performanceappraisal.service.ProductService;
import com.cha.performanceappraisal.service.TransactionService;
import com.cha.performanceappraisal.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	@Autowired
	private TransactionService transactionService;

	@PostMapping("/api/user/registration")
	public ResponseEntity<?> register(@RequestBody User user) {
		if (Objects.nonNull(userService.findUserByUserName(user.getUserName()))) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		user.setRole(Role.USER);
		return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
	}

	@GetMapping("/api/user/login")
	public ResponseEntity<?> getUser(Principal principal) {
		// principal = HttpServletRequest.getUserPrincipal();
		if (Stream.of(principal, principal.getName()).anyMatch(Objects::isNull)) {
			// logout will also should here so thats why we return httpStatus OK
			return ResponseEntity.ok(principal);
		}
		return new ResponseEntity<>(userService.findUserByUserName(principal.getName()), HttpStatus.OK);
	}

	@PostMapping("/api/user/purchase")
	public ResponseEntity<?> purchaseProduct(@RequestBody Transaction transaction) {
		transaction.setPurchaseDate(LocalDateTime.now());
		return new ResponseEntity<>(transactionService.saveTransaction(transaction), HttpStatus.CREATED);
	}

	@GetMapping("/api/user/products")
	public ResponseEntity<?> findAllProducts() {
		return new ResponseEntity<>(productService.findAllProducts(), HttpStatus.OK);
	}

}
