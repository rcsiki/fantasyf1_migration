/*
 * Created on Feb 7, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.homelinux.bela.web.util;

/**
 * @author mrcsiki
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class StringUtil
{
	public static String replace(String strSource, String strSearch, String strReplace)
	{
	   int iTo = strSource.indexOf(strSearch);
	   if ( iTo == -1 )
	   {
		  // no occurences of search string in source string - simply return
		  return strSource;
	   }
      
	   StringBuffer buf = new StringBuffer(strSource.length());
      
	   int searchLen = strSearch.length();
	   int iFrom = 0;

	   do 
	   {
		  if ( iTo > iFrom )
		  {
			 buf.append(strSource.substring(iFrom, iTo));
		  }
		  iFrom = iTo + searchLen;
         
		  buf.append(strReplace);
         
		  iTo = strSource.indexOf(strSearch, iFrom);
         
	   } while ( iTo != -1 );
         
	   if ( iFrom < strSource.length() )
	   {
		  buf.append(strSource.substring(iFrom));
	   }
      
	   return buf.toString();
	}
	
	
	/**
	 * Don't show the decimal point if the given float is an integer
	 * 
	 * @param fFloat the float value to analyze
	 * @return a String representing the int value if the float is an int,
	 *         float value otherwise
	 */
	public static String formatFloat(float fFloat)
	{
		String strFormattedFloat = "";
		if ((float)(fFloat - (int)fFloat) != 0f)
		{
			strFormattedFloat += fFloat;
		}
		else
		{
			strFormattedFloat += (int)fFloat;
		}
		return strFormattedFloat;
	}
}
