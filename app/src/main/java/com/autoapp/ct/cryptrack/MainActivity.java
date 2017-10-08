package com.autoapp.ct.cryptrack;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.autoapp.ct.cryptrack.helper.HttpUrlConnection;
import com.autoapp.ct.cryptrack.helper.QueryFactory;
import com.autoapp.ct.cryptrack.helper.QueryType;
import com.autoapp.ct.cryptrack.helper.RegExMatcher;
import com.autoapp.ct.cryptrack.job.SystemJobService;
import com.autoapp.ct.cryptrack.task.URLResponseTask;

public class MainActivity extends AppCompatActivity {
    private static final int BIT_COIN_JOB_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new URLResponseTask(this).execute(QueryFactory.getQuery(QueryType.BIT_COIN));
        JobScheduler jobScheduler;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            jobScheduler = getSystemService(JobScheduler.class);
        } else {
            jobScheduler = (JobScheduler) getSystemService( Context.JOB_SCHEDULER_SERVICE );
        }
        JobInfo jobInfo = new JobInfo.Builder(BIT_COIN_JOB_ID, new ComponentName(this, SystemJobService.class))
                .setPeriodic(15 * 60 * 1000)
                .setPersisted(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setBackoffCriteria(60 * 1000, JobInfo.BACKOFF_POLICY_LINEAR)
                .build();
        jobScheduler.schedule(jobInfo);
    }
}
