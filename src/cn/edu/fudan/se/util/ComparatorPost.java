package cn.edu.fudan.se.util;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import com.stackoverflow.bean.Post;

 public class ComparatorPost implements Comparator{


@Override
public int compare(Object arg0, Object arg1) {
	// TODO Auto-generated method stub
	   Post post0=(Post)arg0;
	   Post post1=(Post)arg1;

	    //���ȱȽ����䣬���������ͬ����Ƚ�����

	   if(post0.grade > post1.grade)
		   return 1;
	return 0;
}
  
 }
