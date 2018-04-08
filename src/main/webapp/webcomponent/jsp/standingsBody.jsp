<%@ page errorPage="error.jsp" import="com.robertcsiki.f1.web.manager.CompetitionManager, com.robertcsiki.f1.web.util.FormUtil "%>

<HTML>
<HEAD>
    <link rel="stylesheet" href='<%=FormUtil.makeURL(request, "/webcomponent/interface/style/globalstyle.css")%>' type="text/css">
    <script language='JavaScript1.2' src='<%=FormUtil.makeURL(request, "/webcomponent/interface/javascript/js_functions.js")%>'></script>
    
<%
		// ensure the rules have been applied to drivers and teams
		// for all the completed races
		CompetitionManager cm = CompetitionManager.getInstance();
		cm.applyRules();
		boolean paidOnly = (request.getParameter("paidonly") != null);
		
%>
</HEAD>
<BODY CLASS="inner" onload="top.mainframe.headerframe.location.href='standingsHeader.jsp'" onunload="top.mainframe.headerframe.location.href='<%=FormUtil.makeURL(request, "/webcomponent/html/empty.html")%>'">
<%=cm.drawOverallStandingsBody(paidOnly)%>
</BODY>
</HTML>