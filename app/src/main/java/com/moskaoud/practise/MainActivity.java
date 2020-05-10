package com.moskaoud.practise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;

import static java.lang.Thread.sleep;


public class MainActivity extends AppCompatActivity {

    public static final String TXT_REPLAY = "text_replay";
    private final String CHANNEL_ID =  "channel_id";
    public final static int NOTIFICATION_ID = 121;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Display simple messages in a popup using a Toast
        //shortcut write Toast then tab will create a toast
        Toast.makeText(this, "TOAST Mostafa", Toast.LENGTH_SHORT).show();

        // custom Toast
        //View v = LayoutInflater.from(this).inflate(R.layout.cutom_toast,null,false);
        View v = getLayoutInflater().inflate(R.layout.cutom_toast, null, false);
        Toast t = new Toast(this);
        t.setView(v);
        t.setDuration(Toast.LENGTH_SHORT);
        t.show();

        //Display simple messages in a popup using a  a Snackbar
        CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinator);
        //Snackbar.make(coordinatorLayout,"Snackbar ", Snackbar.LENGTH_SHORT).show();
        Snackbar.make(coordinatorLayout,"Snackbar Mostafa ", Snackbar.LENGTH_SHORT)
                .setAction("Click", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "MOSKAOUD", Toast.LENGTH_SHORT).show();
            }
        }).show();



    }
    public void displayNotification(View view ){
        // for Oreo and above versions
        notificationOreo();

        //for handle notification press
        Intent secondActivity = new Intent(this,SecondActivity.class);
        secondActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent =  PendingIntent.getActivity(this,
                3,secondActivity,PendingIntent.FLAG_ONE_SHOT);

        // when action yes
        Intent yesActivity = new Intent(this,YesActivity.class);
        yesActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent yesPendingIntent =
                PendingIntent.getActivity(this,1,yesActivity,PendingIntent.FLAG_ONE_SHOT);

        // when action no
        Intent noActivity = new Intent(this,NoActivity.class);
        yesActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent noPendingIntent =
                PendingIntent.getActivity(this,1,noActivity,PendingIntent.FLAG_ONE_SHOT);


        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_sms);
        builder.setContentTitle("Mostafa");
        builder.setContentText("Mostafa Mohamed Abdelhameed");
        builder.setPriority(Notification.DEFAULT_ALL);
        builder.setAutoCancel(true);

        builder.setContentIntent(pendingIntent);

        builder.addAction(android.R.drawable.star_big_on,"YES",yesPendingIntent);
        builder.addAction(android.R.drawable.arrow_up_float,"NO",noPendingIntent);

        //direct replay on notification
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            RemoteInput remoteInput = new RemoteInput.Builder(TXT_REPLAY).setLabel("REPLAY").build();

            Intent replayIntent = new Intent(this,SecondActivity.class);
            replayIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent replayPendingIntent =
                    PendingIntent.getActivity(this,0,replayIntent,PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Action action =
                    new NotificationCompat.Action.Builder(android.R.drawable.btn_radio,"GO",replayPendingIntent)
                            .addRemoteInput(remoteInput).build();
            builder.addAction(action);

        }
        final NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        //notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());

        //Expandable Notification image
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.nature);
        builder.setLargeIcon(bitmap);//this small picture beside title
        //builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null));
        //notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
        //Expandable Notification text
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(getString(R.string.me)));
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());

        //custom Layout
        RemoteViews normal = new RemoteViews(getPackageName(),R.layout.normal_notification);
        RemoteViews expanded = new RemoteViews(getPackageName(),R.layout.expanded_notification);
        builder.setStyle(new NotificationCompat.DecoratedCustomViewStyle());
        builder.setCustomContentView(normal);
        builder.setCustomBigContentView(expanded);
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());

        // progressbar in notification
        /*final int maxProgress = 100;
        int currentProgress = 0;
        builder.setProgress(maxProgress,currentProgress,false);
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                while (count<100)
                {
                    count++;
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    builder.setProgress(maxProgress,count,false);
                    notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
                }
            }
        });
        thread.start();
            */
    }

    private void notificationOreo() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = "Notification Channel Name";
            String description = "Description For Channel notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name,importance);
            notificationChannel.setDescription(description);
        }
    }
}
