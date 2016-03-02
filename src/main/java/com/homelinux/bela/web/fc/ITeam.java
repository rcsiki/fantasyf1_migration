/*
 * Created on Feb 4, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.homelinux.bela.web.fc;


/**
 * @author mrcsiki
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface ITeam extends IPlayerComponent
{
	public void setDriverId1 (String strDriverId);
	
	public void setDriverId2 (String strDriverId);
	
	public String getDriverId1 ();
	
	public String getDriverId2 ();	
}
