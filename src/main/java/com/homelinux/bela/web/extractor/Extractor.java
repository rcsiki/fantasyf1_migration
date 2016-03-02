/*
 * Created on 5-Feb-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.homelinux.bela.web.extractor;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.TimerTask;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;

import com.homelinux.bela.web.manager.CompetitionManager;


/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Extractor extends TimerTask {
	
	private String url;
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

/*			
			URL[] urls = {
				new URL("http://www.formula1.com/race/result/2/8.html"), // australia
				new URL("http://www.formula1.com/race/result/3/8.html"), // malaysia
				new URL("http://www.formula1.com/race/result/4/8.html"), // brazil
				new URL("http://www.formula1.com/race/result/21/8.html"), // san marino
				new URL("http://www.formula1.com/race/result/22/8.html"), // spain
				new URL("http://www.formula1.com/race/result/23/8.html"), // austria
				new URL("http://www.formula1.com/race/result/24/8.html"), // monaco
				new URL("http://www.formula1.com/race/result/25/8.html"), // canada
				new URL("http://www.formula1.com/race/result/26/8.html"), // europe
				new URL("http://www.formula1.com/race/result/27/8.html"), // france
				new URL("http://www.formula1.com/race/result/28/8.html"), // britain
				new URL("http://www.formula1.com/race/result/29/8.html"), // germany
				new URL("http://www.formula1.com/race/result/30/8.html"), // hungary
				new URL("http://www.formula1.com/race/result/31/8.html"), // italy
				new URL("http://www.formula1.com/race/result/32/8.html"), // us
				new URL("http://www.formula1.com/race/result/33/8.html") // japan
			};
			
			for (int i=0; i < urls.length; i++ ) {	
				extract(urls[i]);
			}
*/				
			new Extractor().run();
	}
	
	/**
	 * 
	 */
	public void run() {
		try {
			extract(new URL("http://www.formula1.com/race/result/714/8.html"));
		} catch (MalformedURLException x) {
			System.err.println("Wrong URL:\n" + x.getMessage());
		} catch (ExtractorException x) {
			System.err.println(
				"There was an error in the extraction process:\n" + 
				x.getMessage());
		}
	}
	
	/**
	 * 
	 * @param url
	 * @throws ExtractorException
	 */
	public void extract(String url) throws ExtractorException {
		try {
			extract(new URL(url));
		} catch (MalformedURLException e) {
			throw new ExtractorException("Problem during transformation process", e);
		}
	}
	
	/**
	 * 
	 * @param source
	 * @throws ExtractorHelperException
	 */
	private void extract(URL source) throws ExtractorException {

		try {
			Document docSource = ExtractorHelper.tidyHTML(source);
			//ExtractorHelper.outputXMLToFile(docSource, ExtractorHelper.TEMP);
			//Document docSource = ExtractorHelper.parseXMLFromFile(new File(ExtractorHelper.TEMP));
			
			// Use the DOM Document to define a DOMSource object.
			DOMSource xmlDomSource = new DOMSource(docSource);
			
			// Create an empty DOMResult for the Result.
			DOMResult xmlDomResult = new DOMResult();
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer(new StreamSource(ExtractorHelper.XSL));
			transformer.transform(xmlDomSource, xmlDomResult);
			Document docResult = (Document)xmlDomResult.getNode();
			
			//ExtractorHelper.outputXMLToFile(docResult, ExtractorHelper.TEMP);
			processDocument(docResult);
			
			appendResults(docResult);
			
		} catch (TransformerConfigurationException e) {
			throw new ExtractorException("Transformer configuration error", e);
		//} catch (TransformerFactoryConfigurationError e) {
		//	throw new ExtractorHelperException("Transformer configuration error", e);
		} catch (TransformerException e) {
			throw new ExtractorException("Problem during transformation process", e);
		}
	}
	
	/**
	 * 
	 * @param results
	 * @throws ExtractorHelperException
	 */
	private void processDocument(Document results) throws ExtractorException {

		try {
			Node node;
			NodeIterator ni;
			String name, xpath;
			
			// robert
			Node firstChild;
			boolean bHasErrors = false;
			
			// robert: moved this RaceID detection on the top of the method
			// because we need the it afterwards (for detecting the
			// correct driver id by name if some driver replacements took place)
			
			// Replace race name with it's id
			xpath = "/results/race/id";
			ni = XPathAPI.selectNodeIterator(results, xpath);
			node = ni.nextNode().getFirstChild();
			name = node.getNodeValue().trim();
			String raceId = getRaceId(name);
			node.setNodeValue(raceId);

			//Get driver id based on his name 
			xpath = "/results/race/driver/id";
			ni = XPathAPI.selectNodeIterator(results, xpath);
			while ((node = ni.nextNode()) != null) {
				firstChild = node.getFirstChild();
				// robert - the result table may be incomplete
				// (example: Monaco 2003 result table has just 19 rows,
				// one driver  - Jenson Button - did not start the race) 
				if(firstChild != null)
				{
  				   name = firstChild.getNodeValue();
				   // robert - get the driver id by name and race id
				   node.getFirstChild().setNodeValue(getDriverId(name, raceId));
				}
				else
				{
					bHasErrors = true;
				}
			}

			// robert - if errors, the fastest lap info gets screwed as well
			if (!bHasErrors)
			{
			   // Get the fastest driver name and replace it with he's id
			   xpath = "/results/race/fastestlap";
			   ni = XPathAPI.selectNodeIterator(results, xpath);
			   node = ni.nextNode().getFirstChild();
			   name = node.getNodeValue();
			   int idx1 = "Fastest Lap:".length();
			   int idx2 = name.indexOf('1');
			   name = name.substring(idx1, idx2).trim();
			   // robert - get the driver id by name and race id
			   node.setNodeValue(getDriverId(name, raceId));
			}
						
		} catch (Throwable e) {
			throw new ExtractorException("Problem during transformation process", new Exception(e));
		}	   		
	}
	
	/**
	 * 
	 * @param results
	 * @throws ExtractorHelperException
	 */
	private void appendResults(Document results) throws ExtractorException {
		Element root = results.getDocumentElement();
		NodeList nl = root.getElementsByTagName("race");
		Element race = (Element)nl.item(0);
				
		File xmlFile  = new File(ExtractorHelper.RACES_RESULTS);
		if (xmlFile.exists()) {
			// If we have extracted before, merge the data and write the file
			Document oldData = ExtractorHelper.parseXMLFromFile(xmlFile);
			ExtractorHelper.mergeXML(oldData.getDocumentElement(), race, false);
			ExtractorHelper.outputXMLToFile(oldData, ExtractorHelper.RACES_RESULTS);
		} else {
			// If this is our first extraction, just write the file
			ExtractorHelper.outputXMLToFile(results, ExtractorHelper.RACES_RESULTS);
		}
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 * @throws ExtractorHelperException
	 */
	private String getRaceId(String description) throws ExtractorException {
		try {
			
			// robert - to avoid the expensive per call parsing
			CompetitionManager cm = CompetitionManager.getInstance();
			return cm.getRaceIdByRaceDescription(description);
			
//			Document races = ExtractorHelper.parseXMLFromFile(new File(ExtractorHelper.RACES));
//			String xpath = "/races/race/description[text()='"+ name +"']";
//			NodeIterator ni = XPathAPI.selectNodeIterator(races, xpath);
//			NodeList list = ni.nextNode().getParentNode().getChildNodes();
//			for (int i=1; i<list.getLength(); i++ ) {
//				Node node = list.item(i);
//				if (node.getNodeName().equals("id")) {
//					return node.getFirstChild().getNodeValue();
//				}
//			}
		} catch (Exception e) {
			throw new ExtractorException("Problem during transformation process", e);
		}

//		return null;
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 * @throws ExtractorHelperException
	 */
	private String getDriverId(String name, String raceId) throws ExtractorException {
 	   try {
 	   	
			System.out.println(name);
			
            // robert - to avoid the expensive per call parsing
            CompetitionManager cm = CompetitionManager.getInstance();
            // get the driver id by driver name and race id
            // (to detect the right one, if some driver replacements occured)
            return cm.getDriverIdByDriverNameAndRaceId(name, raceId);
            
//		    Document drivers = ExtractorHelper.parseXMLFromFile(new File(ExtractorHelper.DRIVERS));            
//			String xpath = "/drivers/driver/name[text()='"+ name +"']";
//			NodeIterator ni = XPathAPI.selectNodeIterator(drivers, xpath);
//			NodeList list = ni.nextNode().getParentNode().getChildNodes();
//			for (int i=1; i<list.getLength(); i++ ) {
//				Node node = list.item(i);
//				if (node.getNodeName().equals("id")) {
//					return node.getFirstChild().getNodeValue();
//				}
//			}
		} catch (Exception e) {
			throw new ExtractorException("Problem during transformation process", e);
		}
//		return null;
	}
}