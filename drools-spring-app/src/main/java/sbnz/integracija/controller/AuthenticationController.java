package sbnz.integracija.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sbnz.integracija.domain.User;
import sbnz.integracija.domain.dto.RegistrationUserDTO;
import sbnz.integracija.domain.exceptions.EmailTakenException;
import sbnz.integracija.domain.exceptions.UsernameTakenException;
import sbnz.integracija.security.TokenUtils;
import sbnz.integracija.security.auth.JwtAuthenticationRequest;
import sbnz.integracija.security.service.UserService;


@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {
	@Autowired
    public TokenUtils tokenUtils;

    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    public UserService userService;
    
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity logOut(HttpServletRequest request) {
        SecurityContextHolder.getContext().setAuthentication(null);
        return new ResponseEntity(HttpStatus.OK);
    }
	

    @RequestMapping(value = "/login", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> logIn(@RequestBody JwtAuthenticationRequest authenticationRequest) {
        final Authentication authentication = authenticationManager
                                .authenticate(new UsernamePasswordAuthenticationToken(
                                        authenticationRequest.getUsername(),
                                        authenticationRequest.getPassword()
                                ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User)authentication.getPrincipal();

        return ResponseEntity.ok(new RegistrationUserDTO(user,tokenUtils.generateToken(user.getUsername())));
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity register(@RequestBody RegistrationUserDTO userDTO){
        RegistrationUserDTO dto = null;
        try {
            dto = new RegistrationUserDTO(userService.registerUser(userDTO));
        }catch (UsernameTakenException e){
            return ResponseEntity.badRequest().body("Username is taken");
        }catch (EmailTakenException e){
            return ResponseEntity.badRequest().body("Email is taken");
        }
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/testUser")
    @PreAuthorize("hasRole('USER')")
    public String testUser(){
        return "USER WORKS";
    }

    @GetMapping(value = "/testAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public String testAdmin(){
        return "ADMIN WORKS";
    }

}
