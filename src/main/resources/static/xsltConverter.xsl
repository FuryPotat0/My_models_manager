<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:template match="/">
        <html>
            <head>
                <title>Models</title>
                <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
                      integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
                      crossorigin="anonymous"/>
            </head>
            <body>
                <h1>Preview</h1>
                <form action="/" method="get">
                    <button type="submit" class="button-get">Return home</button>
                </form>
                <h2>Manufacturers</h2>
                <table border="2px" rules="cols" cellpadding="10" align="center" width="99%">
                    <tr>
                        <th>Id</th>
                        <th>Name</th>
                    </tr>
                    <xsl:for-each select="ModelManagerRootXml/Manufacturers/manufacturer">
                        <tr>
                            <td>
                                <xsl:value-of select="id"/>
                            </td>
                            <td>
                                <xsl:value-of select="name"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>

                <h2>Model statuses</h2>
                <table border="2px" rules="cols" cellpadding="10" align="center" width="99%">
                    <tr>
                        <th>Id</th>
                        <th>Name</th>
                    </tr>
                    <xsl:for-each select="ModelManagerRootXml/ModelStatuses/status">
                        <tr>
                            <td>
                                <xsl:value-of select="id"/>
                            </td>
                            <td>
                                <xsl:value-of select="name"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>

                <h2>Rooms</h2>
                <table border="2px" rules="cols" cellpadding="10" align="center" width="99%">
                    <tr>
                        <th>Id</th>
                        <th>Name</th>
                    </tr>
                    <xsl:for-each select="ModelManagerRootXml/Rooms/room">
                        <tr>
                            <td>
                                <xsl:value-of select="id"/>
                            </td>
                            <td>
                                <xsl:value-of select="name"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>

                <h2>Places</h2>
                <table border="2px" rules="cols" cellpadding="10" align="center" width="99%">
                    <tr>
                        <th>Id</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Room Id</th>
                    </tr>
                    <xsl:for-each select="ModelManagerRootXml/Places/place">
                        <tr>
                            <td>
                                <xsl:value-of select="id"/>
                            </td>
                            <td>
                                <xsl:value-of select="name"/>
                            </td>
                            <td>
                                <xsl:value-of select="description"/>
                            </td>
                            <td>
                                <xsl:value-of select="roomId"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>

                <h2>Storages</h2>
                <table border="2px" rules="cols" cellpadding="10" align="center" width="99%">
                    <tr>
                        <th>Id</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Place Id</th>
                    </tr>
                    <xsl:for-each select="ModelManagerRootXml/Storages/storage">
                        <tr>
                            <td>
                                <xsl:value-of select="id"/>
                            </td>
                            <td>
                                <xsl:value-of select="name"/>
                            </td>
                            <td>
                                <xsl:value-of select="description"/>
                            </td>
                            <td>
                                <xsl:value-of select="placeId"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>

                <h2>Models</h2>
                <table border="2px" rules="cols" cellpadding="10" align="center" width="99%">
                    <tr>
                        <th>Id</th>
                        <th>Name</th>
                        <th>Models in squad</th>
                        <th>Description</th>
                        <th>Manufacturer id</th>
                        <th>ModelStatus id</th>
                        <th>Storage id</th>
                    </tr>
                    <xsl:for-each select="ModelManagerRootXml/Models/model">
                        <tr>
                            <td>
                                <xsl:value-of select="id"/>
                            </td>
                            <td>
                                <xsl:value-of select="name"/>
                            </td>
                            <td>
                                <xsl:value-of select="modelsInSquad"/>
                            </td>
                            <td>
                                <xsl:value-of select="description"/>
                            </td>
                            <td>
                                <xsl:value-of select="manufacturerId"/>
                            </td>
                            <td>
                                <xsl:value-of select="modelStatusId"/>
                            </td>
                            <td>
                                <xsl:value-of select="storageId"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>