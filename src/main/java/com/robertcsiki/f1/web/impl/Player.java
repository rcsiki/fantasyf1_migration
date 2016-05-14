/*
 * Created on Feb 7, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.robertcsiki.f1.web.impl;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.robertcsiki.f1.web.fc.IDriver;
import com.robertcsiki.f1.web.fc.IPlayer;
import com.robertcsiki.f1.web.fc.IPlayerComponent;
import com.robertcsiki.f1.web.fc.IRace;
import com.robertcsiki.f1.web.fc.IRule;
import com.robertcsiki.f1.web.manager.CompetitionManager;
import com.robertcsiki.f1.web.util.StringUtil;

/**
 * @author mrcsiki
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Player extends ObjectBase implements IPlayer
{
	/**
	 * @param id
	 * @param name
	 * @param description
	 */
	public Player(String id, String name, String description)
	{
		super(id, name, description);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see com.robertcsiki.f1.web.fc.IPlayer#setDriverId1(java.lang.String)
	 */
	public void setDriverId1(String strDriverId)
	{
		m_strDriverId1  = strDriverId;
	}
	
	/* (non-Javadoc)
	 * @see com.robertcsiki.f1.web.fc.IPlayer#setDriverId2(java.lang.String)
	 */
	public void setDriverId2(String strDriverId)
	{
		m_strDriverId2 = strDriverId;
	}
	
	/* (non-Javadoc)
	 * @see com.robertcsiki.f1.web.fc.IPlayer#setTeamId1(java.lang.String)
	 */
	public void setTeamId1(String strTeamId)
	{
		m_strTeamId1 = strTeamId;
	}
	
	/* (non-Javadoc)
	 * @see com.robertcsiki.f1.web.fc.IPlayer#setTeamId2(java.lang.String)
	 */
	public void setTeamId2(String strTeamId)
	{
		m_strTeamId2 = strTeamId;
	}
	
	public String getDriverId1 ()
	{
		return m_strDriverId1;
	}
	
	public String getDriverId2 ()
	{
		return m_strDriverId2;
	}
	
	public String getTeamId1 ()
	{
		return m_strTeamId1;
	}
	
	public String getTeamId2 ()
	{
		return m_strTeamId2;
	}
	
	
	public void setEmailAddress (String strEmailAddress)
	{
		m_strEmailAddress = strEmailAddress; 
	}
	
	public String getEmailAddress ()
	{
		return m_strEmailAddress;
	}
	
	public void setTieBreakerAnswer(int iTieBreakerAnswer)
	{
		m_iTieBreakerAnswer = iTieBreakerAnswer;
	}
	
	public int getTieBreakerAnswer()
	{
		return m_iTieBreakerAnswer;
	}

	public void setPaymentReceived(boolean bPaymentReceived)
	{
		m_bPaymentReceived = bPaymentReceived; 
	}
	
	public boolean isPaymentReceived()
	{
		return m_bPaymentReceived;
	}

	/* (non-Javadoc)
	 * @see com.robertcsiki.f1.web.fc.IPlayer#printPlayerDetails(java.io.OutputStream)
	 */
	public void printPlayerDetails(OutputStream out)
	{
	}
	
	/* (non-Javadoc)
	 * @see com.robertcsiki.f1.web.fc.IPlayer#printPlayerStandings(java.io.OutputStream)
	 */
	public void printPlayerStandings(OutputStream out)
	   throws Exception
	{
		StringBuffer sb = new StringBuffer();
		sb.append(this.getName());
		sb.append(": ");
		sb.append(StringUtil.formatFloat(this.getGrandTotalPoints()));
		String[] completedRacesIds = CompetitionManager.getInstance().getCompletedRacesIds();
		for (int i = 0 ; i < completedRacesIds.length ; i++)
		{
			sb.append("   ");
			sb.append(StringUtil.formatFloat(this.getRacePoints(completedRacesIds[i])));
		}
		out.write(sb.toString().getBytes());
	}
	
	/* (non-Javadoc)
	 * @see com.robertcsiki.f1.web.fc.IPlayer#printPlayerRaceDetails(java.lang.String, java.io.OutputStream)
	 */
	public String printPlayerRaceDetails(String strRaceId)
	   throws Exception
	{
		CompetitionManager cm = CompetitionManager.getInstance();
		IRace race = cm.getRaceById(strRaceId);
		StringBuffer sb = new StringBuffer();
		sb.append("<table width='100%' height='100%' cellspacing='10' border='0'>");
		sb.append("<tr>");
		sb.append("<td align='center' valign='top'>");
		
		sb.append("<table border='0'>");
		
		// page title
		sb.append("<tr height='30'>");
		sb.append("<td  class=\"tablelayoutheader\" align='center' valign='top'>");
		sb.append("Player Race Details");
		sb.append("</td>");
		sb.append("</tr>");
		
		// some driver and race info
		sb.append("<tr>");
		sb.append("<td align='left' valign='top'>");
		sb.append("<table border='0'>");
		sb.append("<tr><td>");
		sb.append("<table border='0'>");
		sb.append("<tr><td class='tablelayoutnobold'>Race:");
		sb.append("</td></tr><tr><td class='tablelayoutnobold'>Team Name:");
		sb.append("</td></tr><tr><td class='tablelayoutnobold'>Team Manager:");
		sb.append("</td></tr><tr><td class='tablelayoutnobold'>Points awarded for this race:");
		sb.append("</td></tr></table>");
		sb.append("</td><td>");
		sb.append("<table border='0'>");
		sb.append("<tr><td class='tablelayout'>");
		sb.append("<a href='#' title='Click to see the official race result' ");
		sb.append("onClick=\"window.open('officialRaceResults.jsp?raceId=");
		sb.append(race.getId());                         
		sb.append("&windowTitle=");                         
		sb.append(race.getDescription());                         
		sb.append("', 'officialResultWindow','menubar=no,personalbar=no,resizable=yes,toolbar=no,scroolbars=yes,width=450,height=600,screenX=300,screenY=150');\">");
		sb.append(race.getName());
		sb.append("</a></td></tr>");		
		sb.append("<tr><td class='tablelayout'>"+this.getName()+"</td></tr>");
		sb.append("<tr><td class='tablelayout'>"+this.getDescription()+"</td></tr>");
		sb.append("<tr><td class='tablelayout'>"+StringUtil.formatFloat(this.getRacePoints(strRaceId))+"</td></tr>");
		sb.append("</table>");		
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</table>");		
		sb.append("</td>");
		sb.append("</tr>");
		 		
		// Points breakdown table by Player Component and Rule
		sb.append("<tr>");
		sb.append("<td align='left' valign='top'>");
		sb.append("<table border='0' cellspacing='2' cellpadding='2'>");
		sb.append("<caption  class='tablelayoutheader'>Points Details per Player Component and Rule</caption>");
		
		IPlayerComponent pc = null;
		String[] strRulesIds = cm.getRulesIds();
		int iRows = 0;
		boolean wasDriver1Replaced = false;
		boolean wasDriver2Replaced = false;
		while (true)
		{
            if (iRows == 6) break; // the table is completed
			// altarnating the bg color
			if (iRows > 0 && iRows % 2 == 0)
			{
			   sb.append("<tr class='standingsRowBackgroundColor'>");
			}
			else if (iRows > 0 && iRows % 2 != 0)
			{
				sb.append("<tr class='standingsRowAltBackgroundColor'>");
			}
			else
			{
				sb.append("<tr>");
			}
            for (int i = -1 ; i <= strRulesIds.length ; i++)
            {						
                if (iRows == 0 || iRows == 5)
                {
					sb.append("<td class='tablelayoutheader' align='center' valign='center'>");
                }
                else
                {
					if (i == -1 || i == strRulesIds.length)
					{
						sb.append("<td class='tablelayout' align='center' valign='center'>");
					}
					else
					{
						sb.append("<td class='tablelayoutnobold' align='center' valign='center'>");
					}
                }
                switch (iRows)
                {
                    case 0:
                    				 
                        if (i == -1)
                        {
                            sb.append("Component \\ Rule");
                        }
                        else if (i == strRulesIds.length)
                        {
                            sb.append("TOTAL");
                        }
                        else
                        {   sb.append("<a href='#' title='Click to see the description for this Rule' onClick=\"alert('");
                        	sb.append(((IRule)cm.getRuleById(strRulesIds[i])).getDescription());                         
							sb.append("');\">"); 
                            sb.append(((IRule)cm.getRuleById(strRulesIds[i])).getName());
							sb.append("</a>");
                        }
                        break;
                        
					case 1:
					
                    	pc = cm.getDriverById(this.getDriverId1());			 
						if (i == -1)
						{
							sb.append("<a href='#' title=\"Click to see the Component's performance for this race\" onClick=\"alert('");
							sb.append(pc.getRaceDetails(strRaceId));
							sb.append("');\">");
							String strName = ((IDriver)pc).getNameByRace(strRaceId);
							sb.append(strName);
							if (!strName.equals(pc.getName()))
							{
								sb.append(" *");
								wasDriver1Replaced = true;
							}
							sb.append("</a>");
						}
						else if (i == strRulesIds.length)
						{
							sb.append(StringUtil.formatFloat(pc.getPointsForRace(strRaceId)));
						}
						else
						{
							if(cm.getRuleById(strRulesIds[i]).isApplicableForTeamOnly())
							{
								sb.append("n/a");
							}
							else
							{
							    sb.append(StringUtil.formatFloat(pc.getPointsForRaceAndRule(strRaceId, strRulesIds[i])));
							}
						}
						break;
						
					case 2:
					
                        pc = cm.getDriverById(this.getDriverId2());			 
						if (i == -1)
						{
							sb.append("<a href='#' title=\"Click to see the Component's performance for this race\" onClick=\"alert('");
							sb.append(pc.getRaceDetails(strRaceId));
							sb.append("');\">"); 
							String strName = ((IDriver)pc).getNameByRace(strRaceId);
							sb.append(strName);
							if (!strName.equals(pc.getName()))
							{
								sb.append(wasDriver1Replaced ? " **" : " *");
								wasDriver2Replaced = true;
							}
							sb.append("</a>");
						}
						else if (i == strRulesIds.length)
						{
							sb.append(StringUtil.formatFloat(pc.getPointsForRace(strRaceId)));
						}
						else
						{
							if(cm.getRuleById(strRulesIds[i]).isApplicableForTeamOnly())
							{
								sb.append("n/a");
							}
							else
							{
								sb.append(StringUtil.formatFloat(pc.getPointsForRaceAndRule(strRaceId, strRulesIds[i])));
							}
						}
						break;

					case 3:
					
					    pc = cm.getTeamById(this.getTeamId1());			 
						if (i == -1)
						{
							sb.append("<a href='#' title=\"Click to see the Component's performance for this race\" onClick=\"alert('");
							sb.append(pc.getRaceDetails(strRaceId));
							sb.append("');\">"); 
							sb.append(pc.getName()+"</a>");
						}
						else if (i == strRulesIds.length)
						{
							sb.append(StringUtil.formatFloat(pc.getPointsForRace(strRaceId)));
						}
						else
						{
							if(cm.getRuleById(strRulesIds[i]).isApplicableForDriverOnly())
							{
								sb.append("n/a");
							}
							else
							{
								sb.append(StringUtil.formatFloat(pc.getPointsForRaceAndRule(strRaceId, strRulesIds[i])));
							}
						}
						break;

					case 4:
					
					    pc = cm.getTeamById(this.getTeamId2());                    				
						if (i == -1)
						{
							sb.append("<a href='#' title=\"Click to see the Component's performance for this race\" onClick=\"alert('");
							sb.append(pc.getRaceDetails(strRaceId));
							sb.append("');\">"); 
							sb.append(pc.getName()+"</a>");
						}
						else if (i == strRulesIds.length)
						{
							sb.append(StringUtil.formatFloat(pc.getPointsForRace(strRaceId)));
						}
						else
						{
							if(cm.getRuleById(strRulesIds[i]).isApplicableForDriverOnly())
							{
								sb.append("n/a");
							}
							else
							{
								sb.append(StringUtil.formatFloat(pc.getPointsForRaceAndRule(strRaceId, strRulesIds[i])));
							}
						}
						break;

					case 5:
                    				 
						if (i == -1)
						{
							sb.append("TOTAL");
						}
						else if (i == strRulesIds.length)
						{
							sb.append(StringUtil.formatFloat(getRacePoints(strRaceId)));
						}
						else
						{
							sb.append(StringUtil.formatFloat(getPointsForRaceAndRule(strRaceId, strRulesIds[i])));
						}
						break;
			   }
			   sb.append("</td>");
		   }
		   sb.append("</tr>");
		   iRows++;
		}
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		
		// append some explanations if driver replacements have occured for this Player
		if (wasDriver1Replaced == true || wasDriver2Replaced == true)
		{
			sb.append("<tr>");
			sb.append("<td align='left' valign='top'>");
			sb.append("<table><tr><td align'left' class=\"tablelayoutnobold\">");
			if (wasDriver1Replaced == true)
			{				pc = cm.getDriverById(this.getDriverId1());	
				sb.append("* ");	
				sb.append(((IDriver)pc).getNameByRace(strRaceId));	
				sb.append(" has replaced ");	
				sb.append(pc.getName());	
				sb.append(" on team ");	
                sb.append(cm.getTeamById(((IDriver) pc).getTeamId(strRaceId)).getName());
				sb.append(".</td></tr>");	
			}
			if (wasDriver2Replaced == true)
			{
				pc = cm.getDriverById(this.getDriverId2());	
				sb.append("<tr><td align'left' class=\"tablelayoutnobold\">");
				if (wasDriver1Replaced == true)
				{
					sb.append("** ");	
				}
				else
				{
					sb.append("* ");	
				}
				sb.append(((IDriver)pc).getNameByRace(strRaceId));	
				sb.append(" has replaced ");	
				sb.append(pc.getName());	
				sb.append(" on team ");	
                sb.append(cm.getTeamById(((IDriver) pc).getTeamId(strRaceId)).getName());
				sb.append(".</td></tr>");	
			}

			sb.append("</table>");	
			sb.append("</td>");
			sb.append("</tr>");
		}
		
		sb.append("<tr>");
		sb.append("<td align='left' valign='top'>");
		sb.append("<table><tr><td align'left' class=\"tablelayoutnobold\">");
		sb.append("<p><i><b>Notes:</b></i><br><i>&nbsp;&nbsp;&nbsp; - Drivers points are awarded in full.</i><br>");
		sb.append("<i>&nbsp;&nbsp;&nbsp; - Cars points are awarded as an <b>average</b> of the points scored by the constructor's 2 drivers in the race.</i>");
		sb.append("</td></tr>");	
		sb.append("</table>");	
		sb.append("</td>");
		sb.append("</tr>");
		
        // the Back Button
		sb.append("<tr>");
		sb.append("<td align='right' valign='top'>");
		sb.append("<br><table><tr><td align'right' class=\"tablelayout\"><a href='JavaScript:window.history.go(-2)'>Return</a></td></tr></table>");
		sb.append("</td>");
		sb.append("</tr>");
		
		// we are all done, close the main datacell
		sb.append("</td></tr></table>");	
		
		return sb.toString();		
	}
	
	public float getRacePoints(String strRaceId)
	   throws Exception
	{
		CompetitionManager cm = CompetitionManager.getInstance();
		float fRacePoints = 0;
		IPlayerComponent pc = cm.getDriverById( getDriverId1() );
		fRacePoints += pc.getPointsForRace(strRaceId);
		pc = cm.getDriverById( getDriverId2() );
		fRacePoints += pc.getPointsForRace(strRaceId);
		pc = cm.getTeamById( getTeamId1() );
		fRacePoints += pc.getPointsForRace(strRaceId);
		pc = cm.getTeamById( getTeamId2() );
		fRacePoints += pc.getPointsForRace(strRaceId);
		
		return fRacePoints;				
	}
	
	private float getPointsForRaceAndRule(String strRaceId, String strRuleId)
 	   throws Exception
	
	{
		CompetitionManager cm = CompetitionManager.getInstance();
		float fRacePointsForRule = 0;
		IPlayerComponent pc = null;
		
		if (!cm.getRuleById(strRuleId).isApplicableForTeamOnly())
		{
		    pc = cm.getDriverById( getDriverId1() );
		    fRacePointsForRule += pc.getPointsForRaceAndRule(strRaceId, strRuleId);
		    pc = cm.getDriverById( getDriverId2() );
		    fRacePointsForRule += pc.getPointsForRaceAndRule(strRaceId, strRuleId);
		}
		
		if (!cm.getRuleById(strRuleId).isApplicableForDriverOnly())
		{
		    pc = cm.getTeamById( getTeamId1() );
		    fRacePointsForRule += pc.getPointsForRaceAndRule(strRaceId, strRuleId);
		    pc = cm.getTeamById( getTeamId2() );
		    fRacePointsForRule += pc.getPointsForRaceAndRule(strRaceId, strRuleId);
		}
		return fRacePointsForRule;				
	}
	
	public float getGrandTotalPoints()
	   throws Exception
	{
		CompetitionManager cm = CompetitionManager.getInstance();
		float fGrandTotalPoints = 0;
		IPlayerComponent pc = cm.getDriverById( getDriverId1() );
		fGrandTotalPoints += pc.getGrandTotalPoints();
		pc = cm.getDriverById( getDriverId2() );
		fGrandTotalPoints += pc.getGrandTotalPoints();
		pc = cm.getTeamById( getTeamId1() );
		fGrandTotalPoints += pc.getGrandTotalPoints();
		pc = cm.getTeamById( getTeamId2() );
		fGrandTotalPoints += pc.getGrandTotalPoints();
		
		return fGrandTotalPoints;				
	}
	
	public float getGrandTotalPointsLessRace(IRace race) throws Exception
	{
		CompetitionManager cm = CompetitionManager.getInstance();
		float fGrandTotalPointsLessRace = 0;
		IPlayerComponent pc = cm.getDriverById( getDriverId1() );
		fGrandTotalPointsLessRace += pc.getGrandTotalPointsLessRace(race);
		pc = cm.getDriverById( getDriverId2() );
		fGrandTotalPointsLessRace += pc.getGrandTotalPointsLessRace(race);
		pc = cm.getTeamById( getTeamId1() );
		fGrandTotalPointsLessRace += pc.getGrandTotalPointsLessRace(race);
		pc = cm.getTeamById( getTeamId2() );
		fGrandTotalPointsLessRace += pc.getGrandTotalPointsLessRace(race);
		
		return fGrandTotalPointsLessRace;				
	}

	
	public int getCost()
		throws Exception
	 {
		 CompetitionManager cm = CompetitionManager.getInstance();
		 int iCost = 0;
		 IPlayerComponent pc = cm.getDriverById( getDriverId1() );
		 iCost += pc.getCost();
		 pc = cm.getDriverById( getDriverId2() );
		 iCost += pc.getCost();
		 pc = cm.getTeamById( getTeamId1() );
		 iCost += pc.getCost();
		 pc = cm.getTeamById( getTeamId2() );
		 iCost += pc.getCost();
		
		 return iCost;				
	 }
	 
	public void addWinner(IRace race)
	{
		m_winners.add(race);
	}
	
	public List getWinners()
	{
		return m_winners;
	}
	
	public void resetWinners()
	{
		m_winners.clear();
	}

	public boolean equals(Object that)
	 {
	 	return (that != null) && (that instanceof Player) &&
	 	((Player)that).getDriverId1().equals(getDriverId1()) &&
		((Player)that).getDriverId2().equals(getDriverId2()) &&
		((Player)that).getTeamId1().equals(getTeamId1()) &&
		((Player)that).getTeamId2().equals(getTeamId2()) ;
	 }
	 
	 public int hashCode()
	 {
	 	return (int)((getDriverId1().hashCode() + getDriverId2().hashCode() + getTeamId1().hashCode() + getTeamId2().hashCode())/4);
	 }
	
	
	private String m_strDriverId1 = null;
	private String m_strDriverId2 = null;
	private String m_strTeamId1 = null;
	private String m_strTeamId2 = null;
	private String m_strEmailAddress = null;
	private int m_iTieBreakerAnswer = 0;
	private boolean m_bPaymentReceived = false;
	private List m_winners = new ArrayList();;
	
}
