<%@ page errorPage="../error.jsp" import="java.lang.System,com.homelinux.bela.web.manager.CompetitionManager,com.homelinux.bela.web.util.FormUtil"%>

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