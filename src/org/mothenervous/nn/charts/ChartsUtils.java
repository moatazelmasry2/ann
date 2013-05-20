package org.mothenervous.nn.charts;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.management.RuntimeErrorException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ChartsUtils {

    /**
     * To indicate a horizontal chart type
     */
    public static final int ORIENTATION_HORIZONTAL = 1001;

    /**
     * To indicate a vertical chart type
     */
    public static final int ORIENTATION_VERTICAL = 1002;

    /**
     * Creates an XY Line chart
     * 
     * @param title
     * @param keyedValues
     *            key is the category name, value is a series tupels Format: key
     *            = x1,y1;x2,y2;x3,y3 Example row1 = 2,3;4,10 Note that x and y
     *            have to be numbers
     * @param width
     *            of the output image
     * @param height
     *            height of the output image
     * @param legend
     *            for the output chart
     * @param tooltips
     *            for the output de.latlon.charts
     * @param orientation
     *            Horiyontal or vertical chart
     * @param imageType
     *            of the output image
     * @param horizontalAxisName
     *            Name of the Horizontal Axis
     * @param verticalAxisName
     *            Name of the vertical Axis
     * @param chartConfigs
     *            to configure the output chart, or null to use the default
     *            ChartConfig
     * @return BufferedImage representing the generated chart
     * @throws IncorrectFormatException
     */
    public static BufferedImage createXYLineChart(String title, QueuedMap<String, String> keyedValues, int width,
            int height, boolean legend, boolean tooltips, int orientation, String imageType, String horizontalAxisName,
            String verticalAxisName) throws RuntimeException {
        XYDataset dataset = convertMapToXYSeriesDataSet(keyedValues);

        JFreeChart chart = null;
        chart = ChartFactory.createXYLineChart(title, horizontalAxisName, verticalAxisName, dataset,
                translateToPlotOrientation(orientation), legend, tooltips, false);

        XYSplineRenderer renderer = new XYSplineRenderer();
        XYPlot plot = (XYPlot) chart.getPlot();

        // FIXME
        plot.setRenderer(renderer);

        return createBufferedImage(configLineChart(chart, new ChartConfig()), width, height, imageType);
    }

    /**
     * Configures the pie chart according to the stored configurations file
     * 
     * @param chart
     * @param chartConfigs
     *            to configure the output chart
     * @return configured JFreeChart
     */
    protected static JFreeChart configLineChart(JFreeChart chart, ChartConfig chartConfigs) {

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(chartConfigs.isLineRenderLines(), chartConfigs
                .isLineRenderShapes());

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setRenderer(renderer);
        return chart;
    }

    /**
     * Creates a BufferedImage instance from a given chart, according to the
     * given additional parameters
     * 
     * @param chart
     * @param width
     *            of the generated image
     * @param height
     *            of the generated image
     * @param imageType
     *            ex image/png, image/jpg
     * @return BufferedImage
     */
    protected static BufferedImage createBufferedImage(JFreeChart chart, int width, int height, String imageType) {

        chart.setTextAntiAlias(true);
        chart.setAntiAlias(true);
        BufferedImage image = new BufferedImage(width, height, mapImageformat(imageType));
        Graphics2D g2 = image.createGraphics();
        chart.draw(g2, new Rectangle(new Dimension(width, height)));
        return image;
    }

    /**
     * Maps the image format to an appropriate type, either RGB or RGBA (allow
     * opacity). There are image types that allow opacity like png, while others
     * don't, like jpg
     * 
     * @param imgFormat
     * @return BufferedImage Type INT_ARGB if the mime type is image/png or
     *         image/gif INT_RGB else.
     */
    protected static int mapImageformat(String imgFormat) {
        if (("image/png").equals(imgFormat) || ("image/gif").equals(imgFormat)) {
            return BufferedImage.TYPE_INT_ARGB;
        }
        return BufferedImage.TYPE_INT_RGB;
    }

    /**
     * It takes in a map a QueuedMap and converts it to a XYDataSet.The two
     * tokens of each tupel have to be numbers Format: key = x1,y1;x2,y2;x3,y3;
     * Example row1 = 2,3;4,10;
     * 
     * @param keyedValues
     * @return CategoryDataSet
     * @throws IncorrectFormatException
     */
    protected static XYDataset convertMapToXYSeriesDataSet(QueuedMap<String, String> keyedValues) {

        XYSeriesCollection dataset = new XYSeriesCollection();

        for (String key : keyedValues.keySet()) {
            String value = keyedValues.get(key);
            ValueFormatsParser parser = new ValueFormatsParser(value);

            // if (!parser.isFormatSeriesXY() && !parser.isFormatUnknown()) {
            // throw new RuntimeException(new Error("Bad separator"));
            // }
            // if (parser.isFormatUnknown()) {
            // continue;
            // } else if (!parser.isFormatSeriesXY()) {
            // throw new RuntimeException(new Error("wrong format"));
            // }

            XYSeries series = new XYSeries(key);
            while (parser.hasNext()) {
                String tupel = parser.getNext();
                int separatorIndex = tupel.indexOf(",");
                if (separatorIndex == -1) {
                    separatorIndex = tupel.indexOf(" ");
                }
                if (separatorIndex == -1) {
                    throw new RuntimeErrorException(new Error("missing separator"));
                }

                String xValue = tupel.substring(0, separatorIndex);
                String yValue = tupel.substring(separatorIndex + 1, tupel.length());
                try {
                    series.add(Double.parseDouble(xValue), Double.parseDouble(yValue));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            dataset.addSeries(series);
        }
        return dataset;
    }

    /**
     * Translates an integer that represents the chart orientation to a plot
     * orientation instance
     * 
     * @param orientation
     * @return Horizontal plot orientation if orientation is Horizontal else
     *         Vertical
     */
    protected static PlotOrientation translateToPlotOrientation(int orientation) {

        if (orientation == ORIENTATION_HORIZONTAL) {
            return PlotOrientation.HORIZONTAL;
        }
        return PlotOrientation.VERTICAL;
    }
}
