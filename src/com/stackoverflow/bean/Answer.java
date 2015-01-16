package com.stackoverflow.bean;

import java.io.Serializable;

public class Answer extends Post implements Serializable
{
	boolean accepted;
	
	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public Answer(int postId,String post_title,String post_body,String post_tag,
			int post_comment_count,int parentId,int post_answer_count,int accepted_answerId,String focus,String environment)
	{
		super(postId, post_title, post_body, post_tag, 
				post_comment_count, parentId, post_answer_count, accepted_answerId,focus,environment);
	}	
}