<%@ page errorPage="error.jsp" import="java.lang.System,com.robertcsiki.f1.web.manager.CompetitionManager,com.robertcsiki.f1.web.config.WebConfig,com.robertcsiki.f1.web.util.FormUtil"%>
<HTML>
<HEAD>
    <link rel="stylesheet" href='<%=FormUtil.makeURL(request, "/webcomponent/interface/style/globalstyle.css")%>' type="text/css">
</HEAD>
<BODY MARGINHEIGHT=0 TOPMARGIN=0 CLASS="inner">
<table width='100%' height='100%' border=0>
  <tr>
    <td class="tablelayoutrules" align='center' valign='center'>
      <p><b>Welcome to Fantasy Formula1 <%=WebConfig.CURRENT_YEAR%>!</b></p>
    </td>
  </tr>
  <tr>
    <td class="tablelayoutrules" align='left' valign='center'>
      <p>
        <b>Fantasy Formula 1</b> is a competition among the fans of F1 racing.</br><br>
        Click the Rules link in the Main Menu and find out what the rules for this season are.
      <p>
	<font size="2">
        An optional <b>entry fee</b> of $20 CDN will be used for building up a prize pool. 
			  Prizes will be awarded at the end of the season for the <b>top five players</b> who have 
        contributed to the prize pool, as follows (they will be notified by e-mail):
        <br><ul>
         <li><b>the winner</b> takes 45% from the prize pool
         <li>2nd place takes 25% from the prize pool
         <li>3rd place takes 15% from the prize pool
         <li>4th place takes 10% from the prize pool
         <li>5th place takes 5% from the prize pool
        </ul>
        You can participate but <b>you can not win</b> 
        any prizes if <b>your payment is not received by <%=WebConfig.PAYMENT_DEADLINE_TEXT%></b>.</br>
        If you would like to contribute to the prize pool please mail in a <b>personal check</b> 
			  valued at $20 CDN, to the following address:
        <p>
        <b><%=WebConfig.PAYMENT_MAIL_ADDRESS%></b>
        <p>
        Don't forget to mention your <b>Team Name</b> when submitting the fee. 
			  When the payment is received, the <b>Paid</b> column in the <b>Standings</b> page will updated accordingly.
        <br>
        </b>
	</font>
    </td>	
  </tr>
  <tr>
    <td class="tablelayoutrules" align='left' valign='center'>
      <p>
        <font size="2">
          Contacts:<br/>
          <%=WebConfig.CONTACT_INFO%>
        </font>
      </p>
    </td>
  </tr>
</table>
</BODY>
</HTML>