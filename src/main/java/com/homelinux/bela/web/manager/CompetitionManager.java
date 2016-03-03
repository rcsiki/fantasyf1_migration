/*
 * Created on Feb 5, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.homelinux.bela.web.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.InputSource;

import com.homelinux.bela.web.config.WebConfig;
import com.homelinux.bela.web.extractor.Extractor;
import com.homelinux.bela.web.extractor.ExtractorException;
import com.homelinux.bela.web.fc.IConstants;
import com.homelinux.bela.web.fc.IDriver;
import com.homelinux.bela.web.fc.IPlayer;
import com.homelinux.bela.web.fc.IPlayerComponent;
import com.homelinux.bela.web.fc.IRace;
import com.homelinux.bela.web.fc.IRacePositionDetail;
import com.homelinux.bela.web.fc.IRaceResult;
import com.homelinux.bela.web.fc.IRule;
import com.homelinux.bela.web.fc.IRuleCase;
import com.homelinux.bela.web.fc.ITeam;
import com.homelinux.bela.web.impl.Driver;
import com.homelinux.bela.web.impl.Player;
import com.homelinux.bela.web.impl.Race;
import com.homelinux.bela.web.impl.RacePositionDetail;
import com.homelinux.bela.web.impl.RaceResult;
import com.homelinux.bela.web.impl.Rule;
import com.homelinux.bela.web.impl.RuleCase;
import com.homelinux.bela.web.impl.Team;
import com.homelinux.bela.web.util.StringUtil;


/**
 * @author mrcsiki
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CompetitionManager
{
	public static synchronized CompetitionManager getInstance()
	   throws Exception
	{
		if (m_CompetitionManager == null)
		{
			m_CompetitionManager = new CompetitionManager();
		}
		return m_CompetitionManager;
	}
	
	private static synchronized void clearInstance()
	{
		if (m_rules != null)
		{
			m_rules.clear();
			m_rules = null;
		}
		
		if (m_races != null)
		{
			m_races.clear();
			m_races = null;
		}

		if (m_drivers != null)
		{
			m_drivers.clear();
			m_drivers = null;
		}

		if (m_teams != null)
		{
			m_teams.clear();
			m_teams = null;
		}

		if (m_racesResults != null)
		{
			m_racesResults.clear();
			m_racesResults = null;
		}

		if (m_players != null)
		{
			m_players.clear();
			m_players = null;
		}
		
		m_blHaveRulesBeenApplied = false;
		m_CompetitionManager = null;
	}	
	
	/**
	 * The C-tor 
	 */
	public CompetitionManager()
	   throws Exception
	{
		m_blHaveRulesBeenApplied = false;

		m_rules = new TreeMap();
		m_races = new TreeMap();
		m_drivers = new TreeMap();
		m_teams = new TreeMap();		

		// sorted stuff - also synchronized
		m_players = Collections.synchronizedSortedMap(new TreeMap());
		m_racesResults = Collections.synchronizedSortedMap(new TreeMap());
		
        loadDrivers();
		loadTeams();
		loadRaces();
    	loadRacesResults();
   		loadRules();
		loadPlayers();
		
	}
	
	public static synchronized String reloadConfiguration()
	{
		try
		{
			clearInstance();
			getInstance();
			WebConfig.load();
		}
		catch(Exception x)
		{
			x.printStackTrace();
			return "Reload Configuration failed!"+x.getMessage();
		}
		return "Configuration successfully reloaded!";
				
	}
		
	public IRaceResult getRaceResultById(String strRaceId)
	{
		return (IRaceResult)m_racesResults.get(strRaceId);
	}
	
	public IDriver getDriverById(String strDriverId)
	{
		IDriver result = (IDriver)m_drivers.get(strDriverId);
		return result;
	}
	
	public ITeam getTeamById(String strTeamId)
	{
		ITeam result = (ITeam)m_teams.get(strTeamId);
		return result;
	}
	
	public IRule getRuleById(String strRuleId)
	{
		return (IRule)m_rules.get(strRuleId);
	}
	
	public IRace getRaceById(String strRaceId)
	{
		return (IRace)m_races.get(strRaceId);
	}
	
	public IPlayer getPlayerById(String strPlayerId)
	{
		return (IPlayer)m_players.get(strPlayerId);
	}
	
	public String printPlayerRaceDetails(String strPlayerId, String strRaceId)
	   throws Exception
	{
		IPlayer player = getPlayerById(strPlayerId);
		return player.printPlayerRaceDetails(strRaceId);
	}
	
	public String renderRegistrationLink()
	{
		StringBuffer sb  = new StringBuffer();
		sb.append("<TR><TD class=\"tablelayoutnobold\"");
		if ( Calendar.getInstance().getTime().after( WebConfig.REGISTRATION_DEADLINE) )
		{
			sb.append(" title=\"Sorry, the registration time has expired.\">Registration</TD");			
		}
		else
		{
			sb.append("><A href=\"registrationBody.jsp\" target=\"dataframe\">Registration</A></TD>");			
		}
		sb.append("</TR>");
		return sb.toString();
	}
	
	public String renderAdminLinks()
	{
		StringBuffer sb  = new StringBuffer("");
		String strAdminPassword = getAdminPassword();
		if (strAdminPassword != null && strAdminPassword.equals(WebConfig.ADMIN_PWD))
		{
			sb.append("<TR>");
			sb.append("<TD>");
			sb.append("<TABLE BORDER=0 cellspacing=2 cellpadding=2>");
			sb.append("<TR>");
			sb.append("<TD class=\"tablelayoutheader\">Admin Utilities<b></TD>");
			sb.append("</TR>");
//			sb.append("<TR>");
//			sb.append("<TD class=\"tablelayoutnobold\"><A href=\"admin/extractRaceResult.jsp\" target=\"dataframe\">Extract Race Result</A></TD>");
//			sb.append("</TR>");
			sb.append("<TR>");
			sb.append("<TD class=\"tablelayoutnobold\"><A href=\"admin/editRaceResult.jsp\" target=\"dataframe\">Next Race Result</A></TD>");
			sb.append("</TR>");
			sb.append("<TR>");
			sb.append("<TD class=\"tablelayoutnobold\"><A href=\"admin/reloadResult.jsp\" target=\"dataframe\">Reload Config</A></TD>");
			sb.append("</TR>");
			sb.append("</TABLE>");
			sb.append("<br><br>");
			sb.append("</TD>");
			sb.append("</TR>");
		}
		return sb.toString();
	}
	
	public String getDriverIdByDriverNameAndRaceId(String strDriverName, String strRaceId)
	{
		IDriver driver = null;
		String strDriverId = null;
		for (Iterator iter = m_drivers.values().iterator() ; iter.hasNext() ; )
		{
			driver = (IDriver)iter.next();
			if (driver.getNameByRace(strRaceId).equalsIgnoreCase(strDriverName))
			{
				strDriverId = driver.getId();
				break;
			}
		}
		return strDriverId;
	}
	
	public String getRaceIdByRaceDescription(String strRaceName)
	{
		IRace race = null;
		String strRaceId = null;
		for (Iterator iter = m_races.values().iterator() ; iter.hasNext() ; )
		{
			race = (IRace)iter.next();
			if (race.getDescription().equalsIgnoreCase(strRaceName))
			{
				strRaceId = race.getId();
				break;
			}
		}
		return strRaceId;
	}
	
	public String drawNextRaceResultToExtractForm()
	{
		StringBuffer sb = new StringBuffer();
		int iNextRace = m_racesResults.size() + 1;
		if ( iNextRace > WebConfig.RACES_THIS_SEASON )
		{
			return "<TABLE><TR><TD CLASS=\"tablelayout\">All Race Results for this season are already extracted!</TD></TR></TABLE>";
		}
		String strNextRaceId = "0" + iNextRace;
		strNextRaceId = strNextRaceId.substring(strNextRaceId.length() - 2);
		strNextRaceId = "race_" + strNextRaceId;
		IRace race = getRaceById(strNextRaceId);
		sb.append("<FORM NAME = 'raceResultExtractorForm' TARGET=\"dataframe\">");
		sb.append("<TABLE><TR>");
		sb.append("<TD CLASS=\"tablelayout\">Next race result to extract is: </TD>");
		sb.append("<TD CLASS=\"tablelayout\">");
		sb.append(race.getName());
		sb.append(" (Race# ");
		sb.append(iNextRace);
		sb.append(")<BR></TD>");
		sb.append("</TR><TR>");
		sb.append("<TD CLASS=\"tablelayout\">URL (edit if incorrect): </TD>");
		sb.append("<TD CLASS=\"textfield\"><INPUT CLASS=\"textfield\"");
		sb.append("TYPE=TEXT NAME=\"nextRaceResultUrlTextField\" SIZE=60 value=");
		sb.append(race.getUrl());
		sb.append("><BR></TD>");
		sb.append("</TR><TR>");
		sb.append("<TD><INPUT TYPE=SUBMIT NAME=\"extractSubmit\" value=\"Extract it!\"></TD>");
		sb.append("</TR></TABLE></FORM>");
		return sb.toString();
	}
	
	public IRace getNextRaceToProcess()
	{
		int iNextRace = m_racesResults.size() + 1;
		if ( iNextRace > WebConfig.RACES_THIS_SEASON )
		{
			// season is over
			return null;
		}
		String strNextRaceId = "0" + iNextRace;
		strNextRaceId = strNextRaceId.substring(strNextRaceId.length() - 2);
		strNextRaceId = "race_" + strNextRaceId;
		IRace race = getRaceById(strNextRaceId);
		
		return race;
	}
	
	public IRace getLastDisputedRace()
	{
		int iCurrentRace = m_racesResults.size();
		String strCurrentRaceId = "0" + iCurrentRace;
		strCurrentRaceId = strCurrentRaceId.substring(strCurrentRaceId.length() - 2);
		strCurrentRaceId = "race_" + strCurrentRaceId;
		IRace race = getRaceById(strCurrentRaceId);
		
		return race;
	}

	// returns a map with the results for all the disputed races
	public Map getRaceResults()
	{
		return m_racesResults;
	}
	
	public String renderDriverOptionList()
	{
		StringBuffer sb = new StringBuffer(200);
		sb.append("<option value=\"\"></option>\r\n");
		for (Iterator iter = m_drivers.values().iterator() ; iter.hasNext();)
		{
			IDriver driver = (IDriver)iter.next();
			sb.append("<option value=\"");
			sb.append(driver.getId());
			sb.append("\">");
			sb.append(driver.getName());
			sb.append("</option>\r\n");
		}
		return sb.toString();
	}
	
	public int getDriverCount()
	{
		return m_drivers.size();
	}
	
	public synchronized String extractRaceResult(String strUrl)
	{
		try
		{
			Extractor extractor = new Extractor();
			extractor.extract(strUrl);		
		}
		catch (ExtractorException exe)
		{
			exe.printStackTrace();
			return "Extraction FAILED or NOT COMPLETED, check the log and races_results XML file<br>The configuration has not been reloaded";
		}
		// also reload the configuration
		if ( reloadConfiguration().indexOf("successfully") != -1)
		{
			return "Race Result SUCCESSFULLY extracted, configuration SUCCESSFULLY reloaded!";
		}
		else
		{
			return "Race Result SUCCESSFULLY extracted, BUT RELOADING THE CONFIGURATION FAILED!";
		}
	}

	public synchronized String registerPlayer( String strTeamName,
                                               String strTeamManager,
                                               String strEmailAddress,
                                               String strDriverId1,
                                               String strDriverId2,
                                               String strTeamId1,
                                               String strTeamId2,
                                               String strTieBreakerAnswer )
    {
    	StringBuffer result = new StringBuffer();
    	
		result.append("<table border='0'>");
		result.append("<tr><td align='center' valign='top'>");
		result.append("<table width='60%' border = '0'><tr>");
		result.append("<td class=\"tablelayoutheader\" align='center' valign='top'>Registration Result:<br><br></td></tr>");
		result.append("<tr><td><table border='0'>");
		result.append("<TR><TD");

    	Vector allRegisteredTeamNames = getAllRegisteredTeamNames();
    	Vector allRegisteredEmails = getAllRegisteredEmails();
    	
    	// check to see if the player did use the Registration page or not to register
		if ( Calendar.getInstance().getTime().after( WebConfig.REGISTRATION_DEADLINE) )
		{
			result.append(" class=\"tablelayoutnobold\"><b>REGISTRATION FAILED!</b><p>We are sorry, but the registration ");
			result.append("period has expired.");
			result.append("</TD></TR>");
		}
    	else if (!validatePlayerSelection( strTeamName,
									  strTeamManager,
									  strEmailAddress,
									  strDriverId1,
									  strDriverId2,
									  strTeamId1,
									  strTeamId2,
									  strTieBreakerAnswer ))    	
		{
			result.append(" class=\"tablelayoutnobold\"><b>REGISTRATION FAILED!</b><p>We are sorry, but your team cannot be registered");
			result.append(" because you did not use the correct procedure to do it.");
			result.append("</TD></TR>");
		}
		else if (allRegisteredEmails.contains(strEmailAddress))
		{
			// email already on database
			result.append(" class=\"tablelayoutnobold\"><b>REGISTRATION FAILED!</b><p>We are sorry, but the email you entered ");
			result.append("<b>"+strEmailAddress+"</b>");
			result.append(" is already used by another Player. Please use the<br>link below to go back to the Registration page and choose another.");
			result.append("</TD></TR>");
			// the Back Button
			result.append("<tr>");
			result.append("<td align='right' valign='top'>");
			result.append("<br><table><tr><td align'right'><a href='JavaScript:window.history.go(-2)'>Back to Registration</a></td></tr></table>");
			result.append("</td>");
			result.append("</tr>");			
		}
    	else if (allRegisteredTeamNames.contains(strTeamName))
    	{
			// team name already on database
			result.append(" class=\"tablelayoutnobold\"><b>REGISTRATION FAILED!</b><p>We are sorry, but a Team with name ");
			result.append("<b>"+strTeamName+"</b>");
			result.append(" is already registered. Please use the<br>link below to go back to the Registration page and choose another name for your Team.");
			result.append("</TD></TR>");
			// the Back Button
			result.append("<tr>");
			result.append("<td align='right' valign='top'>");
			result.append("<br><table><tr><td align'right'><a href='JavaScript:window.history.go(-2)'>Back to Registration</a></td></tr></table>");
			result.append("</td>");
			result.append("</tr>");			
    	}
    	else
    	{
			// all ok, proceed with the registration
			String strNewPlayerId = generateNewPlayerId();
			IPlayer player = new Player(strNewPlayerId, strTeamName, strTeamManager);
			
			player.setEmailAddress(strEmailAddress);
			player.setTieBreakerAnswer((int)Float.parseFloat(strTieBreakerAnswer));
			player.setDriverId1(strDriverId1);
			player.setDriverId2(strDriverId2);
			player.setTeamId1(strTeamId1);
			player.setTeamId2(strTeamId2);
			
			m_players.put(strNewPlayerId, player);
			
			boolean error = false;
			try
			{
				writeNewPlayerRegistrationToXML(player);
			}
			catch(Exception x)
			{
				// rollback
				m_players.remove(strNewPlayerId);
				x.printStackTrace();
				error = true;
			}
			if (error)
			{
				// an error has occured during writting the XML
				result.append(" class=\"tablelayoutnobold\"><b>REGISTRATION FAILED!</b><p>A server error has occured. Please try again later.");
			}
			else
			{
                result.append(" class=\"tablelayout\">Congratulations! Your "
                        + WebConfig.CURRENT_YEAR + " Fantasy Formula1 Team configuration is:");
				result.append("<br><br><table border='0'>");
				
				result.append("<tr><td class=\"tablelayoutnobold\">");
				result.append("Team Name:");
				result.append("</td><td class=\"tablelayout\">");
				result.append(player.getName());
				result.append("</td></tr>");

				result.append("<tr><td class=\"tablelayoutnobold\">");
				result.append("Team Manager:");
				result.append("</td><td class=\"tablelayout\">");
				result.append(player.getDescription());
				result.append("</td></tr>");

				result.append("<tr><td class=\"tablelayoutnobold\">");
				result.append("Email:");
				result.append("</td><td class=\"tablelayout\">");
				result.append(player.getEmailAddress());
				result.append("</td></tr>");

				result.append("<tr><td class=\"tablelayoutnobold\">");
				result.append("Driver 1:");
				result.append("</td><td class=\"tablelayout\">");
				result.append((this.getDriverById(player.getDriverId1())).getName());
				result.append("</td></tr>");

				result.append("<tr><td class=\"tablelayoutnobold\">");
				result.append("Driver 2:");
				result.append("</td><td class=\"tablelayout\">");
				result.append((this.getDriverById(player.getDriverId2())).getName());
				result.append("</td></tr>");

				result.append("<tr><td class=\"tablelayoutnobold\">");
				result.append("Constructor 1:");
				result.append("</td><td class=\"tablelayout\">");
				result.append((this.getTeamById(player.getTeamId1())).getName());
				result.append("</td></tr>");

				result.append("<tr><td class=\"tablelayoutnobold\">");
				result.append("Constructor 2:");
				result.append("</td><td class=\"tablelayout\">");
				result.append((this.getTeamById(player.getTeamId2())).getName());
				result.append("</td></tr>");

				result.append("<tr><td class=\"tablelayoutnobold\">");
				result.append("Tie-Breaker answer:");
				result.append("</td><td class=\"tablelayout\">");
				result.append(player.getTieBreakerAnswer());
				result.append("</td></tr></table></td></tr>");
				result.append("<tr><td class=\"tablelayoutnobold\">");

			    result.append("<br><p><b>Remainder:</b> In order to be eligible to win any prize at the end of the season,");
			    result.append(" you are now asked to please mail in a <b>personal check</b>");
                result.append(" valued at $20 CDN by <b>" + WebConfig.PAYMENT_DEADLINE_TEXT
                        + "</b>, to the following mail address:<p>");
                result.append("<b>" + WebConfig.PAYMENT_MAIL_ADDRESS + "</b><p>");
				result.append("representing the entry fee to the competition. Please <b>don't forget</b> to mention");
				result.append(" your <b>Team Name</b> when submitting the fee. When the payment is received, ");
				result.append("an acknowledgement will be sent back to you by e-mail.");
                result.append("<br><p><b>Thank You and we wish you have a strong "
                        + WebConfig.CURRENT_YEAR + " Fantasy Formula 1 Season!</b>");
			}    		
    	}
		result.append("</TD></TR></table></td></tr></table>");
    	return result.toString();
    }
	
	private boolean validatePlayerSelection( String strTeamName,
								 			 String strTeamManager,
											 String strEmailAddress,
											 String strDriverId1,
											 String strDriverId2,
											 String strTeamId1,
											 String strTeamId2,
											 String strTieBreakerAnswer )
	{
		if (strTeamName.length() == 0 || strTeamName.length() > 30)
		{
			System.out.println("strTeamName invalid: "+strTeamName);
			return false;
		}
    	
		if (strTeamManager.length() == 0 || strTeamManager.length() > 30)
		{
			System.out.println("strTeamManager invalid: "+strTeamManager);
			return false;
		}
		
		if (strEmailAddress.length() == 0 || strEmailAddress.length() > 150)
		{
			System.out.println("strEmailAddress invalid: "+strEmailAddress);
			return false;
		}

		// tiebreaker
		try
		{
			int iTiBreakerAnswer = Integer.parseInt(strTieBreakerAnswer);
            if (iTiBreakerAnswer < 0 || iTiBreakerAnswer > WebConfig.RACES_THIS_SEASON * 25)
			{
				System.out.println("strTieBreakerAnswer invalid: "+strTieBreakerAnswer);
				return false; 
			}
		}
		catch (NumberFormatException nfe)
		{
			// not a number
			System.out.println("strTieBreakerAnswer invalid: "+strTieBreakerAnswer);
			return false;
		}
        

		if (!m_drivers.containsKey(strDriverId1))
		{
			System.out.println("strDriverId1 invalid: "+strDriverId1);
			return false;
		}
		
		if (!m_drivers.containsKey(strDriverId2))
		{
			System.out.println("strDriverId2 invalid: "+strDriverId2);
			return false;
		}
		
		if (!m_teams.containsKey(strTeamId1))
		{
			System.out.println("strTeamId1 invalid: "+strTeamId1);
			return false;
		}
		
		if (!m_teams.containsKey(strTeamId2))
		{
			System.out.println("strTeamId2 invalid: "+strTeamId2);
			return false;
		}
		
		// check the selection cost
		int iCost = getDriverById(strDriverId1).getCost();
		iCost += getDriverById(strDriverId2).getCost();
		iCost += getTeamById(strTeamId1).getCost();
		iCost += getTeamById(strTeamId2).getCost();
		
		if (iCost > WebConfig.PLAYER_BUDGET)
		{
			System.out.println("budget exceeded! "+iCost);
			return false;
		}
		
		if ( (strDriverId1.equals(strDriverId2)) || (strTeamId1.equals(strTeamId2)))
		{
			System.out.println("cannot have the same component twice: "+ strDriverId1+", "+strDriverId2+", "+strTeamId1+", "+strTeamId2);
			return false;
		}
		
		return true;
	}

	private void writeNewPlayerRegistrationToXML(IPlayer player)
	   throws Exception
	{
		Document doc = null;
		File playersXmlFile = new File(ROOT_FOLDER_PATH + IConstants.PLAYERS_RELATIVE_FILEPATH);
		if ( playersXmlFile.exists() )
		{
			doc = getDocument(IConstants.PLAYERS_RELATIVE_FILEPATH);
		}
		else
		{
			// no players registered yet, create a brand new new document
			// for players and add this player into it
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			doc = dbFactory.newDocumentBuilder().newDocument();
			Element playersElement = doc.createElement("players");
			doc.appendChild(playersElement);
		}

		Element playerElement = doc.createElement("player");
	
		Element idElement = doc.createElement("id");
		playerElement.appendChild(idElement);
		
		Text idText = doc.createTextNode(player.getId());
		idElement.appendChild(idText);
		
		Element nameElement = doc.createElement("name");
		playerElement.appendChild(nameElement);
		
		Text nameText = doc.createTextNode(player.getName());
		nameElement.appendChild(nameText);
		
		Element descriptionElement = doc.createElement("description");
		playerElement.appendChild(descriptionElement);

		Text descriptionText = doc.createTextNode(player.getDescription());
		descriptionElement.appendChild(descriptionText);
		
		Element emailElement = doc.createElement("email");
		playerElement.appendChild(emailElement);
		
		Text emailText = doc.createTextNode(player.getEmailAddress());
		emailElement.appendChild(emailText);
		
		Element tieBreakerAnswerElement = doc.createElement("tiebreakeranswer");
		playerElement.appendChild(tieBreakerAnswerElement);
		
		Text tieBreakerAnswerText = doc.createTextNode(""+player.getTieBreakerAnswer());
		tieBreakerAnswerElement.appendChild(tieBreakerAnswerText);

		Element paymentReceivedElement = doc.createElement("paymentreceived");
		playerElement.appendChild(paymentReceivedElement);
		
		Text paymentReceivedText = doc.createTextNode(""+player.isPaymentReceived());
		paymentReceivedElement.appendChild(paymentReceivedText);

		Element picksElement = doc.createElement("picks");
		playerElement.appendChild(picksElement);
		
		Element driverId1Element = doc.createElement("driver1");
		picksElement.appendChild(driverId1Element);
		
		Text driverId1Text = doc.createTextNode(player.getDriverId1());
		driverId1Element.appendChild(driverId1Text);

		Element driverId2Element = doc.createElement("driver2");
		picksElement.appendChild(driverId2Element);
		
		Text driverId2Text = doc.createTextNode(player.getDriverId2());
		driverId2Element.appendChild(driverId2Text);

		Element teamId1Element = doc.createElement("team1");
		picksElement.appendChild(teamId1Element);
		
		Text teamId1Text = doc.createTextNode(player.getTeamId1());
		teamId1Element.appendChild(teamId1Text);
		
		Element teamId2Element = doc.createElement("team2");
		picksElement.appendChild(teamId2Element);
		
		Text teamId2Text = doc.createTextNode(player.getTeamId2());
		teamId2Element.appendChild(teamId2Text);
		
		// append to document
		NodeList players = doc.getElementsByTagName("players");
		players.item(0).appendChild(playerElement);
		
		// Set up an identity transformer to use as serializer.
		Transformer serializer = TransformerFactory.newInstance().newTransformer();
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		serializer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		serializer.setOutputProperty(OutputKeys.ENCODING, WebConfig.CONTENT_TYPE);
		
		if (playersXmlFile.exists())
		{
			// sanity action - back up players.xml on the folder "../backup",
			// before inserting this new player
			
			File backupDir = new File( playersXmlFile.getParentFile().getAbsolutePath() +			  
			                           File.separator +
			                           "backup" );
			if ( !backupDir.exists() )
			{
				backupDir.mkdirs();
			}
			
			File playersBackupXmlFile =
			  new File ( backupDir, playersXmlFile.getName() + ".backup" );
			  
			if ( playersBackupXmlFile.exists() )
			{
			   playersBackupXmlFile.delete();
			}
			
			boolean bDidBackupSucceed = playersXmlFile.renameTo(playersBackupXmlFile);
			if( !bDidBackupSucceed )
			{
				// for some reason, the backup failed, we have to protect the
				// file that contains all entered players !!
				// so better stop here and debug the host machine to see what happened
				throw new IOException("Error backing up players.xml!");
			}
		}
		
		FileWriter fw = null;
		try
		{
			fw = new FileWriter(playersXmlFile);
			StreamResult sr = new StreamResult(fw);
			// write DOM into players.xml file 
			serializer.transform( new DOMSource(doc), sr);
		}
		finally
		{
			// make sure the writer is closed
			if (fw != null)
			{
				try
				{
					fw.close();		
				}
				catch(IOException io)
				{
					System.out.println("*** IOException during closing players.xml: "+io.getMessage());
				}
			}
		}
		
	}
	
	public boolean writeRaceResultToXML(IRaceResult raceResult)
	   throws Exception
	{
		Document doc = null;
		File raceResultsXmlFile = new File(ROOT_FOLDER_PATH + IConstants.RACE_RESULTS_RELATIVE_FILEPATH);
		if ( raceResultsXmlFile.exists() )
		{
			doc = getDocument(IConstants.RACE_RESULTS_RELATIVE_FILEPATH);
		}
		else
		{
			// no race results yet, create a brand new new document
			// for race results and add this race result into it
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			doc = dbFactory.newDocumentBuilder().newDocument();
			Element resultsElement = doc.createElement("results");
			doc.appendChild(resultsElement);
		}

		Element raceElement = doc.createElement("race");
	
		Element idElement = doc.createElement("id");
		raceElement.appendChild(idElement);
		
		Text idText = doc.createTextNode(raceResult.getRaceId());
		idElement.appendChild(idText);
		
		Element fastestLapElement = doc.createElement("fastestlap");
		raceElement.appendChild(fastestLapElement);
		
		Text nameText = doc.createTextNode(raceResult.getFastestLapDriverId());
		fastestLapElement.appendChild(nameText);
		
		for (Iterator iter = m_drivers.values().iterator() ; iter.hasNext() ; )
		{
			IDriver driver = (IDriver)iter.next();
			IRacePositionDetail racePositionDetail =
				raceResult.getRacePositionDetailByDriverId(driver.getId());
			
			Element driverElement = doc.createElement("driver");
			raceElement.appendChild(driverElement);
			
			Element posElement = doc.createElement("pos");
			driverElement.appendChild(posElement);
			
			Text posText = doc.createTextNode(racePositionDetail.getRacePosition());
			posElement.appendChild(posText);

			Element driverIdElement = doc.createElement("id");
			driverElement.appendChild(driverIdElement);
			
			Text driverIdText = doc.createTextNode(racePositionDetail.getDriverId());
			driverIdElement.appendChild(driverIdText);

			Element lapsElement = doc.createElement("laps");
			driverElement.appendChild(lapsElement);
			
			Text lapsText = doc.createTextNode("" + racePositionDetail.getLapsCompleted());
			lapsElement.appendChild(lapsText);
			
			Element gridElement = doc.createElement("grid");
			driverElement.appendChild(gridElement);
			
			Text gridText = doc.createTextNode("" + racePositionDetail.getGridPosition());
			gridElement.appendChild(gridText);
		}
		
		
		// append to document
		NodeList players = doc.getElementsByTagName("results");
		players.item(0).appendChild(raceElement);
		
		// Set up an identity transformer to use as serializer.
		Transformer serializer = TransformerFactory.newInstance().newTransformer();
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		serializer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		serializer.setOutputProperty(OutputKeys.ENCODING, WebConfig.CONTENT_TYPE);
		
		if (raceResultsXmlFile.exists())
		{
			// sanity action - back up races_results.xml on the folder "../backup",
			// before inserting this new race result
			
			File backupDir = new File( raceResultsXmlFile.getParentFile().getAbsolutePath() +			  
			                           File.separator +
			                           "backup" );
			if ( !backupDir.exists() )
			{
				backupDir.mkdirs();
			}
			
			File playersBackupXmlFile =
			  new File ( backupDir, raceResultsXmlFile.getName() + ".backup" );
			  
			if ( playersBackupXmlFile.exists() )
			{
			   playersBackupXmlFile.delete();
			}
			
			boolean bDidBackupSucceed = raceResultsXmlFile.renameTo(playersBackupXmlFile);
			if( !bDidBackupSucceed )
			{
				// for some reason, the backup failed, we have to protect the
				// file that contains all entered race results...
				// so better stop here and debug the host machine to see what happened
				throw new IOException("Error backing up races_results.xml!");
			}
		}
		
		FileWriter fw = null;
		try
		{
			fw = new FileWriter(raceResultsXmlFile);
			StreamResult sr = new StreamResult(fw);
			// write DOM into players.xml file 
			serializer.transform( new DOMSource(doc), sr);
			
			// we're done
			return true;
		}
		finally
		{
			// make sure the writer is closed
			if (fw != null)
			{
				try
				{
					fw.close();		
				}
				catch(IOException io)
				{
					System.out.println("*** IOException during closing races_results.xml: "+io.getMessage());
				}
			}
		}
	}

	private String generateNewPlayerId()
	{
		String strNewPlayerId = "player_";
		if (m_players.isEmpty())
		{
			strNewPlayerId += "001";
		}
		else
		{
			String strLastKey = (String)m_players.lastKey();
			int iIndex = strLastKey.indexOf(strNewPlayerId);
			int iNumber = Integer.parseInt(strLastKey.substring(iIndex + strNewPlayerId.length()));
			// increment it
			int iNewNumber = iNumber + 1;
			// padding with zeros if necessary
			String strNewIdPostfix = "00" + iNewNumber;
			strNewIdPostfix = strNewIdPostfix.substring(strNewIdPostfix.length() - 3);
			strNewPlayerId += strNewIdPostfix;
		}
		return strNewPlayerId;
	}
	
	private Vector getAllRegisteredTeamNames()
	{
		Collection coll = m_players.values();
		Vector result = new Vector();
		for(Iterator iter = coll.iterator() ; iter.hasNext() ; )
		{
			result.add(((IPlayer)iter.next()).getName());
		}
		return result;
	}
	
	private Vector getAllRegisteredEmails()
	{
		Collection coll = m_players.values();
		Vector result = new Vector();
		for(Iterator iter = coll.iterator() ; iter.hasNext() ; )
		{
			result.add(((IPlayer)iter.next()).getEmailAddress());
		}
		return result;
	}

	public String printAllPlayerComponentsForRegistrationForm(String strPlayerComponentType)
	   throws Exception
	{
		StringBuffer sb = new StringBuffer();
		sb.append("<table border='0'>\r\n");
		sb.append("                       <tr>\r\n");
		sb.append("                         <td align='center' valign='top'>\r\n");
		sb.append("                           <table border='0'>\r\n");
		sb.append("                             <tr>\r\n");
		sb.append("                               <td  class=\"tablelayoutheader\" align='center' valign='top'>\r\n");
		Collection coll = null;
		String strCheckboxIdPrefix = null;
		boolean bRenderInSingleRow = false;
		if (strPlayerComponentType.equals("driver"))
		{
			strCheckboxIdPrefix = "drivercheckbox_";
			sb.append("                                 Drivers (select two)\r\n");
			coll = m_drivers.values();
		}
		else
		{
			bRenderInSingleRow = true;
			strCheckboxIdPrefix = "teamcheckbox_";
			sb.append("                                 Cars (select two)\r\n");
			coll = m_teams.values();
		}
		sb.append("                               </td>\r\n");
		sb.append("                             </tr>\r\n");
		sb.append("                           </table>\r\n");
		sb.append("                         </td>\r\n");
		sb.append("                       </tr>\r\n");
		sb.append("                       <tr>\r\n");
		sb.append("                         <td align='center' valign='top'>\r\n");
		sb.append("                           <table border='0'>\r\n");
		IPlayerComponent playerComponent = null;
		boolean bFirstDataInRow = true;
		int iCheckboxCount = 0;
		String strCheckboxId = null;
		for (Iterator iter = coll.iterator() ; iter.hasNext() ;)
		{
			strCheckboxId = strCheckboxIdPrefix + iCheckboxCount;
			if (bFirstDataInRow || bRenderInSingleRow)
			{
				sb.append("                             <tr>\r\n");
			}
			playerComponent = (IPlayerComponent)iter.next();
			sb.append(printPlayerComponentForRegistrationForm(playerComponent,
			strCheckboxId, bFirstDataInRow, bRenderInSingleRow));
			if (!bFirstDataInRow || bRenderInSingleRow)
			{
				sb.append("                             </tr>\r\n");
			}
			bFirstDataInRow = !bFirstDataInRow;
			iCheckboxCount++;
		}
		sb.append("                           </table>\r\n");
		sb.append("                         </td>\r\n");
		sb.append("                       </tr>\r\n");
		sb.append("                     </table>\r\n");
		
		return sb.toString();
	}
	
	private String printPlayerComponentForRegistrationForm( IPlayerComponent playerComponent,
	                                                        String strCheckboxId,  
	                                                        boolean bFirstDataInRow,
	                                                        boolean bRenderInSingleRow)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("                               <td id='cost_"+strCheckboxId+"' align='left' valign='top' title='" + playerComponent.getCost() + "'>\r\n");
		if( !bFirstDataInRow &&! bRenderInSingleRow )
		{
			sb.append("                                 &nbsp;&nbsp;&nbsp;\r\n");
		}
		sb.append("                                 <input type=checkbox id='"+strCheckboxId+"' name='"+playerComponent.getId()+"'>\r\n");
		sb.append("                               </td>\r\n");
		sb.append("                               <td class=\"tablelayout\" align='left' valign='top'>\r\n");
		sb.append("                                 " + playerComponent.getName() + "&nbsp;&nbsp;&nbsp;\r\n");
		sb.append("                               </td>\r\n");
		sb.append("                               <td class=\"tablelayout\" align='right' valign='top'>\r\n");
		sb.append("                                 " + playerComponent.getCost() + "\r\n");
		sb.append("                               </td>\r\n");
		return sb.toString();
	}

	public String drawOverallStandingsHeader()
	   throws Exception	   
{
		if (m_racesResults.size() > 0)
		{
		   StringBuffer sb = new StringBuffer();
		   sb.append("<table border='0'>");
		   sb.append("<tr>");
		   sb.append("<td width='30' nowrap class='tablelayoutheader' align='right' valign='middle'>Pos</td>");
		   sb.append("<td width='30' nowrap class='tablelayoutheader' align='center' valign='middle'>+/-</td>");
	       sb.append("<td width='160' nowrap class='tablelayoutheader' align='left' valign='middle'>&nbsp;&nbsp;&nbsp;Team Name</td>");
		   sb.append("<td width=160' nowrap class='tablelayoutheader' align='left' valign='middle'>Team Manager</td>");
	       sb.append("<td width='60' class='tablelayoutheader' align='center' valign='middle'>Paid</td>");
		   sb.append("<td width='60' nowrap class='tablelayoutheader' align='left' valign='middle'>Points&nbsp;&nbsp;&nbsp;</td>");
   	       sb.append("<td width='175' class='tablelayoutheader' align='right' valign='middle'>Race Details</td>");
		   sb.append("</tr></table>");
		   return sb.toString();
		}
		else
		{
			return "";
		}
	}
	
	public String drawOverallStandingsBody()
	   throws Exception
	{
		if (m_racesResults.size() == 0)
		{
			return "<table width='100%' border='0'><tr><td class=\"tablelayout\" align='center' valign='top'>The standings will be available once the season starts.</td></tr></table>";
		}
		String strDriverId = getDriverIdByDriverNameAndRaceId(WebConfig.TIEBREAKER_DRIVER_NAME, "race_01");
		IDriver tieBreakerDriverName = this.getDriverById(strDriverId);
		int iTieBreakerRealResponse = tieBreakerDriverName.getTotalRealPoints();
		PlayerPositionComparator ppc = null;
		PlayerPositionComparator ppc2 = null;
		
		StringBuffer sb = new StringBuffer();
		sb.append("<table border='0'>");	
		Map standings = new TreeMap(new PlayerPositionComparator());
		Map previousStandings = new TreeMap(new PlayerPositionComparator());
		Set set = m_players.keySet();
		IPlayer player = null;
		IRace lastDisputedRace = getLastDisputedRace();
		for (Iterator iter = set.iterator() ; iter.hasNext() ; )
		{
			player = (IPlayer)m_players.get(iter.next());
			
			int tieBreakerDecision = Math.abs(player.getTieBreakerAnswer() - iTieBreakerRealResponse);
			
			ppc = new PlayerPositionComparator( player.getGrandTotalPoints(), tieBreakerDecision );
			ppc2 =
				new PlayerPositionComparator( player.getGrandTotalPointsLessRace(lastDisputedRace), tieBreakerDecision );
			
			standings.put(ppc, player);
			previousStandings.put(ppc2, player);
		}
		
		int iPosCount = 1;
		Set sortedPlayers = standings.keySet();
		String strDropdownClass = null;						
		for (Iterator iter = sortedPlayers.iterator() ; iter.hasNext() ; )
		{						
			// alternating the bg color
			if (iPosCount % 2 == 0)
			{
  			   sb.append("<tr class='standingsRowBackgroundColor'>");
			   strDropdownClass = "dropdown";
			}
  		    else
  		    {
				sb.append("<tr class='standingsRowAltBackgroundColor'>");
				strDropdownClass = "altdropdown";
  		    }
  		    
			player = (IPlayer)standings.get(iter.next());
			
			int previousPos = getPreviousPlayerPosition(player, previousStandings);
			String strImgName = null;
			String strAlt = null;
			int iMove = Math.abs(previousPos - iPosCount);
			String strMove = null;
			if (iPosCount < previousPos)
			{
				strImgName = "up_arrow.gif";
				strAlt = "Moved up " + iMove + " position";
				if (iMove > 1)
				{
					strAlt += "s";
				}
				strMove = "<img src=\"../interface/images/" + strImgName +"\" alt=\"" + strAlt + "\" border=\"0\" valign=\"middle\"/>&nbsp;<font size=1>" + iMove + "</font>";
			}
			else if (iPosCount > previousPos)
			{
				strImgName = "down_arrow.gif";
				strAlt = "Moved down " + iMove + " position";
				if (iMove > 1)
				{
					strAlt += "s";
				}
				strMove = "<img src=\"../interface/images/" + strImgName +"\" alt=\"" + strAlt + "\" border=\"0\" valign=\"middle\"/>&nbsp;<font size=1>" + iMove + "</font>";
			}
			else
			{
				strImgName = "right_arrow.gif";
				strAlt = "Remained";
				strMove = "<img src=\"../interface/images/" + strImgName +"\" alt=\"" + strAlt + "\" border=\"0\" valign=\"middle\"/>";
			}
			

			sb.append("<td width=30' nowrap class='tablelayout' align='right' valign='middle'>" + iPosCount + "</td>");
			sb.append("<td width=30' nowrap class='tablelayout' align='center' valign='middle'>" + strMove + "</td>");
			// Team Name
			sb.append("<td width='160' nowrap class='tablelayout' align='left' valign='middle'>&nbsp;&nbsp;&nbsp;"+player.getName()+"</td>");
			// Team Manager
			sb.append("<td width='160' nowrap class='tablelayout' align='left' valign='middle'>"+player.getDescription()+"</td>");
			
			String strPaymentReceived = player.isPaymentReceived() ? "Yes" : "No";
			sb.append("<td width='60' nowrap class='tablelayout' align='center' valign='middle'>"+strPaymentReceived+"</td>");
			
			sb.append("<td width='60' nowrap class='tablelayout' align='left' valign='middle'>"+StringUtil.formatFloat(player.getGrandTotalPoints())+"&nbsp;&nbsp;&nbsp;</td>");
			IRaceResult raceResult = null;
			Object[] racesResultsSet = m_racesResults.keySet().toArray();
			
			sb.append("<td width='175' align='right' valign='middle'>");
			sb.append("<select class=\"");
			sb.append(strDropdownClass);
			sb.append("\" onchange=\"displayRaceDetail('dataframe',this)\">");
			sb.append("<option class=\"");
			sb.append(strDropdownClass);
			sb.append("\" value=\"nohref\" selected>-------------------- Select a race --------------------");
			
			
			for (int i=racesResultsSet.length-1; i>=0; i--)
			{
				raceResult = (IRaceResult)m_racesResults.get(racesResultsSet[i]);
				sb.append("<option class=\"");
				sb.append(strDropdownClass);
				sb.append("\" value=\"playerDetails.jsp?playerId=");
				sb.append(player.getId());
				sb.append("&raceId=");
				sb.append(raceResult.getRaceId());
				sb.append("\">");
				sb.append(StringUtil.formatFloat(player.getRacePoints(raceResult.getRaceId())));
				sb.append(" points for ");
				sb.append((this.getRaceById((raceResult.getRaceId())).getName()));
			}
			sb.append("</select></td>");
						
			sb.append("</tr>");
			iPosCount++;
		}				
		sb.append("</table>");
		return sb.toString();
	}
	
	private int getPreviousPlayerPosition(IPlayer player, Map players)
	{
		int pos = 1;
		for (Iterator iter = players.keySet().iterator() ; iter.hasNext() ; )
		{						
			IPlayer p = (IPlayer)players.get(iter.next());
			if (player.getId().equals(p.getId()))
			{
				return pos;
			}
			pos++;
		}
		return pos;
	}
	

	public String drawDriverCosts()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("<table border='0' cellspacing='2' cellpadding='2'>");
		sb.append("<tr height='20'><td class=\"tablelayoutheader\">Driver" +			"</td><td class=\"tablelayoutheader\">Cost</td><td class=\"tablelayoutheader\">" +			"Races For</td></tr>");
		Set set = m_drivers.keySet();
		IDriver driver = null;		
		int iPosCount = 1;
		for (Iterator iter = set.iterator() ; iter.hasNext() ; )
		{			
			// altarnating the bg color
			if (iPosCount % 2 == 0)
			{
			   sb.append("<tr class='standingsRowBackgroundColor'>");
			}
			else
			{
				sb.append("<tr class='standingsRowAltBackgroundColor'>");
			}
			driver = (IDriver)m_drivers.get(iter.next());
			sb.append("<td class=\"tablelayout\">");
			sb.append(driver.getName());
			sb.append("</td>");
			sb.append("<td class=\"tablelayout\">$"+driver.getCost());
			sb.append(" million</td>");
			sb.append("<td class=\"tablelayout\">"+(getTeamById(driver.getTeamId())).getName());
			sb.append("</td></tr>");
			iPosCount ++;
		}
		sb.append("</table>");
		return sb.toString();		
	}
	
	public String drawTeamCosts()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("<table border='0' cellspacing='2' cellpadding='2'>");
		sb.append("<tr height='20'><td class=\"tablelayoutheader\">Car</td><td class=\"tablelayoutheader\">Cost</td></tr>");
		Set set = m_teams.keySet();
		ITeam team = null;		
		int iPosCount = 1;
		for (Iterator iter = set.iterator() ; iter.hasNext() ; )
		{			
			// altarnating the bg color
			if (iPosCount % 2 == 0)
			{
			   sb.append("<tr class='standingsRowBackgroundColor'>");
			}
			else
			{
				sb.append("<tr class='standingsRowAltBackgroundColor'>");
			}
			team = (ITeam)m_teams.get(iter.next());
			sb.append("<td class=\"tablelayout\">");
			sb.append(team.getName());
			sb.append("</td>");
			sb.append("<td class=\"tablelayout\">$"+team.getCost());
			sb.append(" million</td></tr>");
			iPosCount ++;
		}
		sb.append("</table>");
		return sb.toString();		
	}

	/**
	 * This method returns all the completed races ids
	 * @return a String array containing the IDs for all completed races
	 */
	public String[] getCompletedRacesIds()
	{
		String[] result = new String[m_racesResults.keySet().size()];
		m_racesResults.keySet().toArray(result);
		return result;
	}
	
	public String[] getRulesIds()
	{
		String[] result = new String[m_rules.keySet().size()];
		m_rules.keySet().toArray(result);
		return result;
	}
	
	public synchronized void applyRules()
	   throws Exception
	{
		if (m_blHaveRulesBeenApplied)
		{
			return;
		}
		String[] strRulesKeys  = new String[m_rules.size()];
		m_rules.keySet().toArray(strRulesKeys);
		
		String[] strDriversKeys  = new String[m_drivers.size()];
		m_drivers.keySet().toArray(strDriversKeys);
		
		String[] strTeamsKeys  = new String[m_teams.size()];
		m_teams.keySet().toArray(strTeamsKeys);
		
		String[] strRacesResultsKeys  = new String[m_racesResults.size()];
		m_racesResults.keySet().toArray(strRacesResultsKeys);
		
		IRule rule = null;
		IDriver driver = null;
		ITeam team = null;
		IRaceResult raceResult = null;
		
		for (int i = 0 ; i < strRulesKeys.length ; i++)
		{
			rule = (Rule)m_rules.get(strRulesKeys[i]);
			for (int j = 0 ; j < strRacesResultsKeys.length ; j++)
			{
				raceResult = (RaceResult)m_racesResults.get(strRacesResultsKeys[j]);
		    	
				for (int k = 0 ; k < strDriversKeys.length ; k++)
				{
					driver = (Driver)m_drivers.get(strDriversKeys[k]);
					rule.apply(raceResult.getRaceId(), driver);
					
					// this is for statistics only - update the drivers
					// with FIA "real" (official) points
					if (i == 0)
					{
						driver.updateTotalRealPoints(raceResult);	
					}
				}
		    	
				for (int l = 0 ; l < strTeamsKeys.length ; l++)
				{
					team = (Team)m_teams.get(strTeamsKeys[l]);
					rule.apply(raceResult.getRaceId(), team);	
				}				
			}
		}
		m_blHaveRulesBeenApplied = true;		
	}
	

	private void loadDrivers()
	   throws Exception
	{
		Document doc = getDocument(IConstants.DRIVERS_RELATIVE_FILEPATH);
		// let's see how many rules we have defined
		NodeIterator ni = XPathAPI.selectNodeIterator(doc, "/drivers/driver");
		int nodesCount = 0;
		while ((ni.nextNode())!= null)
		{
			nodesCount++;
		}
		String strId = null;
		String strName = null;
		String strDescription = null;
		String strTeamId = null;
		String strCost = null;
		// now lookup in the doc and build the rules map
		for (int i = 1 ; i <= nodesCount ; i++)
		{
			ni = XPathAPI.selectNodeIterator(doc, "/drivers/driver["+i+"]/id");
			strId = ni.nextNode().getFirstChild().getNodeValue();
			
			ni = XPathAPI.selectNodeIterator(doc, "/drivers/driver["+i+"]/name");
			strName = ni.nextNode().getFirstChild().getNodeValue();         
			         
			ni = XPathAPI.selectNodeIterator(doc, "/drivers/driver["+i+"]/description");
			strDescription = ni.nextNode().getFirstChild().getNodeValue();         
			         
			ni = XPathAPI.selectNodeIterator(doc, "/drivers/driver["+i+"]/team");
			strTeamId = ni.nextNode().getFirstChild().getNodeValue();         
			         
			ni = XPathAPI.selectNodeIterator(doc, "/drivers/driver["+i+"]/cost");
			strCost = ni.nextNode().getFirstChild().getNodeValue();
			
			// search for driver replacements
			NodeIterator ni1 = XPathAPI.selectNodeIterator(doc, "/drivers/driver["+i+"]/replacements/replacement");
			Node replacement = null;
			String strRaceId = null;
			String strDriverName = null;
			Map replacements = null;
			int j = 1;
			while ( (replacement = ni1.nextNode()) != null )
			{
				if (replacements == null)
				{
					replacements = new TreeMap();
				}
				NodeIterator ni2 = XPathAPI.selectNodeIterator(doc, "/drivers/driver["+i+"]/replacements/replacement["+j+"]/raceid");
				strRaceId = ni2.nextNode().getFirstChild().getNodeValue();
				ni2 = XPathAPI.selectNodeIterator(doc, "/drivers/driver["+i+"]/replacements/replacement["+j+"]/drivername");
				strDriverName = ni2.nextNode().getFirstChild().getNodeValue();
				replacements.put(strRaceId, strDriverName);
				j++;
			}
			
			IDriver driver = new Driver(strId, strName, strDescription, Integer.parseInt(strCost), strTeamId);
			if (replacements != null)
			{
   			   driver.setReplacements(replacements);
			}
			m_drivers.put(strId, driver);         			         
		}
	}
	
	private void loadTeams()
	   throws Exception
	{
		Document doc = getDocument(IConstants.TEAMS_RELATIVE_FILEPATH);
		// let's see how many rules we have defined
		NodeIterator ni = XPathAPI.selectNodeIterator(doc, "/teams/team");
		int nodesCount = 0;
		while ((ni.nextNode())!= null)
		{
			nodesCount++;
		}
		String strId = null;
		String strName = null;
		String strDescription = null;
		String strDriverId1 = null;
		String strDriverId2 = null;
		String strCost = null;
		// now lookup in the doc and build the rules map
		for (int i = 1 ; i <= nodesCount ; i++)
		{
			ni = XPathAPI.selectNodeIterator(doc, "/teams/team["+i+"]/id");
			strId = ni.nextNode().getFirstChild().getNodeValue();
			
			ni = XPathAPI.selectNodeIterator(doc, "/teams/team["+i+"]/name");
			strName = ni.nextNode().getFirstChild().getNodeValue();         
			         
			ni = XPathAPI.selectNodeIterator(doc, "/teams/team["+i+"]/description");
			strDescription = ni.nextNode().getFirstChild().getNodeValue();         
			         
			ni = XPathAPI.selectNodeIterator(doc, "/teams/team["+i+"]/driver1");
			strDriverId1 = ni.nextNode().getFirstChild().getNodeValue();         
			         
			ni = XPathAPI.selectNodeIterator(doc, "/teams/team["+i+"]/driver2");
			strDriverId2 = ni.nextNode().getFirstChild().getNodeValue();         

			ni = XPathAPI.selectNodeIterator(doc, "/teams/team["+i+"]/cost");
			strCost = ni.nextNode().getFirstChild().getNodeValue();
			
			ITeam team = new Team(strId, strName, strDescription, Integer.parseInt(strCost));
			team.setDriverId1(strDriverId1);
			team.setDriverId2(strDriverId2);
			m_teams.put(strId, team);         			         
		}
	}

	private void loadRaces()
	   throws Exception
	{
		Document doc = getDocument(IConstants.RACES_RELATIVE_FILEPATH);
		// let's see how many rules we have defined
		NodeIterator ni = XPathAPI.selectNodeIterator(doc, "/races/race");
		int nodesCount = 0;
		while ((ni.nextNode())!= null)
		{
			nodesCount++;
		}
		String strId = null;
		String strName = null;
		String strDescription = null;
		String strDate = null;
		String strUrl = null;
		// now lookup in the doc and build the rules map
		for (int i = 1 ; i <= nodesCount ; i++)
		{
			ni = XPathAPI.selectNodeIterator(doc, "/races/race["+i+"]/id");
			strId = ni.nextNode().getFirstChild().getNodeValue();
			
			ni = XPathAPI.selectNodeIterator(doc, "/races/race["+i+"]/name");
			strName = ni.nextNode().getFirstChild().getNodeValue();         
			         
			ni = XPathAPI.selectNodeIterator(doc, "/races/race["+i+"]/description");
			strDescription = ni.nextNode().getFirstChild().getNodeValue();         
			         
			ni = XPathAPI.selectNodeIterator(doc, "/races/race["+i+"]/date");
			strDate = ni.nextNode().getFirstChild().getNodeValue();         
			         			
			ni = XPathAPI.selectNodeIterator(doc, "/races/race["+i+"]/url");
			strUrl = ni.nextNode().getFirstChild().getNodeValue();
			if (strUrl == null || strUrl.length() == 0)
			{
				strUrl = "http://... (to be completed)";         
			}

			IRace race = new Race(strId, strName, strDescription);
			race.setDate(new Date(Date.parse(strDate)));
			race.setUrl(strUrl);
			m_races.put(strId, race);    
		}     			         
	}

	private void loadRacesResults()
	   throws Exception
	{
		File racesResultsXmlFile = new File(ROOT_FOLDER_PATH + IConstants.RACE_RESULTS_RELATIVE_FILEPATH);
		if (!racesResultsXmlFile.exists())
		{
			// the season hasn't started yed?
			return;
		}

		Document doc = getDocument(IConstants.RACE_RESULTS_RELATIVE_FILEPATH);
		// let's see how many races have completed
		NodeIterator ni = XPathAPI.selectNodeIterator(doc, "/results/race");
		int nodesCount = 0;
		while ((ni.nextNode())!= null)
		{
			nodesCount++;
		}
		
		// now lookup in the doc and build the rules map
		for (int i = 1 ; i <= nodesCount ; i++)
		{
			loadRaceResults(doc, i);
		}     			         
	}
	
	private void loadRaceResults(Document doc, int iRaceIndex)
	   throws Exception
	{		

		NodeIterator ni = XPathAPI.selectNodeIterator(doc, "/results/race["+iRaceIndex+"]/driver");
		int nodesCount = 0;
		while ((ni.nextNode())!= null)
		{
			nodesCount++;
		}
		
		String strRaceId = null;
		String strFastestLapDriverId = null;
		String strDriverId = null;
		String strRacePosition = null;
		String strLapsCompleted = null;
		String strGridPosition = null;
		
		IRaceResult raceResult = new RaceResult();
		
		// first get the race id and the fstest lap driver id
		ni = XPathAPI.selectNodeIterator(doc, "/results/race["+iRaceIndex+"]/id");
		strRaceId = ni.nextNode().getFirstChild().getNodeValue();
		
		ni = XPathAPI.selectNodeIterator(doc, "/results/race["+iRaceIndex+"]/fastestlap");
		strFastestLapDriverId = ni.nextNode().getFirstChild().getNodeValue();
		
		raceResult.setRaceId(strRaceId);
		raceResult.setFastestLapDriverId(strFastestLapDriverId);

		// now lookup in the doc and build the rules map
		for (int i = 1 ; i <= nodesCount ; i++)
		{
			ni = XPathAPI.selectNodeIterator(doc, "/results/race["+iRaceIndex+"]/driver["+i+"]/id");
			strDriverId = ni.nextNode().getFirstChild().getNodeValue();
			
			ni = XPathAPI.selectNodeIterator(doc, "/results/race["+iRaceIndex+"]/driver["+i+"]/pos");
			strRacePosition = ni.nextNode().getFirstChild().getNodeValue().toLowerCase();         
			         
			ni = XPathAPI.selectNodeIterator(doc, "/results/race["+iRaceIndex+"]/driver["+i+"]/laps");
			strLapsCompleted = ni.nextNode().getFirstChild().getNodeValue();         
			         
			ni = XPathAPI.selectNodeIterator(doc, "/results/race["+iRaceIndex+"]/driver["+i+"]/grid");
			strGridPosition = ni.nextNode().getFirstChild().getNodeValue();         
			         			
			IRacePositionDetail raceResultDetail = new RacePositionDetail();
			raceResultDetail.setDriverId(strDriverId);
			raceResultDetail.setRacePosition(strRacePosition);
			raceResultDetail.setLapsCompleted(Integer.parseInt(strLapsCompleted));
			raceResultDetail.setGridPosition(Integer.parseInt(strGridPosition));
			
			raceResult.addRacePositionDetail(raceResultDetail);			
		}
		
		m_racesResults.put(strRaceId, raceResult);     			         
	}

	private void loadRules()
	   throws Exception
	{
		Document doc = getDocument(IConstants.RULES_RELATIVE_FILEPATH);
		// let's see how many rules we have defined
		NodeIterator ni = XPathAPI.selectNodeIterator(doc, "/rules/rule");
		int nodesCount = 0;
		while ((ni.nextNode())!= null)
        {
			nodesCount++;
        }
        
        // now lookup in the doc and build the rules map
        for (int i = 1 ; i <= nodesCount ; i++)
        {
        	loadRule(doc, i);
        }

	}
	
	private void loadRule(Document doc, int iRuleIndex)
	   throws Exception
	{	
		// let's see how many cases have for this rule
		NodeIterator ni = XPathAPI.selectNodeIterator(doc, "/rules/rule["+iRuleIndex+"]/case");
		int nodesCount = 0;
		while ((ni.nextNode())!= null)
		{
			nodesCount++;
		}
		
		String strRuleId = null;
		String strName = null;
		String strDescription = null;
		String strIsApplicable = null;
		
		ni = XPathAPI.selectNodeIterator(doc, "/rules/rule["+iRuleIndex+"]/id");
		strRuleId = ni.nextNode().getFirstChild().getNodeValue();
		
		ni = XPathAPI.selectNodeIterator(doc, "/rules/rule["+iRuleIndex+"]/name");
		strName = ni.nextNode().getFirstChild().getNodeValue();
		
		ni = XPathAPI.selectNodeIterator(doc, "/rules/rule["+iRuleIndex+"]/description");
		strDescription = ni.nextNode().getFirstChild().getNodeValue();
		
		ni = XPathAPI.selectNodeIterator(doc, "/rules/rule["+iRuleIndex+"]/isapplicable");
		strIsApplicable = ni.nextNode().getFirstChild().getNodeValue();
		
		IRule rule = new Rule(strRuleId, strName, strDescription, strIsApplicable);
		
		// now lookup in the doc and build the rules map
		for (int i = 1 ; i <= nodesCount ; i++)
		{
			loadRuleCase(doc, iRuleIndex, i, rule);
		}
		
		m_rules.put(strRuleId, rule);     			         
		
	}
	
	private void loadRuleCase(Document doc, int iRuleIndex, int iRuleCase, IRule rule)
	   throws Exception
	{
		// let's see how many conditions this rule case defines
		NodeIterator ni = XPathAPI.selectNodeIterator(doc, "/rules/rule["+iRuleIndex+"]/case["+iRuleCase+"]/condition");
		int nodesCount = 0;
		while ((ni.nextNode())!= null)
		{
			nodesCount++;
		}
		
		ni = XPathAPI.selectNodeIterator(doc, "/rules/rule["+iRuleIndex+"]/case["+iRuleCase+"]/fantasypoints");
		String strFormula = ni.nextNode().getFirstChild().getNodeValue();
		IRuleCase ruleCase = new RuleCase();
		ruleCase.setFormula(strFormula);
		
		// now lookup in the doc and build the rules map
		for (int i = 1 ; i <= nodesCount ; i++)
		{
			loadRuleCaseCondition(doc, iRuleIndex, iRuleCase, i, ruleCase);
		}
		
		rule.addRuleCase(ruleCase);     			         
		 
	}
	
	private void loadRuleCaseCondition( Document doc,
	                                    int iRuleIndex,
	                                    int iRuleCase,
	                                    int iRuleCaseCondition,
	                                    IRuleCase ruleCase )
	    throws Exception
	{
		// if neither the key nor the value exist, that means no condition
		NodeIterator ni =
		   XPathAPI.selectNodeIterator(doc, "/rules/rule["+iRuleIndex+"]/case["+iRuleCase+"]/condition["+iRuleCaseCondition+"]/key");
		Node node = ni.nextNode();
		if (node == null)
		{
			// no condition
			return;
		}
		Node fc = node.getFirstChild();		
		String strKey = fc.getNodeValue();
		
		ni = XPathAPI.selectNodeIterator(doc, "/rules/rule["+iRuleIndex+"]/case["+iRuleCase+"]/condition["+iRuleCaseCondition+"]/value");
		node = ni.nextNode();
		if (node == null)
		{
			// no condition
			return;
		}
		fc = node.getFirstChild();		
		String strValue = fc.getNodeValue();
		
		ruleCase.addCondition(strKey, strValue);
	}
	
	
	private void loadPlayers()
	   throws Exception
	{
		File playersXmlFile = new File(ROOT_FOLDER_PATH + IConstants.PLAYERS_RELATIVE_FILEPATH);
		if (!playersXmlFile.exists())
		{
			// no players registered yet
			return;
		}
		
		Document doc = getDocument(IConstants.PLAYERS_RELATIVE_FILEPATH);
		// let's see how many rules we have defined
		NodeIterator ni = XPathAPI.selectNodeIterator(doc, "/players/player");
		int nodesCount = 0;
		while ((ni.nextNode())!= null)
		{
			nodesCount++;
		}
		
		String strPlayerId = null;
		String strPlayerName = null;
		String strPlayerDescription = null;
		String strPlayerEmail = null;
		String strPlayerDriverId1 = null;
		String strPlayerDriverId2 = null;
		String strPlayerTeamId1 = null;
		String strPlayerTeamId2 = null;
		String strTieBreakerAnswer = null;
		String strPaymentReceived = null;
		
		// now lookup in the doc and build the rules map
		for (int i = 1 ; i <= nodesCount ; i++)
		{
			ni = XPathAPI.selectNodeIterator(doc, "/players/player["+i+"]/id");
			strPlayerId = ni.nextNode().getFirstChild().getNodeValue();
			
			ni = XPathAPI.selectNodeIterator(doc, "/players/player["+i+"]/name");
			strPlayerName = ni.nextNode().getFirstChild().getNodeValue();         
			         
			ni = XPathAPI.selectNodeIterator(doc, "/players/player["+i+"]/description");
			strPlayerDescription = ni.nextNode().getFirstChild().getNodeValue();         
			         
			ni = XPathAPI.selectNodeIterator(doc, "/players/player["+i+"]/email");
			strPlayerEmail = ni.nextNode().getFirstChild().getNodeValue();
			
			ni = XPathAPI.selectNodeIterator(doc, "/players/player["+i+"]/tiebreakeranswer");
			strTieBreakerAnswer = ni.nextNode().getFirstChild().getNodeValue();			
						         
			ni = XPathAPI.selectNodeIterator(doc, "/players/player["+i+"]/paymentreceived");
			strPaymentReceived = ni.nextNode().getFirstChild().getNodeValue();			

			ni = XPathAPI.selectNodeIterator(doc, "/players/player["+i+"]/picks/driver1");
			strPlayerDriverId1 = ni.nextNode().getFirstChild().getNodeValue();
			         
			ni = XPathAPI.selectNodeIterator(doc, "/players/player["+i+"]/picks/driver2");
			strPlayerDriverId2 = ni.nextNode().getFirstChild().getNodeValue();

			ni = XPathAPI.selectNodeIterator(doc, "/players/player["+i+"]/picks/team1");
			strPlayerTeamId1 = ni.nextNode().getFirstChild().getNodeValue();

			ni = XPathAPI.selectNodeIterator(doc, "/players/player["+i+"]/picks/team2");
			strPlayerTeamId2 = ni.nextNode().getFirstChild().getNodeValue();
			
			IPlayer player = new Player(strPlayerId, strPlayerName, strPlayerDescription);
			
			player.setEmailAddress(strPlayerEmail);
			player.setTieBreakerAnswer(Integer.parseInt(strTieBreakerAnswer));
			
			player.setDriverId1(strPlayerDriverId1);
			getDriverById(strPlayerDriverId1).addPick();
			
			player.setDriverId2(strPlayerDriverId2);
			getDriverById(strPlayerDriverId2).addPick();

			player.setTeamId1(strPlayerTeamId1);
			getTeamById(strPlayerTeamId1).addPick();
			
			player.setTeamId2(strPlayerTeamId2);
			getTeamById(strPlayerTeamId2).addPick();
			
 		    player.setPaymentReceived((new Boolean(strPaymentReceived)).booleanValue());
			
			m_players.put(strPlayerId, player);
		}
	}
		

	private Document getDocument(String strRelativePath)
	   throws Exception
	{
		String strAbsolutePath = ROOT_FOLDER_PATH + strRelativePath;
		Document doc = null;
        DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
        FileInputStream fis = new FileInputStream(strAbsolutePath);
        doc = dfactory.newDocumentBuilder().parse(new InputSource(fis));
        fis.close();
		return doc;
	}
	
/**
 * ******************************************************
 * 
 *          Statistics-related methods
 * 
 * ******************************************************
 */		

    public int getRaceResultsCount()
    {
    	return m_racesResults.size();
    }    
    
	private String drawStatsMostWanted(String strPlayerComponentType)
	{		
		boolean bIsDriver = strPlayerComponentType.equals("drivers");
    	
		StringBuffer sb = new StringBuffer();
		sb.append("<table width='100%' cellspacing='6'>");
		sb.append("<tr>");
		sb.append("<td align='center' valign='top'>");
		sb.append("<table border='0' cellspacing='2' cellpadding='2'><tr>");
		sb.append("<td align='right' class=\"tablelayout\">Pos&nbsp;&nbsp;&nbsp;</td>");
		if (bIsDriver)
		{
			sb.append("<td class=\"tablelayout\">Driver</td>");
			sb.append("<td align='right' class=\"tablelayout\">Cost&nbsp;&nbsp;&nbsp;</td>");
			sb.append("<td class=\"tablelayout\">Team&nbsp;&nbsp;&nbsp;</td>");
		}
		else
		{
			sb.append("<td class=\"tablelayout\">Car</td>");
			sb.append("<td class=\"tablelayout\">Cost</td>");
			sb.append("<td class=\"tablelayout\">Driver 1</td>");
			sb.append("<td class=\"tablelayout\">Driver 2</td>");
		}
		sb.append("<td align='right' class=\"tablelayout\">No. of picks</td>");
		sb.append("</tr>");
		
		List list = null;
		if (bIsDriver)
		{
			list = Arrays.asList(m_drivers.values().toArray());
		}
		else
		{
			list = Arrays.asList(m_teams.values().toArray());
		}
		
		Collections.sort(list, new Comparator()
		{
			public int compare (Object o1, Object o2)
			{
				IPlayerComponent pc1 = (IPlayerComponent)o1;
				IPlayerComponent pc2 = (IPlayerComponent)o2;
				return (-1)*(new Integer(pc1.getPicks())).compareTo(new Integer(pc2.getPicks()));
			}				
		});
		
		IPlayerComponent playerComponent = null;
		int iPosCount = 1;
		for (Iterator iter =list.iterator() ; iter.hasNext() ; )
		{
			playerComponent = (IPlayerComponent)iter.next();
			// alternating the bg color
			if (iPosCount % 2 == 0)
			{
			   sb.append("<tr class='standingsRowBackgroundColor'>");
			}
			else
			{
				sb.append("<tr class='standingsRowAltBackgroundColor'>");
			}
			sb.append("<td align='right' class=\"tablelayout\">");
			sb.append(iPosCount);
			sb.append("&nbsp;&nbsp;&nbsp;</td>");
			
			sb.append("<td class=\"tablelayout\">");
			sb.append(playerComponent.getName());
			sb.append("</td>");
			sb.append("<td align='right' class=\"tablelayout\">");
			sb.append(playerComponent.getCost());
			sb.append("&nbsp;&nbsp;&nbsp;</td>");
			if (bIsDriver )
			{
				sb.append("<td class=\"tablelayout\">");
				sb.append(getTeamById(((IDriver)playerComponent).getTeamId()).getName());
				sb.append("&nbsp;&nbsp;&nbsp;</td>");
			}
			else
			{
				sb.append("<td class=\"tablelayout\">");
				sb.append( getDriverById(((ITeam)playerComponent).getDriverId1()).getName());
				sb.append("</td>");
				sb.append("<td class=\"tablelayout\">");
				sb.append( getDriverById(((ITeam)playerComponent).getDriverId2()).getName());
				sb.append("</td>");
			}
			sb.append("<td align='left' class=\"tablelayout\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			sb.append(playerComponent.getPicks());
			sb.append("</td></tr>");
			iPosCount ++;
		}
		sb.append("</table>");
		sb.append("</td></tr></table>");
		return sb.toString();
	}


    private String drawStatsPlayerComponentsOverall(String strPlayerComponentType)
    {		
    	boolean bIsDriver = strPlayerComponentType.equals("drivers");
    	
    	StringBuffer sb = new StringBuffer();
		sb.append("<table width='100%' cellspacing='6'>");
		sb.append("<tr>");
		sb.append("<td align='center' valign='top'>");
    	sb.append("<table border='0' cellspacing='2' cellpadding='2'><tr>");
		sb.append("<td align='right' class=\"tablelayout\">Pos&nbsp;&nbsp;&nbsp;</td>");
		if (bIsDriver)
		{
			sb.append("<td class=\"tablelayout\">Driver</td>");
			sb.append("<td align='right' class=\"tablelayout\">Cost&nbsp;&nbsp;&nbsp;</td>");
			sb.append("<td class=\"tablelayout\">Team&nbsp;&nbsp;&nbsp;</td>");
			sb.append("<td align='right' class=\"tablelayout\">Points</td>");
		}
		else
		{
			sb.append("<td class=\"tablelayout\">Car</td>");
			sb.append("<td class=\"tablelayout\">Cost</td>");
			sb.append("<td class=\"tablelayout\">Driver 1</td>");
			sb.append("<td class=\"tablelayout\">Points 1</td>");
			sb.append("<td class=\"tablelayout\">Driver 2</td>");
			sb.append("<td class=\"tablelayout\">Points 2&nbsp;&nbsp;&nbsp;</td>");
			sb.append("<td class=\"tablelayout\">Avg. pts</td>");
		}
		sb.append("</tr>");
		
		List list = null;
		if (bIsDriver)
		{
			list = Arrays.asList(m_drivers.values().toArray());
			Collections.sort(list, new Driver());
		}
		else
		{
			list = Arrays.asList(m_teams.values().toArray());
			Collections.sort(list, new Team());
		}
		
		IPlayerComponent playerComponent = null;
		int iPosCount = 1;
		for (Iterator iter =list.iterator() ; iter.hasNext() ; )
		{
			playerComponent = (IPlayerComponent)iter.next();
			// alternating the bg color
			if (iPosCount % 2 == 0)
			{
			   sb.append("<tr class='standingsRowBackgroundColor'>");
			}
			else
			{
				sb.append("<tr class='standingsRowAltBackgroundColor'>");
			}
			sb.append("<td align='right' class=\"tablelayout\">");
			sb.append(iPosCount);
			sb.append("&nbsp;&nbsp;&nbsp;</td>");
			
			sb.append("<td class=\"tablelayout\">");
			sb.append(playerComponent.getName());
			sb.append("</td>");
			sb.append("<td align='right' class=\"tablelayout\">");
			sb.append(playerComponent.getCost());
			sb.append("&nbsp;&nbsp;&nbsp;</td>");
			if (bIsDriver )
			{
				sb.append("<td class=\"tablelayout\">");
				sb.append(getTeamById(((IDriver)playerComponent).getTeamId()).getName());
				sb.append("&nbsp;&nbsp;&nbsp;</td>");
			}
			else
			{
				sb.append("<td class=\"tablelayout\">");
				sb.append( getDriverById(((ITeam)playerComponent).getDriverId1()).getName());
				sb.append("</td>");
				sb.append("<td align='right' class=\"tablelayout\">");
				sb.append( StringUtil.formatFloat(getDriverById(((ITeam)playerComponent).getDriverId1()).getGrandTotalPoints()));
				sb.append("&nbsp;&nbsp;&nbsp;</td>");
				sb.append("<td class=\"tablelayout\">");
				sb.append( getDriverById(((ITeam)playerComponent).getDriverId2()).getName());
				sb.append("</td>");
				sb.append("<td align='right' class=\"tablelayout\">");
				sb.append( StringUtil.formatFloat(getDriverById(((ITeam)playerComponent).getDriverId2()).getGrandTotalPoints()));
				sb.append("&nbsp;&nbsp;&nbsp;</td>");
			}
			sb.append("<td align='left' class=\"tablelayout\">");
			sb.append(StringUtil.formatFloat(playerComponent.getGrandTotalPoints()));
			sb.append("</td></tr>");
			iPosCount ++;
		}
		sb.append("</table>");
		sb.append("</td></tr></table>");
    	return sb.toString();
    }
    
    public int getRegisteredPlayersCount()
    {
    	return m_players.size();
    }
    
	private String drawStatsAllTeamStandings()
	   throws Exception
	{
		StringBuffer sb = new StringBuffer();
		sb.append("<table STYLE=\"table-layout:fixed\" width='98%' border='0' align='center' cellspacing='2' cellpadding='2'>");
		sb.append("<COLGROUP>");
		sb.append("<COL WIDTH='6%'>");
		sb.append("<COL WIDTH='14%'>");
		sb.append("<COL WIDTH='6%'>");
		sb.append("<COL WIDTH='15%'>");
		sb.append("<COL WIDTH='15%'>");
		sb.append("<COL WIDTH='11%'>");
		sb.append("<COL WIDTH='11%'>");
		sb.append("<COL WIDTH='7%'>");
		sb.append("<COL WIDTH='15%'>");
		sb.append("</COLGROUP>");
		sb.append("<tr>");
		sb.append("<td align='right' class=\"tablelayout\">Pos&nbsp;&nbsp;&nbsp;</td>");
		
		sb.append("<td class=\"tablelayout\">Team Name&nbsp;&nbsp;&nbsp;</td>");
		sb.append("<td class=\"tablelayout\">Cost</td>");
		sb.append("<td class=\"tablelayout\">Driver1</td>");
		sb.append("<td class=\"tablelayout\">Driver2</td>");
		sb.append("<td class=\"tablelayout\">Car1&nbsp;&nbsp;&nbsp;</td>");
		sb.append("<td class=\"tablelayout\">Car2&nbsp;&nbsp;&nbsp;</td>");
		sb.append("<td class=\"tablelayout\">Points&nbsp;&nbsp;&nbsp;</td>");
		sb.append("<td class=\"tablelayout\" align='center'>Who is it?</td>");
		sb.append("</tr>");
		Map standings = new TreeMap(new PlayerPositionComparator());
		IPlayer player = null;
		PlayerPositionComparator ppc = null;
		IPlayerComponent firstDriver = null;
		IPlayerComponent secondDriver = null;
		IPlayerComponent firstCar = null;
		IPlayerComponent secondCar = null;
		String firstDriverId = null;
		String secondDriverId = null;
		String firstCarId = null;
		String secondCarId = null;
		int iValidPlayersCount = 1;
		for (int i = 0 ; i <= m_drivers.size() ; i++)
		{
			firstDriverId = "driver_"+ ("0"+(i+1)).substring(("0"+(i+1)).length() - 2);
			firstDriver = this.getDriverById(firstDriverId);
			for (int j = i+1 ; j <= m_drivers.size() ; j++)
			{
				secondDriverId = "driver_"+ ("0"+(j+1)).substring(("0"+(j+1)).length() - 2);
				secondDriver = this.getDriverById(secondDriverId);
				for (int k = 0 ; k < m_teams.size() ; k++)
				{
					firstCarId = "team_"+ ("0"+(k+1)).substring(("0"+(k+1)).length() - 2);
					firstCar = this.getTeamById(firstCarId);
					for (int l = k+1 ; l < m_teams.size() ;l++)
					{
						secondCarId = "team_"+ ("0"+(l+1)).substring(("0"+(l+1)).length() - 2);
						secondCar = this.getTeamById(secondCarId);
						if (firstDriver == null || secondDriver == null || (firstDriver.getCost() + secondDriver.getCost() + firstCar.getCost() + secondCar.getCost()) > WebConfig.PLAYER_BUDGET)
						{
							continue;
						}
						else
						{
							player = new Player(null, "Team_"+("000"+(iValidPlayersCount)).substring(("000"+(iValidPlayersCount)).length() - 4), null);
							player.setDriverId1(firstDriverId);
							player.setDriverId2(secondDriverId);
							player.setTeamId1(firstCarId);
							player.setTeamId2(secondCarId);
							ppc = new PlayerPositionComparator( player.getGrandTotalPoints(),
																(int)(100000000*Math.random()));
							standings.put(ppc, player);
							iValidPlayersCount++;
						}						
					}
				}				
			}
		}

		int iPosCount = 1;		
		Set sortedPlayers = standings.keySet();
		String strPlayerName = null;
		for (Iterator iter = sortedPlayers.iterator() ; iter.hasNext() ; )
		{
			player = (IPlayer)standings.get(iter.next());
			if ((strPlayerName = isRegisteredPlayer(player)) != null)
			{
				sb.append("<tr class='standingsMatchedPlayerRowBackgroundColor'>");
			}
			else
			{
			   strPlayerName = "***";
			   // alternating the bg color
			   if (iPosCount % 2 == 0)
			   {
   			      sb.append("<tr class='standingsRowBackgroundColor'>");
			   }
			   else
			   {
			      sb.append("<tr class='standingsRowAltBackgroundColor'>");
			   }
			}
			
			sb.append("<td align='right' class=\"tablelayout\">");
			sb.append(iPosCount);
			sb.append("&nbsp;&nbsp;&nbsp;</td>");

			sb.append("<td class=\"tablelayout\">");
			sb.append(player.getName());
			sb.append("&nbsp;&nbsp;&nbsp;</td>");

			sb.append("<td class=\"tablelayout\">");
			sb.append(player.getCost());
			sb.append("</td>");

			sb.append("<td class=\"tablelayout\">");
			sb.append(getDriverById(player.getDriverId1()).getName());
			sb.append("</td>");

			sb.append("<td class=\"tablelayout\">");
			sb.append(getDriverById(player.getDriverId2()).getName());
			sb.append("</td>");

			sb.append("<td class=\"tablelayout\">");
			sb.append(getTeamById(player.getTeamId1()).getName());
			sb.append("&nbsp;&nbsp;&nbsp;</td>");
			
			sb.append("<td class=\"tablelayout\">");
			sb.append(getTeamById(player.getTeamId2()).getName());
			sb.append("&nbsp;&nbsp;&nbsp;</td>");

			sb.append("<td class=\"tablelayout\">");
			sb.append(StringUtil.formatFloat(player.getGrandTotalPoints()));
			sb.append("&nbsp;&nbsp;&nbsp;</td>");

			sb.append("<td class=\"tablelayout\" align='center'>");
			sb.append(strPlayerName);
			sb.append("</td></tr>");
			
			iPosCount++;
		}
		sb.append("</table>");
		return sb.toString();
		
	}
    
	private String drawStandingsForRaceHeader(String strRaceId)
    {
		   StringBuffer sb = new StringBuffer();
		   IRace race = getRaceById(strRaceId);
		   sb.append("<table border='0' width='490'>");
		   sb.append("<tr>");
		   sb.append("<td width='490' nowrap class='tablelayoutheader' align='center' valign='middle'>");
		   sb.append("-----&nbsp;&nbsp;" + race.getName());
		   sb.append("&nbsp;&nbsp;(");
		   sb.append(new SimpleDateFormat("MMM-dd-yyyy").format(race.getDate()));
		   sb.append(")&nbsp;&nbsp;-----</td>");
		   sb.append("</tr></table>");
		   return sb.toString();
    }

	private String drawStandingsForRaceBody(String strRaceId, String strRenderReturn)
	   throws Exception
	{
		if (strRaceId == null)
		{
			return "<table width='100%' border='0'><tr><td class=\"tablelayout\" align='center' valign='top'>Error. Race to draw the standings for was not received.</td></tr></table>";
		}
		
		String strDriverId = getDriverIdByDriverNameAndRaceId(WebConfig.TIEBREAKER_DRIVER_NAME, "race_01");
		IDriver tieBreakerDriverName = this.getDriverById(strDriverId);
		int iTieBreakerRealResponse = tieBreakerDriverName.getTotalRealPoints();
		PlayerPositionComparator ppc = null;
		
		StringBuffer sb = new StringBuffer();
		sb.append("<table width='100%' cellspacing='6'>");
		sb.append("<tr>");
		sb.append("<td align='center' valign='top'>");
		sb.append("<table border='0' cellspacing='2' cellpadding='2'>");	
		Map standings = new TreeMap(new PlayerPositionComparator());
		Set set = m_players.keySet();
		IPlayer player = null;
		for (Iterator iter = set.iterator() ; iter.hasNext() ; )
		{
			player = (IPlayer)m_players.get(iter.next());
			
			int tieBreakerDecision = Math.abs(player.getTieBreakerAnswer() - iTieBreakerRealResponse);
			
			ppc = new PlayerPositionComparator( player.getRacePoints(strRaceId), tieBreakerDecision );
			
			standings.put(ppc, player);			
		}

		int iPosCount = 1;
		Set sortedPlayers = standings.keySet();
		String strDropdownClass = null;						
		for (Iterator iter = sortedPlayers.iterator() ; iter.hasNext() ; )
		{						
			// alternating the bg color
			if (iPosCount % 2 == 0)
			{
			   sb.append("<tr class='standingsRowBackgroundColor'>");
			   strDropdownClass = "dropdown";
			}
		    else
		    {
				sb.append("<tr class='standingsRowAltBackgroundColor'>");
				strDropdownClass = "altdropdown";
		    }
		    
			player = (IPlayer)standings.get(iter.next());

			sb.append("<td width=30' nowrap class='tablelayout' align='right' valign='middle'>"+iPosCount+"</td>");
			// Team Name
			sb.append("<td width='175' nowrap class='tablelayout' align='left' valign='middle'>&nbsp;&nbsp;&nbsp;"+player.getName()+"</td>");
			// Team Manager
			sb.append("<td width='175' nowrap class='tablelayout' align='left' valign='middle'>"+player.getDescription()+"</td>");
			// race points
			sb.append("<td width='60' nowrap class='tablelayout' align='left' valign='middle'>"+StringUtil.formatFloat(player.getRacePoints(strRaceId))+"&nbsp;&nbsp;&nbsp;</td>");
			// details link
			sb.append("<td width='50' class='tablelayout' align='right' valign='middle'>");
			sb.append("<a href=\"playerDetails.jsp?playerId=");
			sb.append(player.getId());
			sb.append("&raceId=");
			sb.append(strRaceId);
			sb.append("\" target=\"dataframe\">[&nbsp;details&nbsp;]</a>");
			sb.append("</td>");
						
			sb.append("</tr>");
			iPosCount++;
		}				
		sb.append("</table></td></tr>");
		if (strRenderReturn != null && !strRenderReturn.equalsIgnoreCase("null"))
		{
			sb.append("<tr><td align='center' class=\"tablelayout\"><a href=\"statsBody.jsp?component=");
			sb.append(strRenderReturn);
			sb.append("\" target=\"dataframe\">Return</a></td><tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

    private String drawStatsWinners() throws Exception
    {		
		String strDriverId = getDriverIdByDriverNameAndRaceId(WebConfig.TIEBREAKER_DRIVER_NAME, "race_01");
		IDriver tieBreakerDriverName = this.getDriverById(strDriverId);
		int iTieBreakerRealResponse = tieBreakerDriverName.getTotalRealPoints();
		
		PlayerPositionComparator ppc = null;
		
		StringBuffer sb = new StringBuffer();
		sb.append("<table width='100%' cellspacing='6'>");
		sb.append("<tr>");
		sb.append("<td align='center' valign='top'>");
		sb.append("<table border='0' cellspacing='2' cellpadding='2'><tr>");
		sb.append("<td class=\"tablelayout\">Team Name</td>");
		sb.append("<td class=\"tablelayout\">Team Manager</td>");
		sb.append("<td class=\"tablelayout\">Races Won</td>");
		sb.append("<td class=\"tablelayout\">Details</td></tr>");
		
		Map raceStandings = null;
		Map winnersStandings = new TreeMap(new PlayerPositionComparator());
		
		Set racesResultsSet = m_racesResults.keySet();
		for (Iterator raceIter = racesResultsSet.iterator() ; raceIter.hasNext() ; )
		{
			raceStandings = new TreeMap(new PlayerPositionComparator());
			IRaceResult raceResult = (IRaceResult)m_racesResults.get(raceIter.next());
			Set set = m_players.keySet();
			IPlayer player = null;
			for (Iterator iter = set.iterator() ; iter.hasNext() ; )
			{
				player = (IPlayer)m_players.get(iter.next());
				
				int tieBreakerDecision = Math.abs(player.getTieBreakerAnswer() - iTieBreakerRealResponse);
				
				ppc = new PlayerPositionComparator( player.getRacePoints(raceResult.getRaceId()), tieBreakerDecision );
				
				raceStandings.put(ppc, player);			
			}
			Set sortedPlayers = raceStandings.keySet();
			
			// now get the winner for this race
			for (Iterator sortedPlayersIter = sortedPlayers.iterator() ; sortedPlayersIter.hasNext() ; )
			{
				IPlayer raceWinner = (IPlayer)raceStandings.get(sortedPlayersIter.next());
				raceWinner.addWinner(getRaceById(raceResult.getRaceId()));
				break;
			}
		}
		
		// now build the winners standings
		Set set = m_players.keySet();
		IPlayer player = null;
		for (Iterator iter = set.iterator() ; iter.hasNext() ; )
		{
			player = (IPlayer)m_players.get(iter.next());
			
			int winnersCount = 0;
			List winners = player.getWinners();
			if (winners !=  null)
			{
				winnersCount = winners.size();
			}
			
			ppc = new PlayerPositionComparator( winnersCount, (int)(100000000*Math.random()) );
			
			winnersStandings.put(ppc, player);			
		}
		
		Set sortedPlayers = winnersStandings.keySet();
		

		int iPosCount = 1;
		String strDropdownClass = null;						
		for (Iterator iter = sortedPlayers.iterator() ; iter.hasNext() ; )
		{						
			// alternating the bg color
			if (iPosCount % 2 == 0)
			{
			   sb.append("<tr class='standingsRowBackgroundColor'>");
			   strDropdownClass = "dropdown";
			}
		    else
		    {
				sb.append("<tr class='standingsRowAltBackgroundColor'>");
				strDropdownClass = "altdropdown";
		    }
		    
			player = (IPlayer)winnersStandings.get(iter.next());

			// Team Name
			sb.append("<td width='175' nowrap class='tablelayout' align='left' valign='middle'>&nbsp;&nbsp;&nbsp;"+player.getName()+"</td>");
			// Team Manager
			sb.append("<td width='175' nowrap class='tablelayout' align='left' valign='middle'>"+player.getDescription()+"</td>");

			// the races won
			int winnersCount = 0;
			List winners = player.getWinners();
			if (winners !=  null)
			{
				winnersCount = winners.size();
			}
			sb.append("<td width='60' nowrap class='tablelayout' align='left' valign='middle'>"+winnersCount+"&nbsp;&nbsp;&nbsp;</td>");
			
			// details link
			sb.append("<td width='50' class='tablelayout' valign='middle'>");
			
			for (int i = 0 ; i < winnersCount ; i++)
			{
				IRace raceWon = (IRace)winners.get(i);
				sb.append("<a href=\"statsBody.jsp?component=");
				sb.append(IConstants.STATS_RACE_STANDINGS);
				sb.append("&raceId=");
				sb.append(raceWon.getId());
				sb.append("&renderReturn=6");
				sb.append("\" target=\"dataframe\">");
				sb.append(getRaceShortName(raceWon.getName()));
				sb.append("</a>&nbsp;&nbsp;");
			}
			sb.append("</td>");
						
			sb.append("</tr>");
			player.resetWinners();
			iPosCount++;
		}				
		sb.append("</table></td></tr></table>");
		return sb.toString();
    }

    private String drawStatsTop25() throws Exception
    {		
		PlayerPositionComparator ppc = null;
		
		StringBuffer sb = new StringBuffer();
		sb.append("<table width='100%' cellspacing='6'>");
		sb.append("<tr>");
		sb.append("<td align='center' valign='top'>");
		sb.append("<table border='0' cellspacing='2' cellpadding='2'><tr>");
		sb.append("<td class=\"tablelayout\">Pos</td>");
		sb.append("<td class=\"tablelayout\">Team Name</td>");
		sb.append("<td class=\"tablelayout\">Team Manager</td>");
		sb.append("<td class=\"tablelayout\">Score</td>");
		sb.append("<td class=\"tablelayout\">Race</td></tr>");
		
		Map playerScoresMap = new TreeMap(new PlayerPositionComparator());
		Map raceScoresMap = new TreeMap(new PlayerPositionComparator());
		
		Set racesResultsSet = m_racesResults.keySet();
		for (Iterator raceIter = racesResultsSet.iterator() ; raceIter.hasNext() ; )
		{
			IRaceResult raceResult = (IRaceResult)m_racesResults.get(raceIter.next());
			Set set = m_players.keySet();
			IPlayer player = null;
			for (Iterator iter = set.iterator() ; iter.hasNext() ; )
			{
				player = (IPlayer)m_players.get(iter.next());
				ppc = new PlayerPositionComparator( player.getRacePoints(raceResult.getRaceId()), (int)(100000000*Math.random()) );
				playerScoresMap.put(ppc, player);
				raceScoresMap.put(ppc, getRaceById(raceResult.getRaceId()));
			}
		}
		
		Set sortedPlayers = playerScoresMap.keySet();

		int iPosCount = 1;
		String strDropdownClass = null;						
		for (Iterator iter = sortedPlayers.iterator() ; iter.hasNext() ; )
		{						
			if (iPosCount > 25) break;
			
			// alternating the bg color
			if (iPosCount % 2 == 0)
			{
			   sb.append("<tr class='standingsRowBackgroundColor'>");
			   strDropdownClass = "dropdown";
			}
		    else
		    {
				sb.append("<tr class='standingsRowAltBackgroundColor'>");
				strDropdownClass = "altdropdown";
		    }
		    
			Object key = iter.next();
			IPlayer player = (IPlayer)playerScoresMap.get(key);

			// position
			sb.append("<td width=30' nowrap class='tablelayout' align='right' valign='middle'>"+iPosCount+"</td>");

			// Team Name
			sb.append("<td width='175' nowrap class='tablelayout' align='left' valign='middle'>&nbsp;&nbsp;&nbsp;"+player.getName()+"</td>");
			// Team Manager
			sb.append("<td width='175' nowrap class='tablelayout' align='left' valign='middle'>"+player.getDescription()+"</td>");

			IRace race = (IRace)raceScoresMap.get(key);
			sb.append("<td width='60' nowrap class='tablelayout' align='left' valign='middle'>"+StringUtil.formatFloat(player.getRacePoints(race.getId()))+"&nbsp;&nbsp;&nbsp;</td>");
			
			// details link
			sb.append("<td width='50' class='tablelayout' valign='middle'>");
			
			sb.append("<a href=\"statsBody.jsp?component=");
			sb.append(IConstants.STATS_RACE_STANDINGS);
			sb.append("&raceId=");
			sb.append(race.getId());
			sb.append("&renderReturn=7");
			sb.append("\" target=\"dataframe\">");
			sb.append(getRaceShortName(race.getName()));
			sb.append("</a>&nbsp;&nbsp;");
			sb.append("</td>");
						
			sb.append("</tr>");
			iPosCount++;
		}				
		sb.append("</table></td></tr></table>");
		return sb.toString();
    }

    private String isRegisteredPlayer(IPlayer player)
    {
    	StringBuffer sb = new StringBuffer("");
    	IPlayer regPlayer = null;
    	for (Iterator iter = m_players.values().iterator() ; iter.hasNext() ; )
    	{
			regPlayer = (IPlayer)iter.next();
    		if (regPlayer.equals(player))
    		{
    			sb.append(regPlayer.getName());
				sb.append(", ");
    		}
    	}
    	
    	String strResult = sb.toString();
    	if (strResult.length() > 0)
    	{
    		// removing the trailing comma and space
    		strResult = strResult.substring(0, strResult.length()-2);
    		return strResult;
    	}
    	
    	return null;	
    }
    
    public synchronized void setAdminPassword(String strPassword)
    {
		ADMIN_PASSWORD = strPassword;
    }
    
    
	private synchronized String getAdminPassword()
	{
		String strPwd = ADMIN_PASSWORD;		
		// reset the password
		ADMIN_PASSWORD = null;
		return strPwd;
	}
	
	public String drawStatsHeader(String strComponent, String strRaceId)
	{
		String strResult = "";

	    int iRaceResultsCount = getRaceResultsCount();
	    if (iRaceResultsCount == 0)
	    {
	    	return strResult;
	    }
	    		
		if (strComponent != null)
		{
			int iCommand = Integer.parseInt(strComponent);
			
			switch (iCommand)
			{
				case IConstants.STATS_ALL_TEAM_STAND:
				   strResult = "How would you rank if competing against all the possible Teams?";
				   break;
				   
				case IConstants.STATS_DRIVERS_PERF:
				   strResult = "Drivers Performance (Fantasy Points) after " + iRaceResultsCount + " races";
				   break;
				
				case IConstants.STATS_CARS_PERF:
				   strResult = "Cars Performance (Fantasy Points) after " + iRaceResultsCount + " races";
				   break;
				   
				case IConstants.STATS_MOST_PICKED_DRIVER:
				strResult = "This table shows how the drivers were picked by Players";
				   break;
				   
				case IConstants.STATS_MOST_PICKED_CAR:
				   strResult = "This table shows how the cars were picked by Players";
				   break;

				case IConstants.STATS_RACE_STANDINGS:
					   strResult = drawStandingsForRaceHeader(strRaceId);
					   break;

				case IConstants.STATS_WINNERS:
					   strResult = "This table shows how many races any of you had won";
					   break;

				case IConstants.STATS_TOP_25:
					   strResult = "This table shows the top 25 of all time scores";
					   break;
			}
			
		}
		return strResult;
	}
	
	public String drawStatsBody(String strComponent, String strRaceId, String strRenderReturn)
	   throws Exception
	{
		String strResult = "<table width='100%' cellspacing='6'><tr><td class=\"tablelayout\" align='center' valign='top'>This will be available after the F1 season starts.</td></tr></table>";

		int iRaceResultsCount = getRaceResultsCount();
		if (iRaceResultsCount == 0)
		{
			return strResult;
		}
	    		
		if (strComponent != null)
		{
			int iCommand = Integer.parseInt(strComponent);
			
			switch (iCommand)
			{
				case IConstants.STATS_ALL_TEAM_STAND:
				   strResult = drawStatsAllTeamStandings();
				   break;
				   
				case IConstants.STATS_DRIVERS_PERF:
				   strResult = drawStatsPlayerComponentsOverall("drivers");
				   break;
				
				case IConstants.STATS_CARS_PERF:
				   strResult = drawStatsPlayerComponentsOverall("cars");
				   break;
				   
				case IConstants.STATS_MOST_PICKED_DRIVER:
				   strResult = drawStatsMostWanted("drivers");
				   break;
				   
				case IConstants.STATS_MOST_PICKED_CAR:
				   strResult = drawStatsMostWanted("cars");
				   break;

				case IConstants.STATS_RACE_STANDINGS:
					   strResult = drawStandingsForRaceBody(strRaceId, strRenderReturn);
					   break;

				case IConstants.STATS_WINNERS:
					   strResult = drawStatsWinners();
					   break;

				case IConstants.STATS_TOP_25:
					   strResult = drawStatsTop25();
					   break;
			}
			
		}
		return strResult;
	}
	
	public String renderRaceStandingStatControl()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("<select id=\"statRaceSelectorCtrl\" onchange=\"displayRaceStandings('dataframe',this)\">\r\n");
		sb.append("<option value=\"nohref\" selected>-select a race-\r\n");
		
		Object[] racesResultsSet = m_racesResults.keySet().toArray();
		IRaceResult raceResult;
		for (int i=racesResultsSet.length-1; i>=0; i--)
		{
			raceResult = (IRaceResult)m_racesResults.get(racesResultsSet[i]);
			sb.append("<option value=\"statsBody.jsp?component=");
			sb.append(IConstants.STATS_RACE_STANDINGS);
			sb.append("&raceId=");
			sb.append(raceResult.getRaceId());
			sb.append("\">");
			String raceName = getRaceById((raceResult.getRaceId())).getName();
			sb.append(getRaceShortName(raceName));
			sb.append("</option>\r\n");
		}
		sb.append("</select>\r\n");
		return sb.toString();
	}
	
	private String getRaceShortName(String raceName)
	{
		StringTokenizer st = new StringTokenizer(raceName, " ");
		String rnShort = "";
		boolean isOfPassed = false;
		while (st.hasMoreTokens())
		{
			String token = st.nextToken();
			if ("of".equalsIgnoreCase(token))
			{
				isOfPassed = true;
				continue;
			}
			
			if (isOfPassed)
			{
				rnShort += token; 
			}
		}
		return rnShort;
	}
	

    // The Unique instance for our manager
    private static CompetitionManager m_CompetitionManager;

	private static SortedMap m_rules = null;
	private static SortedMap m_races = null;
	private static SortedMap m_racesResults = null;
	private static SortedMap m_drivers = null;
	private static SortedMap m_teams = null;
	private static SortedMap m_players = null;
	
	private static boolean m_blHaveRulesBeenApplied = false;
	public static String ROOT_FOLDER_PATH = null;
	private static String ADMIN_PASSWORD = null;
}

/**
 * Comparator class for helping the Standings printout
 * @author mrcsiki
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
class PlayerPositionComparator implements Comparator
{
	
	public PlayerPositionComparator ()
	{
	}
	
	public PlayerPositionComparator ( float fTotalPoints,
  									  int iTieBreakerDecision )
	{
		m_fTotalPoints = fTotalPoints;
		m_iTieBreakerDecision = iTieBreakerDecision;
	}
		
	private float getTotalPoints()
	{
		return m_fTotalPoints;
	}

		
	private int getTieBreakerDecision()
	{
		return m_iTieBreakerDecision;
	}
	
	public int compare (Object o1, Object o2)
	{
		PlayerPositionComparator ppc1 = (PlayerPositionComparator)o1;
		PlayerPositionComparator ppc2 = (PlayerPositionComparator)o2;
		if(ppc1.getTotalPoints() != ppc2.getTotalPoints())
		{
			// descending order by total points
			return (-1)*((new Float(ppc1.getTotalPoints())).compareTo(new Float(ppc2.getTotalPoints())));
		}
		else
		{
			// ascending by tiebreaker decision (i.e. whoever Player gets closer with his response goes first)
			return ( (new Integer(ppc1.getTieBreakerDecision())).compareTo(new Integer(ppc2.getTieBreakerDecision())));
		}
	}

	private float m_fTotalPoints;
	private int m_iTieBreakerDecision;			
}

