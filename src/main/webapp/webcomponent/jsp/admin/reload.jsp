<%@ page errorPage="../error.jsp" import="java.lang.System,com.robertcsiki.f1.web.manager.CompetitionManager,com.robertcsiki.f1.web.util.FormUtil"%>

<%
  String strPwd = request.getParameter("admword");
  if (strPwd != null)
  {
     CompetitionManager.getInstance().setAdminPassword(strPwd);  
  }
%>

<HTML>
  <HEAD>
	<TITLE>Fantasy Formula 1</TITLE>
  </HEAD>
  <BODY onload="window.location.href='<%=FormUtil.makeURL(request, "index.jsp")%>'"></BODY>
</HTML>