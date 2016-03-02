<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:output method="xml" encoding="ISO-8859-1" indent="yes"/>
  <xsl:template match="/html">
    <results>
      <race>
        <xsl:apply-templates/>
      </race>
    </results>
  </xsl:template>
  <xsl:template match="text()"></xsl:template>
    <xsl:template match="table[starts-with(normalize-space(tr[1]/td[2]),'&#160;')]">
      <id>
        <xsl:value-of select="normalize-space(tr[1]/td[1])"/>
      </id>
      <driver>
        <pos>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[3]/td[1])"/>
        </pos>
        <id>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[3]/td[5])"/>
        </id>
        <laps>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[3]/td[8])"/>
        </laps>
        <grid>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[3]/td[11])"/>
        </grid>
      </driver>
      <driver>
        <pos>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[5]/td[1])"/>
        </pos>
        <id>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[5]/td[5])"/>
        </id>
        <laps>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[5]/td[8])"/>
        </laps>
        <grid>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[5]/td[11])"/>
        </grid>
      </driver>
      <driver>
        <pos>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[7]/td[1])"/>
        </pos>
        <id>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[7]/td[5])"/>
        </id>
        <laps>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[7]/td[8])"/>
        </laps>
        <grid>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[7]/td[11])"/>
        </grid>
      </driver>
      <driver>
        <pos>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[9]/td[1])"/>
        </pos>
        <id>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[9]/td[5])"/>
        </id>
        <laps>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[9]/td[8])"/>
        </laps>
        <grid>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[9]/td[11])"/>
        </grid>
      </driver>
      <driver>
        <pos>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[11]/td[1])"/>
        </pos>
        <id>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[11]/td[5])"/>
        </id>
        <laps>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[11]/td[8])"/>
        </laps>
        <grid>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[11]/td[11])"/>
        </grid>
      </driver>
      <driver>
        <pos>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[13]/td[1])"/>
        </pos>
        <id>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[13]/td[5])"/>
        </id>
        <laps>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[13]/td[8])"/>
        </laps>
        <grid>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[13]/td[11])"/>
        </grid>
      </driver>
      <driver>
        <pos>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[15]/td[1])"/>
        </pos>
        <id>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[15]/td[5])"/>
        </id>
        <laps>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[15]/td[8])"/>
        </laps>
        <grid>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[15]/td[11])"/>
        </grid>
      </driver>
      <driver>
        <pos>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[17]/td[1])"/>
        </pos>
        <id>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[17]/td[5])"/>
        </id>
        <laps>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[17]/td[8])"/>
        </laps>
        <grid>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[17]/td[11])"/>
        </grid>
      </driver>
      <driver>
        <pos>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[19]/td[1])"/>
        </pos>
        <id>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[19]/td[5])"/>
        </id>
        <laps>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[19]/td[8])"/>
        </laps>
        <grid>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[19]/td[11])"/>
        </grid>
      </driver>
      <driver>
        <pos>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[21]/td[1])"/>
        </pos>
        <id>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[21]/td[5])"/>
        </id>
        <laps>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[21]/td[8])"/>
        </laps>
        <grid>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[21]/td[11])"/>
        </grid>
      </driver>
      <driver>
        <pos>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[23]/td[1])"/>
        </pos>
        <id>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[23]/td[5])"/>
        </id>
        <laps>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[23]/td[8])"/>
        </laps>
        <grid>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[23]/td[11])"/>
        </grid>
      </driver>
      <driver>
        <pos>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[25]/td[1])"/>
        </pos>
        <id>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[25]/td[5])"/>
        </id>
        <laps>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[25]/td[8])"/>
        </laps>
        <grid>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[25]/td[11])"/>
        </grid>
      </driver>
      <driver>
        <pos>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[27]/td[1])"/>
        </pos>
        <id>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[27]/td[5])"/>
        </id>
        <laps>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[27]/td[8])"/>
        </laps>
        <grid>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[27]/td[11])"/>
        </grid>
      </driver>
      <driver>
        <pos>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[29]/td[1])"/>
        </pos>
        <id>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[29]/td[5])"/>
        </id>
        <laps>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[29]/td[8])"/>
        </laps>
        <grid>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[29]/td[11])"/>
        </grid>
      </driver>
      <driver>
        <pos>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[31]/td[1])"/>
        </pos>
        <id>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[31]/td[5])"/>
        </id>
        <laps>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[31]/td[8])"/>
        </laps>
        <grid>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[31]/td[11])"/>
        </grid>
      </driver>
      <driver>
        <pos>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[33]/td[1])"/>
        </pos>
        <id>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[33]/td[5])"/>
        </id>
        <laps>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[33]/td[8])"/>
        </laps>
        <grid>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[33]/td[11])"/>
        </grid>
      </driver>
      <driver>
        <pos>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[35]/td[1])"/>
        </pos>
        <id>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[35]/td[5])"/>
        </id>
        <laps>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[35]/td[8])"/>
        </laps>
        <grid>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[35]/td[11])"/>
        </grid>
      </driver>
      <driver>
        <pos>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[37]/td[1])"/>
        </pos>
        <id>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[37]/td[5])"/>
        </id>
        <laps>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[37]/td[8])"/>
        </laps>
        <grid>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[37]/td[11])"/>
        </grid>
      </driver>
      <driver>
        <pos>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[39]/td[1])"/>
        </pos>
        <id>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[39]/td[5])"/>
        </id>
        <laps>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[39]/td[8])"/>
        </laps>
        <grid>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[39]/td[11])"/>
        </grid>
      </driver>
      <driver>
        <pos>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[41]/td[1])"/>
        </pos>
        <id>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[41]/td[5])"/>
        </id>
        <laps>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[41]/td[8])"/>
        </laps>
        <grid>
          <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[41]/td[11])"/>
        </grid>
      </driver>
      <fastestlap>
        <xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[43]/td[1])"/>
      </fastestlap>
  </xsl:template>
</xsl:stylesheet>