package cn.edu.fudan.se.web.action;

import java.util.Hashtable;
import java.util.Iterator;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

//HttpSessionListener接口监听会话的创建，销毁的信息
public class SessionListener implements HttpSessionListener {
  /**
    * 该类实现了HttpSessionListener接口。
    * 该类还有一个属性Hashtable，用来保存所有的登录信息。
    * 当创建一个Session时，就调用 sessionCreate()方法将登录会话保存到Hashtable中;
    * 当销毁一个Session时， 就调用sessionDetoryed()方法将 登录信息从Hashtable中移除
    * 这就就实现了管理在线用户登录的会话信息目的 
   */
  // 集合对象，保存session 对象的引用
  static Hashtable<Object,Object> ht = new Hashtable<Object,Object>();

  // 实现HttpSessionListener接口，完成session创建事件控制
  public void sessionCreated(HttpSessionEvent arg0) {
       HttpSession session = arg0.getSession();
       ht.put(session.getId(), session);
       System.out.println("create session :" + session.getId());
   }

  // 实现HttpSessionListener接口，完成session销毁事件控制
  public void sessionDestroyed(HttpSessionEvent arg0) {

       HttpSession session = arg0.getSession();
       System.out.println("destory session :" + session.getId());
       ht.remove(session.getId());
   }

  // 返回全部session对象集合
  static public Iterator<Object> getSet() {
      return ht.values().iterator();
   }

  // 依据session id返回指定的session对象
  static public HttpSession getSession(String sessionId) {
      return (HttpSession) ht.get(sessionId);
   }
  
}