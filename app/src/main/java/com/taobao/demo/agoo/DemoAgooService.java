package com.taobao.demo.agoo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.accs.utl.ALog;
import com.taobao.agoo.TaobaoBaseIntentService;
import com.taobao.agoo.TaobaoRegister;
import com.example.luyujie.innovationcourse.R;
import org.android.agoo.common.AgooConstants;

/**
 * 默认agoo消息响应类
 * <p>
 * Created by wuer on 2018/1/29.
 */

public class DemoAgooService extends TaobaoBaseIntentService {
    private static final String TAG = "DEMO.DemoAgooService";

    private static final int REMIND_NONE = 0;
    private static final int REMIND_VIBRATION = 1;
    private static final int REMIND_SOUND = 2;
    private static final int REMIND_BOTH = 3;

    private static final int OPEN_APP = 1;
    private static final int OPEN_ACTIVITY = 2;
    private static final int OPEN_URL = 3;
    private static final int OPEN_NOP = 4;

    private static final String DEFAULT_ACTIVITY = "com.taobao.demo.agoo.NotifyActivity";

    private static final String OPEN = "open";
    private static final String URL = "url";
    private static final String ACTIVITY = "activity";


    @Override
    protected void onError(Context context, String errorId) {
        ALog.i(TAG, "onError", "errorId", errorId);
    }

    @Override
    protected void onRegistered(Context context, String registrationId) {
        ALog.i(TAG, "onRegistered", "registrationId", registrationId);
    }

    @Override
    protected void onUnregistered(Context context, String registrationId) {
        ALog.i(TAG, "onUnregistered", "registrationId", registrationId);
    }

    @Override
    protected void onMessage(Context context, Intent intent) {
        String messageId = intent.getStringExtra(AgooConstants.MESSAGE_ID);
        String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        String source = intent.getStringExtra(AgooConstants.MESSAGE_SOURCE);
        ALog.i(TAG, "onMessage", "messageId", messageId, "message", message);

        JSONObject jsonObject = JSON.parseObject(message);
        String title = "error message";
        String content = "content error";
        int remind = REMIND_NONE;
        int open = OPEN_ACTIVITY;
        String activity = DEFAULT_ACTIVITY;
        String url = "null";
        String notification_channel = "emas_push_demo_channel";
        String icon = null;
        String sound = null;

        if (jsonObject != null) {
            title = jsonObject.getString("title");
            content = jsonObject.getString("content");
            remind = jsonObject.getInteger("remind");
            open = jsonObject.getInteger("open");
            activity = jsonObject.getString("activity");
            url = jsonObject.getString("url");
            notification_channel = jsonObject.getString("notification_channel");
            sound = jsonObject.getString("sound");
            icon = jsonObject.getString("icon");

        }


        //解析agoo消息, 通知栏显示, 仅供参考
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(notification_channel, "emasdemo:"+notification_channel, NotificationManager.IMPORTANCE_DEFAULT);
            switch (remind) {
                case REMIND_BOTH:
                    channel.enableVibration(true);
                    break;
                case REMIND_VIBRATION:
                    channel.enableVibration(true);
                    channel.setSound(null,Notification.AUDIO_ATTRIBUTES_DEFAULT);
                    break;
                case REMIND_SOUND:
                    break;
                default:
                    break;
            }
            if (!TextUtils.isEmpty(sound)) {
                channel.setSound(Uri.parse("android.resource://"
                        + getPackageName() + "/" + R.raw.push_hongbao), Notification.AUDIO_ATTRIBUTES_DEFAULT);
            }
            channel.setBypassDnd(true);
            try {
                manager.createNotificationChannel(channel);
            } catch (Throwable t) {
                Log.e(TAG,"createNotificationChannel " + t.toString());
                t.printStackTrace();
            }


            Notification.Builder builder = new Notification.Builder(this, notification_channel);
            int notifyID = (int)(System.currentTimeMillis() / 1000);

            Intent clickIntent = new Intent(context, NotificationClickReceiver.class);
            clickIntent.setAction(NotificationClickReceiver.CLICKED_ACTION);
            clickIntent.putExtra(AgooConstants.MESSAGE_BODY, message);
            clickIntent.putExtra(NotificationClickReceiver.MESSAGE_ID, messageId);
            clickIntent.putExtra(OPEN, open);
            clickIntent.putExtra(URL, url);
            clickIntent.putExtra(ACTIVITY, activity);
            clickIntent.putExtra(AgooConstants.MESSAGE_SOURCE, source);
            PendingIntent pendingIntentClick = PendingIntent.getBroadcast(this, 0, clickIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

            Intent dismissIntent = new Intent(context, NotificationClickReceiver.class);
            dismissIntent.setAction(NotificationClickReceiver.DISMISS_ACTION);
            dismissIntent.putExtra(AgooConstants.MESSAGE_BODY, message);
            dismissIntent.putExtra(NotificationClickReceiver.MESSAGE_ID, messageId);
            PendingIntent pendingIntentDismiss = PendingIntent.getBroadcast(this, 0, dismissIntent,
                PendingIntent.FLAG_ONE_SHOT);

            if (jsonObject == null) {
                builder.setSmallIcon(android.R.drawable.stat_notify_chat)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.icon_two_selected))
                        .setContentTitle(title)
                        .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE)
                    .setContentText(message)
                        .setAutoCancel(true)
                    .setContentIntent(pendingIntentClick)
                    .setDeleteIntent(pendingIntentDismiss);
                manager.notify(notifyID, builder.build());
            } else {
                builder.setSmallIcon(android.R.drawable.stat_notify_chat)
                        .setContentTitle(title)
                        .setAutoCancel(true)
                    .setContentText(content)
                    .setContentIntent(pendingIntentClick)
                    .setDeleteIntent(pendingIntentDismiss);

                if (!TextUtils.isEmpty(icon)) {
                    builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.icon_two_selected));
                }

                switch (remind) {
                    case REMIND_BOTH:
                        if (!TextUtils.isEmpty(sound)) {
                            builder.setSound(Uri.parse("android.resource://"
                                    + getPackageName() + "/" + R.raw.push_hongbao));
                            builder.setDefaults(Notification.DEFAULT_VIBRATE);
                        } else {
                            builder.setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE);
                        }
                        break;
                    case REMIND_VIBRATION:
                        builder.setDefaults(Notification.DEFAULT_VIBRATE);
                        break;
                    case REMIND_SOUND:
                        if (!TextUtils.isEmpty(sound)) {
                            builder.setSound(Uri.parse("android.resource://"
                                    + getPackageName() + "/" + R.raw.push_hongbao));
                        } else {
                            builder.setDefaults(Notification.DEFAULT_SOUND);
                        }
                        break;
                    default:
                        break;
                }


                manager.notify(notifyID, builder.build());
            }
        } else {
            NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            int notifyID = (int)(System.currentTimeMillis() / 1000);

            Intent clickIntent = new Intent(context, NotificationClickReceiver.class);
            clickIntent.setAction(NotificationClickReceiver.CLICKED_ACTION);
            clickIntent.putExtra(AgooConstants.MESSAGE_BODY, message);
            clickIntent.putExtra(NotificationClickReceiver.MESSAGE_ID, messageId);
            clickIntent.putExtra(OPEN, open);
            clickIntent.putExtra(URL, url);
            clickIntent.putExtra(ACTIVITY, activity);
            clickIntent.putExtra(AgooConstants.MESSAGE_SOURCE, source);
            PendingIntent pendingIntentClick = PendingIntent.getBroadcast(this, 0, clickIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

            Intent dismissIntent = new Intent(context, NotificationClickReceiver.class);
            dismissIntent.setAction(NotificationClickReceiver.DISMISS_ACTION);
            dismissIntent.putExtra(AgooConstants.MESSAGE_BODY, message);
            dismissIntent.putExtra(NotificationClickReceiver.MESSAGE_ID, messageId);
            PendingIntent pendingIntentDismiss = PendingIntent.getBroadcast(this, 0, dismissIntent,
                PendingIntent.FLAG_ONE_SHOT);




            if (jsonObject == null) {
                builder.setSmallIcon(android.R.drawable.stat_notify_chat)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.splash_logo))
                        .setContentTitle(title)
                        .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE)
                    .setContentText(message)
                        .setAutoCancel(true)
                    .setContentIntent(pendingIntentClick)
                    .setDeleteIntent(pendingIntentDismiss);
                manager.notify(notifyID, builder.build());
            } else {

                builder.setSmallIcon(android.R.drawable.stat_notify_chat)
                        .setContentTitle(title)
                    .setContentText(content)
                    .setContentIntent(pendingIntentClick)
                        .setAutoCancel(true)
                    .setDeleteIntent(pendingIntentDismiss);
                if (!TextUtils.isEmpty(icon)) {
                    builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.splash_logo));
                }
                switch (remind) {
                    case REMIND_BOTH:
                        if (!TextUtils.isEmpty(sound)) {
                            builder.setSound(Uri.parse("android.resource://"
                                    + getPackageName() + "/" + R.raw.push_hongbao));
                            builder.setDefaults(Notification.DEFAULT_VIBRATE);
                        } else {
                            builder.setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE);
                        }
                        break;
                    case REMIND_VIBRATION:
                        builder.setDefaults(Notification.DEFAULT_VIBRATE);
                        break;
                    case REMIND_SOUND:
                        if (!TextUtils.isEmpty(sound)) {
                            builder.setSound(Uri.parse("android.resource://"
                                    + getPackageName() + "/" + R.raw.push_hongbao));
                        }else {
                            builder.setDefaults(Notification.DEFAULT_SOUND);
                        }
                        break;
                     default:
                            break;
                }

                manager.notify(notifyID, builder.build());
            }
        }
    }

    public static class NotificationClickReceiver extends BroadcastReceiver {
        public static final String CLICKED_ACTION = "notification_clicked";
        public static final String DISMISS_ACTION = "notification_dismiss";
        public static final String MESSAGE_ID = "message_id";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String messageId = intent.getStringExtra(MESSAGE_ID);

            if (action.equals(CLICKED_ACTION)) {
                ALog.d(TAG, "clicked", "msgid", messageId);
                TaobaoRegister.clickMessage(context, messageId, null); //通知栏点击上报, 服务端报表使用
                int open = intent.getIntExtra(OPEN, OPEN_ACTIVITY);
                String url = intent.getStringExtra(URL);
                String activity = intent.getStringExtra(ACTIVITY);
                Intent clickIntent = null;

                if (open == OPEN_ACTIVITY) {
                    clickIntent = new Intent();
                    clickIntent.setClassName(context, activity);
                }

                if (open == OPEN_URL) {
                    clickIntent= new Intent();
                    clickIntent.setAction("android.intent.action.VIEW");
                    clickIntent.setData(Uri.parse(url));
                }

                if (open == OPEN_APP) {
                    clickIntent = new Intent();
                    clickIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
                }

                if (clickIntent != null) {
                    clickIntent.putExtra(AgooConstants.MESSAGE_BODY, intent.getStringExtra(AgooConstants.MESSAGE_BODY));
                    clickIntent.putExtra(AgooConstants.MESSAGE_SOURCE, intent.getStringExtra(AgooConstants.MESSAGE_SOURCE));
                    clickIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(clickIntent);
                } else  {
                    Toast.makeText(context, "open type error, open none", Toast.LENGTH_LONG).show();
                    ALog.e(TAG, "open type error, open none");
                }

            } else if (action.equals(DISMISS_ACTION)) {
                ALog.d(TAG, "dismiss", "msgid", messageId);
                TaobaoRegister.dismissMessage(context, messageId, null); //通知栏清除上报, 服务端报表使用
            }
        }
    }
}
