package com.example.com.my_achartnegine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.List;

/**
 * Created by Administrator on 2016/7/28.
 */
public class ChartService {
    private GraphicalView mGraphicalView;
    private XYMultipleSeriesDataset multipleSeriesDataset;// 数据集容器
    private XYMultipleSeriesRenderer multipleSeriesRenderer;// 渲染器容器
    private XYSeries mSeries;// 单条曲线数据集
    private XYSeriesRenderer mRenderer;// 单条曲线渲染器
    private Context context;

    public ChartService(Context context) {
        this.context = context;
    }

    /**
     * 获取图表
     *
     * @return
     */
    public GraphicalView getGraphicalView() {
        mGraphicalView =ChartFactory.getLineChartView(context,
                multipleSeriesDataset, multipleSeriesRenderer);


        return mGraphicalView;
    }

    public GraphicalView getGraphicalView_Bar() {
        mGraphicalView =  ChartFactory.getBarChartView(context, multipleSeriesDataset, multipleSeriesRenderer,
                BarChart.Type.STACKED);
//        		 ChartFactory.getCubeLineChartView(context,
//                 multipleSeriesDataset, multipleSeriesRenderer, 0f);
        return mGraphicalView;
    }
    /**
     * 获取数据集，及xy坐标的集合
     *
     * @param curveTitle
     */
    public void setXYMultipleSeriesDataset(String curveTitle) {
        multipleSeriesDataset = new XYMultipleSeriesDataset();
        mSeries = new XYSeries(curveTitle);
        multipleSeriesDataset.addSeries(mSeries);
    }

    /**
     * 获取渲染器
     *
     * @param maxX
     *            x轴最大值
     * @param maxY
     *            y轴最大值
     * @param chartTitle
     *            曲线的标题
     * @param xTitle
     *            x轴标题
     * @param yTitle
     *            y轴标题
     * @param axeColor
     *            坐标轴颜色
     * @param labelColor
     *            标题颜色
     * @param curveColor
     *            曲线颜色
     * @param gridColor
     *            网格颜色
     */
    public void setXYMultipleSeriesRenderer(double maxX, double maxY,
                                            String chartTitle, String xTitle, String yTitle, int axeColor,
                                            int labelColor, int curveColor, int gridColor) {
        multipleSeriesRenderer = new XYMultipleSeriesRenderer();//获得一个渲染器
        if (chartTitle != null) {//对系统的优化
            multipleSeriesRenderer.setChartTitle(chartTitle);
        }
        multipleSeriesRenderer.setXTitle(xTitle);//设置X轴标识
        multipleSeriesRenderer.setYTitle(yTitle);//设置Y轴标识
        multipleSeriesRenderer.setRange(new double[] { 0, maxX, 0, maxY });//xy轴的范围
        multipleSeriesRenderer.setLabelsColor(labelColor);
//        multipleSeriesRenderer.setXLabels(5);//设置段数
//        multipleSeriesRenderer.setYLabels(5);
//        multipleSeriesRenderer.setXLabelsAlign(Paint.Align.CENTER); //设置X、y轴数字的位置
//        multipleSeriesRenderer.setYLabelsAlign(Paint.Align.RIGHT);
//        multipleSeriesRenderer.setAxisTitleTextSize(10);
//        multipleSeriesRenderer.setChartTitleTextSize(10);//设置X、y轴的标识大小
//        multipleSeriesRenderer.setLabelsTextSize(10);//设置X、y轴数字的大小
//        multipleSeriesRenderer.setLegendTextSize(10);
//        multipleSeriesRenderer.setPointSize(2f);//曲线描点尺寸
//        multipleSeriesRenderer.setFitLegend(false);// 调整合适的位置
        multipleSeriesRenderer.setMargins(new int[] { 20, 30, 15, 20 });
//        multipleSeriesRenderer.setShowGrid(false);
        multipleSeriesRenderer.setBarSpacing(0.5f);//柱子间宽度
        multipleSeriesRenderer.setZoomEnabled(true, false);//设置放大镜
        multipleSeriesRenderer.setAxesColor(axeColor);
        multipleSeriesRenderer.setGridColor(gridColor);
//        multipleSeriesRenderer.setBackgroundColor(Color.WHITE);//背景色
//        multipleSeriesRenderer.setMarginsColor(Color.WHITE);//边距背景色，默认背景色为黑色，这里修改为白色
        mRenderer = new XYSeriesRenderer();//主要是用来设置一条线条的风格，颜色啊，粗细之类的。
        mRenderer.setColor(curveColor);
//        mRenderer.setPointStyle(PointStyle.CIRCLE);//描点风格，可以为圆点，方形点等等
        multipleSeriesRenderer.addSeriesRenderer(mRenderer);
    }

    /**
     * 根据新加的数据，更新曲线，只能运行在主线程
     *
     * @param x
     *            新加点的x坐标
     * @param y
     *            新加点的y坐标
     */
    public void updateChart(double x, double y) {
        mSeries.add(x, y);
        mGraphicalView.repaint();//此处也可以调用invalidate()
    }

    /**
     * 添加新的数据，多组，更新曲线，只能运行在主线程
     * @param xList
     * @param yList
     */
    public void updateChart(List<Double> xList, List<Double> yList) {
        for (int i = 0; i < xList.size(); i++) {
            mSeries.add(xList.get(i), yList.get(i));
        }
        mGraphicalView.repaint();//此处也可以调用invalidate()
    }
}
