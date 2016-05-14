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
public interface ITeam extends IPlayerComponent
{
    public String getDriverId1(String strRaceId);

    public String getDriverId2(String strRaceId);

    public void setReplacements(Map replacements);

}
