<%@ page errorPage="../error.jsp"
    import="com.robertcsiki.f1.web.manager.CompetitionManager,com.robertcsiki.f1.web.util.FormUtil"%>
    
<%
   String raceId = request.getParameter("raceId");
%>

<HTML>
<HEAD>
    <link rel="stylesheet" href='<%=FormUtil.makeURL(request, "/webcomponent/interface/style/globalstyle.css")%>' type="text/css">
</HEAD>

<%
   if (raceId != null)
   {
%>
 <BODY CLASS="inner">
    <p class='tablelayout'>The race result was successfully saved in memory and file (XML)<BR>
       Please review the standings below, then RELOAD CONFIG.
    </p>
    <%=CompetitionManager.getInstance().getRaceResultById(raceId).printOfficialResults(false)%>
 </BODY>
<%
   }
   else
   {
%>
 <BODY CLASS="inner">
    <p>Sorry, the operation has failed. Consult the logs for errors.</p>
 </BODY>
<%
   }
%>
</HTML>
