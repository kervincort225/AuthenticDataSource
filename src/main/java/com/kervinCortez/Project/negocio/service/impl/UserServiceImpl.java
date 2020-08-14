package com.kervinCortez.Project.negocio.service.impl;


import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import com.kervinCortez.Project.entity.Phone;
import com.kervinCortez.Project.entity.Usuario;
import com.kervinCortez.Project.negocio.exception.ExistsEmailException;
import com.kervinCortez.Project.negocio.exception.InvalidFormatEmailException;
import com.kervinCortez.Project.negocio.exception.InvalidFormatPasswordException;
import com.kervinCortez.Project.negocio.repository.PhonesRepository;
import com.kervinCortez.Project.negocio.repository.UserRepository;
import com.kervinCortez.Project.negocio.service.UserService;
import com.kervinCortez.Project.util.ConstantsEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


/**
* This service is responsible for persisting the data sent from the controller
*
* @author  Kervin Cortez
* @version 1.0
* @since   2020-02-07 
*/

@Service
public class UserServiceImpl implements UserService {

	private final static Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PhonesRepository phonesRepository;
	
	@Override
	public Optional<Usuario> insertUser(Usuario user, Map<String, String> headers) {
		
		LOGGER.info("Begins to persist the user: " + user.getEmail().toString());
		
		if(!validateEmail(user.getEmail())) {
			throw new InvalidFormatEmailException();
		}else if(emailExists(user.getEmail())) {
			throw new ExistsEmailException();
		}else if(!validatePassword(user.getPassword_U())) {
			throw new InvalidFormatPasswordException();
		}
		
		user.setToken(headers.get("authorization").substring(7));
		user.setCreated(new Date());
		user.setLast_login(user.getCreated());
		user.setIsactive(true);
		String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword_U());
		user.setPassword_U(encodedPassword);
		
		userRepository.save(user);
		
		LOGGER.info("User persisted successfully");
		
		for (Iterator<Phone> it = user.getPhones().iterator(); it.hasNext(); ) {
			Phone f = it.next();
			f.setId_user(user.getId());
		}
		
		phonesRepository.saveAll(user.getPhones());
		
		LOGGER.info("Phones persisted successfully");
		
		Optional<Usuario> userFinal = userRepository.findById(user.getId());
		
		return userFinal;
	}


	@Override
	public Boolean validateEmail(String mail) {
		String regex = ConstantsEnum.REGEXEMAIL.toString();
	    return mail.matches(regex);
	}


	@Override
	public Boolean validatePassword(String password) {
		String regex = ConstantsEnum.REGEXPASSWORD.toString();
		return password.matches(regex);
	}


	@Override
	public Boolean emailExists(String mail) {
		Usuario user = userRepository.findByEmail(mail);
		return user != null;
	}

}
