package vn.edu.tnut.duycop.smsbackup;

import android.util.Log;
import java.util.HashMap;
import java.util.Map;

public class SendCloud {
    String num, msg, time;
    public SendCloud(String num, String  msg,String  time){
        this.num=num;
        this.msg=msg;
        this.time=time;
    }
    public boolean sendCloud(){
        try{
            //code send here
            Map<String, String> params = new HashMap<String, String>();
            params.put("num", num);
            params.put("msg", msg);
            params.put("time", time);
            String response = HttpRequest.post("https://tms.tnut.edu.vn/a.txt").form(params).body();

            Log.d("sendCloud OK","đã gửi thành công num="+num+" msg="+msg+" time="+time + " response="+response);
            return  true;
        }catch (Exception e){
            Log.d("sendCloud ERROR",e.getMessage());
            return false;
        }
    }

}
