package com.niit.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.UsersDao;
import com.niit.model.Users;

@RestController
public class UserController 
{
	@Autowired
	
	private UsersDao usersDao;
	@RequestMapping(value="/registration",method=RequestMethod.POST)
	
	public ResponseEntity<?> registration(@RequestBody Users user)
	{
		try
		{
			user.setEnabled(true);
			List<Users> users=usersDao.getAllUsers();
			//for(T var:collection)
			for(Users u:users)
			{
				if(u.getUsername().equals(user.getUsername()))
				{
					Error error=new Error("Username already exists");
					return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
					
				}
			}
			
			usersDao.registration(user);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}
		catch(Exception e)
		{
			Error error=new Error("cannot register user details");
			return new ResponseEntity<Error>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody Users users,HttpSession session) {
		return null;
	}
	{
		Users validUser=usersDao.login(users);
		if(validUser==null)
		{
			Error error=new Error("Invalid username and password..please enter valid credentials");
			return new ResponseEntity<Error>(HttpStatus.UNAUTHORIZED)
		
		
		}
		else{
			validUser.setOnline(true);
			validUser.usersDao.updateUser(validUser);
			session.setAttribute("user",validUser);
			return new ResponseEntity<Users>(validUser,HttpStatus.OK);
		}
	}
	
}
