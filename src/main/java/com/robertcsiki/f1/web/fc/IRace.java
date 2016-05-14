/*
 * Created on Feb 4, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.robertcsiki.f1.web.fc;

import java.util.Date;

/**
 * @author mrcsiki
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface IRace extends IObjectBase
{
	public void setDate(Date date);
	public Date getDate();
		
	public void setUrl(String strUrl);
	public String getUrl();

	public IRaceResult getRaceResult() throws Exception;
}
