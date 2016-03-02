package com.homelinux.bela.web.servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.homelinux.bela.web.fc.IRacePositionDetail;
import com.homelinux.bela.web.fc.IRaceResult;
import com.homelinux.bela.web.impl.RacePositionDetail;
import com.homelinux.bela.web.impl.RaceResult;
import com.homelinux.bela.web.manager.CompetitionManager;

public class RaceResultsProcessor extends HttpServlet
{
	// services the client's request
	public void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException
	{

		// local variables for the category properties
		IRaceResult raceResult = new RaceResult();
		
		Map positionMap = new HashMap();
		Map driverMap = new HashMap();
		Map lapsMap = new HashMap();
		Map gridMap = new HashMap();

		// get all the request's parameter names
		Enumeration e = req.getParameterNames();
		
		// iterate through
		while(e.hasMoreElements())
		{
			// get the next parameter from this enumeration
			String parameter = (String)e.nextElement();
			
			Integer index = getIndex(parameter);
			
			// check what this parameter represents
			if (parameter.indexOf("selectPos") != -1)
			{
				positionMap.put(index, req.getParameter(parameter));
			}
			else if (parameter.indexOf("selectDriver") != -1)
			{
				driverMap.put(index, req.getParameter(parameter));
			}
			else if (parameter.indexOf("laps") != -1)
			{
				lapsMap.put(index, Integer.valueOf(req.getParameter(parameter)));
			}
			else if (parameter.indexOf("grid") != -1)
			{
				gridMap.put(index, Integer.valueOf(req.getParameter(parameter)));
			}
			else if (parameter.indexOf("fastestLap") != -1)
			{
				raceResult.setFastestLapDriverId(req.getParameter(parameter));
			}
			else if (parameter.indexOf("raceId") != -1)
			{
				raceResult.setRaceId(req.getParameter(parameter));
			}
		}
		
		// populate the race result
		for (Iterator iter = positionMap.keySet().iterator() ; iter.hasNext() ; )
		{
			Integer index = (Integer)iter.next();
			
			IRacePositionDetail racePositionDetail = new RacePositionDetail();
			
			racePositionDetail.setDriverId((String)driverMap.get(index));
			racePositionDetail.setRacePosition((String)positionMap.get(index));
			racePositionDetail.setGridPosition(((Integer)gridMap.get(index)).intValue());
			racePositionDetail.setLapsCompleted(((Integer)lapsMap.get(index)).intValue());
			
			raceResult.addRacePositionDetail(racePositionDetail);
		}
		
		// now update the XML and the in-memory object
		boolean success = false;
		try
		{
			CompetitionManager cm = CompetitionManager.getInstance();
			success = cm.writeRaceResultToXML(raceResult);
			if (success)
			{
				cm.getRaceResults().put(raceResult.getRaceId(), raceResult);
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
			success = false;
		}
		
		res.setContentType("text/html");
		
		String url = "/webcomponent/jsp/admin/editRaceResultDone.jsp";
		if (success)
		{
			// append the race id
			url += "?raceId=" + raceResult.getRaceId();
		}
    	// now redirect the response to a JSP page that will
		// display the results of this operation
		getServletContext().getRequestDispatcher(res.encodeURL(url)).forward(req, res);
	}
	
	
	private Integer getIndex(String strParameter)
	{
		int index = strParameter.indexOf("_");
		if (index != -1)
		{
			return new Integer(Integer.parseInt(strParameter.substring(index+1)));
		}
		return null;
	}
}


