package com.autoapp.ct.cryptrack.task;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;

import com.autoapp.ct.cryptrack.MainActivity;
import com.autoapp.ct.cryptrack.R;
import com.autoapp.ct.cryptrack.helper.HttpUrlConnection;
import com.autoapp.ct.cryptrack.helper.QueryFactory;
import com.autoapp.ct.cryptrack.helper.QueryType;
import com.autoapp.ct.cryptrack.helper.RegExMatcher;
import com.autoapp.ct.cryptrack.job.SystemJobService;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by shwg on 10/8/2017.
 */

public class URLResponseTask extends AsyncTask<String, Void, String> {

    private Context mContext;
    private JobParameters mParameters;

    public URLResponseTask(Context context) {
        mContext = context;
    }

    public URLResponseTask(Context context, JobParameters parameters) {
        mContext = context;
        mParameters = parameters;
    }

    @Override
    protected String doInBackground(String... strings) {
        if(strings == null)
            return "";
        String query = strings[0];
        HttpUrlConnection http = new HttpUrlConnection();

        String result = "";
        try {
            result = http.sendGet(query);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        String result = RegExMatcher.getInstance().match(s, QueryFactory.getQueryPattern(QueryType.BIT_COIN));
        if( mContext instanceof MainActivity) {
            TextView tv = ((Activity) mContext).findViewById(R.id.value);
            tv.setText(result);
        } else {
            ((SystemJobService) mContext).jobFinished(mParameters, false);
            NotificationManager notificationManager = (NotificationManager)
                    mContext.getSystemService(NOTIFICATION_SERVICE);
            Intent intent = new Intent(mContext, MainActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(mContext, (int) System.currentTimeMillis(), intent, 0);
            Notification noti = new Notification.Builder(mContext)
                    .setContentTitle("Crypt Traker New updates")
                    .setContentText(result)
                    .setContentIntent(pIntent)
                    .setSmallIcon(R.drawable.ic_noti)
                    .build();
            noti.flags |= Notification.FLAG_AUTO_CANCEL;
            noti.defaults |= Notification.DEFAULT_VIBRATE;
            noti.defaults |= Notification.DEFAULT_SOUND;
            notificationManager.notify(0, noti);
        }
    }
}