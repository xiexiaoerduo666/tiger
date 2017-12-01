package com.example.oo;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	//保存八张图片
    private ImageView[] images = new ImageView[8];
    private TextView tv_coin;
    //金币的价值
    public int coin =10000;
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		images[0]=(ImageView) findViewById(R.id.img1);
		images[1]=(ImageView) findViewById(R.id.img2);
		images[2]=(ImageView) findViewById(R.id.img3);
		images[3]=(ImageView) findViewById(R.id.img4);
		images[4]=(ImageView) findViewById(R.id.img5);
		images[5]=(ImageView) findViewById(R.id.img6);
		images[6]=(ImageView) findViewById(R.id.img7);
		images[7]=(ImageView) findViewById(R.id.img8);
		tv_coin = (TextView)  findViewById(R.id.tv_coin);
	}
	
	//点击下注按钮
	public static final int REQUEST_CODE = 9527;
	public void btnBet(View view){
		//跳转到BetActivity
		 Intent intent = new Intent();
	     intent.setClass(this, BetActivity.class);
	     //通过startActivity启动betactivity并期望能获取返回值
	     startActivityForResult(intent,REQUEST_CODE);
	}
	
	//当BETACTIVITY结束的时候MAINACTIVITY将触发该方法
	//参数1 requestCode 请求码 对应 startActivityForResult的第二个参数
	//参数2 resultCode  结果码 对应setResult的第一个参数
	//参数3 data 数据  对应setResult的第二个参数
	//PS: 重写方法通过输入onActiv...部分内容后按ALT+/可以自动补全
	
	
	private int currentPos = 0;//当前的位置
	private int selectedPos = -1;//用户选择的位置
	private int selectedMoney = 0; //用户下注的金额
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	 
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
			//获取data数量
			selectedMoney = data.getIntExtra("money", 0);
			selectedPos = data.getIntExtra("pos",0);
			Toast.makeText(MainActivity.this,  
					"money: "+selectedMoney+" , pos:" + selectedPos,
					 Toast.LENGTH_LONG).show();
			
		}
	}
	
 
	//点击开始按钮
	public void btnStart(View view){
		//在界面显示弹窗
		//参数1 上下文 在哪个Activity中显示
		//参数2 字符串显示的内容
		//参数3 Toast显示持续的时间
		if(selectedPos<0){
			Toast.makeText(MainActivity.this, "请下注", Toast.LENGTH_LONG);
			return;
		}
		//发送第一次的消息
		handler.sendEmptyMessage(3344);
		//随机
		Random random = new Random();
		int delayTime =random.nextInt(3000)+1000;
		//1s后停下来
		//延时一段时间做某事  参数1要做神马 参数2延时多久
		tv_coin.postDelayed(new Runnable(){
			@Override
			public void run(){
				//移除消息
				handler.removeMessages(3344);
				//处理结果
				handleResult();
			}
		}, delayTime);
		//moveNext();
		//handleResult();
	}
    //handle的对象用于处理消息
	MyHandler handler = new MyHandler();
	class MyHandler extends Handler{
		//收到消息进行处理
		@Override
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			if(msg.what==3344){
				//每收到一次消息后移一格
				moveNext();
				//发送下一次消息（延迟0.1s=100ms）
				handler.sendEmptyMessageDelayed(3344, 100);
			}
		}
	}
	//后退一格
	private void moveNext() {
		currentPos=(currentPos+1)%8;//0--7
		for(int i=0;i<images.length;i++){
			if(i==currentPos){
				//当前图片添加背景
				images[i].setBackgroundResource(R.drawable.img_bg);
			}
			else{
				//其他图片添加背景
				images[i].setBackgroundColor(Color.TRANSPARENT);
			}
			
		}
			
		
		
	}
	
	//移动后结果的处理
	private void handleResult() {
		if(currentPos==selectedPos){
			coin=(int)(coin+selectedMoney * BetActivity.RATES[selectedPos]);
			Toast.makeText(MainActivity.this, "恭喜中奖", Toast.LENGTH_LONG).show();
		} else{
			coin=coin-selectedMoney;
			Toast.makeText(MainActivity.this, "呵呵没中", Toast.LENGTH_LONG).show();
		}
		tv_coin.setText(coin+"");//整数转字符串
		selectedPos=-1;//清空当前值，重新下注
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
