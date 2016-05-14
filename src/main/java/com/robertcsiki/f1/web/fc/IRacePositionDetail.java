/*
 * Created on Feb 7, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.robertcsiki.f1.web.fc;

/**
 * @author mrcsiki
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface IRacePositionDetail
{
   public void setDriverId (String strDriverId);
   
   public String getDriverId ();
   
   public void setRacePosition (String strRacePosition);
   
   public String getRacePosition ();
   
   public void setLapsCompleted (int iLapsCompleted);
   
   public int getLapsCompleted ();
   
   public void setGridPosition (int iGridPosition);
   
   public int getGridPosition ();
   
  
}
