package com.so.android;


import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class MyAdmin extends DeviceAdminReceiver {
	@Override 
    public void onReceive(Context context, Intent intent) { 
        super.onReceive(context, intent); 
        System.out.println("onreceiver"); 
    } 
   
    @Override 
    public void onEnabled(Context context, Intent intent) { 
        System.out.println("激活使用"); 
        super.onEnabled(context, intent); 
        Toast.makeText(context, "激活使用",
                Toast.LENGTH_SHORT).show();
        //Uri uri = Uri.parse("http://baidu.com");  
		//Intent it = new Intent(Intent.ACTION_VIEW, uri);  
		//context.startActivity(it); 
    } 
   
    @Override 
    public void onDisabled(Context context, Intent intent) { 
        System.out.println("取消激活"); 
        super.onDisabled(context, intent); 
        
    } 
}
