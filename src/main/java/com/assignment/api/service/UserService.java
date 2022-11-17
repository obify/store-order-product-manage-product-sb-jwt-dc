package com.assignment.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.assignment.api.entity.User;
import com.assignment.api.exception.ExistException;
import com.assignment.api.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;

	public User saveUser(User user) throws Exception {
		User fuser = userRepository.findByUserName(user.getUserName());
		if (fuser != null) {
			throw new ExistException("User exist");
		}
		user.setStatus(true);
		user.setAuthority("ROLE_USER");
		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		List<GrantedAuthority> authorities = new ArrayList<>();
		User user = userRepository.findByUserName(userName);
		if (user == null) {
			throw new UsernameNotFoundException("User Not Found");
		}
		authorities.add(new SimpleGrantedAuthority(user.getAuthority()));
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
	}

}
