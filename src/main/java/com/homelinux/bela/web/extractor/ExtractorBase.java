package com.homelinux.bela.web.extractor;

import com.homelinux.bela.web.manager.CompetitionManager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;

public class ExtractorBase {

    protected String mXsl;

    public ExtractorBase() {

    }

    protected final void appendResults(Document results) throws ExtractorException {
        Element root = results.getDocumentElement();
        NodeList nl = root.getElementsByTagName("race");
        Element race = (Element) nl.item(0);

        File xmlFile = new File(ExtractorHelper.RACES_RESULTS);
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

    public void extract(String url) throws ExtractorException {
        try {
            extract(new URL(url));
        } catch (MalformedURLException e) {
            throw new ExtractorException("Problem during transformation process", e);
        }
    }

    /**
     * @param source
     * @throws ExtractorHelperException
     */
    protected void extract(URL source) throws ExtractorException {

        try {
            Document docSource = getDocument(source);
            // ExtractorHelper.outputXMLToFile(docSource, ExtractorHelper.TEMP);
            // Document docSource = ExtractorHelper.parseXMLFromFile(new
            // File(ExtractorHelper.TEMP));

            // Use the DOM Document to define a DOMSource object.
            DOMSource xmlDomSource = new DOMSource(docSource);

            // Create an empty DOMResult for the Result.
            DOMResult xmlDomResult = new DOMResult();
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory
                    .newTransformer(new StreamSource(mXsl));
            transformer.transform(xmlDomSource, xmlDomResult);
            Document docResult = (Document) xmlDomResult.getNode();

            // ExtractorHelper.outputXMLToFile(docResult, ExtractorHelper.TEMP);
            postProcessDocument(docResult);

            appendResults(docResult);

        } catch (TransformerConfigurationException e) {
            throw new ExtractorException("Transformer configuration error", e);
            // } catch (TransformerFactoryConfigurationError e) {
            // throw new
            // ExtractorHelperException("Transformer configuration error", e);
        } catch (TransformerException e) {
            throw new ExtractorException("Problem during transformation process", e);
        }
    }

    // subclasses to implement
    protected void postProcessDocument(Document results) throws ExtractorException {
    }

    /**
     * @param name
     * @return
     * @throws ExtractorHelperException
     */
    protected String getRaceId(String description) throws ExtractorException {
        try {
            CompetitionManager cm = CompetitionManager.getInstance();
            return cm.getRaceIdByRaceDescription(description);
        } catch (Exception e) {
            throw new ExtractorException("Problem during transformation process", e);
        }
    }

    /**
     * @param name
     * @return
     * @throws ExtractorHelperException
     */
    protected String getDriverId(String name, String raceId) throws ExtractorException {
        try {
            CompetitionManager cm = CompetitionManager.getInstance();
            // get the driver id by driver name and race id
            // (to detect the right one, if some driver replacements occured)
            return cm.getDriverIdByDriverNameAndRaceId(name, raceId);

        } catch (Exception e) {
            throw new ExtractorException("Problem during transformation process", e);
        }
    }

    protected Document getDocument(URL url) throws ExtractorException {
        try {
            URLConnection inConnection = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    inConnection.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            br.close();

            return tidy(sb.toString());

        } catch (IOException e) {
            throw new ExtractorException("Unable to perform input/output", e);
        }
    }

    protected Document tidy(String s) throws ExtractorException {
        InputStream bais = new ByteArrayInputStream(s.getBytes());

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

        tidy.parse(bais, baos);

        String result = baos.toString();
        if (result.indexOf("<?xml version=") == -1) {
            result = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                    result;
        }
        System.out.println(result);

        try {
            bais.close();
            baos.close();

            return ExtractorHelper.parseXMLFromString(result);
        } catch (IOException e) {
            throw new ExtractorException("Unable to perform input/output", e);
        }
    }
}
