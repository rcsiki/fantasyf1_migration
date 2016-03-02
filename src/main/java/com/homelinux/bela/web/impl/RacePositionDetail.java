/*
 * Created on Feb 7, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.homelinux.bela.web.impl;

import java.util.Comparator;

import com.homelinux.bela.web.fc.IConstants;
import com.homelinux.bela.web.fc.IRacePositionDetail;

/**
 * @author mrcsiki
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class RacePositionDetail implements IRacePositionDetail, Comparator
{
	
	public void setDriverId(String strDriverId)
	{
		m_strDriverId =  strDriverId;
	}

	public String getDriverId()
	{
		return m_strDriverId;
	}

	public void setRacePosition(String strRacePosition)
	{
		m_strRacePosition = strRacePosition;
	}

	public String getRacePosition()
	{
		return m_strRacePosition;
	}

	public void setLapsCompleted(int iLapsCompleted)
	{
		m_iLapsCompleted = iLapsCompleted;
	}

	public int getLapsCompleted()
	{
		return m_iLapsCompleted;
	}

	public void setGridPosition(int iGridPosition)
	{
		m_iGridPosition = iGridPosition;
	}

	public int getGridPosition()
	{
		return m_iGridPosition;
	}
		
	/**
	 * implements Comparator's method
	 */
	public int compare(Object o1, Object o2)
	{
		RacePositionDetail rpd1 = (RacePositionDetail)o1;
		RacePositionDetail rpd2 = (RacePositionDetail)o2;
		
		if ( (rpd1.getRacePosition().equals(IConstants.DSQ_KEYWORD_RULE)) )
		{
			// first was disqualified
			return 1;
		}
		else if ( (rpd2.getRacePosition().equals(IConstants.DSQ_KEYWORD_RULE)) )
		{
			// second was disqualified
			return -1;
		}
		else if ( (!rpd1.getRacePosition().equals(IConstants.RET_KEYWORD_RULE)) &&
			 (!rpd1.getRacePosition().equals(IConstants.DNS_KEYWORD_RULE)) )
		{
			if ( (!rpd2.getRacePosition().equals(IConstants.RET_KEYWORD_RULE)) &&
				 (!rpd2.getRacePosition().equals(IConstants.DNS_KEYWORD_RULE)) )
			{
				// both finished the race
				return (new Integer(rpd1.getRacePosition()).compareTo(new Integer(rpd2.getRacePosition())));
			}
			else
			{
				// only the first did finish
				return -1;
			}
		}
		else
		{
			if ( (!rpd2.getRacePosition().equals(IConstants.RET_KEYWORD_RULE)) &&
				 (!rpd2.getRacePosition().equals(IConstants.DNS_KEYWORD_RULE)) )
			{
				// only the second did finish
				return 1;			
			}
			else
			{
				// both retired or dns
				if (rpd1.getRacePosition().equals(IConstants.DNS_KEYWORD_RULE))
				{
					if (!rpd2.getRacePosition().equals(IConstants.DNS_KEYWORD_RULE))
					{
						// only the second did start the race
						return 1;
					}
					else
					{
						// both dns - it doesn't really matter the order
						// let's pick a value
						return 1;
					}						
				}
				else
				{
					if (rpd2.getRacePosition().equals(IConstants.DNS_KEYWORD_RULE))
					{
						// only the first did start the race
						return -1;
					}
					else
					{
						// both retired - who has more completed laps?
						int iResult = (new Integer(rpd1.getLapsCompleted()).compareTo(new Integer(rpd2.getLapsCompleted())));
						if (iResult == 0)
						{ 
							// if the number the lap completed is the same,
							// then the grid position will make the difference
							return (new Integer(rpd1.getGridPosition()).compareTo(new Integer(rpd2.getGridPosition())));
						}
						else
						{
							return (-1)*iResult;
						}
					}						
				}
			}
		}
	}
	
	private String m_strDriverId = null;
	private String m_strRacePosition = null;
	private int m_iLapsCompleted;
	private int m_iGridPosition;
	

}
