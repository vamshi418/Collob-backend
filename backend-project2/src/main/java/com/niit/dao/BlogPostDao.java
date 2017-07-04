package com.niit.dao;

import java.util.List;

import com.niit.model.BlogPost;

public interface BlogPostDao 
{
void saveBlogPost(BlogPost blogpost);
List<BlogPost> getAllBlogs(int approved);
}
