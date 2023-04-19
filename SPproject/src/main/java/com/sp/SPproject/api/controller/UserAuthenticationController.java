package com.sp.SPproject.api.controller;

import com.sp.SPproject.api.model.UserRequest;
import com.sp.SPproject.service.UserAuthenticationDetailsService;
import org.audit4j.core.annotation.Audit;
import org.audit4j.core.annotation.IgnoreAudit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import com.sp.SPproject.configuration.JwtTokenUtil;
import com.sp.SPproject.api.model.UserResponse;

@RestController
@CrossOrigin
public class UserAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	UserAuthenticationDetailsService userAuthenticationDetailsService;

	@Audit(action = "User authentication")
	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticateUser(@IgnoreAudit @RequestBody UserRequest userRequest) throws Exception {

		authenticate(userRequest.getUsername(), userRequest.getPassword());

		final UserDetails userDetails = userAuthenticationDetailsService
				.loadUserByUsername(userRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new UserResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
