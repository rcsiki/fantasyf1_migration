<%@ page errorPage="error.jsp"  import="com.homelinux.bela.web.manager.CompetitionManager,com.homelinux.bela.web.util.FormUtil"%>
<HTML>
<HEAD>
<%
   String strRaceId = request.getParameter("raceId");
   String strTitle = request.getParameter("windowTitle");
   CompetitionManager cm = CompetitionManager.getInstance();
%>
      <TITLE><%=strTitle%></TITLE>
     <link rel="stylesheet" href='<%=FormUtil.makeURL(request, "/webcomponent/interface/style/globalstyle.css")%>' type="text/css">
</HEAD>


   <BODY CLASS="inner"><%=cm.getRaceById(strRaceId).getRaceResult().printOfficialResults(true)%></BODY>
</HTML>    