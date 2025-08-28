package com.example.smartclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.example.smartclock.weather.WeatherApiClient;
import com.example.smartclock.weather.WeatherApiResponse;
import java.util.Calendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getBooleanExtra("isPostponed", false)) {
            showAlarmNotification(context, "Wake up!", "Time to start your day!");
            return;
        }

        double latitude = intent.getDoubleExtra("latitude", 0);
        double longitude = intent.getDoubleExtra("longitude", 0);

        if (latitude != 0 && longitude != 0) {
            // TODO: For long-running tasks, consider using goAsync() and a background thread.
            fetchWeatherAndProcessAlarm(context, latitude, longitude);
        } else {
            showAlarmNotification(context, "Wake up!", "Time to start your day!");
        }
    }

    private void fetchWeatherAndProcessAlarm(Context context, double latitude, double longitude) {
        String apiKey = context.getString(R.string.weather_api_key);
        String q = latitude + "," + longitude;
        WeatherApiClient.getClient().getWeather(apiKey, q)
                .enqueue(new Callback<WeatherApiResponse>() {
                    @Override
                    public void onResponse(Call<WeatherApiResponse> call, Response<WeatherApiResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            WeatherApiResponse weatherResponse = response.body();
                            String weatherText = weatherResponse.current.condition.text.toLowerCase();
                            double temp = weatherResponse.current.tempC;

                            if (weatherText.contains("rain") || weatherText.contains("snow") || temp < -15) {
                                postponeAlarm(context);
                            } else {
                                showAlarmNotification(context, "Wake up!", "Time to start your day!");
                            }
                        } else {
                            showAlarmNotification(context, "Wake up!", "Time to start your day!");
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherApiResponse> call, Throwable t) {
                        showAlarmNotification(context, "Wake up!", "Time to start your day!");
                    }
                });
    }

    private void postponeAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("isPostponed", true);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 10);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        showAlarmNotification(context, "Alarm Postponed", "Weather is bad. Alarm postponed for 10 minutes.");
    }

    private void showAlarmNotification(Context context, String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Constants.ALARM_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(123, builder.build());
    }
}
