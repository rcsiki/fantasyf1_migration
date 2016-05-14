<%@ page errorPage="error.jsp" import="com.robertcsiki.f1.web.manager.CompetitionManager,com.robertcsiki.f1.web.config.WebConfig,com.robertcsiki.f1.web.util.FormUtil"%>

<HTML>

<HEAD>
    <link rel="stylesheet" href='<%=FormUtil.makeURL(request, "/webcomponent/interface/style/globalstyle.css")%>' type="text/css">
</HEAD>
<BODY CLASS="inner" onLoad="top.mainframe.headerframe.location.href='<%=FormUtil.makeURL(request, "/webcomponent/html/rulesHeader.html")%>'" onunload="top.mainframe.headerframe.location.href='<%=FormUtil.makeURL(request, "/webcomponent/html/empty.html")%>'">
<table border=0 cellspacing=5 cellpadding=5><tr><td class="tablelayoutrules">
<p>Please carefully read the following before registering.<br>
<p>
<b>GENERAL INSTRUCTIONS</b>
<p>
1. During the registration process, you (also referred as <b>the player</b>) will be required to build a <b>team</b>, composed by
<b>two drivers</b> and <b>two constructors</b> (cars). The drivers and cars that will compete are available for your selection.

<p>
2. Each driver and car has a published cost and you must not exceed an
<b>overall "virtual" budget</b> of <b>$<%=WebConfig.PLAYER_BUDGET%> million</b>, that is allocated for you to build your Team.

<p>
3. Players can not choose the same driver or constructor twice.

<p>
4. You will also be asked to provide a name for your <b>team</b> and <b>team manager</b>, as well as a valid <b>email address</b>.
The email will be later used (when the season ends) to notify the winners.

<p>
5. The team name you choose must be <b>unique</b>. If somebody else has registered it before, then you will be given the opportunity to choose another.
The team manager could be your name if you would like others to know your position in standings.

<p>
6. Only one player can be registered per email address.

<p>
7. When registering, each player will be asked to answer the following <b>"tie-breaker" question</b>: 
"<i>How many real F1 points will <%=WebConfig.TIEBREAKER_DRIVER_NAME%> score this season?</i>" A number in 0 - <%=WebConfig.RACES_THIS_SEASON * WebConfig.RACE_WINNER_REAL_POINTS%> range must be provided. 
It will be used at the end of the season on the standings ordering, for the case two or more Players 
will finish the Fantasy Formula 1 season with the same number of points.

<p>
8. The <b>deadline</b> for receiving your registration entry is the <b>end of day Friday</b> preceding the inaugural Grand Prix 
<b>(<%=WebConfig.REGISTRATION_DEADLINE_TEXT%>)</b>. The <%=WebConfig.CURRENT_YEAR%> Formula 1 season will start the following day, will have <%=WebConfig.RACES_THIS_SEASON%> races 
and it will last until the end of the year.

<p>
9. No team changes will be allowed after you have registered it. That means <b>your team structure 
is final</b>. If any of your drivers <b>do not race</b> then you will score points for their <b>replacement driver</b>. 
If there is no replacement driver the score for that selection will be zero. 
If your driver moves to another team then you will score points for whoever replaces that driver in 
the existing team.

<p>
10. The Fantasy Formula 1 results (i.e. <b>the overall standings</b>) will be updated and made 
available soon after each Grand Prix race. Each race result will be based on those from 
the FIA, moderated by the <a target="_blank" href= "http://www.formula1.com"><b>official Formula 1 website</b></a>.

<p><p>
<b>THE ENTRY FEE (Optional)</b>
<p>
11. An <b>optional entry fee</b> of $20 CDN will be used for building up a prize pool. You can always participate but <b>you can not win</b> 
any prizes if <b>your payment is not received by <%=WebConfig.PAYMENT_DEADLINE_TEXT%></b>. 
Prizes will be awarded at the end of the season for the <b>top five players</b> who have 
sent the payment, as follows (they will be notified by e-mail):
<br><ul>
 <li><b>the winner</b> takes 45% from the prize pool
 <li>2nd place takes 25% from the prize pool
 <li>3rd place takes 15% from the prize pool
 <li>4th place takes 10% from the prize pool
 <li>5th place takes 5% from the prize pool
</ul>
After registering, you will be asked to please mail in a <b>personal check</b> valued at $20 CDN, to the following address:
<p>
<b><%=WebConfig.PAYMENT_MAIL_ADDRESS%></b>
<p>
Please don't forget to mention your <b>Team Name</b> when submitting the fee. When the payment is received, an <b>acknowledgement</b> will be sent back to you by e-mail.
<br>

<p>
<b>THE POINTS SYSTEM</b>
<p>

12. <b>Points</b> will be <b>awarded / deducted</b> according to how the components of the Player's 
Team will perform in each Grand Prix race. Each Player component scores the sum of points 
awarded less points deducted:
<p>
 - Drivers points are awarded in full.<br>
 - Cars points are awarded as an <b>average</b> of the points scored by the constructor's 2 
drivers in the race.<br>
<p>
<b>Points awarded for each race, if your driver or car</b> (unless specified otherwise):

<p>
 - finishes in the top eight:<br>
<ul>
     <li>1st - 100 points
     <li>2nd - 80 points
     <li>3rd - 60 points
     <li>4th - 50 points
     <li>5th - 40 points
     <li>6th - 30 points
     <li>7th - 20 points
     <li>8th - 10 points
</ul>

 - achieves a grid position (that is, all get points here, if they do race):<br>
<ul>
     <li>1st - 22 points
     <li>2nd - 21 points
     <li>3rd - 20 points
     <li>.........................
     <li>20th - 3 points
     <li>21st - 2 points
     <li>22nd - 1 point
</ul>

 - improves positions from starting grid to chequered flag, except where the car didn't finish the race: 10 points per position
 
<p>
 - completes laps: 1 point per lap
 
<p>
 - has the fastest lap: 10 points <b>(applies to driver only!)</b>
 
<p>
 - scores a hat-trick of pole position, fastest lap and winner: 50 points <b>(applies to driver only!)</b>

<p>

<b>Points deducted for each race, if your driver or car:</b>
<p>
 - fails to finish the race: 100 points.
<p>
 - loses positions between starting grid and finish, except where the car didn't finish the race: 
10 points per position.

<p>
<b>No points are given if the car did not start the race or is disqualified.</b> 

<p>
<b>DRIVERS AND CARS COST</b>
<p>

13. Each driver and car has a <b>published cost</b>, as outlined below. When making your selection, 
you will not be permitted to exceed your budget.
    <p>

    <table>
      <tr>
        <td  align='center' valign='top'>
          <%=CompetitionManager.getInstance().drawDriverCosts()%>
        </td>
        <td width='120'>
           &nbsp;
        </td>
        <td align='center' valign='top'>
          <%=CompetitionManager.getInstance().drawTeamCosts()%>
        </td>
      </tr>
    </table>


</td></tr></table>
</BODY>
</HTML>