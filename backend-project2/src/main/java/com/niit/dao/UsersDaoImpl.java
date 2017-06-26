package com.niit.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.Users;
@Repository
public class UsersDaoImpl implements UsersDao 
{
	@Autowired
	private SessionFactory sessionFactory;
	public void registration(Users users) 
	{
		Session session=sessionFactory.openSession();
		session.save(users); 
		session.flush();
		session.close();
	}
	@Override
	public List<Users> getAllUsers() 
	{
		Session session=sessionFactory.openSession();
		Query query=session.createQuery("from users");
		List<Users> users=query.list();
		session.close();
		return users;
	}
	
	public Users login(Users users)
	{
		Session session=sessionFactory.openSession();
		Query query=session.createQuery("from users where username=? and password=? and enabled=?");
		query.setString(0, users.getUsername()); //for assigning the values to parameter username
		query.setString(1, users.getPassword());
		query.setBoolean(2, true);
		Users validUsers=(Users)query.uniqueResult();
		session.close();
		return validUsers;
	}
    
 public Users updateUser(Users validUser)
 {
	 Session session=sessionFactory.openSession();
	 session.update(validUser);
	 session.flush();
	 session.close();
	 return validUser;
	 
 }


}
