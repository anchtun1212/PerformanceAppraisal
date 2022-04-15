package com.cha.product.controller;

import static java.util.Objects.isNull;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cha.product.jwt.JwtTokenProvider;
import com.cha.product.model.Role;
import com.cha.product.model.Transaction;
import com.cha.product.model.User;
import com.cha.product.service.ProductService;
import com.cha.product.service.TransactionService;
import com.cha.product.service.UserService;

@RestController
public class UserController {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
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
		if (isNull(principal)) {
			// logout will also should here so thats why we return httpStatus OK
			return ResponseEntity.ok(principal);
		}
		UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
		User user = userService.findUserByUserName(authenticationToken.getName());
		user.setToken(jwtTokenProvider.generateToken(authenticationToken));
		
		return new ResponseEntity<>(user, HttpStatus.OK);
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
