/*
 * Created on Feb 5, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.robertcsiki.f1.web.impl;

import com.robertcsiki.f1.web.fc.ITeam;
import com.robertcsiki.f1.web.manager.CompetitionManager;

import java.util.Map;
import java.util.TreeMap;

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
    public Team(String id, String name, String description, int cost, String strDriverId1,
            String strDriverId2)
	{
		super(id, name, description, cost);
        m_strDriverId1 = strDriverId1;
        m_strDriverId2 = strDriverId2;
	}
	
    @Override
    public String getDriverId1(String strRaceId) {
        return getDriverId(strRaceId, true);
    }

    @Override
    public String getDriverId2(String strRaceId) {
        return getDriverId(strRaceId, false);
    }

    private String getDriverId(String strRaceId, boolean isFirstDriver) {
        if (m_replacements == null || strRaceId == null || m_replacements.get(strRaceId) == null)
        {
            return isFirstDriver ? m_strDriverId1 : m_strDriverId2;
        }
        else
        {
            String[] replacements = ((String) m_replacements.get(strRaceId)).split(",");
            return isFirstDriver ? replacements[0] : replacements[1];
        }
    }

    public void setReplacements(Map replacements) {
        m_replacements = replacements;
    }
	
	
	public String getRaceDetails(String strRaceId)
	   throws Exception
	{
		StringBuffer sb = new StringBuffer();
		CompetitionManager cm = CompetitionManager.getInstance();
		sb.append("Race details for team: "+this.getName());
		sb.append("\\n-----------------------------------------\\n");
        sb.append("\\nFirst driver: "
                + cm.getDriverById(this.getDriverId1(strRaceId)).getNameByRace(strRaceId));
		sb.append("\\n");
        sb.append(((Driver) cm.getDriverById(this.getDriverId1(strRaceId)))
                .getDriverRacePositionSummary(strRaceId));
        sb.append("\\n\\nSecond driver: "
                + cm.getDriverById(this.getDriverId2(strRaceId)).getNameByRace(strRaceId));
		sb.append("\\n");
        sb.append(((Driver) cm.getDriverById(this.getDriverId2(strRaceId)))
                .getDriverRacePositionSummary(strRaceId));
		sb.append("\\n-----------------------------------------\\n");
		return sb.toString();
	}

	
	private String m_strDriverId1 = null;
	private String m_strDriverId2 = null;
    private Map m_replacements = new TreeMap();

}
