package xfc.com.edu.jsdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private WebView mWebView;

    private Button mButton;

    private WebAPPInterface mInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = (WebView) findViewById(R.id.webView);
        //打开本包内assets目录下的index.html文件
        mWebView.loadUrl("file:///android_asset/js.html");
        //1、设置允许执行的JS脚本
        mWebView.getSettings().setJavaScriptEnabled(true);
        mInterface = new WebAPPInterface(MainActivity.this);
        //2、添加通信接口
        mWebView.addJavascriptInterface(mInterface, "app");
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(this);
        mInterface.showName("超超");
    }

    @Override
    public void onClick(View v) {
        //as调用js
        mInterface.showName("月月");
    }

    class WebAPPInterface {

        public Context mContext;

        public WebAPPInterface(MainActivity context) {
            this.mContext = context;
        }

        //js调用as
        @JavascriptInterface  //加上这一句话，不然的话在高版本的时候有可能报错
        public void sayHello(String name) {
            Toast.makeText(mContext, "name= " + name, Toast.LENGTH_SHORT).show();
        }

        //as调用js
        public void showName(final String name) {
            Log.e("数据",name);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mWebView.loadUrl("javascript:showName('" + name + "')");
                }
            });
        }
        }

}
