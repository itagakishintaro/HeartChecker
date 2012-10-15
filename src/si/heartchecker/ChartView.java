package si.heartchecker;

import org.afree.chart.AFreeChart;
import org.afree.graphics.geom.RectShape;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

@SuppressLint("DrawAllocation")
public class ChartView extends View {
    private AFreeChart chart;

    static final double RECT_SHAPE_WIDTH = 450.0;
    static final double RECT_SHAPE_HEIGHT = 450.0;

    public ChartView(final Context context, final AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected final void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        RectShape chartArea = new RectShape(0.0, 0.0, RECT_SHAPE_WIDTH,
                RECT_SHAPE_HEIGHT);
        this.chart.draw(canvas, chartArea);
    }

    public final void setChart(final AFreeChart chart) {
        this.chart = chart;
    }

}
