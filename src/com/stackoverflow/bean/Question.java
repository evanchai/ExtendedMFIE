package com.stackoverflow.bean;

import java.io.Serializable;
import java.util.List;

public class Question extends Post  implements Serializable
{
	public Question(int postId,String post_title,String post_body,String post_tag,int post_comment_count,
			int parentId,int post_answer_count,int accepted_answerId,String focus,String system,String language)
	{
		super(postId, post_title, post_body, post_tag, post_comment_count, 
				parentId, post_answer_count, accepted_answerId,focus,system,language);
	}	
}
