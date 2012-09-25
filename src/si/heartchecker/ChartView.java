package si.heartchecker;

import org.afree.chart.AFreeChart;
import org.afree.graphics.geom.RectShape;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class ChartView extends View {

	private AFreeChart chart;

	public ChartView(final Context context, final AttributeSet attributeSet) {
		super(context, attributeSet);
	}

	@Override
    protected final void onDraw(final Canvas canvas) {
		super.onDraw(canvas);
		RectShape chartArea = new RectShape(0.0, 0.0, 450.0, 450.0);
		this.chart.draw(canvas, chartArea);
	}

	public final void setChart(final AFreeChart chart) {
		this.chart = chart;
	}

}
