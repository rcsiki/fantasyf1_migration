<%@ page isErrorPage="true" import="com.homelinux.bela.web.util.WebLogWriter,com.homelinux.bela.web.config.WebConfig, com.homelinux.bela.web.util.FormUtil"  %>

<HTML>
    <HEAD>
        <TITLE>Fantasy Formula 1 2016</TITLE>
    <link rel="stylesheet" href='<%=FormUtil.makeURL(request, "/webcomponent/interface/style/globalstyle.css")%>' type="text/css">
    <script language='JavaScript1.2' src='<%=FormUtil.makeURL(request, "/webcomponent/interface/javascript/js_functions.js")%>'></script>
    </HEAD>

    <BODY CLASS="inner">
  <DIV ALIGN="center">
  <TABLE BORDER=0 WIDTH=500 HEIGHT=8 CELLPADDING=0 CELLSPACING=0 BACKGROUND='<%=FormUtil.makeURL(request, "/webcomponent/interface/images/outer_fill.gif")%>'>
    <TR>
      <TD WIDTH=8 HEIGHT=8 ALIGN="left" BACKGROUND='<%=FormUtil.makeURL(request, "/webcomponent/interface/images/outer_t.gif")%>'><IMG SRC='<%=FormUtil.makeURL(request, "/webcomponent/interface/images/outer_tl.gif")%>'></TD>
      <TD BACKGROUND='<%=FormUtil.makeURL(request, "/webcomponent/interface/images/outer_t.gif")%>'><IMG SRC="/webcomponent/interface/images/1-pixel.gif"></TD>
      <TD WIDTH=8 ALIGN="right" BACKGROUND='<%=FormUtil.makeURL(request, "/webcomponent/interface/images/outer_t.gif")%>'><IMG SRC='<%=FormUtil.makeURL(request, "/webcomponent/interface/images/outer_tr.gif")%>'></TD>
    </TR>
  </TABLE>
  <TABLE BORDER=0 WIDTH=500 HEIGHT=40 CELLPADDING=0 CELLSPACING=0 BACKGROUND='<%=FormUtil.makeURL(request, "/webcomponent/interface/images/outer_fill.gif")%>'>
    <TR>
      <TD WIDTH=8 ALIGN="left" VALIGN="top">
        <TABLE BORDER=0 WIDTH=8 HEIGHT=100% CELLPADDING=0 CELLSPACING=0 BACKGROUND='<%=FormUtil.makeURL(request, "/webcomponent/interface/images/outer_l.gif")%>'>
          <TR><TD>
            <SCRIPT LANGUAGE="JavaScript">
              if( isNav == true ) {
                document.write("<SPACER TYPE=block WIDTH=4 HEIGHT=250>");
              } else document.write("<IMG SRC='<%=FormUtil.makeURL(request, "/webcomponent/interface/images/1-pixel.gif")%>'");
            </SCRIPT>
          </TD></TR>
        </TABLE>
      </TD>
      <TD>
        <!-- Error message Code Goes Here -->
        <DIV CLASS="errormessage">

        Exception: <br>
        <%= exception.getMessage() %>
        <br><br>Stacktrace: <br>
        <%= WebLogWriter.stackTraceToString (exception) %>


        </DIV>
        <!-- Error message Code Ends Here -->
      </TD>
      <TD WIDTH=8 ALIGN="right" VALIGN="top">
        <TABLE BORDER=0 WIDTH=8 HEIGHT=100% CELLPADDING=0 CELLSPACING=0 BACKGROUND='<%=FormUtil.makeURL(request, "/webcomponent/interface/images/outer_r.gif")%>'>
          <TR><TD>
            <SCRIPT LANGUAGE="JavaScript">
              if( isNav == true ) {
                document.write("<SPACER TYPE=block WIDTH=4 HEIGHT=250>");
              } else document.write("<IMG SRC='<%=FormUtil.makeURL(request, "/webcomponent/interface/images/1-pixel.gif")%>'>");
            </SCRIPT>
          </TD></TR>
        </TABLE>
      </TD>
    </TR>
  </TABLE>
  <TABLE BORDER=0 WIDTH=500 CELLPADDING=0 CELLSPACING=0 BACKGROUND='<%=FormUtil.makeURL(request, "/webcomponent/interface/images/outer_fill.gif")%>'>
    <TR>
      <TD WIDTH=8 ALIGN="left" BACKGROUND='<%=FormUtil.makeURL(request, "/webcomponent/interface/images/outer_b.gif")%>'><IMG SRC='<%=FormUtil.makeURL(request, "/webcomponent/interface/images/outer_bl.gif")%>'></TD>
      <TD BACKGROUND='<%=FormUtil.makeURL(request, "/webcomponent/interface/images/outer_b.gif")%>'><IMG SRC='<%=FormUtil.makeURL(request, "/webcomponent/interface/images/1-pixel.gif")%>'></TD>
      <TD WIDTH=8 ALIGN="right" BACKGROUND='<%=FormUtil.makeURL(request, "/webcomponent/interface/images/outer_b.gif")%>'><IMG SRC='<%=FormUtil.makeURL(request, "/webcomponent/interface/images/outer_br.gif")%>'></TD>
    </TR>
  </TABLE>
  </DIV>
    </BODY>
</HTML>

