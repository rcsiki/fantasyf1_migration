/*
 * Created on Feb 4, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.homelinux.bela.web.fc;

import java.io.OutputStream;
import java.util.List;

/**
 * @author mrcsiki
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface IPlayer extends IObjectBase
{
	
	public void setDriverId1 (String strDriverId);
	public void setDriverId2 (String strDriverId);
	
	public void setTeamId1 (String strTeamId);
	public void setTeamId2 (String strTeamId);
	
	public String getDriverId1 ();
	public String getDriverId2 ();
	
	public String getTeamId1 ();
	public String getTeamId2 ();
	
	public void setTieBreakerAnswer(int iTieBreakerAnswer);
	public int getTieBreakerAnswer();
		
	public void setEmailAddress (String strEmailAddress);
	public String getEmailAddress ();
	
	public void printPlayerDetails(OutputStream out);
	
	public void printPlayerStandings(OutputStream out) throws Exception;

	public String printPlayerRaceDetails(String strRaceId) throws Exception;
	
	public float getGrandTotalPoints() throws Exception;
	
	public float getGrandTotalPointsLessRace(IRace race) throws Exception;

	public float getRacePoints(String strRaceId) throws Exception;
	
	public void setPaymentReceived(boolean bPaymentReceived);
	public boolean isPaymentReceived();
	
	public int getCost() throws Exception;
	
	public void addWinner(IRace race);
	public List getWinners();
	public void resetWinners();

}
