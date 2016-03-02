/*
 * Created on Feb 5, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.homelinux.bela.web.impl;

import com.homelinux.bela.web.fc.ITeam;
import com.homelinux.bela.web.manager.CompetitionManager;

/**
 * @author mrcsiki
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Team extends PlayerComponent implements ITeam
{

    /**
     * Default C-tor
     */
	public Team ()
	{
	}

	/**
	 * 
	 */
	public Team(String id, String name, String description, int cost)
	{
		super(id, name, description, cost);
	}
	
	public String getDriverId1()
	{
		return m_strDriverId1;
	}
	
	public void setDriverId2 (String strDriverId)
	{
		m_strDriverId2 = strDriverId;
	}
	public String getDriverId2()
	{
		return m_strDriverId2;
	}
	
	public void setDriverId1 (String strDriverId)
	{
		m_strDriverId1 = strDriverId;
	}
	
	public String getRaceDetails(String strRaceId)
	   throws Exception
	{
		StringBuffer sb = new StringBuffer();
		CompetitionManager cm = CompetitionManager.getInstance();
		sb.append("Race details for team: "+this.getName());
		sb.append("\\n-----------------------------------------\\n");
		sb.append("\\nFirst driver: " + cm.getDriverById(this.getDriverId1()).getNameByRace(strRaceId));
		sb.append("\\n");
		sb.append(((Driver)cm.getDriverById(this.getDriverId1())).getDriverRacePositionSummary(strRaceId));
		sb.append("\\n\\nSecond driver: " + cm.getDriverById(this.getDriverId2()).getNameByRace(strRaceId));
		sb.append("\\n");
		sb.append(((Driver)cm.getDriverById(this.getDriverId2())).getDriverRacePositionSummary(strRaceId));
		sb.append("\\n-----------------------------------------\\n");
		return sb.toString();
	}

	
	private String m_strDriverId1 = null;
	private String m_strDriverId2 = null;
}
