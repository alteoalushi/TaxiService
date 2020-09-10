package com.ikubinfo.suggestion.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ikubinfo.suggestion.dao.ClientDao;

import com.ikubinfo.suggestion.model.Client;




@Service
public class UserService  {
	
	@Autowired
	private ClientDao clientDao;
	
	private Client user;

	public List<Client> getAll() {
		return clientDao.getAll();
	}

	public void register(Client client) {
		clientDao.register(client);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ClientDao getCountryDao() {
		return clientDao;
	}

	public void setCountryDao(ClientDao countryDao) {
		this.clientDao = countryDao;
	}
	
	public void validate(Client userModel) throws IOException{
		clientDao.validateUser(userModel);
	}
	  
	public boolean login(String username, String password){
		return clientDao.login(username, password);
	}
	
	
	
	 
	 public void loginButton() throws IOException   {
		 FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
		    }
	  
	 public void registerButton() throws IOException {  
		 FacesContext.getCurrentInstance().getExternalContext().redirect("register.xhtml");
	    }
	  
/*	public void login(String username){
	  userDao.getUserInfo(username);
	  
	}*/
	
/*	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Client userInfo = userDao.getUserInfo(username);
		GrantedAuthority authority = new SimpleGrantedAuthority(userInfo.getRole());
		UserDetails userDetails = (UserDetails)new User(userInfo.getUsername(), 
				userInfo.getPassword(), Arrays.asList(authority));
		return userDetails;
	}*/
	 
}
