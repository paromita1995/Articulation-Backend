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

import com.niit.Articulation.Dao.ForumCommentDao;
import com.niit.Articulation.Dao.ForumDao;
import com.niit.Articulation.Model.BlogComment;
import com.niit.Articulation.Model.Forum;
import com.niit.Articulation.Model.ForumComment;

import com.niit.Articulation.Model.UserDetails;

@RestController

public class ForumCommentController {
	@Autowired
	ForumCommentDao comment;
	@Autowired
	Forum forum;
	@Autowired
	ForumDao forumdao;
	@Autowired
	UserDetails userDetails;
	
	//getAll Commentlist//
	@RequestMapping(value="/forumCommentlist", method=RequestMethod.GET)
	public ResponseEntity<List<ForumComment>> getAllForumComments(){
		List<ForumComment>fc=comment.getAllForumComments();
		if(fc.isEmpty()){
			return new ResponseEntity<List<ForumComment>>(fc, HttpStatus.NO_CONTENT);
		}
		System.out.println(fc.size());
		System.out.println("retrieving forumComments ");
		return new ResponseEntity<List<ForumComment>>(fc, HttpStatus.OK);

}
	//create NewComment//
	@RequestMapping(value="/createNewComment/", method=RequestMethod.POST)
	public ResponseEntity<ForumComment> createForumComment(@RequestBody ForumComment forumComment,HttpSession session){
		System.out.println("Create ForumComment");
		
		UserDetails user=(UserDetails) session.getAttribute("loggedInUser");
		
		forumComment.setForumCommentDate(new Date());
		forumComment.setUserId(user.getUserId());
		forumComment.setUserName(user.getName());
		comment.save(forumComment);
		System.out.println("forumComment" + forumComment.getForumCommentId());
		return new ResponseEntity<ForumComment>(forumComment,HttpStatus.OK);
}
	//check forumComment by {id}//
		@RequestMapping(value= "/forumcommentsById/{id}",method=RequestMethod.GET)
		public ResponseEntity<ForumComment>getByForumCommentId(@PathVariable("id")int id){
			ForumComment forumComment = comment.getByForumCommentId(id);
			if (forumComment == null){
				forumComment = new ForumComment();
				forumComment.setErrorMessage("ForumComment does not exist with id : " + id);
					return new ResponseEntity<ForumComment>(forumComment, HttpStatus.NOT_FOUND);
					
			}
			return new ResponseEntity<ForumComment>(forumComment, HttpStatus.OK);
		}
}
