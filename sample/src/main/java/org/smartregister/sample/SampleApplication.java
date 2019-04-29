package org.smartregister.sample;

import com.evernote.android.job.JobManager;

import org.smartregister.Context;
import org.smartregister.CoreLibrary;
import org.smartregister.reporting.ReportingLibrary;
import org.smartregister.reporting.domain.IndicatorQuery;
import org.smartregister.reporting.domain.ReportIndicator;
import org.smartregister.reporting.job.IndicatorGeneratorJobCreator;
import org.smartregister.repository.Repository;
import org.smartregister.sample.repository.SampleRepository;
import org.smartregister.view.activity.DrishtiApplication;


import java.util.List;

import static org.smartregister.util.Log.logError;

public class SampleApplication extends DrishtiApplication {

    private String indicatorsConfigFile = "config/indicator-definitions.yml";

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        context = Context.getInstance();
        context.updateApplicationContext(getApplicationContext());
        CoreLibrary.init(context);
        repository = getRepository();
        ReportingLibrary.init(Context.getInstance(), repository, null, BuildConfig.VERSION_CODE, BuildConfig.DATABASE_VERSION);
        ReportingLibrary.getInstance().initIndicatorData(indicatorsConfigFile);
        List<ReportIndicator> reportIndicators = ReportingLibrary.getInstance().getReportIndicators();
        List<IndicatorQuery> indicatorQueries = ReportingLibrary.getInstance().getIndicatorQueries();
        SampleRepository.addSampleData(reportIndicators, indicatorQueries);
        JobManager.create(this).addJobCreator(new IndicatorGeneratorJobCreator());
    }

    public Repository getRepository() {
        try {
            if (repository == null) {
                repository = new SampleRepository(getInstance().getApplicationContext(), context);
            }
        } catch (UnsatisfiedLinkError e) {
            logError("Error on getRepository: " + e);

        }
        return repository;
    }

    public static synchronized SampleApplication getInstance() {
        return (SampleApplication) mInstance;
    }

    @Override
    public void logoutCurrentUser() {

    }
}
