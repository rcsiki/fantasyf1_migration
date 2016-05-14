/*
 * Created on 11-Feb-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.robertcsiki.f1.web.extractor;

import com.robertcsiki.f1.web.fc.IConstants;
import com.robertcsiki.f1.web.manager.CompetitionManager;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ExtractorHelper {
	
    public static String DRIVERS, RACES_RESULTS, RACES, TEMP;
	
	static {
		RACES = CompetitionManager.APP_ROOT_FOLDER_PATH + IConstants.RACES_RELATIVE_FILEPATH;
		DRIVERS = CompetitionManager.APP_ROOT_FOLDER_PATH + IConstants.DRIVERS_RELATIVE_FILEPATH;
        RACES_RESULTS = CompetitionManager.APP_DATA_FOLDER_PATH
                + IConstants.RACE_RESULTS_RELATIVE_FILEPATH;
		TEMP = CompetitionManager.APP_ROOT_FOLDER_PATH + IConstants.TEMP_RELATIVE_FILEPATH;
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
