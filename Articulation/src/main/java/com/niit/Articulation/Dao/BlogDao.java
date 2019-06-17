package com.niit.Articulation.Dao;

import java.util.List;

import com.niit.Articulation.Model.Blog;

public interface BlogDao {
	
public List<Blog> getAllBlogs();//1.....
	
	public boolean saveBlog(Blog blog);//2.....
	
	public boolean deleteBlog(Blog blog);//3.....
	
	public boolean updateBlog(Blog blog);//4.....
	
	public Blog getBlogByBlogId(int blogId);//5.....
	
	public List<Blog> getAllApproveBlogs();//6.....
	
	public List<Blog> getAllPendingBlogs();//7.....


}
