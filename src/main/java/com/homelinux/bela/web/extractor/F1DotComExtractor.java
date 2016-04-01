/*
 * Created on 5-Feb-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class F1DotComExtractor extends ExtractorBase {
	
    public F1DotComExtractor() {
        mXsl = CompetitionManager.APP_ROOT_FOLDER_PATH + IConstants.F1DOTCOM_XSL_RELATIVE_FILEPATH;
    }

    /**
     * @param results
     * @throws ExtractorHelperException
     */
    @Override
    protected void postProcessDocument(Document results) throws ExtractorException {

		try {
            Node node, node2, node3;
            NodeIterator ni, ni2, ni3;
            String name, name2, xpath, xpath2, xpath3;
			
			Node firstChild;
			boolean bHasErrors = false;
			
            // Replace race id with its description
			xpath = "/results/race/id";
			ni = XPathAPI.selectNodeIterator(results, xpath);
			node = ni.nextNode().getFirstChild();
			name = node.getNodeValue().trim();
			String raceId = getRaceId(name);
			node.setNodeValue(raceId);

            // Get driver id based on their name
            xpath = "/results/race/driver/first";
            xpath2 = "/results/race/driver/last";
            xpath3 = "/results/race/driver/id";
			ni = XPathAPI.selectNodeIterator(results, xpath);
            ni2 = XPathAPI.selectNodeIterator(results, xpath2);
            ni3 = XPathAPI.selectNodeIterator(results, xpath3);
            while ((node = ni.nextNode()) != null && (node2 = ni2.nextNode()) != null
                    && (node3 = ni3.nextNode()) != null) {
				firstChild = node.getFirstChild();
				if(firstChild != null)
				{
  				   name = firstChild.getNodeValue();
                    firstChild = node2.getFirstChild();
                    if (firstChild != null)
                    {
                        name2 = firstChild.getNodeValue();
                        String driverId = getDriverId(name + " " + name2, raceId);
                        // get the driver id by name and race id
                        node3.getFirstChild().setNodeValue(driverId);
                        // remove the name nodes
                        // node.getParentNode().removeChild(node);
                        // node2.getParentNode().removeChild(node2);
                    }
                    else
                    {
                        bHasErrors = true;
                    }
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
     * @param url
     * @return
     * @throws ExtractorHelperException
     */
    @Override
    protected Document getDocument(URL url) throws ExtractorException {
        try {
            URLConnection inConnection = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    inConnection.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String inputLine;
            String h3 = "<h3 class=\"title race-title hidden\">";
            String tableBegin = "<table>";
            String tableEnd = "</table>";
            boolean canAppend = false;
            // boolean isSecond = false;
            boolean passedh3 = false;
            while ((inputLine = br.readLine()) != null) {
                if (!passedh3 && (inputLine.indexOf(h3) != -1)) {
                    passedh3 = true;
                    continue;
                }
                if (canAppend) {
                    if (inputLine.indexOf(tableEnd) != -1) {
                        sb.append(tableEnd);
                        break;
                    }
                    sb.append(inputLine);
                }

                if (!canAppend && (inputLine.indexOf(tableBegin) != -1)) {
                    sb.append(tableBegin);
                    canAppend = true;
                }
            }
            br.close();

            return tidy(sb.toString());

        } catch (IOException e) {
            throw new ExtractorException("Unable to perform input/output", e);
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        CompetitionManager.APP_ROOT_FOLDER_PATH = "./src/main/webapp/";
        CompetitionManager.APP_DATA_FOLDER_PATH = "./src/main/webapp/";
        try {
            new F1DotComExtractor()
                    .extract(new URL(
                            "http://www.formula1.com/content/fom-website/en/championship/results/2016-race-results/2016-australia-results/race.html"));
        } catch (MalformedURLException x) {
            System.err.println("Wrong URL:\n" + x.getMessage());
        } catch (ExtractorException x) {
            System.err.println(
                    "There was an error in the extraction process:\n" +
                            x.getMessage());
            x.printStackTrace();
        }
    }
}