package kr.co.ezenac.ss.household;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.telephony.SmsMessage;
import android.util.Log;

import kr.co.ezenac.ss.household.db.DBManager;

/**
 * Created by Administrator on 2017-12-11.
 */

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();
            Object messages[] = (Object[]) bundle.get("pdus");

            SmsMessage smsMessage[] = new SmsMessage[messages.length];

            for (int i = 0 ; i < messages.length ; i++) {
                smsMessage[i] = SmsMessage.createFromPdu((byte[]) messages[i]);
            }

            String message = smsMessage[0].getMessageBody().toString();
            String[] tmp = message.split("\n");

            if (tmp.length > 1 && tmp[1].equals("현대카드 M2 승인")) {
                String[] tmp2 = tmp[3].split("원");
                String tmp3 = tmp2[0].replace(",","");

                Integer amount = Integer.parseInt(tmp3);

                Calendar c = Calendar.getInstance();
                int cYear = c.get(Calendar.YEAR);
                int cMonth = c.get(Calendar.MONTH)+1;
                int cDay = c.get(Calendar.DAY_OF_MONTH);

                DBManager dbManager = new DBManager(context, "SaveMyMoneyManager.db", null, 1);
                dbManager.insert(0, amount, cYear, cMonth, cDay);

                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(context)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("가계부")
                                .setContentText(amount + " " + "원");
                Intent resultIntent = new Intent(context, ListActivity.class);

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                stackBuilder.addParentStack(ListActivity.class);
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                mBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(0, mBuilder.build());
            }

            Log.d("kac" , "sms : " + message);
        }
    }
}