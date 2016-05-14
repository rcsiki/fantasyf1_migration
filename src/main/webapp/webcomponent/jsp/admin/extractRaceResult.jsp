<%@ page errorPage="../error.jsp"  import="com.robertcsiki.f1.web.manager.CompetitionManager,com.robertcsiki.f1.web.util.FormUtil"%>
<HTML>
<HEAD>
    <link rel="stylesheet" href='<%=FormUtil.makeURL(request, "/webcomponent/interface/style/globalstyle.css")%>' type="text/css">

   <SCRIPT LANGUAGE='JavaScript1.2'>
      function launchRaceResultExtractor(url)
      {
         var redirectUrl = 'extractRaceResultSubmit.jsp?nextRaceResultUrl='+url;
         top.hiddenframe.location.href = redirectUrl;
      }
   </SCRIPT>
   
</HEAD>

<%
   String strUrl = request.getParameter("nextRaceResultUrlTextField");
   if (strUrl != null)   
   {
%>

     <BODY CLASS="inner" onLoad='launchRaceResultExtractor("<%=strUrl%>");'>
        <TABLE>
          <TR height='50'>
            <TD  class='tablelayout' id='extractraceresulticon'>
              <IMG src= '<%=FormUtil.makeURL(request, "/webcomponent/interface/images/busy.gif")%>' ALT=""></IMG>
            </TD>
            <TD class='tablelayout' id='extractraceresultmessage'>
              Extracting the race result, please wait...
            </TD>
          </TR>
        </TABLE>
      </BODY>
    </HTML>
    
<%
   }
   else
   {
      CompetitionManager cm =  CompetitionManager.getInstance();
%>  
      <BODY CLASS="inner"><%=cm.drawNextRaceResultToExtractForm()%></BODY>     
</HTML>

<%
   }
%>