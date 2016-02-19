package com.egnesse.skulapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.egnesse.skulapp.dto.SessionDTO;
import com.egnesse.skulapp.util.SckulApp;
import com.google.android.gcm.GCMBaseIntentService;
import com.google.gson.Gson;

public class GCMIntentService extends GCMBaseIntentService {
	private static final String TAG = "SckulApp::Service";
	public static final String SENDER_ID = "388722894345";

	public GCMIntentService() {
		super(SENDER_ID);
	}

	@Override
	protected void onRegistered(Context context, String registrationId) {
		SessionDTO sessionDTO = SckulApp.getSession(context);
		SharedPreferences.Editor editor = SckulApp.getSharedEditor(context);
		Gson gson = new Gson();

		if(sessionDTO.getGcmKey() == null){
            sessionDTO.setGcmKey(registrationId);


			editor.putString(SckulApp.SESSION, gson.toJson(sessionDTO));
            editor.commit();

        }
		Log.i(TAG, "onRegistered: registrationId=" + registrationId);


	}

	@Override
	protected void onUnregistered(Context context, String registrationId) {
		Log.i(TAG, "onUnregistered: registrationId=" + registrationId);
	}

	@Override
	protected void onMessage(Context context, Intent data){

      /*  String message;
        message = data.getStringExtra("data");
		Log.i(TAG, message + "aditya");
		Intent intent = new Intent(this, DashboardActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		@SuppressWarnings("deprecation")
		Notification notification = new Notification.Builder(this).setSmallIcon(R.drawable.ic_launcher).setWhen(System.currentTimeMillis())
				.setContentTitle("Newton Gift").setContentText(message).setContentIntent(pIntent).getNotification();
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		manager.notify(R.string.app_name, notification);
		{
			PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
			@SuppressWarnings("deprecation")
			final PowerManager.WakeLock mWakelock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "GCM_PUSH");
			mWakelock.acquire();
			Timer timer = new Timer();
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					mWakelock.release();
				}
			};
			timer.schedule(task, 5000);
		}*/

	}

	@Override
	protected void onError(Context arg0, String errorId) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        SessionDTO sessionDTO  = gson.fromJson(sharedPreferences.getString("session", null), SessionDTO.class);
        /*if(sessionDTO.getGcmKey() == null){
            Intent intent = new Intent("gcm-registration");
            intent.putExtra("message", errorId);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }*/

		Log.i(TAG, "onError: errorId=" + errorId);
	}

}