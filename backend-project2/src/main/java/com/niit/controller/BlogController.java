package com.niit.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.BlogPostDao;
import com.niit.model.BlogPost;
import com.niit.model.Users;
import com.niit.model.Error;

@RestController
public class BlogController
{
@Autowired
private BlogPostDao blogPostDao;
@RequestMapping(value="/saveblogpost",method=RequestMethod.POST)
public ResponseEntity<?> saveBlogPost(@RequestBody BlogPost blogPost,HttpSession session)
{
	Users users=(Users)session.getAttribute("user");
	if(users==null)
	{
		Error error =new Error(3,"UnAutorized user");
		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
	}
	try{
		blogPost.setPostedOn(new Date());
		blogPost.setCreatedBy(users);
		blogPostDao.saveBlogPost(blogPost);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	catch(Exception e)
	{
		Error error =new Error(2,"Cannot insert blog post details");
		return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
@RequestMapping(value="/listofblogs/{approved}",method=RequestMethod.GET)
public ResponseEntity<?> getAllBlogs(@PathVariable int approved,HttpSession session)
{
	Users users=(Users)session.getAttribute("user");
	if(users==null)
	{
		Error error=new Error(3,"UnAutorized user");
		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
	}
	List<BlogPost> blogPosts=blogPostDao.getAllBlogs(approved);
	return new ResponseEntity<List<BlogPost>>(blogPosts,HttpStatus.OK);
}
}

