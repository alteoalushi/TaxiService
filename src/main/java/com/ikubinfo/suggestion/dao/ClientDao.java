package com.ikubinfo.suggestion.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import javax.faces.context.FacesContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.ikubinfo.suggestion.mapper.ClientRowMapper;
import com.ikubinfo.suggestion.model.Client;

@Repository
public class ClientDao  {
	

	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	
	private static final String SearchQUERY = "Select project.name,project.description,project.createdtime,project.modifiedtime,project.email from project where name LIKE LOWER(?)";
	private static final String SelectQUERY = "Select * From project ";
	private static final String DeleteQUERY = "delete from project where id = ? and createdtime < (NOW() - INTERVAL '00:30:00')";
	private static final String CreateQUERY = "insert into client(firstname,lastname,username,email,password,phonenumber) values (?,?,?,?,?,?)";
	private static final String UpdateQUERY = "update project set name=? where id=?";
	private static final String FindQUERY = "select createdtime from project where id = ?";
	

	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	
	
	public void register(Client client) {
        System.out.println("insert method starts");
        jdbcTemplate.update(CreateQUERY,  client.getFirstName(), client.getLastName(),client.getUsername(),client.getEmail(), client.getPassword(), client.getPhonenumber());
    }

	public List<Client> getAll() {
		String QUERY = "Select * from users ";
		return namedParameterJdbcTemplate.query(QUERY, new ClientRowMapper());
	}

	

	
	 public void validateUser(Client user) throws IOException {
		    String sql = "select * from client where username='" + user.getUsername() + "' and password='" + user.getPassword()
		    + "'";
		    List<Client> users = jdbcTemplate.query(sql, new ClientRowMapper());
		    
		    System.out.println("ds" + user.getUsername());
		    if( users.size() > 0 ){
		    	FacesContext.getCurrentInstance().getExternalContext().redirect("success.xhtml");
		    }else {
		    	FacesContext.getCurrentInstance().getExternalContext().redirect("failure.xhtml");
		    }
		    	
		    
		    }
	 
	
	

	
	
	
	
	
	 public List<String> getRoleNames(Long userId) {
	        String sql = "Select authority " //
	                + " from users " //
	                + " where userid = ? ";
	 
	        Object[] params = new Object[] { userId };
	 
	        List<String> roles = this.jdbcTemplate.queryForList(sql, params, String.class);
	     System.out.println("checking authority from dao");
	 
	        return roles;
	    }


	 public  boolean login(String username, String password) {
		 String sql = "select * from users where firstname='" + username + "' and password='" + password + "'";
	                   
		 List<Client> users = jdbcTemplate.query(sql, new ClientRowMapper());
	           
		 if( users.size() > 0 ){
	                return true;
	            } else {
	                return false;
	            }
	        
	    }
	
/*	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String sql = "select * from users where firstname='" + username + "'";
		List<UserModel> users = jdbcTemplate.query(sql, new UserRowMapper());
		return (UserDetails) users;
	}*/
	
    

	public Client getUserInfo(String username){
    	String sql = "SELECT username, password  FROM "+
    			     "users WHERE "+
    			     "username = ?";
    	Client userInfo = (Client)jdbcTemplate.queryForObject(sql, new Object[]{username},new ClientRowMapper());
    	System.out.println("getUsermethod" + userInfo);
    	return userInfo;
    }
	

	
}