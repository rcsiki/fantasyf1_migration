/*
 * Created on Feb 5, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.homelinux.bela.web.impl;

import java.util.Map;
import java.util.TreeMap;

import com.homelinux.bela.web.fc.IConstants;
import com.homelinux.bela.web.fc.IDriver;
import com.homelinux.bela.web.fc.IRacePositionDetail;
import com.homelinux.bela.web.fc.IRaceResult;
import com.homelinux.bela.web.manager.CompetitionManager;


/**
 * @author mrcsiki
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Driver extends PlayerComponent implements IDriver
{

    /**
     * Default C-tor
     */
    public Driver()
    {
    }
    
	/**
	 * @param id
	 * @param name
	 * @param description
	 * @param cost
	 */
	public Driver(String id, String name, String description, int cost, String strTeam)
	{
		super(id, name, description, cost);
		m_strTeamId = strTeam;
	}
	
	public void setTeamId(String strTeam)
	{
		m_strTeamId = strTeam;
	}
	
	
	public String getTeamId()
	{
		return m_strTeamId;
	}
	
	public boolean hasFastestLap(String strRaceId)
	   throws Exception
	{
		return CompetitionManager.getInstance().
		   getRaceResultById(strRaceId).hasFastestLap(getId());
	}
	
	public void setReplacements (Map replacements)
	{
		m_replacements = replacements;
	}
	
	public String getNameByRace(String strRaceId)
	{
		if (m_replacements == null || m_replacements.get(strRaceId) == null)
		{
			return getName();			
		}
		else
		{
			return (String)m_replacements.get(strRaceId);
		}
	}
	
	public void updateTotalRealPoints(IRaceResult raceResult)
	{		
		int iNewPointsForRace = raceResult.getRealPointsForDriver(this.getId());
		m_iTotalRealPoints += iNewPointsForRace;
	}
	
	public int getTotalRealPoints()
	{
		return m_iTotalRealPoints;
	}

	public String getRaceDetails(String strRaceId)
	 throws Exception
	{
		StringBuffer sb = new StringBuffer();
		CompetitionManager cm = CompetitionManager.getInstance();
		sb.append("Race details for driver: "+this.getNameByRace(strRaceId));
		sb.append("\\n(team : " + cm.getTeamById(this.getTeamId()).getName()+")");
		sb.append("\\n---------------------------------\\n");
		sb.append(getDriverRacePositionSummary(strRaceId));
		sb.append("\\n---------------------------------");
		
		return sb.toString();				
	}
	
	String getDriverRacePositionSummary(String strRaceId)
	   throws Exception
	{
		StringBuffer sb = new StringBuffer();
		CompetitionManager cm = CompetitionManager.getInstance();
		IRacePositionDetail racePositiondetail=
		   cm.getRaceResultById(strRaceId).getRacePositionDetailByDriverId(this.getId());
		   
		if (racePositiondetail.getRacePosition().equals(IConstants.DNS_KEYWORD_RULE))
		{
			sb.append("\\n* Did not start this race.");
		}
		else
		{
		    sb.append("\\n* Position at start:\\t\\t" + racePositiondetail.getGridPosition());
		    sb.append("\\n* Position at finish:\\t\\t" + racePositiondetail.getRacePosition());
		    sb.append("\\n* Laps completed:\\t\\t" + racePositiondetail.getLapsCompleted());
		    sb.append("\\n* Scored the fastest lap:\\t" + cm.getRaceResultById(strRaceId).hasFastestLap(this.getId()));;
		}
		
		return sb.toString();				
	}

		
	private String m_strTeamId = null;
	private Map m_replacements = new TreeMap();
	private int m_iTotalRealPoints = 0; 
}
