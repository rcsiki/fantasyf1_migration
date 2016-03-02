<%@ page errorPage="error.jsp" import="com.homelinux.bela.web.manager.CompetitionManager, com.homelinux.bela.web.util.FormUtil "%>

<HTML>
<HEAD>
    <link rel="stylesheet" href='<%=FormUtil.makeURL(request, "/webcomponent/interface/style/globalstyle.css")%>' type="text/css">
    <script language='JavaScript1.2' src='<%=FormUtil.makeURL(request, "/webcomponent/interface/javascript/js_functions.js")%>'></script>
    
<%
		// ensure the rules have been applied to drivers and teams
		// for all the completed races
		CompetitionManager cm = CompetitionManager.getInstance();
		cm.applyRules();
%>
</HEAD>
<BODY CLASS="inner" onLoad="top.mainframe.headerframe.location.href='standingsHeader.jsp'" onunload="top.mainframe.headerframe.location.href='<%=FormUtil.makeURL(request, "/webcomponent/html/empty.html")%>'">
<%=cm.drawOverallStandingsBody()%>
</BODY>
</HTML>