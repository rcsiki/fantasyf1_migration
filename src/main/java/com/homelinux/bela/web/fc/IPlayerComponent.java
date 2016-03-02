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
public interface IPlayerComponent extends IObjectBase
{
	public void setCost(int cost);
	public int getCost();
	
	public void addPointsForRaceAndRule (String strRaceId, String strRuleId, float fPoints);
	
	public float getPointsForRaceAndRule (String strRaceId, String strRuleId);
	
	public float getPointsForRace (String strRaceId);
	
	public float getGrandTotalPoints();
	
	public float getGrandTotalPointsLessRace(IRace race);
	
	public String getRaceDetails(String strRaceId) throws Exception;
	
	public void addPick();
	
	public int getPicks();
	
}
