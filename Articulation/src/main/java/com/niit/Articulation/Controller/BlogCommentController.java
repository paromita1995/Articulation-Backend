package com.niit.Articulation.Controller;

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

import com.niit.Articulation.Dao.BlogCommentDao;
import com.niit.Articulation.Dao.BlogDao;
import com.niit.Articulation.Model.Blog;
import com.niit.Articulation.Model.BlogComment;
import com.niit.Articulation.Model.Forum;
import com.niit.Articulation.Model.Job;
import com.niit.Articulation.Model.UserDetails;

@RestController
public class BlogCommentController {
	@Autowired
	BlogCommentDao comment;
	@Autowired
	Blog blog;
	@Autowired
	BlogDao blogdao;
	@Autowired
	UserDetails userDetails;
	
	//getAll Commentlist//
	@RequestMapping(value="/blogCommentlist", method=RequestMethod.GET)
	public ResponseEntity<List<BlogComment>> getAllBlogComments(){
		List<BlogComment>blogComment=comment.getAllBlogComments();
		if(blogComment.isEmpty()){
			return new ResponseEntity<List<BlogComment>>(blogComment, HttpStatus.NO_CONTENT);
		}
		System.out.println(blogComment.size());
		System.out.println("retrieving blogComments ");
		return new ResponseEntity<List<BlogComment>>(blogComment, HttpStatus.OK);

}
	//create NewComment//
	@RequestMapping(value="/createBlogComment/", method=RequestMethod.POST)
	public ResponseEntity<BlogComment> createBlogComment(@RequestBody BlogComment blogComment,HttpSession session){
		System.out.println("Create BlogComment");
		
		UserDetails user=(UserDetails) session.getAttribute("loggedInUser");
		
		blogComment.setBlogCommentDate(new Date());
		blogComment.setUserId(user.getUserId());
		blogComment.setUserName(user.getName());
		blogComment.setBlogId(blogComment.getBlogId());
		comment.save(blogComment);
		System.out.println("blogComment" + blogComment.getBlogCommentId());
		return new ResponseEntity<BlogComment>(blogComment,HttpStatus.OK);
}
	//check blogComment by {id}//
	@RequestMapping(value= "/commentsById/{id}",method=RequestMethod.GET)
	public ResponseEntity<BlogComment>getByBlogCommentId(@PathVariable("id")int id){
		BlogComment blogComment = comment. getByBlogCommentId(id);
		if (blogComment == null){
			blogComment = new BlogComment();
			blogComment.setErrorMessage("BlogComment does not exist with id : " + id);
				return new ResponseEntity<BlogComment>(blogComment, HttpStatus.NOT_FOUND);
				
		}
		return new ResponseEntity<BlogComment>(blogComment, HttpStatus.OK);
	}

}