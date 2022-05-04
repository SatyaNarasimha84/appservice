package com.jayam.appservice.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if ("satya".equals(username)) {
			return new User("satya", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
					new ArrayList<>());
		} else if ("test".equals(username)) {
			return new User("test", "$2a$10$d6ccKGHn3WsluIBxJLMklOaRJQD3T2CZkHNkAgLpgvgVlU97o42mu",
					new ArrayList<>());
		}else if ("admin".equals(username)) {
			return new User("admin", "$2a$10$U9QLux30vk3C/yFw89yh5.KCU.v6qDCuDeWaPK10tWyd/T39n8PIe",
					new ArrayList<>());
		}
		else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

}