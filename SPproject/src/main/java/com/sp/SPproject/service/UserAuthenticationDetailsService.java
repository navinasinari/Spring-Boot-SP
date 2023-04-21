package com.sp.SPproject.service;
import java.util.ArrayList;
import com.sp.SPproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
	@Autowired
	UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//abc123- $2a$10$Zp3wxKr1hDqOJTVCtUp/De/Wdi96RJU8zjn1mx/EGY8kFRSe5IvAe
		//password - $2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6
//		String encodedPassword = bCryptPasswordEncoder.encode("abc123");
//		System.out.println(encodedPassword);

//		if ("navina".equals(username)) {
//			return new User("navina","$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
//					new ArrayList<>());
//		} else {
//			throw new UsernameNotFoundException("User not found with username: " + username);
//		}
		com.sp.SPproject.api.model.User user = userRepository.findByUsername(username);
		if (user != null) {
			return new User(user.getUsername(),user.getPassword(),
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

}