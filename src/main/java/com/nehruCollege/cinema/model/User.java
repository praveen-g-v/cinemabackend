package com.nehruCollege.cinema.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;





@Document(value = "User")
@Component
public class User implements UserDetails {
	

	private static final long serialVersionUID = 1L;

	@Id
    private String UserId;
	
	private String name;
	
	private String email;
	
	private String password;
	
	private Date createdOn;
	
	private long mobileNo;
	
	private List<String> role;
	
	
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	public List<String> getRole() {
		return role;
	}

	public void setRole(List<String> role) {
		this.role = role;
	}

	public String getUserId() {
		return UserId;
	}
	
	@Override
	public String toString() {
		return name+" "+email+" "+password+" "+createdOn+" "+mobileNo+" "+role;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return   getRole().stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
	}

	@Override
	public String getUsername() {
		return getEmail();
	}
	
	@Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    

}
