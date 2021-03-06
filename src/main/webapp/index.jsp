<%@ page errorPage="../error.jsp" import="java.lang.System,com.robertcsiki.f1.web.manager.CompetitionManager"%>

<%
     // set the app root path and app data path
     if (CompetitionManager.APP_DATA_FOLDER_PATH == null)
     {	      
         String strOpenShiftDataDir =  System.getenv("JWS_HOME"); // System.getProperty("java.io.tmpdir");
         String strOpenShiftRepoDir =  System.getenv("JWS_HOME");
         if (strOpenShiftRepoDir == null && strOpenShiftDataDir == null){
             // the app is not deployed on OpenShift
             CompetitionManager.APP_ROOT_FOLDER_PATH = application.getRealPath("/");
             CompetitionManager.APP_DATA_FOLDER_PATH = CompetitionManager.APP_ROOT_FOLDER_PATH;
         }
         else {
             // the app is deployed on OpenShift
             CompetitionManager.APP_ROOT_FOLDER_PATH = strOpenShiftRepoDir + "/webapps/fantasy/";
             CompetitionManager.APP_DATA_FOLDER_PATH = CompetitionManager.APP_ROOT_FOLDER_PATH;
         }
     }
%>
<HTML>
  <HEAD>
	<TITLE>Fantasy Formula 1</TITLE>
	<LINK REL="icon" HREF="http://unguru-bulan.1d35.starter-us-east-1.openshiftapps.com/fantasy/favicon.ico" TYPE="image/vnd.microsoft.icon" />
  </HEAD>

  <FRAMESET ROWS="0,*,0" NORESIZE BORDER="0" FRAMEBORDER="0" FRAMEBORDER="no" FRAMESPACING="0">

    <!-- Top Frame, with the logo and other stuff -->
    <FRAME SRC="webcomponent/html/topFrame.html" SCROLLING="no" NAME="topframe">

    <!-- Viewer Frame -->
    <FRAMESET ROWS="20,*,16,4" NORESIZE BORDER="0" FRAMEBORDER="0" FRAMEBORDER="no" FRAMESPACING="0">

      <!-- this frame can contain some useful info, like a news ticker -->
      <FRAME SRC="webcomponent/html/tickerFrame.html" SCROLLING="no" NAME="tickerframe">

      <!-- this is the main frame, containing the menu (left) and the data (right) -->
      <FRAMESET COLS="10,*,10" NORESIZE BORDER="0" FRAMEBORDER="0" FRAMEBORDER="no" FRAMESPACING="0">
        <FRAME SRC="webcomponent/interface/html/outer_side_l.html" SCROLLING="no">
        <FRAME SRC="webcomponent/html/mainFrame.html" SCROLLING="no" name="mainframe">
	<FRAME SRC="webcomponent/interface/html/outer_side_r.html" SCROLLING="no">
      </FRAMESET>

      <!-- Footer Frame - for other infos -->
      <FRAMESET COLS="10,*,10" NORESIZE BORDER="0" FRAMEBORDER="0" FRAMEBORDER="no" FRAMESPACING="0">
        <FRAME SRC="webcomponent/interface/html/outer_side_l.html" SCROLLING="no">
        <FRAME SRC="webcomponent/html/footerFrame.html" SCROLLING="no" name="footerframe">
        <FRAME SRC="webcomponent/interface/html/outer_side_r.html" SCROLLING="no">
      </FRAMESET>

      <!-- bottom the page -->
      <FRAME SRC="webcomponent/interface/html/outer_bottom.html" SCROLLING="no">
    </FRAMESET>  <!-- end of viewer frameset -->

  <!-- hidden frame -->
  <FRAME name="hiddenframe" SRC="webcomponent/interface/html/outer_bottom.html" SCROLLING="no">

  </FRAMESET> <!-- end of main page frame set -->
  
    <NOFRAMES>
        <BODY BGCOLOR="#FFFFFF" TEXT="#000000" LINK="#000000" VLINK="#000000">
            <P>This page uses frames, but your browser doesn't support them.</P>
        </BODY>
    </NOFRAMES>

</HTML>
