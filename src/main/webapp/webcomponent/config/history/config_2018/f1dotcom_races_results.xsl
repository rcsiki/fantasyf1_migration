<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:output method="xml" encoding="UTF-8" indent="yes"/>
  <xsl:template match="/html">
    <results>
      <race>
        <xsl:apply-templates/>
      </race>
    </results>
  </xsl:template>
  <xsl:template match="text()"></xsl:template>
    <xsl:template match="table/tbody">
      <id>2016 Bahrain Grand Prix</id>
      <driver>
        <pos>
          <xsl:value-of select="normalize-space(tr[1]/td[1]/span[1]/span[1])"/>
        </pos>
        <first>
          <xsl:value-of select="normalize-space(tr[1]/td[2]/span[2])"/>
        </first>
        <last>
          <xsl:value-of select="normalize-space(tr[1]/td[2]/span[3])"/>
        </last>
        <id>0</id>
        <laps>44</laps>
        <grid>8</grid>
      </driver>
<!--
				fastest lap ignored due to missing info at www.f1.com, results page
				since April 8, 2007
				setting temporary node value to Fernando Alonso
				fastest lap will be set by hand in the results xml 
				<xsl:value-of select="normalize-space(tr[4]/td[1]/table[1]/tr[47]/td[1])"/>
-->
      <fastestlap>Fastest Lap:Fernando Alonso 1:22:33</fastestlap>
      
  </xsl:template>
</xsl:stylesheet>