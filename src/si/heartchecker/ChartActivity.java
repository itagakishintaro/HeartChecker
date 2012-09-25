package si.heartchecker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.afree.chart.AFreeChart;
import org.afree.chart.ChartFactory;
import org.afree.chart.axis.CategoryAxis;
import org.afree.chart.axis.CategoryLabelPositions;
import org.afree.chart.plot.CategoryPlot;
import org.afree.chart.plot.PlotOrientation;
import org.afree.data.category.DefaultCategoryDataset;
//import org.afree.data.general.DefaultPieDataset;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChartActivity extends Activity {
    private ArrayList<String> heartList = new ArrayList<String>();
    private CreateHeartLogHelper helper = null;
    private HeartLogDAO heartLogDAO = null;

    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setup();
        // 週チャート表示
        viewWeekChart();
    }

    private void setup() {
        setContentView(R.layout.chart);

        // Heart Type�ݒ�
        heartList.add("HAPPY");
        heartList.add("SAD");
        heartList.add("ANGRY");
        heartList.add("DOKIDOKI");

        // DB
        helper = new CreateHeartLogHelper(ChartActivity.this);
        heartLogDAO = new HeartLogDAO(helper);
    }

    public final void onWeekChartButtonClick(final View v) {
        setup();
        viewWeekChart();
    }

    public final void onMonthChartButtonClick(final View v) {
        setup();
        viewMonthChart();
    }

    public final void onTopBackButtonClick(final View v) {
        Intent intent = new Intent(ChartActivity.this,
                HeartCheckerActivity.class);
        startActivity(intent);
    }

    private void viewWeekChart() {
        Calendar calendar = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        // 曜日の設定
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String sunday = df.format(calendar.getTime());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String monday = df.format(calendar.getTime());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        String tuesday = df.format(calendar.getTime());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        String wednesday = df.format(calendar.getTime());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        String thursday = df.format(calendar.getTime());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        String friday = df.format(calendar.getTime());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        String saturday = df.format(calendar.getTime());

        // HeartType、曜日ごとのカウント設定
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (String heartType : heartList) {
            dataset.addValue(heartLogDAO.countHeartInDate(heartType, sunday),
                    heartType, "Sun");
            dataset.addValue(heartLogDAO.countHeartInDate(heartType, monday),
                    heartType, "Mon");
            dataset.addValue(heartLogDAO.countHeartInDate(heartType, tuesday),
                    heartType, "Tue");
            dataset.addValue(heartLogDAO.countHeartInDate(heartType, wednesday),
                    heartType, "Wed");
            dataset.addValue(heartLogDAO.countHeartInDate(heartType, thursday),
                    heartType, "Thu");
            dataset.addValue(heartLogDAO.countHeartInDate(heartType, friday),
                    heartType, "Fri");
            dataset.addValue(heartLogDAO.countHeartInDate(heartType, saturday),
                    heartType, "Sat");
        }

        viewChart("Week Graph", "Day", "Points", dataset,
                PlotOrientation.VERTICAL, true, false, false);
    }

    private void viewMonthChart() {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-");
        String yearMonth = df.format(date);
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // �O���t�ɂ���f�[�^�̍쐬
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 1; i <= lastDay; i++) {
            for (String heartType : heartList) {
                dataset.addValue(
                        heartLogDAO.countHeartInDate(heartType, yearMonth
                                + String.format("%02d", i)), heartType,
                        String.valueOf(i));
            }
        }

        // AFreeChart�̍쐬
        viewChart("Month Graph", "Day", "Points", dataset,
                PlotOrientation.VERTICAL, true, false, false);
    }

    private void viewChart(final String title, final String xTitle,
            final String yTitle, final DefaultCategoryDataset dataset,
            final PlotOrientation plotOrientation, final boolean example,
            final boolean tooltip, final boolean url) {

        AFreeChart chart = ChartFactory.createLineChart(title, xTitle, yTitle,
                dataset, plotOrientation, example, tooltip, url);

        // �����̖��̕\�����X����
        final CategoryPlot plot = chart.getCategoryPlot();
        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions
                .createUpRotationLabelPositions(-Math.PI / 6.0));

        // �\��
        ChartView chartview = (ChartView) findViewById(R.id.chart_view);
        chartview.setChart(chart);
    }

}
