<%@ page errorPage="../error.jsp"  import="com.robertcsiki.f1.web.manager.CompetitionManager"%>
<HTML>
<HEAD>
    <SCRIPT LANGUAGE='JavaScript1.2'>
      function extractRaceResult()
      {
        <%
          String strUrl = request.getParameter("nextRaceResultUrl");
          CompetitionManager cm = CompetitionManager.getInstance();
          String strResult = cm.extractRaceResult(strUrl);  
        %>
        var messageLabelElement = top.mainframe.dataframe.document.getElementById('extractraceresultmessage');
        var busyIconElement = top.mainframe.dataframe.document.getElementById('extractraceresulticon');        
        var msgLabel = messageLabelElement.innerHTML;
        var result = '<%=strResult%>';
        busyIconElement.style.visibility = 'hidden';
        messageLabelElement.innerHTML = msgLabel + result;
      }
    </SCRIPT>
</HEAD>
<BODY onLoad='extractRaceResult();'>
</BODY>
</HTML>