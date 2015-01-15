package cn.edu.fudan.se.web.util;

import java.util.Calendar;

public class Timer
{
	
	long startTime, stopTime, spentTime =0, pauseTime = 0;
	String title ;
	
	public Timer(String title)
	{
		this.title = title;
	}

	public void start()
	{
		Calendar startCalendar = Calendar.getInstance();
		startTime = startCalendar.getTimeInMillis();
	}
	
	public void stop()
	{
		Calendar stopCalendar = Calendar.getInstance();
		stopTime = stopCalendar.getTimeInMillis();
		spentTime = stopTime - startTime;		
	}
	
	public void pause()
	{
		Calendar pauseCalendar = Calendar.getInstance();
		long temp = pauseCalendar.getTimeInMillis();
		pauseTime = pauseTime + temp - startTime;
		
	}
	
	public void resume()
	{
		
	}
	
	public void printSpentTime()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(spentTime + pauseTime);
		System.out.println(title + "  spent " + calendar.get(Calendar.MINUTE) + " minutes, " + calendar.get(Calendar.SECOND) + " seconds, " + calendar.get(Calendar.MILLISECOND) + " milliSeconds");
	}
}
