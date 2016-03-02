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
public interface IRule extends IObjectBase
{
	public void setIsApplicable(String applicable);
	
	public boolean isApplicableForDriverOnly();
	
	public boolean isApplicableForTeamOnly();
	
	public boolean isApplicableForDriverAndTeam();
	
	public void addRuleCase(IRuleCase ruleCase);
		
	public void apply(String strRaceId, IPlayerComponent playerComponent) throws Exception;
	
	
}
