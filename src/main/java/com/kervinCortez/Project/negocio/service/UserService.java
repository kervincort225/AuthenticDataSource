package com.kervinCortez.Project.negocio.service;

import java.util.Map;
import java.util.Optional;
import com.kervinCortez.Project.entity.Usuario;

public interface UserService {

	Optional<Usuario> insertUser(Usuario user, Map<String, String> headers);
	
	Boolean validateEmail(String mail);
	
	Boolean validatePassword(String password);
	
	Boolean emailExists(String mail);
}
