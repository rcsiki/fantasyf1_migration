<%@ page errorPage="../error.jsp"  import="com.homelinux.bela.web.util.FormUtil"%>
<HTML>
<HEAD>
    <link rel="stylesheet" href='<%=FormUtil.makeURL(request, "/webcomponent/interface/style/globalstyle.css")%>' type="text/css">

   <SCRIPT LANGUAGE='JavaScript1.2'>
      function launchReload()
      {
         top.hiddenframe.location.href='reloadConfig.jsp';
      }
   </SCRIPT>
   
</HEAD>
<BODY CLASS="inner" onLoad='launchReload();'>
  <TABLE>
    <TR height='50'>
      <TD  class='tablelayout' id='reloadresulticon'>
        <IMG src= '<%=FormUtil.makeURL(request, "/webcomponent/interface/images/busy.gif")%>' ALT=""></IMG>
      </TD>
      <TD class='tablelayout' id='reloadresultmessage'>
        Reloading the configuration, please wait...
      </TD>
    </TR>
  </TABLE>
</BODY>
</HTML>