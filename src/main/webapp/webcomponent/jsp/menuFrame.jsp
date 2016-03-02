<%@ page errorPage="error.jsp" import="com.homelinux.bela.web.manager.CompetitionManager,com.homelinux.bela.web.fc.IConstants,com.homelinux.bela.web.util.FormUtil"%>

<%
  if (CompetitionManager.ROOT_FOLDER_PATH == null) {	      
    // CompetitionManager.ROOT_FOLDER_PATH = application.getRealPath("/");
    CompetitionManager.ROOT_FOLDER_PATH = "/var/lib/openshift/56d645d789f5cfb5710000be/app-root/runtime/repo/target/fantasy/";
  }
  // load the configuration if not already done
  CompetitionManager cm = CompetitionManager.getInstance();
%>

<HTML>
<HEAD>
    <!--<link rel="stylesheet" href='<%=FormUtil.makeURL(request, "/webcomponent/interface/style/globalstyle.css")%>' type="text/css">-->
    <!--<script language='JavaScript1.2' src='<%=FormUtil.makeURL(request, "/webcomponent/interface/javascript/js_functions.js")%>'></script>-->
    <link rel="stylesheet" href="../interface/style/globalstyle.css" type="text/css">
    <script language="JavaScript1.2" src="../interface/javascript/js_functions.js"></script>
</HEAD>
<BODY MARGINWIDTH=0 MARGINHEIGHT=0 LEFTMARGIN=0 TOPMARGIN=0 CLASS="inner">
<TABLE BORDER=0 width='100%' height='100%' cellspacing=2 cellpadding=2>
  <TR>
    <TD>
      <TABLE BORDER=0 cellspacing=2 cellpadding=2>
        <TR>
          <TD class="tablelayoutheader">Main Menu<b></TD>
        </TR>  
        <TR>
          <TD class="tablelayoutnobold"><A href='<%=FormUtil.makeURL(request, "/webcomponent/html/home.html")%>' target="dataframe" onclick="resetRaceOptionSelector();">Home</A></TD>
        </TR>  
        <TR>
          <TD class="tablelayoutnobold"><A href="rulesBody.jsp" target="dataframe" onclick="resetRaceOptionSelector();">Rules</A></TD>
        </TR>          
        	<%=cm.renderRegistrationLink()%>
        <TR>
         <TD class="tablelayoutnobold"><A href="standingsBody.jsp" target="dataframe" onclick="resetRaceOptionSelector();">Standings</A></TD>
        </TR>  
      </TABLE>
    </TD>
  </TR>
  <TR>
    <TD>
      <TABLE BORDER=0 cellspacing=2 cellpadding=2>
        <TR>
          <TD class="tablelayoutheader">Fantasy Stats<b></TD>
        </TR>  
        <TR>
          <TD class="tablelayoutnobold" style="white-space:nowrap; display:inline;"><A href="statsBody.jsp?component=<%=IConstants.STATS_DRIVERS_PERF%>" target="dataframe" onclick="resetRaceOptionSelector();">Drivers Performance</A></TD>
        </TR>  
        <TR>
          <TD class="tablelayoutnobold"><A href="statsBody.jsp?component=<%=IConstants.STATS_CARS_PERF%>" target="dataframe" onclick="resetRaceOptionSelector();">Cars Performance</A></TD>
        </TR>  
        <TR>
          <TD class="tablelayoutnobold"><A href="statsBody.jsp?component=<%=IConstants.STATS_ALL_TEAM_STAND%>" target="dataframe" onclick="resetRaceOptionSelector();">All Team Standings</A></TD>
        </TR>  
        <TR>
          <TD class="tablelayoutnobold"><A href="statsBody.jsp?component=<%=IConstants.STATS_MOST_PICKED_DRIVER%>" target="dataframe" onclick="resetRaceOptionSelector();">Driver Picks</A></TD>
        </TR>  
        <TR>
          <TD class="tablelayoutnobold"><A href="statsBody.jsp?component=<%=IConstants.STATS_MOST_PICKED_CAR%>" target="dataframe" onclick="resetRaceOptionSelector();">Car Picks</A></TD>
        </TR>  
        <TR>
          <TD class="tablelayoutnobold" width='50'>
             <%=cm.renderRaceStandingStatControl()%>
          </TD>
        </TR>  
        <TR>
          <TD class="tablelayoutnobold"><A href="statsBody.jsp?component=<%=IConstants.STATS_WINNERS%>" target="dataframe" onclick="resetRaceOptionSelector();">Winners</A></TD>
        </TR>  
        <TR>
          <TD class="tablelayoutnobold"><A href="statsBody.jsp?component=<%=IConstants.STATS_TOP_25%>" target="dataframe" onclick="resetRaceOptionSelector();">Top 25 All Time</A></TD>
        </TR>  
      </TABLE>
    </TD>
  </TR>
  <TR>
    <TD>
      <TABLE BORDER=0 cellspacing=2 cellpadding=2>
        <TR>
          <TD class="tablelayoutheader">2010 Fantasy Stats<b></TD>
        </TR>  
        <TR>
          <TD class="tablelayoutnobold"><A href="<%=FormUtil.makeURL(request, "/webcomponent/config/history/config_2010/drivers_perf.html")%>" target="dataframe" onclick="resetRaceOptionSelector();">Drivers Performance</A></TD>
        </TR>  
        <TR>
          <TD class="tablelayoutnobold"><A href="<%=FormUtil.makeURL(request, "/webcomponent/config/history/config_2010/cars_perf.html")%>" target="dataframe" onclick="resetRaceOptionSelector();">Cars Performance</A></TD>
        </TR>
      </TABLE>
    </TD>
  </TR>
  <%=cm.renderAdminLinks()%>
  <TR>
    <TD>
      <TABLE BORDER=0 cellspacing=2 cellpadding=2>
        <TR>
          <TD class="tablelayoutheader">Formula 1 Links<b></TD>
        </TR>  
        <TR>
          <TD class="tablelayoutnobold"><A href="http://www.formula1.com" target="dataframe" onclick="resetRaceOptionSelector();">Official F1 Site</A></TD>
        </TR>  
      </TABLE>
    </TD>
  </TR>

  <TR>
    <TD>
      <TABLE BORDER=0 cellspacing=2 cellpadding=2>
        <TR>
          <TD align='center' valign='middle' class="tablelayoutcounter">Registered Players
          <br><%=cm.getRegisteredPlayersCount()%></TD>
        </TR>  
      </TABLE>
    </TD>
  </TR>
  
</TABLE>  
</BODY>
</HTML>