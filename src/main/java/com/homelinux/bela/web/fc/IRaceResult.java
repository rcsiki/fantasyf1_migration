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
public interface IRaceResult
{	
	public void setRaceId (String strRaceId);
	
	public String getRaceId();
	
	public void setFastestLapDriverId (String strFatestLapDriverId);
	
	public String getFastestLapDriverId();	
	
	public boolean hasFastestLap (String strDriverId);

	public void addRacePositionDetail (IRacePositionDetail racePosition);
	
	public IRacePositionDetail getRacePositionDetailByDriverId (String strDriverId);
	
	public String printOfficialResults(boolean generateCloseBttn);
	
	public int getRealPointsForDriver(String strDriverId);
	
}
