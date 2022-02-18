package vn.edu.tnut.duycop.smsbackup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.format.DateFormat;
import android.util.Log;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class BackupSMS extends BroadcastReceiver
{
    private String getDateTime(long timeInMillis) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timeInMillis);
        cal.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        String datetime = DateFormat.format("dd/MM/yyyy HH:mm:ss", cal).toString();
        return datetime;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Log.d("SMS BACKUP","onReceive sms");
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            SmsMessage[] msgs = null;

            if (bundle != null){
                Log.d("SMS BACKUP","bundle not null");
                //---retrieve the SMS message received---
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        SmsMessage sms=msgs[i];
                        String msgFrom = sms.getOriginatingAddress();
                        String msgBody = sms.getMessageBody();
                        String msgTime=getDateTime(sms.getTimestampMillis());
                        Log.d("SMS BACKUP","OK FROM: "+msgFrom+". BODY: "+msgBody +". TIME: "+msgTime);
                        SendCloud s=new SendCloud(msgFrom,msgBody,msgTime);
                        boolean ok=s.sendCloud();
                        if(ok)
                            Log.d("SMS BACKUP"," send cloud ok");
                        else
                            Log.d("SMS BACKUP"," ko send dc toi cloud");
                    }
                }catch(Exception e){
                        Log.d("SMS BACKUP",e.getMessage());
                }
            }
        }
    }
}
