<%@ page errorPage="error.jsp" import="com.robertcsiki.f1.web.manager.CompetitionManager,com.robertcsiki.f1.web.util.FormUtil"%>

<HTML>
<HEAD>
    <link rel="stylesheet" href='<%=FormUtil.makeURL(request, "/webcomponent/interface/style/globalstyle.css")%>' type="text/css">
</HEAD>

<BODY CLASS="inner">
<%=CompetitionManager.getInstance().drawOverallStandingsHeader()%>
</BODY>
</HTML>