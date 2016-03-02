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
    <xsl:template match="table[starts-with(normalize-space(tr[4]/td[1]),'Pos')]">
		<id>
		  <xsl:value-of select="normalize-space(tr[1]/td[1])"/>
		</id>
		<driver>
      <pos>
        <xsl:value-of select="normalize-space(tr[6]/td[1])"/>
      </pos>
      <id>
			  <xsl:value-of select="normalize-space(tr[6]/td[5])"/>
			</id>
      <laps>
        <xsl:value-of select="normalize-space(tr[6]/td[8])"/>
      </laps>
      <grid>
        <xsl:value-of select="normalize-space(tr[6]/td[12])"/>
      </grid>
		</driver>
		
		<driver>
      <pos>
        <xsl:value-of select="normalize-space(tr[8]/td[1])"/>
      </pos>
      <id>
        <xsl:value-of select="normalize-space(tr[8]/td[5])"/>
      </id>
      <laps>
        <xsl:value-of select="normalize-space(tr[8]/td[8])"/>
      </laps>
      <grid>
        <xsl:value-of select="normalize-space(tr[8]/td[12])"/>
      </grid>
		</driver>
		
		<driver>
      <pos>
        <xsl:value-of select="normalize-space(tr[10]/td[1])"/>
      </pos>
      <id>
        <xsl:value-of select="normalize-space(tr[10]/td[5])"/>
      </id>
      <laps>
        <xsl:value-of select="normalize-space(tr[10]/td[8])"/>
      </laps>
      <grid>
        <xsl:value-of select="normalize-space(tr[10]/td[12])"/>
      </grid>
		</driver>

		<driver>
      <pos>
        <xsl:value-of select="normalize-space(tr[12]/td[1])"/>
      </pos>
      <id>
        <xsl:value-of select="normalize-space(tr[12]/td[5])"/>
      </id>
      <laps>
        <xsl:value-of select="normalize-space(tr[12]/td[8])"/>
      </laps>
      <grid>
        <xsl:value-of select="normalize-space(tr[12]/td[12])"/>
      </grid>
		</driver>

		<driver>
      <pos>
        <xsl:value-of select="normalize-space(tr[14]/td[1])"/>
      </pos>
      <id>
        <xsl:value-of select="normalize-space(tr[14]/td[5])"/>
      </id>
      <laps>
        <xsl:value-of select="normalize-space(tr[14]/td[8])"/>
      </laps>
      <grid>
        <xsl:value-of select="normalize-space(tr[14]/td[12])"/>
      </grid>
		</driver>

		<driver>
      <pos>
        <xsl:value-of select="normalize-space(tr[16]/td[1])"/>
      </pos>
      <id>
        <xsl:value-of select="normalize-space(tr[16]/td[5])"/>
      </id>
      <laps>
        <xsl:value-of select="normalize-space(tr[16]/td[8])"/>
      </laps>
      <grid>
        <xsl:value-of select="normalize-space(tr[16]/td[12])"/>
      </grid>
		</driver>

		<driver>
      <pos>
        <xsl:value-of select="normalize-space(tr[18]/td[1])"/>
      </pos>
      <id>
        <xsl:value-of select="normalize-space(tr[18]/td[5])"/>
      </id>
      <laps>
        <xsl:value-of select="normalize-space(tr[18]/td[8])"/>
      </laps>
      <grid>
        <xsl:value-of select="normalize-space(tr[18]/td[12])"/>
      </grid>
		</driver>

		<driver>
      <pos>
        <xsl:value-of select="normalize-space(tr[20]/td[1])"/>
      </pos>
      <id>
        <xsl:value-of select="normalize-space(tr[20]/td[5])"/>
      </id>
      <laps>
        <xsl:value-of select="normalize-space(tr[20]/td[8])"/>
      </laps>
      <grid>
        <xsl:value-of select="normalize-space(tr[20]/td[12])"/>
      </grid>
		</driver>

		<driver>
      <pos>
        <xsl:value-of select="normalize-space(tr[22]/td[1])"/>
      </pos>
      <id>
        <xsl:value-of select="normalize-space(tr[22]/td[5])"/>
      </id>
      <laps>
        <xsl:value-of select="normalize-space(tr[22]/td[8])"/>
      </laps>
      <grid>
        <xsl:value-of select="normalize-space(tr[22]/td[12])"/>
      </grid>
		</driver>

		<driver>
      <pos>
        <xsl:value-of select="normalize-space(tr[24]/td[1])"/>
      </pos>
      <id>
        <xsl:value-of select="normalize-space(tr[24]/td[5])"/>
      </id>
      <laps>
        <xsl:value-of select="normalize-space(tr[24]/td[8])"/>
      </laps>
      <grid>
        <xsl:value-of select="normalize-space(tr[24]/td[12])"/>
      </grid>
		</driver>

		<driver>
      <pos>
        <xsl:value-of select="normalize-space(tr[26]/td[1])"/>
      </pos>
      <id>
        <xsl:value-of select="normalize-space(tr[26]/td[5])"/>
      </id>
      <laps>
        <xsl:value-of select="normalize-space(tr[26]/td[8])"/>
      </laps>
      <grid>
        <xsl:value-of select="normalize-space(tr[26]/td[12])"/>
      </grid>
		</driver>

		<driver>
      <pos>
        <xsl:value-of select="normalize-space(tr[28]/td[1])"/>
      </pos>
      <id>
        <xsl:value-of select="normalize-space(tr[28]/td[5])"/>
      </id>
      <laps>
        <xsl:value-of select="normalize-space(tr[28]/td[8])"/>
      </laps>
      <grid>
        <xsl:value-of select="normalize-space(tr[28]/td[12])"/>
      </grid>
		</driver>

		<driver>
      <pos>
        <xsl:value-of select="normalize-space(tr[30]/td[1])"/>
      </pos>
      <id>
        <xsl:value-of select="normalize-space(tr[30]/td[5])"/>
      </id>
      <laps>
        <xsl:value-of select="normalize-space(tr[30]/td[8])"/>
      </laps>
      <grid>
        <xsl:value-of select="normalize-space(tr[30]/td[12])"/>
      </grid>
		</driver>

		<driver>
      <pos>
        <xsl:value-of select="normalize-space(tr[32]/td[1])"/>
      </pos>
      <id>
        <xsl:value-of select="normalize-space(tr[32]/td[5])"/>
      </id>
      <laps>
        <xsl:value-of select="normalize-space(tr[32]/td[8])"/>
      </laps>
      <grid>
        <xsl:value-of select="normalize-space(tr[32]/td[12])"/>
      </grid>
		</driver>
		
		<driver>
      <pos>
        <xsl:value-of select="normalize-space(tr[34]/td[1])"/>
      </pos>
      <id>
        <xsl:value-of select="normalize-space(tr[34]/td[5])"/>
      </id>
      <laps>
        <xsl:value-of select="normalize-space(tr[34]/td[8])"/>
      </laps>
      <grid>
        <xsl:value-of select="normalize-space(tr[34]/td[12])"/>
      </grid>
		</driver>

		<driver>
      <pos>
        <xsl:value-of select="normalize-space(tr[36]/td[1])"/>
      </pos>
      <id>
        <xsl:value-of select="normalize-space(tr[36]/td[5])"/>
      </id>
      <laps>
        <xsl:value-of select="normalize-space(tr[36]/td[8])"/>
      </laps>
      <grid>
        <xsl:value-of select="normalize-space(tr[36]/td[12])"/>
      </grid>
		</driver>

		<driver>
      <pos>
        <xsl:value-of select="normalize-space(tr[38]/td[1])"/>
      </pos>
      <id>
        <xsl:value-of select="normalize-space(tr[38]/td[5])"/>
      </id>
      <laps>
        <xsl:value-of select="normalize-space(tr[38]/td[8])"/>
      </laps>
      <grid>
        <xsl:value-of select="normalize-space(tr[38]/td[12])"/>
      </grid>
		</driver>

		<driver>
      <pos>
        <xsl:value-of select="normalize-space(tr[40]/td[1])"/>
      </pos>
      <id>
        <xsl:value-of select="normalize-space(tr[40]/td[5])"/>
      </id>
      <laps>
        <xsl:value-of select="normalize-space(tr[40]/td[8])"/>
      </laps>
      <grid>
        <xsl:value-of select="normalize-space(tr[40]/td[12])"/>
      </grid>
		</driver>

		<driver>
      <pos>
        <xsl:value-of select="normalize-space(tr[42]/td[1])"/>
      </pos>
      <id>
        <xsl:value-of select="normalize-space(tr[42]/td[5])"/>
      </id>
      <laps>
        <xsl:value-of select="normalize-space(tr[42]/td[8])"/>
      </laps>
      <grid>
        <xsl:value-of select="normalize-space(tr[42]/td[12])"/>
      </grid>
		</driver>

		<driver>
      <pos>
        <xsl:value-of select="normalize-space(tr[44]/td[1])"/>
      </pos>
      <id>
        <xsl:value-of select="normalize-space(tr[44]/td[5])"/>
      </id>
      <laps>
        <xsl:value-of select="normalize-space(tr[44]/td[8])"/>
      </laps>
      <grid>
        <xsl:value-of select="normalize-space(tr[44]/td[12])"/>
      </grid>
		</driver>
		<fastestlap>
		  <xsl:value-of select="normalize-space(tr[46]/td[1])"/>
		</fastestlap>
  </xsl:template>
</xsl:stylesheet>