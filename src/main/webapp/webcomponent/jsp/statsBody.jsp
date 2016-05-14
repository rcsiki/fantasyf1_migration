<%@ page errorPage="error.jsp" import="com.robertcsiki.f1.web.manager.CompetitionManager,com.robertcsiki.f1.web.util.FormUtil"%>

<HTML>
<HEAD>
    <link rel="stylesheet" href='<%=FormUtil.makeURL(request, "/webcomponent/interface/style/globalstyle.css")%>' type="text/css">
    
<%
		// ensure the rules have been applied to drivers and teams
		// for all the completed races
		CompetitionManager cm = CompetitionManager.getInstance();
		cm.applyRules();
		String strComponent = request.getParameter("component");
		String strRaceId = request.getParameter("raceId");
		String strRenderReturn = request.getParameter("renderReturn");
%>
</HEAD>
<BODY MARGINHEIGHT=0 TOPMARGIN=0 CLASS="inner"   onLoad="top.mainframe.headerframe.location.href='statsHeader.jsp?component=<%=strComponent%>&raceId=<%=strRaceId%>&raceId=<%=strRaceId%>'" onunload="top.mainframe.headerframe.location.href='<%=FormUtil.makeURL(request, "/webcomponent/html/empty.html")%>'">
<%=cm.drawStatsBody(strComponent, strRaceId, strRenderReturn)%>
</BODY>
</HTML>