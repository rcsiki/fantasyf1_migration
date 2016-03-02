/*
 * Created on Feb 7, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.homelinux.bela.web.impl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.homelinux.bela.web.fc.IRace;
import com.homelinux.bela.web.fc.IRacePositionDetail;
import com.homelinux.bela.web.fc.IRaceResult;
import com.homelinux.bela.web.manager.CompetitionManager;

/**
 * @author mrcsiki
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class RaceResult implements IRaceResult
{
	public void setRaceId(String strRaceId)
	{
		m_strRaceId = strRaceId;
	}

	public String getRaceId()
	{
		return m_strRaceId;
	}

	public void setFastestLapDriverId(String strFatestLapDriverId)
	{
		m_strFastestLapDriverId = strFatestLapDriverId;
	}

	public String getFastestLapDriverId()
	{
		return m_strFastestLapDriverId;
	}

	public boolean hasFastestLap(String strDriverId)
	{
		return (m_strFastestLapDriverId != null && m_strFastestLapDriverId.equals(strDriverId));
	}

	public void addRacePositionDetail(IRacePositionDetail racePosition)
	{
		m_hashRacePositionDetails.put(racePosition.getDriverId(), racePosition);
	}

	public IRacePositionDetail getRacePositionDetailByDriverId(String strDriverId)
	{
		return (IRacePositionDetail) m_hashRacePositionDetails.get( strDriverId);
	}
	
	public String printOfficialResults (boolean generateCloseBttn)
	{
		CompetitionManager cm = null;
		try
		{
			cm = CompetitionManager.getInstance();
		}
		catch (Exception x){}
		
		IRace race = cm.getRaceById(this.getRaceId());
		StringBuffer sb = new StringBuffer();
		sb.append("<table border='0'><tr>");
		sb.append("<td align='center' valign='top'><table border='0'><tr>");
		sb.append("<td class=\"tablelayoutheader\" align='center' valign='top'>");
		sb.append("<table cellspacing='3' cellpading='3' border='0'>");
		sb.append("<caption class=\"tablelayoutheader\">");
		sb.append((new SimpleDateFormat("MMM/dd/yyyy")).format(race.getDate()));
		sb.append(", ");
		sb.append(race.getName());
		sb.append(" - Official results");
		sb.append("</caption>");
		sb.append("<tr><td  class=\"tablelayoutheader\">Pos" +			"</td><td class=\"tablelayoutheader\">Driver</td><td class=\"tablelayoutheader\">Team</td>");
		sb.append("<td class=\"tablelayoutheader\">Laps</td><td class=\"tablelayoutheader\">Grid" +			"</td><td class=\"tablelayoutheader\">Points</td></tr>");
		IRacePositionDetail raceResultDetail = null;
		List racePositionDetailsList = Arrays.asList(m_hashRacePositionDetails.values().toArray());		
		Collections.sort(racePositionDetailsList, new RacePositionDetail());
		int iPosCount = 0;
		for (Iterator iter = racePositionDetailsList.iterator() ; iter.hasNext() ; )
		{
			raceResultDetail = (IRacePositionDetail)iter.next();
			// alternating the bg color
			if (iPosCount % 2 == 0)
			{
			   sb.append("<tr class='standingsRowBackgroundColor'>");
			}
			else
			{
				sb.append("<tr class='standingsRowAltBackgroundColor'>");
			}
			sb.append("<td class='tablelayout' align='right'>");
			sb.append(raceResultDetail.getRacePosition());
			sb.append("</td>");
			sb.append("<td class='tablelayout' align='left'>&nbsp;&nbsp;&nbsp;");
			sb.append(cm.getDriverById(raceResultDetail.getDriverId()).getNameByRace(this.getRaceId()));
			sb.append("</td>");
			sb.append("<td class='tablelayout' align='left'>");
			sb.append(cm.getTeamById(cm.getDriverById(raceResultDetail.getDriverId()).getTeamId()).getName());
			sb.append("</td>");
			sb.append("<td class='tablelayout' align='right'>");
			sb.append(raceResultDetail.getLapsCompleted());
			sb.append("</td>");
			sb.append("<td class='tablelayout' align='right'>");
			sb.append(raceResultDetail.getGridPosition());
			sb.append("</td>");
			sb.append("<td class='tablelayout' align='right'>");
			sb.append(getDriverRealPointsForPosition(iPosCount+1));
			sb.append("</td></tr>");
			iPosCount ++;			
		}
		sb.append("</table></td><br></tr>");
		sb.append("<tr><td  class='tablelayout' align='left'><br>Fastest lap: ");
		sb.append(cm.getDriverById(getFastestLapDriverId()).getNameByRace(this.getRaceId()));
		sb.append("</td></tr>");
		if (generateCloseBttn)
		{
			sb.append("<tr><td class=\"tablelayout\" align = 'right'><a href='#' onclick='window.close();'>Close</a></td><tr>");
		}
		sb.append("</table></td></tr></table>");
		return sb.toString();
	}
	
	private String getDriverRealPointsForPosition (int iPosition)
	{
		String strPoints = null;
		switch (iPosition)
		{
			case 1:
			   strPoints = "10";
			   break;
			   
			case 2:
			   strPoints = "8";
			   break;
			   
			case 3:
			   strPoints = "6";
			   break;
			   
			case 4:
			   strPoints = "5";
			   break;
			   
			case 5:
			   strPoints = "4";
			   break;
			   
			case 6:
			   strPoints = "3";
			   break;
			   
			case 7:
			   strPoints = "2";
			   break;
			   
			case 8:
			   strPoints = "1";
			   break;
			   
			default:
			strPoints = "";
			  
		}
		return strPoints;
	}

	public int getRealPointsForDriver(String strDriverId)
	{
		int iPoints = 0;
		String strRacePosition = getRacePositionDetailByDriverId(strDriverId).getRacePosition();
		try
		{
			iPoints = Integer.parseInt(getDriverRealPointsForPosition(Integer.parseInt(strRacePosition)));
		}
		catch (NumberFormatException nfe)
		{
			// the driver's position is Retired, Did Not Start or > 8 - no points in this case
		}
		return iPoints;	
	}
	
	private String m_strRaceId = null;
	private String m_strFastestLapDriverId = null;
	private Hashtable m_hashRacePositionDetails = new Hashtable();
}
