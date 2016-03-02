/*
 * Created on 11-Feb-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.homelinux.bela.web.extractor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.homelinux.bela.web.fc.IConstants;
import com.homelinux.bela.web.manager.CompetitionManager;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ExtractorHelper {
	
	public static String DRIVERS, RACES_RESULTS, RACES, XSL, TEMP;
	
	static {
		RACES = CompetitionManager.ROOT_FOLDER_PATH + IConstants.RACES_RELATIVE_FILEPATH;
		DRIVERS = CompetitionManager.ROOT_FOLDER_PATH + IConstants.DRIVERS_RELATIVE_FILEPATH;
		RACES_RESULTS = CompetitionManager.ROOT_FOLDER_PATH + IConstants.RACE_RESULTS_RELATIVE_FILEPATH;
		XSL = CompetitionManager.ROOT_FOLDER_PATH + IConstants.XSL_RELATIVE_FILEPATH;
		TEMP = CompetitionManager.ROOT_FOLDER_PATH + IConstants.TEMP_RELATIVE_FILEPATH;
	}

	/**
	 * 
	 * @param mergeToXML
	 * @param mergeFromXML
	 * @param childrenOnly
	 */
	public static void mergeXML(Element mergeToXML, Element mergeFromXML, boolean childrenOnly) {
		Document toDoc = mergeToXML.getOwnerDocument();
		Element copyElem = (Element)(toDoc.importNode(mergeFromXML,true));
		if (childrenOnly) {
			NodeList nlist = copyElem.getChildNodes();
			for (int i=0; i < nlist.getLength(); i++) {
				org.w3c.dom.Node n = nlist.item(i);
				mergeToXML.appendChild(n);
			}
			return;
		} else {
			mergeToXML.appendChild(copyElem);
		}
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 * @throws ExtractorHelperException
	 */
	public static Document tidyHTML(URL url) throws ExtractorException {
		try {
			URLConnection inConnection = url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(inConnection.getInputStream()));
			StringBuffer sb = new StringBuffer(); 
			String inputLine;
			String tableBegin = "<table width=\"100%\" height=\"10\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">";
			String tableEnd = "</table>";
			boolean canAppend = false;
			boolean isSecond = false;
			
			while ((inputLine = br.readLine()) != null) {
				if (canAppend) {
					if ( inputLine.indexOf(tableEnd) != -1 ) {
						if (isSecond) {
							sb.append(tableEnd);
							break;
						}
						isSecond = true;
					}
					sb.append(inputLine);
				}
				
				if ( !canAppend && (inputLine.indexOf(tableBegin) != -1) ) {
					sb.append(tableBegin);
					canAppend = true;
				}
			} 
			br.close();

			InputStream bais = new ByteArrayInputStream(sb.toString().getBytes());
				
			org.w3c.tidy.TagTable tags = new org.w3c.tidy.TagTable();
			tags.defineBlockTag("script");
				
			Tidy tidy = new Tidy();
			
			tidy.setShowWarnings(false);
			tidy.setXmlOut(true);
			tidy.setXmlPi(false);
			tidy.setDocType("omit");
			tidy.setXHTML(false);
			tidy.setRawOut(true);
			tidy.setNumEntities(true);
			tidy.setQuiet(true);
			tidy.setFixComments(true);
			tidy.setIndentContent(true);
			tidy.setCharEncoding(org.w3c.tidy.Configuration.ASCII);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			org.w3c.tidy.Node tNode = tidy.parse(bais, baos);
			
			String result = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
							baos.toString();
				
			bais.close();
			baos.close();

			return parseXMLFromString(result);
			
		} catch (IOException e) {
			throw new ExtractorException("Unable to perform input/output", e);
		}
	}

	/**
	 * 
	 * @param source
	 * @return
	 * @throws ExtractorHelperException
	 */
	public static Document parseXMLFromString(String source) throws ExtractorException {		
		InputSource is = new InputSource(new StringReader(source));
		return parseXMLFromInputSource(is);
	}
	
	/**
	 * 
	 * @param sourceFile
	 * @return
	 * @throws ExtractorHelperException
	 */
	public static Document parseXMLFromFile(File sourceFile) throws ExtractorException {
		InputSource is = null;
		try {
			is = new InputSource(new FileInputStream(sourceFile));
		} catch (FileNotFoundException e) {
			throw new ExtractorException("The XML file could not be retrieved", e);
		}
		return parseXMLFromInputSource(is);
	}
	
	/**
	 * 
	 * @param is
	 * @return
	 * @throws ExtractorHelperException
	 */
	private static Document parseXMLFromInputSource(InputSource is) throws ExtractorException {
		Document doc = null;
		try {
			DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
			doc = dfactory.newDocumentBuilder().parse(is);
		//} catch (FactoryConfigurationError e) {
		//	throw new ExtractorHelperException("Unable to read from source string", e);
		} catch (SAXException e) {
			throw new ExtractorException("Unable to parse the given souce", e);
		} catch (IOException e) {
			throw new ExtractorException("Unable to read from source", e);
		} catch (ParserConfigurationException e) {
			throw new ExtractorException("Unable to parse the given souce", e);
		}
		return doc;
	}

	/**
	 * 
	 * @param doc
	 * @param fileName
	 * @throws ExtractorHelperException
	 */
	public static void outputXMLToFile(Document doc, String fileName) throws ExtractorException {
		try {
			OutputFormat of = new OutputFormat(doc);
			of.setIndenting(true);
			File f = new File(fileName);
			FileOutputStream fos = new FileOutputStream(f);
			XMLSerializer serializer = new XMLSerializer(fos, of);
			serializer.serialize(doc);
			fos.close();
		} catch (IOException ioe) {
			throw new ExtractorException("Unable to write to the given file", ioe);
		}
	}	
}
