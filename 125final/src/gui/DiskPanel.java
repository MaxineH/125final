package gui;

import java.awt.GridLayout;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

@SuppressWarnings("serial")
public class DiskPanel extends JPanel {
	
	private XYSeries series;
	private ChartPanel chartPanel;
	private JFreeChart chart;
	
	private int head;
	
	public DiskPanel(int max,int head) {
		this.head=head;
		XYDataset dataset=createDataSet();
		chart=ChartFactory.createXYLineChart(null,"Time","Cylinder",dataset,
				PlotOrientation.VERTICAL,true,true,false);
		chart.removeLegend();
		
		chartPanel=new ChartPanel(chart);
		setLayout(new GridLayout(1,1));
		add(chartPanel);
		
		XYPlot plot=chart.getXYPlot();
		XYLineAndShapeRenderer renderer=new XYLineAndShapeRenderer();
		plot.setRenderer(renderer);
		
		NumberAxis domain=(NumberAxis)plot.getDomainAxis();
		domain.setRange(0.0,1.0);
		NumberAxis range=(NumberAxis)plot.getRangeAxis();
		range.setRange(0.0,max);
		chartPanel.repaint();
	}
	
	private XYDataset createDataSet() {
		XYSeriesCollection dataset=new XYSeriesCollection();
		series=new XYSeries("Cylinders",false,true);
		
		series.add(0.0,head);
		dataset.addSeries(series);
		return dataset;
	}
	
	public void updateChart(int i,int x) {
		if (series.getItemCount()==1) {
			XYPlot plot=chart.getXYPlot();
			NumberAxis domain=(NumberAxis)plot.getDomainAxis();
			domain.setAutoRange(true);
		}
		series.add(i,x);
	}
}