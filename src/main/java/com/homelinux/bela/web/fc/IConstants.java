/*
 * Created on Feb 4, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.homelinux.bela.web.fc;

import java.io.File;

/**
 * @author mrcsiki
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface IConstants
{
	
//*******************************************************************
//
//              XML FILES
//
//*******************************************************************

    public static final String RELATIVE_PATH = "webcomponent" + File.separator + "config" + File.separator;
	public static final String APP_RELATIVE_FILEPATH = RELATIVE_PATH + "app.xml";
	
	public static final String RULES_RELATIVE_FILEPATH = RELATIVE_PATH + "rules.xml";
	public static final String RACES_RELATIVE_FILEPATH = RELATIVE_PATH + "races.xml";
	public static final String RACE_RESULTS_RELATIVE_FILEPATH=RELATIVE_PATH + "races_results.xml";
	public static final String PLAYERS_RELATIVE_FILEPATH = RELATIVE_PATH + "players.xml";
	public static final String TEAMS_RELATIVE_FILEPATH = RELATIVE_PATH + "teams.xml";
	public static final String DRIVERS_RELATIVE_FILEPATH = RELATIVE_PATH + "drivers.xml";
	
    public static final String F1DOTCOM_XSL_RELATIVE_FILEPATH = RELATIVE_PATH
            + "f1dotcom_races_results.xsl";
    public static final String ERGAST_XSL_RELATIVE_FILEPATH = RELATIVE_PATH
            + "ergast_races_results.xsl";
	public static final String TEMP_RELATIVE_FILEPATH = RELATIVE_PATH + "temp.xml";
			
	public static final String DRIVER_RULE = "driver";
	public static final String TEAM_RULE = "team";
	public static final String DRIVER_TEAM_RULE = "both";
	public static final String POSITION_KEYWORD_RULE = "pos";
	public static final String GRID_KEYWORD_RULE = "grid";
	public static final String LAPS_KEYWORD_RULE = "laps";
	public static final String RET_KEYWORD_RULE = "ret";
	public static final String DNS_KEYWORD_RULE = "dns";
	public static final String DSQ_KEYWORD_RULE = "dsq";
	public static final String FASTESTLAP_KEYWORD_RULE = "fastestlap";
	
	// stats commands
	public static final int STATS_ALL_TEAM_STAND = 0;
	public static final int STATS_DRIVERS_PERF = 1;
	public static final int STATS_CARS_PERF = 2;
	public static final int STATS_MOST_PICKED_DRIVER = 3;
	public static final int STATS_MOST_PICKED_CAR = 4;
	public static final int STATS_RACE_STANDINGS = 5;
	public static final int STATS_WINNERS = 6;
	public static final int STATS_TOP_25 = 7;
	
	
//*******************************************************************
//
//			    COMMON XML TAGS
//
//*******************************************************************

    // used everywhere
	public static final String XML_TAG_ID = "id";
	public static final String XML_TAG_NAME = "name";
	public static final String XML_TAG_DESCRIPTION = "description";
	
	// used in drivers, race results
	public static final String XML_TAG_DRIVER = "driver";
	
	// used in drivers, teams
	public static final String XML_TAG_COST = "cost";
	public static final String XML_TAG_TEAM = "team";
	
	// used in races, race results
	public static final String XML_TAG_LAPS = "laps";
	
	// used in playes, teams
	public static final String XML_TAG_DRIVER1 = "driver1";
	public static final String XML_TAG_DRIVER2 = "driver2";
	
//******************************************************************
//
//              DRIVERS XML SPECIFIC TAGS
//
//******************************************************************

	public static final String XML_TAG_DRIVERS = "drivers";
		
//******************************************************************
//
//              PLAYERS XML SPECIFIC TAGS
//
//******************************************************************

	public static final String XML_TAG_PLAYERS = "players";
	public static final String XML_TAG_PLAYER = "player";
	public static final String XML_TAG_PICKS = "picks";
	public static final String XML_TAG_TEAM1 = "team1";
	public static final String XML_TAG_TEAM2 = "team2";
	
//******************************************************************
//
//             RACE RESULTS XML SPECIFIC TAGS
//
//******************************************************************

	public static final String XML_TAG_RESULTS = "results";
	public static final String XML_TAG_POS = "pos";
	public static final String XML_TAG_GRID = "grid";
	public static final String FASTESTLAP = "fastestlap";
	
//******************************************************************
//
//				RACES XML SPECIFIC TAGS
//
//******************************************************************

	public static final String XML_TAG_RACES = "races";
	public static final String XML_TAG_RACE = "race";
	public static final String XML_TAG_DATE = "date";
	
//******************************************************************
//
//			  RULES XML SPECIFIC TAGS
//
//******************************************************************

  public static final String XML_TAG_RULES = "rules";
  public static final String XML_TAG_RULE = "rule";
  public static final String XML_TAG_ISAPPLICABLE = "isapplicable";
  public static final String XML_TAG_CASE = "case";
  public static final String XML_TAG_CONDITION = "condition";
  public static final String XML_TAG_KEY = "key";
  public static final String XML_TAG_VALUE = "value";
  public static final String XML_TAG_FANTASYPOINTS = "fantasypoints";
  
//******************************************************************
//
//			  TEAMS XML SPECIFIC TAGS
//
//******************************************************************

  public static final String XML_TAG_TEAMS = "teams";
  
  
}

