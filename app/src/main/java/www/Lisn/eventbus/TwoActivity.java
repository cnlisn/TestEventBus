package www.Lisn.eventbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.ThreadMode;

/**
 * 当前类注释:onEventMainThread
 * ProjectName：TestEventBus
 */
public class TwoActivity extends AppCompatActivity {
    private Button btn_two,btn_three;
    private boolean isFirstFlag=true;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        btn_two = (Button) findViewById(R.id.btn_two);
        btn_three = (Button) findViewById(R.id.btn_three);
        btn_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageEventFirst("消息传入1"));
                TwoActivity.this.finish();
            }
        });
        btn_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("MessageEventTwo", "TwoActivity当前线程:" + Thread.currentThread() + toString());
                EventBus.getDefault().post(new MessageEventTwo("消息传入2"));
                TwoActivity.this.finish();
            }
        });

        //接收从MainActivity发过来的粘性消息
        Button button = (Button) findViewById(R.id.js);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFirstFlag) {
                    isFirstFlag = false;
                    // 注册粘性事件
                    EventBus.getDefault().registerSticky(TwoActivity.this);
                }
            }
        });


    }
    //  接收粘性事件
    public void onEventMainThread(StickyEvent event){
        Toast.makeText(this,"接收到消息onEvent:"+event.getMsg(),Toast.LENGTH_SHORT).show();
        Log.e("MessageEventTwo","onEvent当前线程:"+Thread.currentThread()+toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 解注册
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(TwoActivity.this);
    }
}
