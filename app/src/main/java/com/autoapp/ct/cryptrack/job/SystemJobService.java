package com.autoapp.ct.cryptrack.job;

import android.app.job.JobParameters;
import android.app.job.JobService;

import com.autoapp.ct.cryptrack.helper.QueryFactory;
import com.autoapp.ct.cryptrack.helper.QueryType;
import com.autoapp.ct.cryptrack.task.URLResponseTask;

public class SystemJobService extends JobService {
    public SystemJobService() {
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        new URLResponseTask(this, jobParameters).execute(QueryFactory.getQuery(QueryType.BIT_COIN));
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}
