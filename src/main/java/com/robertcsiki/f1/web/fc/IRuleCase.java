/*
 * Created on Feb 7, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.robertcsiki.f1.web.fc;

import java.util.Hashtable;

/**
 * @author mrcsiki
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface IRuleCase
{
	public void addCondition (String key, String value);
	public void setFormula(String formula);
	
	public Hashtable getConditions();
	public String getFormula();
}
