package cn.edu.fudan.se.data.util;

import cn.edu.fudan.se.web.bean.Method;

public interface DataWatcher {

	public void watch(Method newMethod);

	public void watched();

}
