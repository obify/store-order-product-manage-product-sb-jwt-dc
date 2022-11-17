package com.assignment.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.api.constants.CodeMessage;
import com.assignment.api.entity.User;
import com.assignment.api.exception.NotFoundException;
import com.assignment.api.model.APIResponse;
import com.assignment.api.security.util.JwtUtil;
import com.assignment.api.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtil jwtUtils;

	@PostMapping("/")
	public ResponseEntity<?> register(@RequestBody User user) throws Exception {
		APIResponse response = new APIResponse();
		User suser = userService.saveUser(user);
		response.setCode(CodeMessage.SUCCESS_REGISTRATION);
		response.setMessage(CodeMessage.SUCCESS_REGISTRATION_MESSAGE);
		response.setPayload(suser);

		return new ResponseEntity(response, HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestParam("userName") String userName,
			@RequestParam("password") String password) throws Exception {
		APIResponse response = new APIResponse();

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
		} catch (BadCredentialsException e) {
			throw new NotFoundException(CodeMessage.INCORRECT_USER_MESSAGE);
		}

		final UserDetails userDetails = userService.loadUserByUsername(userName);
		String jwt = jwtUtils.generateToken(userDetails);
		Map<String, Object> loginMap = new HashMap<String, Object>();
		loginMap.put("accessToken", jwt);
		response.setCode(CodeMessage.OTP_VERIFIED);
		response.setMessage(CodeMessage.OTP_VERIFIED_MESSAGE);
		response.setPayload(loginMap);
		return new ResponseEntity(response, HttpStatus.OK);
	}

}
