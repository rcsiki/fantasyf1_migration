/*
 * Created on Feb 12, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.robertcsiki.f1.web.config;

import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.InputSource;

import com.robertcsiki.f1.web.fc.IConstants;
import com.robertcsiki.f1.web.manager.CompetitionManager;


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
    
    /** Stores the number of points awarded to the real F1 race winner */
    public static int RACE_WINNER_REAL_POINTS;

    /** Stores the registration deadline */
	public static Date REGISTRATION_DEADLINE;

    /** Stores the registration deadline (as String) */
    public static String REGISTRATION_DEADLINE_TEXT;

    /** Payment deadline (String) */
    public static String PAYMENT_DEADLINE_TEXT;

    /** Payment destination mail address (String) */
    public static String PAYMENT_MAIL_ADDRESS;

    /** Hosts contact info (String) */
    public static String CONTACT_INFO;

    /** Stores the admin password*/
	public static String ADMIN_PWD;

	/** Driver name to use for the tie breaker case*/
	public static String TIEBREAKER_DRIVER_NAME;

    /** The current year */
    public static int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);

	static
    {
    	load();
    }
    
    public static final void load()
    {
		try
		{
		   String strAppXmlAbsolutePath = CompetitionManager.APP_ROOT_FOLDER_PATH + IConstants.APP_RELATIVE_FILEPATH;
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
            ni = XPathAPI.selectNodeIterator(doc, "/app/config/racewinnerrealpoints");
            RACE_WINNER_REAL_POINTS = Integer
                    .parseInt(ni.nextNode().getFirstChild().getNodeValue());
		   ni = XPathAPI.selectNodeIterator(doc, "/app/config/regdeadline");
            REGISTRATION_DEADLINE_TEXT =
                    ni.nextNode().getFirstChild().getNodeValue();
            REGISTRATION_DEADLINE = new Date(REGISTRATION_DEADLINE_TEXT);
            ni = XPathAPI.selectNodeIterator(doc, "/app/config/paymentdeadlinetext");
            PAYMENT_DEADLINE_TEXT = ni.nextNode().getFirstChild().getNodeValue();
            ni = XPathAPI.selectNodeIterator(doc, "/app/config/paymentmailaddress");
            PAYMENT_MAIL_ADDRESS = ni.nextNode().getFirstChild().getNodeValue();
            ni = XPathAPI.selectNodeIterator(doc, "/app/config/contactinfo");
            CONTACT_INFO = ni.nextNode().getFirstChild().getNodeValue();
		   ni = XPathAPI.selectNodeIterator(doc, "/app/config/adminpwd");
		   ADMIN_PWD = ni.nextNode().getFirstChild().getNodeValue();
		   ni = XPathAPI.selectNodeIterator(doc, "/app/config/tiebreakerdrivername");
		   TIEBREAKER_DRIVER_NAME = ni.nextNode().getFirstChild().getNodeValue();
		}
		catch(Throwable x)
		{
			System.out.println("Failed to load app.xml file! - will use default values.");
            x.printStackTrace();
			PLAYER_BUDGET = 36;
			CONTENT_TYPE = "UTF-8";
            RACES_THIS_SEASON = 21;
            RACE_WINNER_REAL_POINTS = 25;
            REGISTRATION_DEADLINE_TEXT = "Fri Mar 18 24:00:00 EDT 2016";
            REGISTRATION_DEADLINE = new Date(REGISTRATION_DEADLINE_TEXT);
            PAYMENT_DEADLINE_TEXT = "April 2nd, 2016";
            PAYMENT_MAIL_ADDRESS = "Laszlo Benedek, 37 Old Orchard Crescent, Richmond Hill, ON, L4S 0A2, Canada";
            CONTACT_INFO = "Robert Csiki at robert.csiki@gmail.com, Laszlo Benedek at benedekl@yahoo.com";
			ADMIN_PWD = "belizna";
            TIEBREAKER_DRIVER_NAME = "Fernando Alonso";
		}
    }
}