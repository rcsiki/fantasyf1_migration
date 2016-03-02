/*
 * Created on Feb 7, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.homelinux.bela.web.impl;

import java.util.Hashtable;

import com.homelinux.bela.web.fc.IRuleCase;

/**
 * @author mrcsiki
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class RuleCase implements IRuleCase
{
	public void addCondition(String key, String value)
	{
	    m_hashConditions.put(key, value);
	}

	public void setFormula(String formula)
	{
		m_strFormula = formula; 
	}
	
	public Hashtable getConditions()
	{
		return m_hashConditions;
	}
	
	public String getFormula()
	{
		return m_strFormula;
	}
	
	private Hashtable m_hashConditions = new Hashtable();
	private String m_strFormula = null;
}
