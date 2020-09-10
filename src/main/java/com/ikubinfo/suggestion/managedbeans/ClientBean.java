package com.ikubinfo.suggestion.managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.ikubinfo.suggestion.model.Client;
import com.ikubinfo.suggestion.service.UserService;

@ManagedBean(name = "clientBean")
@SessionScoped
public class ClientBean implements Serializable {

	private static final long serialVersionUID = 5448895347701230045L;

	private List<Client> clientList;

	
	@ManagedProperty(value = "#{userService}")
	private UserService userService;

	private Client client;

	@PostConstruct
	public void notInit() {
		client = new Client();
		
	}

	public void search() {
		clientList = userService.getAll();

	}

	public void register() {
		userService.register(client);
		notInit();
	}

	public void validate( ) throws IOException{
		userService.validate(client);
	}

	 
	 public void loginButton() throws IOException {
		 userService.loginButton();
		    }
	 public void registerButton() throws IOException  {
		 userService.registerButton();
	    }

	


	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<Client> getClientList() {
		return clientList;
	}

	public void setClientList(List<Client> clientList) {
		this.clientList = clientList;
	}



}
