package com.stackoverflow.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;

public class Post  implements Serializable{
	//common attributes
	private String focus;
	private String system;
	private String language;
	

	private String developE;
	private String component;
	private String technology;
	private String database;
	
	public int postId;

	public String post_body;
	public String post_body_text;
	public String post_body_code;
	public String post_title;
	public String post_tag;
	
	public float grade = 0;
	
	public int post_comment_count;
	public List<Comment> commentList;
	
	//answer related
	public int parentId;
	public Question parent_question;
	//question related 
	public int post_answer_count;
	public List<Answer> AnswerList;	
	public int accepted_answerId;
	public Answer accpted_answer;
	
	
	public void setFacetName(String focus, String system, String language, String developE, String component, String technology, String database)
	{
		this.focus = focus;
		this.system = system;
		this.language = language;
		this.database = database;
		this.developE = developE;
		this.technology = technology;
		this.component = component;
	}
	public Post(int postId,String post_title,String post_body,String post_tag,int post_comment_count,
			int parentId,int post_answer_count,int accepted_answerId)
	{
		this.postId = postId;
		this.post_title=post_title;
		this.post_body=post_body;
		this.post_tag=post_tag;
		this.post_comment_count = post_comment_count;
		this.parentId= parentId;
		this.post_answer_count= post_answer_count;
		this.accepted_answerId= accepted_answerId;
	

		splitBody(post_body);
		commentList= null;
		parent_question = null;
		AnswerList = null;
		accpted_answer =null;
	}
	
	public void splitBody(String post_body)
	{
		this.post_body_code="";
		this.post_body_text="";
		String codePrefix="<pre><code>";
		String codePostfix="</code></pre>";
		while(post_body.contains(codePrefix)&post_body.contains(codePrefix))
		{
			int prefixIndex = post_body.indexOf(codePrefix);
			int postfixIndex= post_body.indexOf(codePostfix)+codePostfix.length();
			post_body_code += post_body.substring(prefixIndex, postfixIndex);
			post_body = post_body.substring(0,prefixIndex)+post_body.substring(postfixIndex,post_body.length());
		}
		post_body_text = post_body;
	}
	
	public void setGrade(float gade)
	{
		this.grade = grade;
	}
	
	public float getGrade()
	{
		return grade;
	}
	
	public String getText(String text)
	{
		return Jsoup.parse(text).body().text();
	}
	
	public String getPost_body() {
		return this.post_body;
	}

	public String getPost_body_text() {
		return Jsoup.parse(post_body_text).body().text();
	}

	public String getPost_body_code() {
		return post_body_code;
	}

	public String getPost_title() {
		return post_title;
	}

	public String getPost_tag() {
	
		return post_tag.replace("<", " ").replace(">", " ");
	}
	
	public void setPost_tag(String post_tag) {
		this.post_tag = post_tag;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getPost_comment_count() {
		return post_comment_count;
	}

	public void setPost_comment_count(int post_comment_count) {
		this.post_comment_count = post_comment_count;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}
	
	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public Question getParent_question() {
		return parent_question;
	}

	public void setParent_question(Question parent_question) {
		this.parent_question = parent_question;
	}
	
	public int getPost_answer_count() {
		return post_answer_count;
	}

	public void setPost_answer_count(int post_answer_count) {
		this.post_answer_count = post_answer_count;
	}

	public List<Answer> getAnswerList() {
		return AnswerList;
	}

	public void setAnswerList(List<Answer> answerList) {
		AnswerList = answerList;
	}

	public int getAccepted_answerId() {
		return accepted_answerId;
	}

	public void setAccepted_answerId(int accepted_answerId) {
		this.accepted_answerId = accepted_answerId;
	}

	public Answer getAccpted_answer() {
		return accpted_answer;
	}

	public void setAccpted_answer(Answer accpted_answer) {
		this.accpted_answer = accpted_answer;
	}
	
	public void setFocus(String focus)
	{
		this.focus = focus;
	}
	
	public String getFocus()
	{
		return this.focus;
	}
	public void setSystem(String system)
	{
		this.system = system;
	}
	
	public String getSystem()
	{
		return this.system;
	}
	public void setLanguage(String language)
	{
		this.language = language;
	}
	
	public String getLanguage()
	{
		return this.language;
	}
	
	public String getDevelopE() {
		return developE;
	}
	public void setDevelopE(String developE) {
		this.developE = developE;
	}
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public String getTechnology() {
		return technology;
	}
	public void setTechnology(String technology) {
		this.technology = technology;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}

}