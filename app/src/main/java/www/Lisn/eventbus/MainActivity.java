package www.Lisn.eventbus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import de.greenrobot.event.EventBus;



/**
 * 接收顺序
 E/MessageEventTwo: onEvent当前线程:Thread[main,5,main]
 E/MessageEventTwo: onEventAsync当前线程:Thread[pool-1-thread-3,5,main]
 E/MessageEventTwo: onEventBackground当前线程:Thread[pool-1-thread-4,5,main]
 E/MessageEventTwo: onEventMainThread当前线程:Thread[main,5,main]
 */
public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int priority = 1; //设置优先级别，默认级别0 是最高级别
        EventBus.getDefault().register(this,priority);
        setContentView(R.layout.activity_main);
        button=(Button)findViewById(R.id.btn_one);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(MainActivity.this,TwoActivity.class);
                MainActivity.this.startActivity(mIntent);
            }
        });

        button2=(Button)findViewById(R.id.btn);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new StickyEvent("从MainActivity发出的粘性消息"));
                Intent mIntent=new Intent(MainActivity.this,TwoActivity.class);
                MainActivity.this.startActivity(mIntent);
                finish();
            }
        });
    }
    public void onEventMainThread(MessageEventFirst event){
        Toast.makeText(this,"接收到消息MessageEventFirst:"+event.getMsg(),Toast.LENGTH_SHORT).show();
    }
    public void onEventMainThread(MessageEventTwo event){
        Toast.makeText(this,"接收到消息MessageEventTwo:"+event.getMsg(),Toast.LENGTH_SHORT).show();
        Log.e("MessageEventTwo","onEventMainThread当前线程:"+Thread.currentThread()+toString());

        //高优先级的订阅者可以在事件处理函数中取消事件的传递
//        EventBus.getDefault().cancelEventDelivery(event) ;
    }
    public void onEvent(MessageEventTwo eventTwo){
        Toast.makeText(this,"接收到消息onEvent:"+eventTwo.getMsg(),Toast.LENGTH_SHORT).show();
        Log.e("MessageEventTwo","onEvent当前线程:"+Thread.currentThread()+toString());
    }

    public void onEventBackgroundThread(MessageEventTwo eventTwo){
        Log.e("MessageEventTwo","onEventBackground当前线程:"+Thread.currentThread()+toString());
    }
    public void onEventAsync(MessageEventTwo eventTwo){
        Log.e("MessageEventTwo","onEventAsync当前线程:"+Thread.currentThread()+toString());
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.e("MessageEventTwo","onstop..");
        //EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("MainActivity","onDestroy..");
        EventBus.getDefault().unregister(this);
    }
}
