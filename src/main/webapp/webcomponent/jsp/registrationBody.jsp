<%@ page errorPage="error.jsp" import="com.homelinux.bela.web.manager.CompetitionManager,
                                       com.homelinux.bela.web.config.WebConfig,
                                       com.homelinux.bela.web.util.FormUtil"%>
<HTML>
<HEAD>
    <link rel="stylesheet" href='<%=FormUtil.makeURL(request, "/webcomponent/interface/style/globalstyle.css")%>' type="text/css">
    <script language='JavaScript1.2' src='<%=FormUtil.makeURL(request, "/webcomponent/interface/javascript/js_functions.js")%>'></script>
</HEAD>

<%
  // check the request to determine what to render
  // (i.e. whether it's the form itself or it's the registration submission request)
  if (request.getParameter("teamManager") == null)
  {
%>
<BODY MARGINHEIGHT=0 TOPMARGIN=0 CLASS="inner"  onLoad="top.mainframe.headerframe.location.href='<%=FormUtil.makeURL(request, "/webcomponent/html/registrationHeader.html")%>'" onunload="top.mainframe.headerframe.location.href='<%=FormUtil.makeURL(request, "/webcomponent/html/empty.html")%>'">
<FORM NAME = 'registrationForm' TARGET="dataframe">
<table width='100%' height='100%' border='0' >
  <tr>
    <td align='center' valign='top'>
      <table border = '0'>
        <!-- The Text inputs, budget display and the "Your selection" label -->
        <tr>
          <td>
            <table border='0'>
               <TR>
                 <TD CLASS="tablelayout">Team Name:</TD>
                 <TD CLASS="textfield"><INPUT CLASS="textfield" TYPE=TEXT NAME="teamName" SIZE=20 maxlength=20>&nbsp;<font color="red" size=4>*</font></TD>
               </TR>
               <TR>
                 <TD CLASS="tablelayout">Team Manager:</TD>
                 <TD CLASS="textfield"><INPUT CLASS="textfield" TYPE=TEXT NAME="teamManager" SIZE=20 maxlength=20>&nbsp;<font color="red" size=4>*</font></TD>
               </TR>
               <TR>
                 <TD CLASS="tablelayout">Email Address:</TD>
                 <TD CLASS="textfield"><INPUT CLASS="textfield" TYPE=TEXT NAME="emailAddress" SIZE=20>&nbsp;<font color="red" size=4>*</font></TD>
               </TR>
            </table>
          </td>
        </tr>
        <tr>
          <td>
            <table>
               <TR>
                 <TD CLASS="tablelayout"><u>Tie-Breaker question:</u> How many real F1 points will <%=WebConfig.TIEBREAKER_DRIVER_NAME%> score this season? (0 - <%=WebConfig.RACES_THIS_SEASON * WebConfig.RACE_WINNER_REAL_POINTS%>)</TD>
                 <TD CLASS="textfield"><INPUT CLASS="textfield" TYPE=TEXT NAME="tbQuestion" SIZE=3 maxlength=3>&nbsp;<font color="red" size=4>*</font>
                 <a href="#" onclick="alert('The answer you provide here will be used at the end\nof the season to differentiate you from other Players\n for the case there will be a tie.');"><img src='<%=FormUtil.makeURL(request, "/webcomponent/interface/images/help.gif")%>' alt="Help" border="0" align="middle"/></a></TD>
               </TR>
            </table>
          </td>
        </tr>
        <tr height='30'>
          <td  class="tablelayoutheader" valign='bottom'>
            <br>Build your Team (2 drivers and 2 cars) within an overall budget of <u>$<%=WebConfig.PLAYER_BUDGET%> million</u>
           </td>
        </tr>
        <!-- Driver & Teams, add/remove buttons, current selection panel -->
        <tr>
          <td><br>
            <table>
               <tr>
                  <td id='drivers'>
                     <%=CompetitionManager.getInstance().printAllPlayerComponentsForRegistrationForm("driver")%>
                  </td>
                  <td>&nbsp;&nbsp;&nbsp;</td>
                  <td id ='teams'>
                     <%=CompetitionManager.getInstance().printAllPlayerComponentsForRegistrationForm("team")%>
                  </td>
               </tr>
            </table>
          </td>
        </tr>
        <!-- Register button -->
        <tr>
          <td align='center' valign='bottom'>
            <INPUT TYPE=SUBMIT NAME="submit" value="Register" onClick='return validateRegistrationForm(<%=WebConfig.PLAYER_BUDGET%>,<%=WebConfig.RACES_THIS_SEASON * WebConfig.RACE_WINNER_REAL_POINTS%>);'>          
          </td>
        </tr>
      </table>    
    </td>
  </tr>
</table>
<INPUT TYPE=HIDDEN NAME="driverId1">
<INPUT TYPE=HIDDEN NAME="driverId2">
<INPUT TYPE=HIDDEN NAME="teamId1">
<INPUT TYPE=HIDDEN NAME="teamId2">

</FORM>
<%
  }
  else
  {
%>

<BODY MARGINHEIGHT=0 TOPMARGIN=0 CLASS="inner"">

<%
    // the registration request has been submitted
    // catch the request params and proceed with the Player registration
    String strTeamManager = request.getParameter("teamManager");
    String strTeamName = request.getParameter("teamName");
    String strEmailAddress = request.getParameter("emailAddress");
    String strDriverId1 = request.getParameter("driverId1");
    String strDriverId2 = request.getParameter("driverId2");
    String strTeamId1 = request.getParameter("teamId1");
    String strTeamId2 = request.getParameter("teamId2");
    String strTieBreakerAnswer = request.getParameter("tbQuestion");
    String registrationResult =
       CompetitionManager.getInstance().registerPlayer( strTeamName,
                                                        strTeamManager,
                                                        strEmailAddress,
                                                        strDriverId1,
                                                        strDriverId2,
                                                        strTeamId1,
                                                        strTeamId2,
                                                        strTieBreakerAnswer );
%>

<%=registrationResult%>

<%
  }// endif
%>

</BODY>
</HTML>