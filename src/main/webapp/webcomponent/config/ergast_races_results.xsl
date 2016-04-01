<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:output method="xml" encoding="UTF-8" indent="yes"/>
  <xsl:template match="/MRData">
    <results>
      <race>
        <xsl:apply-templates/>
      </race>
    </results>
  </xsl:template>
  <xsl:template match="text()"></xsl:template>
    <xsl:template match="MRData/RaceTable">
      <id>
          <xsl:value-of select="Race/@round"/>
      </id>
      <xsl:for-each select="Race/ResultsList/Result">
      <driver>
        <xsl:attribute name="givenName">
          <xsl:value-of select="Driver/GivenName"/>
        </xsl:attribute>
        <xsl:attribute name="familyName">
          <xsl:value-of select="Driver/FamilyName"/>
        </xsl:attribute>
        <xsl:attribute name="fastestLapRank">
          <xsl:value-of select="FastestLap/@rank"/>
        </xsl:attribute>
        <pos>
          <xsl:value-of select="@positionText"/>
        </pos>
        <id>placeholder</id>
        <laps>
          <xsl:value-of select="Laps"/>
        </laps>
        <grid>
          <xsl:value-of select="Grid"/>
        </grid>
      </driver>
      </xsl:for-each>
      <fastestlap>placeholder</fastestlap>
  </xsl:template>
</xsl:stylesheet>