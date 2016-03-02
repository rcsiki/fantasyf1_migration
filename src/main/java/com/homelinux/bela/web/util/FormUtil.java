/*
 * Created on Feb 16, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.homelinux.bela.web.util;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @author mrcsiki
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FormUtil
{
	/**
	 * Make a full URL by append the current virtual root to the supplied URL
	 *
	 * @param request       request object
	 * @param strUrl        URL of the form /mydir/myfile...
	 *
	 * @return URL of the form /virtualroot/mydir/myfile...
	 */
	public static String makeURL(ServletRequest request, String strUrl)
	{
	   StringBuffer buf = new StringBuffer(strUrl.length() + 10);

	   // append virtual root
	   buf.append(((HttpServletRequest)request).getContextPath());

	   // append original url
	   if (strUrl.charAt(0) != '/')
	   {
		  buf.append('/');
	   }
	   buf.append(strUrl);

	   return buf.toString();
	}
}
