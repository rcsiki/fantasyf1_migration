/*
 * Created on Feb 7, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.robertcsiki.f1.web.impl;

import java.util.Date;

import com.robertcsiki.f1.web.fc.IRace;
import com.robertcsiki.f1.web.fc.IRaceResult;
import com.robertcsiki.f1.web.manager.CompetitionManager;

/**
 * @author mrcsiki
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Race extends ObjectBase implements IRace
{
	/**
	 * @param id
	 * @param name
	 * @param description
	 */
	public Race(String id, String name, String description)
	{
		super(id, name, description);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see com.robertcsiki.f1.web.fc.IRace#setDate(java.util.Date)
	 */
	public void setDate(Date date)
	{
		m_Date = date;
	}
	
	/* (non-Javadoc)
	 * @see com.robertcsiki.f1.web.fc.IRace#getDate()
	 */
	public Date getDate()
	{
		return m_Date;
	}
	
	public void setUrl(String strUrl)
	{
		m_strUrl = strUrl;
	}
	
	public String getUrl()
	{
		return m_strUrl;
	}
	
	/* (non-Javadoc)
	 * @see com.robertcsiki.f1.web.fc.IRace#getResult()
	 */
	public IRaceResult getRaceResult()
	   throws Exception

	{
		return CompetitionManager.getInstance().getRaceResultById(getId());
	}
	
	
	private Date m_Date = null;
	private String m_strUrl = null;
}
