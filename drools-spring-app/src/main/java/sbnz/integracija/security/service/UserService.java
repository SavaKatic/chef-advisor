package sbnz.integracija.security.service;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sbnz.integracija.config.WebSecurityConfig;
import sbnz.integracija.domain.User;
import sbnz.integracija.domain.dto.RegistrationUserDTO;
import sbnz.integracija.domain.enums.Role;
import sbnz.integracija.domain.exceptions.EmailTakenException;
import sbnz.integracija.domain.exceptions.UsernameTakenException;
import sbnz.integracija.security.domain.Authority;
import sbnz.integracija.security.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityService authorityService;
    
    @Autowired
    private WebSecurityConfig webSecurityConfig;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public UserDetails loadUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User registerUser(RegistrationUserDTO userDTO) {
        ArrayList<Authority> authorities = new ArrayList<Authority>();
        Authority authority = authorityService.findAuthorityByName(Role.ROLE_USER.name());
        if(loadUserByUsername(userDTO.getUsername()) != null){
            throw new UsernameTakenException();
        }
        if(loadUserByEmail(userDTO.getEmail()) != null){
            throw new EmailTakenException();
        }
        authorities.add(authority);
        User user = new User();
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setAuthorities(authorities);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setConfirmed(true);
        user.setEnabled(true);
        return userRepository.save(user);
    }

	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}
    
	@Transactional
	public User promoteUser(String email) {
		User u = userRepository.findByEmail(email);
		if (u != null) {
			Iterator<? extends GrantedAuthority> iter = u.getAuthorities().iterator();
			boolean isAdmin = false;
			ArrayList<Authority> authorities = new ArrayList<Authority>();
			while (iter.hasNext()) {
				GrantedAuthority ga = iter.next();
				// demote
				if (ga.getAuthority().equals("ROLE_ADMIN")) {
					authorities.add(authorityService.findAuthorityByName("ROLE_USER"));
					isAdmin = true;
				}
			}
			// promote
			if (!isAdmin) {
				authorities.add(authorityService.findAuthorityByName("ROLE_ADMIN"));
			}
			u.setAuthorities(authorities);
			return userRepository.save(u);
		}
		return null;
	}

	@Transactional
	public User enableUser(String email) {
		User u = userRepository.findByEmail(email);
		if (u != null) {
			
			if (u.isEnabled())
				u.setEnabled(false);
			else
				u.setEnabled(true);
			return userRepository.save(u);
		} 
		return null;
	}

	public User findById(Long id) {
		Optional<User> ou = userRepository.findById(id);
		if (ou.isPresent())
			return ou.get();
		return null;
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Transactional
	public User update(User user) {
		User userOld = userRepository.findById(user.getId()).orElse(null);
		if (userOld != null) {
			userOld.setUsername(user.getUsername());
			userOld.setEmail(user.getEmail());
			PasswordEncoder pe = this.webSecurityConfig.passwordEncoder();
			userOld.setPassword(pe.encode(user.getPassword()));
			userOld.setEnabled(user.getEnabled());
			userOld.setName(user.getName());
			return userRepository.save(userOld);
		}
		return null;
	}

	public Boolean checkIfPasswordsMatch(String password, String email) {
		User u = userRepository.findByEmail(email);
		PasswordEncoder pe = this.webSecurityConfig.passwordEncoder();
		if (pe.matches(password, u.getPassword())) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean checkIfUsernameAndEmailUnique(String username, String email) {
		Boolean areUnique = true;
		List<User> allUsers = userRepository.findAll();
		for (User u : allUsers) {
			if (username.equalsIgnoreCase(u.getUsername()) || email.equalsIgnoreCase(u.getEmail())) {
				areUnique = false;
				break;
			}
		}
		return areUnique;
	}
    
}
