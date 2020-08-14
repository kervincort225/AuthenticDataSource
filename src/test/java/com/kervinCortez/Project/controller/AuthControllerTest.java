package com.kervinCortez.Project.controller;

import static org.mockito.Mockito.when;

import java.util.LinkedHashMap;
import java.util.Map;

import com.kervinCortez.Project.config.JwtToken;
import com.kervinCortez.Project.entity.JwtRequest;
import com.kervinCortez.Project.entity.Usuario;
import com.kervinCortez.Project.negocio.repository.UserRepository;
import com.kervinCortez.Project.negocio.service.impl.JwtUserDetailsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.util.ReflectionTestUtils;


public class AuthControllerTest {

	private static Usuario user;
	private static JwtRequest jwtRequest;
	private static AuthController authController;
	private static JwtUserDetailsService jwtUserDetailsService;
	private static Map<String,String> headers;
	
	@Autowired
	private static UserRepository userRepository;
	
	@Autowired
	private static AuthenticationManager authenticationManager;
	
	@Autowired
    private static JwtToken jwtToken;
	
	@BeforeAll
	static void setUp() {
		user = new Usuario();
		jwtRequest = new JwtRequest();
		authController = new AuthController();
		jwtUserDetailsService = new JwtUserDetailsService();
		headers = new LinkedHashMap<>();
		jwtToken = new JwtToken();
		userRepository = Mockito.mock(UserRepository.class);
		authenticationManager = Mockito.mock(AuthenticationManager.class);
		jwtRequest.setEmail("christian@mendez.org");
		jwtRequest.setPassword("Hunter21");
		user.setEmail(jwtRequest.getEmail());
		user.setPassword_U(jwtRequest.getPassword());
		headers.put("authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaHJpc3RpYW5AbWVuZGV6Lm9yZyIsImV4cCI6MTU4MTE1MDExNCwiaWF0IjoxNTgxMTMyMTE0fQ.BxoG4YRCenYRlm31lGwplTmeCwU2hP9CzV41sizeICNxTFyXKBrqtu0dVbULI_xJuKsVQUF0Ob1AySlQm8aXBw");
		
		
	}
	
	@Test
	public void registerTest() throws Exception {
		ReflectionTestUtils.setField(jwtUserDetailsService, "userRepository", userRepository);
		ReflectionTestUtils.setField(jwtToken, "secret", "{bcrypt}$christian");
		ReflectionTestUtils.setField(authController, "authenticationManager", authenticationManager);
		ReflectionTestUtils.setField(authController, "jwtToken", jwtToken);
		ReflectionTestUtils.setField(authController, "jwtUserDetailsService", jwtUserDetailsService);
		
		when(userRepository.findByEmail(jwtRequest.getEmail())).thenReturn(user);
		
		authController.createAuthenticationToken(jwtRequest);
	}
	
}
