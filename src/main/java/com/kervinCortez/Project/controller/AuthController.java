package com.kervinCortez.Project.controller;

import java.util.logging.Logger;

import com.kervinCortez.Project.config.JwtToken;
import com.kervinCortez.Project.entity.JwtRequest;
import com.kervinCortez.Project.entity.JwtResponse;
import com.kervinCortez.Project.negocio.service.impl.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import org.springframework.security.authentication.AuthenticationManager;

/**
* This Controller is responsible for validating that the user has access and returns a token if true
*
* @author  Kervin Cortez
* @version 1.0
* @since   2020-02-07 
*/

@RestController
@CrossOrigin
public class AuthController {
	
	private final static Logger LOGGER = Logger.getLogger(AuthController.class.getName());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

    	LOGGER.info("Obtained the request from client");
    	
        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        final UserDetails userDetails = jwtUserDetailsService

                .loadUserByUsername(authenticationRequest.getEmail());

        final String token = jwtToken.generateToken(userDetails);
        
        LOGGER.info("Return Token");

        return ResponseEntity.ok(new JwtResponse(token));

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
