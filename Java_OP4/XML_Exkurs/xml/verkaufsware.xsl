<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="html" encoding="UTF-8" indent="yes"/>
    <xsl:decimal-format name="de" decimal-separator="," grouping-separator="."/>

    <xsl:variable name="anzahl" select="count(//ware)"/>
    <xsl:variable name="summe"  select="sum(//preis)"/>

    <xsl:template match="/">
        <html lang="de">
        <head>
            <meta charset="UTF-8"/>
            <title>Warenliste</title>
            <link rel="stylesheet" href="verkaufsware_for_xsl.css"/>
        </head>
        <body>

            <header class="kopf">
                <h1>Warenliste</h1>
                <p><xsl:value-of select="$anzahl"/> Artikel</p>
            </header>

            <div class="stats">
                <div class="karte">
                    <div class="zahl"><xsl:value-of select="$anzahl"/></div>
                    <div class="label">Artikel</div>
                </div>
                <div class="karte">
                    <div class="zahl"><xsl:value-of select="sum(//bestand)"/></div>
                    <div class="label">Lagerbestand</div>
                </div>
                <div class="karte">
                    <div class="zahl">
                        <xsl:value-of select="format-number($summe,'#.##0,00','de')"/> €
                    </div>
                    <div class="label">Preissumme</div>
                </div>
            </div>

            <div class="tabelle">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th><th>Bezeichnung</th><th>Kategorie</th>
                            <th>Preis</th><th>Bestand</th>
                        </tr>
                    </thead>
                    <tbody>
                        <xsl:for-each select="warenliste/ware">
                            <xsl:sort select="preis" data-type="number" order="ascending"/>
                            <xsl:apply-templates select="."/>
                        </xsl:for-each>
                    </tbody>
                </table>
            </div>

            <footer>
                Warenliste | <xsl:value-of select="$anzahl"/> Artikel |
                Summe: <xsl:value-of select="format-number($summe,'#.##0,00','de')"/> €
            </footer>

        </body>
        </html>
    </xsl:template>

    <xsl:template match="ware">
        <xsl:variable name="p" select="number(preis)"/>
        <xsl:variable name="b" select="number(bestand)"/>
        <tr>
            <td><xsl:value-of select="@id"/></td>
            <td><strong><xsl:value-of select="bezeichnung"/></strong></td>
            <td>
                <span class="badge">
                    <xsl:attribute name="data-kat"><xsl:value-of select="@kategorie"/></xsl:attribute>
                    <xsl:value-of select="@kategorie"/>
                </span>
            </td>
            <td>
                <span>
                    <xsl:attribute name="class">
                        <xsl:choose>
                            <xsl:when test="$p &gt; 300">preis teuer</xsl:when>
                            <xsl:when test="$p &gt; 150">preis mittel</xsl:when>
                            <xsl:otherwise>preis guenstig</xsl:otherwise>
                        </xsl:choose>
                    </xsl:attribute>
                    <xsl:value-of select="format-number($p,'#.##0,00','de')"/> €
                </span>
            </td>
            <td>
                <span>
                    <xsl:attribute name="class">
                        <xsl:choose>
                            <xsl:when test="$b &gt; 50">bestand voll</xsl:when>
                            <xsl:when test="$b &gt; 20">bestand mittel</xsl:when>
                            <xsl:otherwise>bestand leer</xsl:otherwise>
                        </xsl:choose>
                    </xsl:attribute>
                    <span class="ampel"/> <xsl:value-of select="$b"/> Stk.
                </span>
            </td>
        </tr>
    </xsl:template>

</xsl:stylesheet>
