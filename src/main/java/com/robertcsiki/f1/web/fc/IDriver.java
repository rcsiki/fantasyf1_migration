/*
 * Created on Feb 4, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.robertcsiki.f1.web.fc;

import java.util.Map;


/**
 * @author mrcsiki
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface IDriver extends IPlayerComponent
{
    public String getTeamId(String strRaceId) throws Exception;
		
	public boolean hasFastestLap (String raceId) throws Exception;
	
	public void setReplacements (Map replacements);
	public String getNameByRace(String strRaceId);
	
	public void updateTotalRealPoints(IRaceResult raceResult); 
	public int getTotalRealPoints(); 
	
}
