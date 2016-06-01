package comtext.handlerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * handler 是什么？
 * --安卓中 ui更新机制 和 消息处理机制
 * 为什么要用handler
 * --android在设计设计时提供的一整套消息处理机制
 */

/**
 * handler使用
 */
public class MainActivity extends Activity {
    private TextView mTextView;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            mTextView.setText(msg.arg1 + "");
            return true;
        }
    }
    ){
        @Override
        public void handleMessage(Message msg) {
            mTextView.setText(msg.arg1+msg.arg2+"");

        }
    };
    private myRunnable myRunnable = new myRunnable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.text);
        mImageView = (ImageView) findViewById(R.id.imageview);
        new Thread(){
            @Override
            public void run() {
                Message message =  Message.obtain();
                message.arg1 =111;
                message.arg2 =222;
                mHandler.sendMessage(message);
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    //postRunnable
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                           // mTextView.setText("lalalalala");


                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        //postdelayed
        mHandler.postDelayed(myRunnable, 2000);
    }

    private ImageView mImageView;
    private int imagers[] = {R.drawable.image1, R.drawable.image2, R.drawable.image3};
    private int index = 0;

    class myRunnable implements Runnable {
        @Override
        public void run() {
            index++;
            index = index % 3;
            mImageView.setImageResource(imagers[index]);
            mHandler.postDelayed(myRunnable, 2000);
        }
    }

}
