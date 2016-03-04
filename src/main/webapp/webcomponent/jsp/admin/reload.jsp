<%@ page errorPage="../error.jsp" import="java.lang.System,com.homelinux.bela.web.manager.CompetitionManager,com.homelinux.bela.web.util.FormUtil"%>

<%
  String strPwd = request.getParameter("admword");
  if (strPwd != null)
  {
     // set the app root path and app data path
     if (CompetitionManager.APP_DATA_FOLDER_PATH == null)
     {	      
         String strOpenShiftDataDir =  System.getenv("OPENSHIFT_DATA_DIR");
         String strOpenShiftRepoDir =  System.getenv("OPENSHIFT_REPO_DIR");
         if (strOpenShiftRepoDir == null && strOpenShiftDataDir == null){
             // the app is not deployed on OpenShift
             CompetitionManager.APP_ROOT_FOLDER_PATH = application.getRealPath("/");
             CompetitionManager.APP_DATA_FOLDER_PATH = CompetitionManager.APP_ROOT_FOLDER_PATH;
         }
         else {
             // the app is deployed on OpenShift
             CompetitionManager.APP_ROOT_FOLDER_PATH = strOpenShiftRepoDir + "target/fantasy/";
             CompetitionManager.APP_DATA_FOLDER_PATH = strOpenShiftDataDir;
         }
     }
     CompetitionManager.getInstance().setAdminPassword(strPwd);  
  }
%>

<HTML>
  <HEAD>
	<TITLE>Fantasy Formula 1</TITLE>
  </HEAD>
  <BODY onload="window.location.href='<%=FormUtil.makeURL(request, "index.html")%>'"></BODY>
</HTML>