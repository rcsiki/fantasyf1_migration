<%@ page errorPage="error.jsp" import="com.robertcsiki.f1.web.manager.CompetitionManager,com.robertcsiki.f1.web.util.FormUtil"%>

<HTML>
<HEAD>
    <link rel="stylesheet" href='<%=FormUtil.makeURL(request, "/webcomponent/interface/style/globalstyle.css")%>' type="text/css">
</HEAD>
<BODY MARGINHEIGHT=0 TOPMARGIN=0 CLASS="inner">
<%
       // get the race ID and player ID, passed as parameters
       String strPlayerId = request.getParameter("playerId");
       String strRaceId = request.getParameter("raceId");
       
       // print out the details for this player and race
       out.write(CompetitionManager.getInstance().printPlayerRaceDetails(strPlayerId, strRaceId));
%>
</BODY>
</HTML>