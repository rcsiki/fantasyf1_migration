/*
 * Created on Feb 5, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.homelinux.bela.web.impl;

import com.homelinux.bela.web.fc.IObjectBase;

/**
 * @author mrcsiki
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class ObjectBase implements IObjectBase
{
    /**
     * Default C-tor 
     */
    public ObjectBase()
    {
    }
    
	/**
	 * C-tor
	 */
	public ObjectBase (String id, String name, String description)
	{
		m_strId = id;
		m_strName = name;
		m_strDescription = description;
	}
	
	/* (non-Javadoc)
	 * @see com.homelinux.bela.web.interfaces.IObjectBase#setId(java.lang.String)
	 */
	public void setId(String id)
	{
		m_strId = id;
	}
	
	/* (non-Javadoc)
	 * @see com.homelinux.bela.web.interfaces.IObjectBase#getId()
	 */
	public String getId()
	{
		return m_strId;
	}
	
	/* (non-Javadoc)
	 * @see com.homelinux.bela.web.interfaces.IObjectBase#setName(java.lang.String)
	 */
	public void setName(String name)
	{
		m_strName = name;
	}
	
	/* (non-Javadoc)
	 * @see com.homelinux.bela.web.interfaces.IObjectBase#getName()
	 */
	public String getName()
	{
		return m_strName;
	}
	
	/* (non-Javadoc)
	 * @see com.homelinux.bela.web.interfaces.IObjectBase#setDescription(java.lang.String)
	 */
	public void setDescription(String description)
	{
		m_strDescription = description;
	}
	
	/* (non-Javadoc)
	 * @see com.homelinux.bela.web.interfaces.IObjectBase#getDescription()
	 */
	public String getDescription()
	{
		return m_strDescription;
	}
		
	private String m_strName;
	private String m_strDescription;
	private String m_strId;
}
