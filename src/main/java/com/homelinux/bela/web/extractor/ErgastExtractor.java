package com.homelinux.bela.web.extractor;

import com.homelinux.bela.web.fc.IConstants;
import com.homelinux.bela.web.manager.CompetitionManager;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ErgastExtractor extends ExtractorBase {

    private static final String LAST_RACE_RESULTS_URL = "http://ergast.com/api/f1/current/last/results";

    public ErgastExtractor() {
        mXsl = CompetitionManager.APP_ROOT_FOLDER_PATH + IConstants.ERGAST_XSL_RELATIVE_FILEPATH;
    }

    public void extract() throws ExtractorException {
        extract(LAST_RACE_RESULTS_URL);
    }


    @Override
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

            return ExtractorHelper.parseXMLFromString(sb.toString());

        } catch (IOException e) {
            throw new ExtractorException("Unable to perform input/output", e);
        }
    }

    /**
     * @param results
     * @throws ExtractorHelperException
     */
    @Override
    protected void postProcessDocument(Document results) throws ExtractorException {

        try {
            Node node;
            NodeIterator nodeIterator;
            String xpath, fastestLapName = null;
            boolean bHasErrors = false;

            // Race id
            xpath = "/results/race/id";
            nodeIterator = XPathAPI.selectNodeIterator(results, xpath);
            node = nodeIterator.nextNode().getFirstChild();
            String raceId = node.getNodeValue().trim();
            if (raceId.length() == 1) {
                raceId = "0" + raceId;
            }
            raceId = "race_" + raceId;
            node.setNodeValue(raceId);

            // Get driver id based on their name
            xpath = "/results/race/driver";
            nodeIterator = XPathAPI.selectNodeIterator(results, xpath);
            while ((node = nodeIterator.nextNode()) != null) {
                String givenName = node.getAttributes().getNamedItem("givenName")
                        .getNodeValue();
                String familyName = node.getAttributes().getNamedItem("familyName")
                        .getNodeValue();
                String fastestLapRank = node.getAttributes()
                        .getNamedItem("fastestLapRank").getNodeValue();
                if (givenName != null && givenName.length() > 0 && familyName != null
                        && familyName.length() > 0)
                {
                    // save the fastest lap full driver name
                    // for later processing
                    if ("1".equals(fastestLapRank)) {
                        fastestLapName = givenName + " " + familyName;
                    }
                    // get the driver id by name and race id
                    String driverId = getDriverId(givenName + " " + familyName, raceId);
                    // set it to the 'id' child node
                    Node childNode = null;
                    bHasErrors = true;
                    // fix the id and pos node values
                    for (int i = 0; i < node.getChildNodes().getLength(); i++) {
                        childNode = node.getChildNodes().item(i);
                        if ("pos".equals(childNode.getLocalName())) {
                            String nodeValue = childNode.getFirstChild().getNodeValue();
                            String newNodeValue = null;
                            if (nodeValue != null) {
                                newNodeValue = nodeValue;
                                if ("R".equalsIgnoreCase(nodeValue)) {
                                    // retired
                                    newNodeValue = "ret";
                                }
                                else if ("W".equalsIgnoreCase(nodeValue)) {
                                    // did not start
                                    newNodeValue = "dns";
                                }
                                else if ("E".equalsIgnoreCase(nodeValue)) {
                                    // disqualified
                                    newNodeValue = "dsq";
                                }
                                childNode.getFirstChild().setNodeValue(newNodeValue);
                            }
                        }
                        else if ("id".equals(childNode.getLocalName())) {
                            childNode.getFirstChild().setNodeValue(driverId);
                            bHasErrors = false;
                        }
                    }
                }
                else
                {
                    bHasErrors = true;
                }
            }

            // if errors, the fastest lap info gets screwed as well
            if (!bHasErrors)
            {
                // Get the fastest driver name and replace it with their id
                xpath = "/results/race/fastestlap";
                nodeIterator = XPathAPI.selectNodeIterator(results, xpath);
                node = nodeIterator.nextNode().getFirstChild();
                node.setNodeValue(getDriverId(fastestLapName, raceId));
            }

        } catch (Throwable e) {
            throw new ExtractorException("Problem during transformation process", new Exception(e));
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        CompetitionManager.APP_ROOT_FOLDER_PATH = "./src/main/webapp/";
        CompetitionManager.APP_DATA_FOLDER_PATH = "./src/main/webapp/";
        try {
            new ErgastExtractor().extract();
        } catch (ExtractorException x) {
            System.err.println(
                    "There was an error in the extraction process:\n" +
                            x.getMessage());
            x.printStackTrace();
        }
    }
}
