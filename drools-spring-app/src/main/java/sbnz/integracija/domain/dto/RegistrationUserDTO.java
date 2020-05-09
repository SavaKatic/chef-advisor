package sbnz.integracija.domain.dto;


import sbnz.integracija.domain.User;
import org.springframework.security.core.GrantedAuthority;
import java.util.ArrayList;

public class RegistrationUserDTO {

    private String token;

    private Long id;

    private String name;

    private String email;

    private String username;

    private String password;

    private ArrayList<String> authorities;

    public RegistrationUserDTO() {
        this.token = null;
    }

    public RegistrationUserDTO(String token) {
        this.token = token;
    }

    public RegistrationUserDTO(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.authorities = new ArrayList<>();
        for(GrantedAuthority authority : user.getAuthorities()){
            this.authorities.add(authority.getAuthority());
        }
    }

    public RegistrationUserDTO(User user, String token){
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.token = token;
        this.authorities = new ArrayList<>();
        for(GrantedAuthority authority : user.getAuthorities()){
            this.authorities.add(authority.getAuthority());
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArrayList<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(ArrayList<String> authorities) {
        this.authorities = authorities;
    }
}
