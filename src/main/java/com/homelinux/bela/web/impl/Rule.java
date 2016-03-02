/*
 * Created on Feb 7, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.homelinux.bela.web.impl;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.homelinux.bela.web.fc.IConstants;
import com.homelinux.bela.web.fc.IDriver;
import com.homelinux.bela.web.fc.IPlayerComponent;
import com.homelinux.bela.web.fc.IRacePositionDetail;
import com.homelinux.bela.web.fc.IRaceResult;
import com.homelinux.bela.web.fc.IRule;
import com.homelinux.bela.web.fc.IRuleCase;
import com.homelinux.bela.web.manager.CompetitionManager;
import com.homelinux.bela.web.util.ArithmeticExpressionEvaluator;
import com.homelinux.bela.web.util.StringUtil;

/**
 * @author mrcsiki
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Rule extends ObjectBase implements IRule
{
	/**
	 * @param id
	 * @param name
	 * @param description
	 */
	public Rule(String id, String name, String description, String strIsApplicable)
	{
		super(id, name, description);
		m_strIsApplicable = strIsApplicable;
	}

	public void setIsApplicable(String strIsApplicable)
	{
		m_strIsApplicable = strIsApplicable;
	}

	public boolean isApplicableForDriverOnly()
	{
		return ( m_strIsApplicable != null &&
		         m_strIsApplicable.equals(IConstants.DRIVER_RULE) );
	}
	
	public boolean isApplicableForTeamOnly()
	{
		return ( m_strIsApplicable != null &&
				 m_strIsApplicable.equals(IConstants.TEAM_RULE) );
	}
	
	public boolean isApplicableForDriverAndTeam()
	{
		return ( m_strIsApplicable != null &&
				 m_strIsApplicable.equals(IConstants.DRIVER_TEAM_RULE) );
	}

	public String isApplicable()
	{
		return m_strIsApplicable;
	}

	public void addRuleCase(IRuleCase ruleCase)
	{
		m_vectRuleCases.add(ruleCase);
	}

	public void apply(String strRaceId, IPlayerComponent playerComponent)
	   throws Exception
	{
		if (playerComponent instanceof Driver)
		{
			applyToDriver (strRaceId, (Driver)playerComponent);
		}
		else
		{
			// it should be Team
			applyToTeam (strRaceId, (Team)playerComponent);
		}
	}
	
	private void applyToDriver(String strRaceId, Driver driver)
	   throws Exception
	{
		if (isApplicableForTeamOnly())
		{
			return;
		}
		float fPoints = 0;
		for (int i = 0 ; i < m_vectRuleCases.size() ; i++)
		{
			IRuleCase ruleCase = (IRuleCase)m_vectRuleCases.get(i);
			Hashtable hashConditions = ruleCase.getConditions();
			String strFormula = ruleCase.getFormula();
			if (matchConditions(hashConditions, strRaceId, driver))
			{
				fPoints = calculatePoints(strFormula, strRaceId, driver);
				break;
			}			
		}
		driver.addPointsForRaceAndRule(strRaceId, getId(), fPoints);
		
	}
	
	
	private void applyToTeam(String strRaceId, Team team)
	   throws Exception
	{
		if (isApplicableForDriverOnly())
		{
			return;
		}
		
		float fPoints = 0;
		
		// get the points for the 1st driver of the team
		String strDriverId1 = team.getDriverId1();
		IDriver driver = CompetitionManager.getInstance().getDriverById(strDriverId1);
		
		for (int i = 0 ; i < m_vectRuleCases.size() ; i++)
		{
			IRuleCase ruleCase = (IRuleCase)m_vectRuleCases.get(i);
			Hashtable hashConditions = ruleCase.getConditions();
			String strFormula = ruleCase.getFormula();
			if (matchConditions(hashConditions, strRaceId, driver))
			{
				fPoints = calculatePoints(strFormula, strRaceId, driver);
				break;
			}			
		}
		
		// get the points for the 2nd driver of the team
		String strDriverId2 = team.getDriverId2();
		driver = CompetitionManager.getInstance().getDriverById(strDriverId2);
		
		for (int i = 0 ; i < m_vectRuleCases.size() ; i++)
		{
			IRuleCase ruleCase = (IRuleCase)m_vectRuleCases.get(i);
			Hashtable hashConditions = ruleCase.getConditions();
			String strFormula = ruleCase.getFormula();
			if (matchConditions(hashConditions, strRaceId, driver))
			{
				fPoints += calculatePoints(strFormula, strRaceId, driver);
				break;
			}			
		}
		
		// cut it in half (to obtain the average points for the team)
		fPoints = (float)((fPoints)/2f);
		team.addPointsForRaceAndRule(strRaceId, getId(), fPoints);
	}
	
	private boolean matchConditions( Hashtable hashConditions,
	                                 String strRaceId,
	                                 IDriver driver )
	   throws Exception
	{
		if (hashConditions == null || hashConditions.isEmpty())
		{
			// unconditional rule
			return true;
		}
		
		boolean result = true;
		for ( Enumeration enumer = hashConditions.keys() ; enumer.hasMoreElements() ; )
		{
			String strKey = (String)enumer.nextElement();
			String strValue = (String)hashConditions.get(strKey);
			result &= ( (substitute(strKey, strRaceId, driver)).equals(substitute(strValue, strRaceId, driver)));
			if (result == false)
			{
				return false;
			}

		}
		return result;
		
	}
	
	private float calculatePoints( String strFormula,
	                               String strRaceId,
	                               IDriver driver )
	   throws Exception
	{
		
		String strPlayerComponentId = driver.getId();
		IRaceResult raceResult = CompetitionManager.getInstance().getRaceResultById(strRaceId);
		IRacePositionDetail racePositionDetail =
		   raceResult.getRacePositionDetailByDriverId(strPlayerComponentId);
		   
		if (racePositionDetail.getRacePosition().equals(IConstants.DNS_KEYWORD_RULE))
		{
			// the driver did not start this race
			// no points for any rule!
			return 0;
		}
		   
		// search and substitute all the keywords with the correspondent value
		strFormula = StringUtil.replace ( strFormula,
		                                  IConstants.POSITION_KEYWORD_RULE,
		                                  racePositionDetail.getRacePosition());
		
		strFormula = StringUtil.replace ( strFormula,
		                                  IConstants.LAPS_KEYWORD_RULE,
		                                  String.valueOf(racePositionDetail.getLapsCompleted()));
							   
		strFormula = StringUtil.replace ( strFormula,
		                                  IConstants.GRID_KEYWORD_RULE,
		                                  String.valueOf(racePositionDetail.getGridPosition()));
							   
		if ( strFormula.indexOf(IConstants.RET_KEYWORD_RULE) != -1 ||
			 strFormula.indexOf(IConstants.DSQ_KEYWORD_RULE) != -1)
		{
			// that means the player's component didn't finish the race
			// no points for this rule
			return 0;
		}
		
		// evaluate the expression and return the value
		return (float)(new ArithmeticExpressionEvaluator()).eval(strFormula);
	}
	
	
	private String substitute( String strToSubstitute,
	                           String strRaceId,
	                           IDriver driver )
	   throws Exception
	{
		String strPlayerComponentId = driver.getId();
		IRaceResult raceResult = CompetitionManager.getInstance().getRaceResultById(strRaceId);
		IRacePositionDetail racePositionDetail =
		   raceResult.getRacePositionDetailByDriverId(strPlayerComponentId);
		
		if (strToSubstitute.equals(IConstants.POSITION_KEYWORD_RULE))
		{
			return racePositionDetail.getRacePosition();
		}
		else if (strToSubstitute.equals(IConstants.GRID_KEYWORD_RULE))
		{
			return String.valueOf(racePositionDetail.getGridPosition());
		}
		else if (strToSubstitute.equals(IConstants.LAPS_KEYWORD_RULE))
		{
			return String.valueOf(racePositionDetail.getLapsCompleted());
		}
		else if ( strToSubstitute.equals(IConstants.RET_KEYWORD_RULE) ||
				  strToSubstitute.equals(IConstants.DSQ_KEYWORD_RULE) )
		{
			return strToSubstitute;
		}
		else if (strToSubstitute.equals(IConstants.FASTESTLAP_KEYWORD_RULE))
		{
			return (new Boolean(raceResult.hasFastestLap(strPlayerComponentId))).toString();
		}
		
		return strToSubstitute;
		
	}
	
	private String m_strIsApplicable = null;
	private Vector m_vectRuleCases = new Vector(); 
}
