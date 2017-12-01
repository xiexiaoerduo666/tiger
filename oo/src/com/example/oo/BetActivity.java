package com.example.oo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class BetActivity extends Activity {
	public static final int[] IMG_IDS = { R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4,
			R.drawable.img5, R.drawable.img6, R.drawable.img7, R.drawable.img8 };
	public static final String[] NAMES = { "胖次", "大胖次", "肉食大胖次", "草食大胖次", "萝莉", "大萝莉", "超级大萝莉", "喵喵喵喵喵喵喵" };
	public static final double[] RATES = { 1.0, 2.0, 3.0, 4.0, 1.0, 2.0, 3.0, 4.0 };
	private ListView listview;
	// 一行显示多个控件的adapter适配器
	private SimpleAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bet);
		// 初始化adapter
		// 参数1 context 上下文 Activity类名.this
		// 2,data 数据 List<Map<>>类型
		// 3,resource 布局 描述LISTVIE中每一行各个控件的布局
		// 4,from 字符串数组 表示来源
		// 5,to 数据显示到哪个控件 ID
		adapter = new SimpleAdapter(BetActivity.this, getData(), R.layout.item, new String[] { "img", "name", "rate" },
				new int[] { R.id.img, R.id.name, R.id.rate });
		// 找出listview控件
		listview = (ListView) findViewById(R.id.listview);
		// 让listview显示对应元件
		listview.setAdapter(adapter);// 适配器
		// 给listview添加点击事件
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long vrg) {
				pos = position;
			}
		});
	}

	private int pos = -1;

	public void btnConfirm(View view) {
		if (pos < 0) {
			Toast.makeText(BetActivity.this, "请下注", Toast.LENGTH_LONG);
			return;
		}
		// 确定选择的金额
		RadioGroup group = (RadioGroup) findViewById(R.id.group);
		int checkedID = group.getCheckedRadioButtonId();//获得选中的
		RadioButton button = (RadioButton) findViewById(checkedID);
		String selectedCoin = button.getText().toString();//字符串
		int money = Integer.parseInt(selectedCoin);//字符串转整数
		//把money和pos传递回mainactivitiy
		Intent data=new Intent();
		data.putExtra("money", money);//选择金额
		data.putExtra("pos", pos);
		setResult(RESULT_OK,data);
		 //结束
		finish();
	}

	private List<Map<String, Object>> getData() {
		
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		for (int i = 1; i < IMG_IDS.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("img", IMG_IDS[i]);
			map.put("name", NAMES[i]);
			map.put("rate", RATES[i]);
			data.add(map);
		}
		return data;
	}

}
