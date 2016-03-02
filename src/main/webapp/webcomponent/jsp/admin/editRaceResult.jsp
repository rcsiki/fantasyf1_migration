<%@ page errorPage="../error.jsp"
    import="com.homelinux.bela.web.manager.CompetitionManager,com.homelinux.bela.web.fc.IRace,java.text.SimpleDateFormat,com.homelinux.bela.web.util.FormUtil"%>
    
<HTML>
<HEAD>
    <link rel="stylesheet" href='<%=FormUtil.makeURL(request, "/webcomponent/interface/style/globalstyle.css")%>' type="text/css">

   <SCRIPT LANGUAGE='JavaScript1.2'>
   
     function validateForm()
     {
     }
     
   </SCRIPT>
   
</HEAD>

<%
   CompetitionManager cm =  CompetitionManager.getInstance();
   int numberOfDrivers = cm.getDriverCount();
   String strDriverOptionList = cm.renderDriverOptionList();
   IRace race = cm.getNextRaceToProcess();
   if (race == null)
   {
     // season is over
%>
     <BODY CLASS="inner">
        <p class='tablelayout'>Season is over.</p>
<%
   }
   else
   {
%>
     <BODY CLASS="inner">
               <form name="raceResultEditorForm" method=post
                     action='<%=FormUtil.makeURL(request, "RaceResultsProcessor")%>'>
        <table width='100%' border='0'>
           <tr>
            <td class='tablelayout' align='center' valign='top'>
                 <b>Processing:</b>&nbsp;<%=race.getName()%>&nbsp;&nbsp;(<%=(new SimpleDateFormat("MMM-dd-yyyy")).format(race.getDate())%>)<BR>
                 <table border=1>
                   <tr>
                     <td class='tablelayout' align='center' ><b>Pos</b></td>
                     <td class='tablelayout' align='center' ><b>Driver</b></td>
                     <td class='tablelayout' align='center' ><b>Laps</b></td>
                     <td class='tablelayout' align='center' ><b>Grid</b></td>
                   </tr>
<%
               for (int i = 1 ; i <= numberOfDrivers ; i++)
               {
%>
                   <tr>
                     <td>
                        <SELECT NAME="selectPos_<%=i%>">
                           <option value="<%=i%>"><%=i%></option>
                           <option value="ret">ret</option>
                           <option value="dns">dns</option>
                           <option value="dsq">dsq</option>
                        </SELECT>
                     </td>
                     <td>
                        <SELECT NAME="selectDriver_<%=i%>">
                           <%=strDriverOptionList%>
                        </SELECT>
                     </td>
                     <td><input type=text size=2 maxlength=2 name="laps_<%=i%>"</td>
                     <td><input type=text size=2 maxlength=2 name="grid_<%=i%>"</td>
                   </tr>
<%
               }
%>
                   <BR>
                   <tr>
                      <td class='tablelayout' align='center' colspan=4><b>Fastest Lap:</b>&nbsp;
                        <SELECT NAME="fastestLap">
                           <%=strDriverOptionList%>
                        </SELECT>
                      </td>
                   </tr>
              </table>
              <input type=hidden name="raceId" value="<%=race.getId()%>">
              <BR>
              <INPUT TYPE=SUBMIT NAME="submit" value="Submit" onClick='return validateForm();'>          
            </td>
           </tr>
        </table>
   </form>
<%
   }
%>
</BODY>
</HTML>
    