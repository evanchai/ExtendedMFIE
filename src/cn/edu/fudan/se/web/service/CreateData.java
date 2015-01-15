package cn.edu.fudan.se.web.service;

import java.util.ArrayList;
import java.util.List;

import com.stackoverflow.bean.Answer;
import com.stackoverflow.bean.Comment;
import com.stackoverflow.bean.Post;

public class CreateData {
	
	public static List<Post> getData()
	{
		List<Post> postList = new ArrayList<Post>();
		Comment comment = new Comment(1795,55054,"Does this have to be done in SQL Server? It's not something I would associate with a database server, but something done by form validation or even a view.");
		Comment comment2 = new Comment(1795,55054,"Does this have to be done in SQL Server? It's not something I would associate with a database server, but something done by form validation or even a view.");
		Comment comment3 = new Comment(1795,55054,"Does this have to be done in SQL Server? It's not something I would associate with a database server, but something done by form validation or even a view.");
		List <Comment> commentList = new ArrayList<Comment>();
		commentList.add(comment);
		commentList.add(comment2);
		commentList.add(comment3);

		String title="sd";
		String body="<p>I want to use a track-bar to change a form's opacity.</p>&#xA;&#xA;<p>This is my code:</p>&#xA;&#xA;<pre><code>decimal trans = trackBar1.Value / 5000;&#xA;this.Opacity = trans;&#xA;</code></pre>&#xA;&#xA;<p>When I try to build it, I get this error:</p>&#xA;&#xA;<blockquote>&#xA;  <p>Cannot implicitly convert type 'decimal' to 'double'.</p>&#xA;</blockquote>&#xA;&#xA;<p>I tried making <code>trans</code> a <code>double</code>, but then the control doesn't work. This code has worked fine for me in VB.NET in the past. </p>&#xA;";
		String tag="<c#><winforms><forms><type-conversion><opacity>";

		Answer answer1 =new Answer(22222,"",body,"",0,55054,1,0,"","");
		answer1.setCommentList(commentList);
		Answer answer2 =new Answer(22223,"",body,"",0,55054,1,0,"","");
		answer2.setCommentList(commentList);
		Answer answer3 =new Answer(22224,"",body,"",0,55054,1,0,"","");
		answer3.setCommentList(commentList);
		List <Answer> answerList = new ArrayList<Answer>();
		answerList.add(answer1);
		answerList.add(answer2);
		answerList.add(answer3);

		
		Post post = new Post(55054,title,body,tag,3,0,3,22222,"Exception","Java");
		post.setCommentList(commentList);
		post.setAnswerList(answerList);
		
		postList.add(post);
		return postList;
	}

}
