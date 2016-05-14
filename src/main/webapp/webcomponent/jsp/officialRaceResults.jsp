<%@ page errorPage="error.jsp"  import="com.robertcsiki.f1.web.manager.CompetitionManager,com.robertcsiki.f1.web.util.FormUtil"%>
<HTML>
<HEAD>
<%
   String strRaceId = request.getParameter("raceId");
   String strTitle = request.getParameter("windowTitle");
   CompetitionManager cm = CompetitionManager.getInstance();
%>
      <TITLE><%=strTitle%></TITLE>
     <link rel="stylesheet" href='<%=FormUtil.makeURL(request, "/webcomponent/interface/style/globalstyle.css")%>' type="text/css">
</HEAD>


   <BODY CLASS="inner"><%=cm.getRaceById(strRaceId).getRaceResult().printOfficialResults(true)%></BODY>
</HTML>    