<%@ page errorPage="error.jsp" import="com.robertcsiki.f1.web.manager.CompetitionManager,com.robertcsiki.f1.web.util.FormUtil"%>

<HTML>
<HEAD>
    <link rel="stylesheet" href='<%=FormUtil.makeURL(request, "/webcomponent/interface/style/globalstyle.css")%>' type="text/css">
<%
		String strComponent = request.getParameter("component");
		String strRaceId = request.getParameter("raceId");
		CompetitionManager cm = CompetitionManager.getInstance();
%>
</HEAD>

<BODY CLASS="inner">
<table width='100%' height='100%' border='0'>
  <tr>
    <td class="tablelayoutheader" align='center' valign='top'>
      <%=cm.drawStatsHeader(strComponent, strRaceId)%>
    </td>
  </tr>
</table>
</BODY>
</HTML>