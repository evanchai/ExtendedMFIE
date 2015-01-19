package com.stackoverflow.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import cn.edu.fudan.se.facet.PostFacetType;
import cn.edu.fudan.se.filter.FilterPost;
import cn.edu.fudan.se.util.Global;

import com.stackoverflow.bean.*;
import com.stackoverflow.utils.DBConnection;

public class PostDAOImpl implements PostDAO {
	
	
	public List<Integer> findTestComments() throws Exception {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		List<Comment> comments = new ArrayList<Comment>();
		List<Integer> listInteger = new ArrayList<Integer>();

		String SQLString = "SELECT * FROM stackoverflowdata.testposts;";

		try {
			pstmt = conn.prepareStatement(SQLString);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				listInteger.add(rs.getInt("PostId"));
			}
		} catch (Exception ex) {
			System.out.println("Error : " + ex.toString());
		} finally {
			DBConnection.close(conn);
			DBConnection.close(pstmt);
		}
		return listInteger;
	}
	
	
	public List<Post> findPosts(String keywords) {
		Connection conn = null;
		try {
			conn = DBConnection.getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PreparedStatement pstmt = null;
		List<Post> posts = new ArrayList<Post>();
		// String keywords = "java,mysql,database";
		// generate query string based on separate keywords
		// using regexp replace('keyword1,keyword2',',','|')
		System.out.println("String:"+keywords);
		String SQLString = "SELECT * FROM stackoverflowdata.postFacetType, stackoverflowdata.testposts where postFacetType.PostId = testposts.Id and CONCAT(title,tags,body) regexp replace('"
				+keywords+"',',','|')";
		
//		String SQLString = "SELECT * FROM stackoverflowdata.posts WHERE PostTypeId = 1 and title like '%how%' and title like '%connect%' and title like '%mysql%' limit 20";
//		String SQLString = "SELECT * FROM stackoverflowdata.posts WHERE CONCAT(title,tags,body) like '"
//				+ "sort%" +"'" +"or CONCAT(title,tags,body) like 'list%'";

		try {
			pstmt = conn.prepareStatement(SQLString);
			ResultSet rs = pstmt.executeQuery();
			posts = constructTestPostList(rs);
		} catch (Exception ex) {
			System.out.println("Error : " + ex.toString());
		} finally {
			DBConnection.close(conn);
			DBConnection.close(pstmt);
		}
		return posts;
	}
	
	public List<Comment> findRelatedComments(int postId) throws Exception {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		List<Comment> comments = new ArrayList<Comment>();

		String SQLString = "SELECT * FROM stackoverflowdata.testcomments WHERE PostId="+postId+";";

		try {
			pstmt = conn.prepareStatement(SQLString);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				int commentId = rs.getInt("Id");
				String text = rs.getString("Text");
				comments.add(new Comment(commentId,postId,text));
			}
		} catch (Exception ex) {
			System.out.println("Error : " + ex.toString());
		} finally {
			DBConnection.close(conn);
			DBConnection.close(pstmt);
		}
		return comments;
	}
	
	public List<Answer> findRelatedAnswers(int postId) throws Exception {
		Connection conn = DBConnection.getConnection();
//		PreparedStatement pstmt = null;
		Statement stmt = conn.createStatement();
		List<Answer> answers = new ArrayList<Answer>();
//		System.out.println("Id:"+postId);

		String SQLString = "SELECT * FROM stackoverflowdata.posts WHERE ParentId="+postId+";";

		try {
//			pstmt = conn.prepareStatement(SQLString);
//			ResultSet rs = pstmt.executeQuery();
			ResultSet rs = stmt.executeQuery(SQLString);
			while(rs.next())
			{
				//comment Attribute;
				int post_typeId = rs.getInt("PostTypeId");
				String post_title = rs.getString("title");
				String post_body = rs.getString("body");
				String post_tag = rs.getString("tags");
//				System.out.println("check:");
				int post_comment_count = rs.getInt("CommentCount");//may be null
				//Answer Attribute;
				int parentId = rs.getInt("parentId");//may be null
				//Question Attribute;
				int post_answer_count = rs.getInt("AnswerCount");//may be null
				int accepted_answerId = rs.getInt("AcceptedAnswerId");//may be null
				
				answers.add(new Answer(postId,post_title,post_body,post_tag,
						post_comment_count,parentId,post_answer_count,accepted_answerId,null,null,null));
			}		
			} catch (Exception ex) {
			System.out.println("Error : " + ex.toString());
		} finally {
			DBConnection.close(conn);
//			DBConnection.close(pstmt);
			stmt.close();
		}
		return answers;
	}
	
	public Question findRelatedQuestion(int postId) throws Exception {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		Question resultQuestion = null;
		String SQLString = "SELECT * FROM stackoverflowdata.newposts WHERE id="+postId+";";

		try {
			pstmt = conn.prepareStatement(SQLString);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				//comment Attribute;
				int post_typeId = rs.getInt("PostTypeId");
				String post_title = rs.getString("title");
				String post_body = rs.getString("body");
				String post_tag = rs.getString("tags");
				int post_comment_count = rs.getInt("CommentCount");//may be null
				//Answer Attribute;
				int parentId = rs.getInt("parentID");//may be null
				//Question Attribute;
				int post_answer_count = rs.getInt("AnswerCount");//may be null
				int accepted_answerId = rs.getInt("AcceptedAnswerId");//may be null
				
				resultQuestion=new Question(postId,post_title, post_body,post_tag,
						post_comment_count,parentId,post_answer_count,accepted_answerId,null,null,null);
			}		} catch (Exception ex) {
			System.out.println("Error : " + ex.toString());
		} finally {
			DBConnection.close(conn);
			DBConnection.close(pstmt);
		}
		return resultQuestion;
	}
	
	public List<Post> constructTestPostList(ResultSet rs) throws SQLException{
		List<Post> posts = new ArrayList<Post>();
		int id = 0;
		try {
		while (rs.next()) {
			//comment Attribute;
			id++;
			int postId = rs.getInt("postFacetType.PostId");
			String focus = rs.getString("Focus");
			String system = rs.getString("System");
			String language = rs.getString("Language");
			int post_typeId = rs.getInt("PostTypeId");
			String post_title = rs.getString("title");

			String post_body = rs.getString("body");

			String post_tag = rs.getString("tags");
			int post_comment_count = rs.getInt("CommentCount");//may be null
			//Answer Attribute;
			int parentId = rs.getInt("parentID");//may be null
			//Question Attribute;
			int post_answer_count = rs.getInt("AnswerCount");//may be null
			int accepted_answerId = rs.getInt("AcceptedAnswerId");//may be null

//			if(!FilterPost.FilterAnswerCount(post_answer_count))
//				continue;
//			FilterPost.setFilterTag("mysql");
//			if(!FilterPost.FilterTag(post_tag))
//				continue;
			switch (post_typeId) {
			case 1: {
				Question question = new Question(postId,post_title,post_body,post_tag,post_comment_count
						,parentId,post_answer_count,accepted_answerId,focus,system,language);
//				//generate commentList and AnswerList 
//				if(question.getPost_comment_count()>0)
//					question.setCommentList(this.findRelatedComments(question.getPostId()));
//				if(question.getPost_answer_count()>0)
//					question.setAnswerList(this.findRelatedAnswers(question.getPostId()));
	
				posts.add(question);
				break;
			}
			case 2: {
				Answer answer = new Answer(postId,post_title,post_body
						,post_tag,post_comment_count,parentId,post_answer_count,accepted_answerId,focus,system,language);
				
//				//generate commentList and parent Question 
//				if(answer.getPost_comment_count()>0)
//					answer.setCommentList(this.findRelatedComments(answer.getPostId()));
//				if(answer.getParentId()>0)
//					answer.setParent_question(this.findRelatedQuestion(answer.getParentId()));
				
				posts.add(answer);
				break;
			}
			default:
				;
			}
		}
		}catch (Exception ex) {
			System.out.println("Error : " + ex.toString());
		}
		return posts;
	}


	public List<Post> constructedPostList(ResultSet rs) throws SQLException{
		List<Post> posts = new ArrayList<Post>();
		int id = 0;
		try {
		while (rs.next()) {
			//comment Attribute;
			id++;
			int postId = rs.getInt("Id");
			String focus = null;
			String environment = null;
			int post_typeId = rs.getInt("PostTypeId");
			String post_title = rs.getString("title");

			String post_body = rs.getString("body");

			String post_tag = rs.getString("tags");
			int post_comment_count = rs.getInt("CommentCount");//may be null
			//Answer Attribute;
			int parentId = rs.getInt("parentID");//may be null
			//Question Attribute;
			int post_answer_count = rs.getInt("AnswerCount");//may be null
			int accepted_answerId = rs.getInt("AcceptedAnswerId");//may be null

//			if(!FilterPost.FilterAnswerCount(post_answer_count))
//				continue;
//			FilterPost.setFilterTag("mysql");
//			if(!FilterPost.FilterTag(post_tag))
//				continue;
			switch (post_typeId) {
			case 1: {
				Question question = new Question(postId,post_title, post_body,post_tag,post_comment_count
						,parentId,post_answer_count,accepted_answerId,null,null,null);
//				//generate commentList and AnswerList 
//				if(question.getPost_comment_count()>0)
//					question.setCommentList(this.findRelatedComments(question.getPostId()));
//				if(question.getPost_answer_count()>0)
//					question.setAnswerList(this.findRelatedAnswers(question.getPostId()));
				Global.intList.add(postId);
				posts.add(question);
				break;
			}
			case 2: {
				Answer answer = new Answer(postId,post_title,post_body
						,post_tag,post_comment_count,parentId,post_answer_count,accepted_answerId,null,null,null);
				
//				//generate commentList and parent Question 
//				if(answer.getPost_comment_count()>0)
//					answer.setCommentList(this.findRelatedComments(answer.getPostId()));
//				if(answer.getParentId()>0)
//					answer.setParent_question(this.findRelatedQuestion(answer.getParentId()));
				
				posts.add(answer);
				break;
			}
			default:
				;
			}
		}
		}catch (Exception ex) {
			System.out.println("Error : " + ex.toString());
		}
		return posts;
	}

	@Override
	public List<Post> findPosts() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public List<Post> findTestPosts(String keywords) {
		Connection conn = null;
		try {
			conn = DBConnection.getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PreparedStatement pstmt = null;
		List<Post> posts = new ArrayList<Post>();
		// String keywords = "java,mysql,database";
		// generate query string based on separate keywords
		// using regexp replace('keyword1,keyword2',',','|')
		System.out.println("String:"+keywords);
		String SQLString = "SELECT * FROM stackoverflowdata.testposts where Id = 10563619";
	//	where Id>=696 and Id<=234888
//		String SQLString = "SELECT * FROM stackoverflowdata.posts WHERE PostTypeId = 1 and title like '%how%' and title like '%connect%' and title like '%mysql%' limit 20";
//		String SQLString = "SELECT * FROM stackoverflowdata.posts WHERE CONCAT(title,tags,body) like '"
//				+ "sort%" +"'" +"or CONCAT(title,tags,body) like 'list%'";

		try {
			pstmt = conn.prepareStatement(SQLString);
			ResultSet rs = pstmt.executeQuery();
			posts = constructedPostList(rs);
		} catch (Exception ex) {
			System.out.println("Error : " + ex.toString());
		} finally {
			DBConnection.close(conn);
			DBConnection.close(pstmt);
		}
		return posts;
	}
	public void Update(HashMap<Integer,PostFacetType> hashpft)
	{ 
		Connection conn = null;
		Statement stat = null;
		try {
			conn = DBConnection.getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try 
	   { //4.发送SQL语句进行数据更新
			stat = conn.createStatement();
			int id = 928;
             Iterator iter = hashpft.keySet().iterator();
			
			while (iter.hasNext())
			{
				PostFacetType pft = (PostFacetType) hashpft.get(iter.next());
				String sql = "insert into stackoverflowdata.postFacetType values("+id +","+ pft.postId +","+ pft.postTypeId +",'"+pft.focus+"','"+
			   pft.system+"','"+pft.language+ "','"+pft.tag+"','"+pft.content+"','"+pft.code+"');";
				stat.executeUpdate(sql);
				id++;
			}
		} catch(Exception e)
		{ 
			System.out.println("信息："+e.getMessage()); 
		}finally{
			try {
				stat.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DBConnection.close(conn);
		}
		
	}
	
	public static void main(String args[]) throws Exception
	{
		PostDAOImpl pdi = new PostDAOImpl();
		pdi.findRelatedAnswers(696);
	}
}
