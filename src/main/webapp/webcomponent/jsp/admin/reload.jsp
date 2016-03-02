<%@ page errorPage="../error.jsp" import="com.homelinux.bela.web.manager.CompetitionManager,com.homelinux.bela.web.util.FormUtil"%>

<%
  String strPwd = request.getParameter("admword");
  if (strPwd != null)
  {
     // set the root path
     if (CompetitionManager.ROOT_FOLDER_PATH == null)
     {	      
         // CompetitionManager.ROOT_FOLDER_PATH = application.getRealPath("/");
         CompetitionManager.ROOT_FOLDER_PATH = "/var/lib/openshift/56d645d789f5cfb5710000be/app-root/runtime/repo/target/fantasy/";
     }
     CompetitionManager.getInstance().setAdminPassword(strPwd);  
  }
%>

<HTML>
  <HEAD>
	<TITLE>2008 Fantasy Formula 1</TITLE>
  </HEAD>
  <BODY onload="window.location.href='<%=FormUtil.makeURL(request, "index.html")%>'"></BODY>
</HTML>