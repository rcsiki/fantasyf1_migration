<%@ page errorPage="../error.jsp"  import="com.homelinux.bela.web.manager.CompetitionManager"%>
<HTML>
<HEAD>
    <SCRIPT LANGUAGE='JavaScript1.2'>
      function reload()
      {
        <%
          String strResult = CompetitionManager.reloadConfiguration();      
        %>
        var messageLabelElement = top.mainframe.dataframe.document.getElementById('reloadresultmessage');
        var busyIconElement = top.mainframe.dataframe.document.getElementById('reloadresulticon');        
        var msgLabel = messageLabelElement.innerHTML;
        var result = '<%=strResult%>';
        busyIconElement.style.visibility = 'hidden';
        messageLabelElement.innerHTML = msgLabel + result;
      }
    </SCRIPT>
</HEAD>
<BODY onLoad='reload();'>
</BODY>
</HTML>