/*
 * Created on Feb 12, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.homelinux.bela.web.config;

import java.io.FileInputStream;
import java.util.Date;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.InputSource;

import com.homelinux.bela.web.fc.IConstants;
import com.homelinux.bela.web.manager.CompetitionManager;


/**
 * This class is used to access the configuration for the web stuff
 */
public class WebConfig
{

    /** Player budget */
	public static int PLAYER_BUDGET;
		
	/** Encoding type for XMLs */
    public static String CONTENT_TYPE;
    
	/** Stores the number of races for the season */
    public static int RACES_THIS_SEASON;
    
	/** Stores the registration deadline*/
	public static Date REGISTRATION_DEADLINE;

	/** Stores the admin password*/
	public static String ADMIN_PWD;

	/** Driver name to use for the tie breaker case*/
	public static String TIEBREAKER_DRIVER_NAME;

	static
    {
    	load();
    }
    
    public static final void load()
    {
		try
		{
		   String strAppXmlAbsolutePath = CompetitionManager.ROOT_FOLDER_PATH + IConstants.APP_RELATIVE_FILEPATH;
		   Document doc = null;
		   FileInputStream fis = new FileInputStream(strAppXmlAbsolutePath);
		   DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
		   doc = dfactory.newDocumentBuilder().parse(new InputSource(fis));
		   fis.close();
		   
		   NodeIterator ni = XPathAPI.selectNodeIterator(doc, "/app/config/playerbudget");
		   PLAYER_BUDGET = Integer.parseInt(ni.nextNode().getFirstChild().getNodeValue()); 
		   ni = XPathAPI.selectNodeIterator(doc, "/app/config/contenttype");
		   CONTENT_TYPE = ni.nextNode().getFirstChild().getNodeValue(); 
		   ni = XPathAPI.selectNodeIterator(doc, "/app/config/seasonracecount");
		   RACES_THIS_SEASON = Integer.parseInt(ni.nextNode().getFirstChild().getNodeValue());		   
		   ni = XPathAPI.selectNodeIterator(doc, "/app/config/regdeadline");
		   REGISTRATION_DEADLINE = new Date(ni.nextNode().getFirstChild().getNodeValue());
		   ni = XPathAPI.selectNodeIterator(doc, "/app/config/adminpwd");
		   ADMIN_PWD = ni.nextNode().getFirstChild().getNodeValue();
		   ni = XPathAPI.selectNodeIterator(doc, "/app/config/tiebreakerdrivername");
		   TIEBREAKER_DRIVER_NAME = ni.nextNode().getFirstChild().getNodeValue();
		}
		catch(Throwable x)
		{
			System.out.println("Failed to load app.xml file! - will use default values.");
			PLAYER_BUDGET = 36;
			CONTENT_TYPE = "UTF-8";
			RACES_THIS_SEASON = 18;
			REGISTRATION_DEADLINE = new Date("Fri Mar 14 24:00:00 EST 2008");
			ADMIN_PWD = "belizna";
			TIEBREAKER_DRIVER_NAME = "Jenson Button";
		}
    }
}