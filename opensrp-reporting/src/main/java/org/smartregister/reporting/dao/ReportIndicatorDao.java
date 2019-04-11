package org.smartregister.reporting.dao;


import net.sqlcipher.database.SQLiteDatabase;

import org.smartregister.reporting.model.IndicatorQuery;
import org.smartregister.reporting.model.IndicatorTally;
import org.smartregister.reporting.model.ReportIndicator;

import java.util.List;
import java.util.Map;

public interface ReportIndicatorDao {

    void addReportIndicator(ReportIndicator indicator);

    void addIndicatorQuery(IndicatorQuery indicatorQuery);

    List<Map<String, IndicatorTally>> getIndicatorsDailyTallies();

    void generateDailyIndicatorTallies(SQLiteDatabase database, String lastProcessedDate); // For all persisted indicators
}
