package com.kervinCortez.Project.controller;

import com.kervinCortez.Project.entity.Phone;
import com.kervinCortez.Project.entity.Usuario;
import com.kervinCortez.Project.negocio.exception.ExistsEmailException;
import com.kervinCortez.Project.negocio.exception.InvalidFormatEmailException;
import com.kervinCortez.Project.negocio.exception.InvalidFormatPasswordException;
import com.kervinCortez.Project.negocio.repository.PhonesRepository;
import com.kervinCortez.Project.negocio.repository.UserRepository;
import com.kervinCortez.Project.negocio.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;



public class UserControllerTest {
	
	private static Usuario user;
	private static Phone phones;
	private static UserController userController;
	private static UserServiceImpl userServiceImpl;
	private static Map<String,String> headers;
	
	@Autowired
	private static UserRepository userRepository;
	
	@Autowired
	private static PhonesRepository phonesRepository;
	
	@BeforeAll
	static void setUp() {
		user = new Usuario();
		phones = new Phone();
		userController = new UserController();
		userServiceImpl = new UserServiceImpl();
		headers = new LinkedHashMap<>();
		userRepository = Mockito.mock(UserRepository.class);
		phonesRepository = Mockito.mock(PhonesRepository.class);
		user.setId("1");
		user.setName_U("Christian Mendez");
		user.setEmail("test@test.org");
		user.setPassword_U("passwordTest12");
		
		phones.setCitycode_ph(1);
		phones.setContrycode_ph(2);
		phones.setNumber_ph(3);
		
		Set<Phone> phoneSet = new HashSet<Phone>();
		phoneSet.add(phones);
		
		user.setPhones(phoneSet);
		
		headers.put("authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaHJpc3RpYW5AbWVuZGV6Lm9yZyIsImV4cCI6MTU4MTE1MDExNCwiaWF0IjoxNTgxMTMyMTE0fQ.BxoG4YRCenYRlm31lGwplTmeCwU2hP9CzV41sizeICNxTFyXKBrqtu0dVbULI_xJuKsVQUF0Ob1AySlQm8aXBw");
		
		
	}
	
	@Test
	public void registerTest() {
		ReflectionTestUtils.setField(userServiceImpl, "userRepository", userRepository);
		ReflectionTestUtils.setField(userServiceImpl, "phonesRepository", phonesRepository);
		ReflectionTestUtils.setField(userController, "userService", userServiceImpl);
		
		when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		user.setPassword_U("Hunter21");
		userController.registroUsuario(user, headers);
	}
	
	@Test
	public void exceptionExistsMailTest() {
		ReflectionTestUtils.setField(userServiceImpl, "userRepository", userRepository);
		ReflectionTestUtils.setField(userServiceImpl, "phonesRepository", phonesRepository);
		ReflectionTestUtils.setField(userController, "userService", userServiceImpl);
		
		
		when(userRepository.findByEmail(user.getEmail())).thenReturn(new Usuario());
		
		Assertions.assertThrows(ExistsEmailException.class, () -> {
			userController.registroUsuario(user, headers);
		});
		
	}
	
	@Test
	public void exceptionInvalidFormatPasswordTest() {
		ReflectionTestUtils.setField(userServiceImpl, "userRepository", userRepository);
		ReflectionTestUtils.setField(userServiceImpl, "phonesRepository", phonesRepository);
		ReflectionTestUtils.setField(userController, "userService", userServiceImpl);
		
		
		when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
		
		Assertions.assertThrows(InvalidFormatPasswordException.class, () -> {
			user.setPassword_U("test");
			userController.registroUsuario(user, headers);
		});
		
	}
	
	@Test
	public void exceptionInvalidFormatEmailTest() {
		ReflectionTestUtils.setField(userServiceImpl, "userRepository", userRepository);
		ReflectionTestUtils.setField(userServiceImpl, "phonesRepository", phonesRepository);
		ReflectionTestUtils.setField(userController, "userService", userServiceImpl);
		
		Assertions.assertThrows(InvalidFormatEmailException.class, () -> {
			user.setEmail("@test");
			userController.registroUsuario(user, headers);
		});
		
	}

}
