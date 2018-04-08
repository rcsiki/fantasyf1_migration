/*
 * Created on Feb 5, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.robertcsiki.f1.web.impl;

import com.robertcsiki.f1.web.fc.IPlayerComponent;
import com.robertcsiki.f1.web.fc.IRace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author mrcsiki
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class PlayerComponent extends ObjectBase implements IPlayerComponent, Comparator
{
	/**
	 * 
	 */
	public PlayerComponent()
	{
	}
	
	/**
	 * 
	 */
	public PlayerComponent(String id, String name, String description, int cost)
	{
		super(id, name, description);
		m_intCost = cost;
	}
	
	public void setCost(int cost)
	{
		m_intCost = cost;
	}
	
	public int getCost()
	{
		return m_intCost;
	}
	
	public void addPointsForRaceAndRule (String strRaceId, String strRuleId, float fPoints)
	{
		Map racePoints = (Map)m_mapPoints.get(strRaceId);
		if (racePoints != null)
		{
			m_mapPoints.remove(strRaceId);
		}
		else
		{
			racePoints = new TreeMap();
		}
		racePoints.put(strRuleId, new Float(fPoints));
		m_mapPoints.put(strRaceId, racePoints);
	}
	
	public float getPointsForRaceAndRule (String strRaceId, String strRuleId)
	{
		Map racePoints = (Map)m_mapPoints.get(strRaceId);
		return ((Float)racePoints.get(strRuleId)).floatValue();
	}
	
	public float getPointsForRace (String strRaceId)
	{
		float fPoints = 0;
		Map racePoints = (Map)m_mapPoints.get(strRaceId);
		Collection coll = racePoints.values();
		for ( Iterator iter = coll.iterator(); iter.hasNext() ; )
		{
			fPoints += ((Float)iter.next()).floatValue();
		}
		return fPoints;
	}
	
	public float getGrandTotalPoints()
	{
		float fPoints = 0;
		Collection coll = m_mapPoints.keySet();
		for ( Iterator iter = coll.iterator(); iter.hasNext() ; )
		{
			String strKey = (String)iter.next();
			fPoints += getPointsForRace(strKey); 
		}
		return fPoints;
	}
	
	public float getGrandTotalPoints(int raceCount)
	{
		float fPoints = 0;
		Collection coll = m_mapPoints.keySet();
		int rc = 0;
		for ( Iterator iter = coll.iterator(); iter.hasNext() ; )
		{
			String strKey = (String)iter.next();
			fPoints += getPointsForRace(strKey);
			rc ++;
			if (rc == raceCount) {
				break;
			}
		}
		return fPoints;
	}

	public float getGrandTotalPointsLessRace(IRace race)
	{
		float fPoints = 0;
		Collection coll = m_mapPoints.keySet();
		for ( Iterator iter = coll.iterator(); iter.hasNext() ; )
		{
			String strKey = (String)iter.next();
			if (!strKey.equals(race.getId()))
			{
				fPoints += getPointsForRace(strKey); 
			}
		}
		return fPoints;
	}

	public void addPick()
	{
		m_iPicks ++;
	}
	
    public void addPicker(String teamName)
    {
        m_Pickers.add(teamName);
    }

    public String getPickers() {
        StringBuilder sb = new StringBuilder("");
        if (m_Pickers.size() > 0) {
            for (String teamName : m_Pickers) {
                sb.append(teamName).append(", ");
            }
            // shave off the last comma
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }

    public int getPicks()
	{
		return m_iPicks;
	}

	/**
	 * implements Comparator's method
	 */
	public int compare(Object o1, Object o2)
	{
		PlayerComponent pc1 = (PlayerComponent)o1; 
		PlayerComponent pc2 = (PlayerComponent)o2;
		return (-1)*(new Float(pc1.getGrandTotalPoints())).compareTo(new Float(pc2.getGrandTotalPoints()));
	}
		
	private int m_intCost;
	private Map m_mapPoints = new TreeMap();
	private int m_iPicks = 0;
    private List<String> m_Pickers = new ArrayList<String>();
}
