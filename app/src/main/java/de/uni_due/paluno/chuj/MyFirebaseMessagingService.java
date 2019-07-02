package de.uni_due.paluno.chuj;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import de.uni_due.paluno.chuj.Models.GetMessages;
import de.uni_due.paluno.chuj.Models.Status;
import de.uni_due.paluno.chuj.Models.User;
import de.uni_due.paluno.chuj.Models.partConversationModel;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    SharedPreferences prefrences;
    private String username;
    private boolean loginStatus;
    private ActivityManager activityManager;
    private boolean msgStatus;
    private String password;
    private boolean menuStatus;
    private Date date;
    private SimpleDateFormat formatter;




        @Override
        public void onMessageReceived(RemoteMessage remoteMessage) {


            Status.setNotificationStatus(true);
                prefrences = getSharedPreferences("login", MODE_PRIVATE);
                username = prefrences.getString("username", "").toString();
                msgStatus = Status.getMsgStatus();
                password=prefrences.getString("password","");
                menuStatus=Status.getMenuStatus();


            formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");


                loginStatus = prefrences.getBoolean("loginStatus",false);
            String sender = remoteMessage.getData().get("sender");
            String preview = remoteMessage.getData().get("preview");
            String showPreview;

            if(isBase64(preview)){
                showPreview=sender+" sent you a picture!";
            } else

                if(preview.contains("|"))
                {
                    showPreview=sender+"´s location";

                }
                else
                {
                    showPreview=preview;
                }

           // Toast.makeText(this,loginStatus+"",Toast.LENGTH_SHORT).show();
               if (loginStatus == true) {
                   if(msgStatus==true)
                   {
                       if (!sender.equals(username)) {
                      // showNotificationInApp(showPreview, sender);
                   }
                   }

                   else
                       if(Status.getMenuStatus()==true)
                       {
                          date = new Date();
                          date.setTime(date.getTime()-100000);
                           try {
                               date = formatter.parse(date.toString());
                           } catch (ParseException e) {
                               e.printStackTrace();
                           }
                         //  MessaginActivity.getPartConversation( new partConversationModel(username, password, sender,date.toString()));
                          // showNotificationInMenu(showPreview,sender);

                       }
                       else
                   {
                       showNotification(remoteMessage.getData().get("preview"), sender);
                   }

                }


        }

        private void showNotification(String message, String recipent) {



            Intent i = new Intent(this,MessaginActivity.class);
            i.putExtra("recipent",recipent);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            MessaginActivity.getMesseages( new GetMessages(username, password, recipent));

            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String name = "Channel";
                String description = "Description";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel("id", name, importance);
                channel.setDescription(description);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this

                notificationManager.createNotificationChannel(channel);

                builder =
                        new NotificationCompat.Builder(this, "id").setAutoCancel(true)
                                .setContentTitle("Push Notification")
                                .setContentText(recipent+" "+message)
                                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent);

            }
            else
            {
                builder = new NotificationCompat.Builder(this)
                        .setAutoCancel(true)
                        .setContentTitle(message)
                        .setContentText(recipent+" "+message)
                        .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                        .setContentIntent(pendingIntent);
            }

            notificationManager.notify(0, builder.build());




        }

    public static boolean isBase64(String str) {
        if (TextUtils.isEmpty(str))
            return false;
        try {
            Base64.decode(str, 12);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    private void showNotificationInApp(String message, String recipent) {



        MessaginActivity.getMesseages( new GetMessages(username, password, recipent));



    }
    private void showNotificationInMenu(String message, String recipent) {


        Intent i = new Intent(this,MessaginActivity.class);
        i.putExtra("recipent",recipent);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);



    }


    @Override
    public void onNewToken(String token) {

        Log.d("D", "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.

    }



}