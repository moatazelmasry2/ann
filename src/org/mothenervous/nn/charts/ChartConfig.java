//$$Header: $$
/*----------------------------------------------------------------------------
 This file is part of deegree, http://deegree.org/
 Copyright (C) 2001-2009 by:
 Department of Geography, University of Bonn
 and
 lat/lon GmbH

 This library is free software; you can redistribute it and/or modify it under
 the terms of the GNU Lesser General Public License as published by the Free
 Software Foundation; either version 2.1 of the License, or (at your option)
 any later version.
 This library is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 details.
 You should have received a copy of the GNU Lesser General Public License
 along with this library; if not, write to the Free Software Foundation, Inc.,
 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

 Contact information:

 lat/lon GmbH
 Aennchenstr. 19, 53177 Bonn
 Germany
 http://lat-lon.de/

 Department of Geography, University of Bonn
 Prof. Dr. Klaus Greve
 Postfach 1147, 53001 Bonn
 Germany
 http://www.geographie.uni-bonn.de/deegree/

 e-mail: info@deegree.org
 ----------------------------------------------------------------------------*/

package org.mothenervous.nn.charts;

import java.awt.Color;

/**
 * This class reads the configurations file for the charts and acts as a data
 * class for the configurations It parses the configurations file according to a
 * given schema and throws an exception if the parsing failed It also assigns
 * default values to variables that are not mandatory and don't exist in the
 * configs file
 * 
 * @author <a href="mailto:elmasry@lat-lon.de">Moataz Elmasry</a>
 * @author last edited by: $Author: elmasri$
 * 
 * @version $Revision: $, $Date: 25 Apr 2008 13:50:16$
 */
public class ChartConfig {

    private boolean genAntiAliasing = true;

    private boolean genTextAntiAlias = false;

    private boolean genBorderVisible = true;

    private boolean plotOutlineVisible = true;

    private String genRectangleInsets = "0,0,40,40";

    private Color genBackgroundColor = new Color(255, 255, 255);

    private String genFontFamily;

    private String genFontType;

    private double genFontSize;

    private double plotForegroundOpacity;

    private Color plotBackgroundColor = null;

    private double plotBackgroundOpacity = 0.5;

    private double pieInteriorGap = 0.01;

    private double pieLabelGap = 0.01;

    private boolean pieCircular;

    private Color pieBaseSectionColor = new Color(255, 255, 255);

    private Color pieShadowColor = new Color(255, 255, 255);

    private boolean lineRenderLines = true;

    private boolean lineRenderShapes = true;

    /**
     * @return is enabled Antialiasing for the chart
     */
    public boolean isGenAntiAliasing() {
        return this.genAntiAliasing;
    }

    /**
     * @return Background color of the chart
     */
    public Color getGenBackgroundColor() {
        return this.genBackgroundColor;
    }

    /**
     * @return is cart borders visible
     */
    public boolean isGenBorderVisible() {
        return this.genBorderVisible;
    }

    /**
     * @return font family of the chart
     */
    public String getGenFontFamily() {
        return this.genFontFamily;
    }

    /**
     * @return font size of the chart
     */
    public double getGenFontSize() {
        return this.genFontSize;
    }

    /**
     * @return font type of the chart
     */
    public String getGenFontType() {
        return this.genFontType;
    }

    /**
     * @return is chart outline visible
     */
    public boolean isPlotOutlineVisible() {
        return this.plotOutlineVisible;
    }

    /**
     * @return RectangleInsets of the chart
     */
    public String getGenRectangleInsets() {
        return this.genRectangleInsets;
    }

    /**
     * @return is enabled Text AntiAliasing
     */
    public boolean isGenTextAntiAlias() {
        return this.genTextAntiAlias;
    }

    /**
     * @return is enabled line rendering in Line Chart
     */
    public boolean isLineRenderLines() {
        return this.lineRenderLines;
    }

    /**
     * @return is enabled shape rendering in Line Chart
     */
    public boolean isLineRenderShapes() {
        return this.lineRenderShapes;
    }

    /**
     * @return BaseSectionColor of Pie chart
     */
    public Color getPieBaseSectionColor() {
        return this.pieBaseSectionColor;
    }

    /**
     * @return is the Pie Chart circular
     */
    public boolean isPieCircular() {
        return this.pieCircular;
    }

    /**
     * @return interior gap of the Pie chart
     */
    public double getPieInteriorGap() {
        return this.pieInteriorGap;
    }

    /**
     * @return label gap of the Pie Chart
     */
    public double getPieLabelGap() {
        return this.pieLabelGap;
    }

    /**
     * @return Background color of the general plot
     */
    public Color getPlotBackgroundColor() {
        return this.plotBackgroundColor;
    }

    /**
     * @return Background opacity of the general plot
     */
    public double getPlotBackgroundOpacity() {
        return this.plotBackgroundOpacity;
    }

    /**
     * @return Foreground opacity of the plot
     */
    public double getPlotForegroundOpacity() {
        return this.plotForegroundOpacity;
    }

    /**
     * @return Shadow Color of the Pie Chart
     */
    public Color getPieShadowColor() {
        return this.pieShadowColor;
    }
}
