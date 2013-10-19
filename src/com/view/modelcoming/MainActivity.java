package com.view.modelcoming;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	//
    private FragmentTabHost mTabHost;  
           
    //布局
    private LayoutInflater layoutInflater;  
               
    //各个fragment类 
    private Class fragmentArray[] = {FragmentPageModel.class,FragmentPageWeibo.class,FragmentPageRank.class,FragmentPageSetting.class};
    
    //各个tab图片
    private int mImageViewArray[] = {R.drawable.chemo_icon_01,R.drawable.weibo_icon_01,R.drawable.paihang_icon_01,R.drawable.shezhi_icon_01};  
           
    //Tab文字
   // private String mTextviewArray[] = {this.getResources().getString(R.string.firstpage),this.getResources().getString(R.string.weibo),this.getResources().getString(R.string.rank),this.getResources().getString(R.string.setting)}; 
    private String mTextviewArray[] = {"firstpage","weibo","rank","setting"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}    /** 
     * 初始化
     */
    private void initView(){  
        
        layoutInflater = LayoutInflater.from(this);  
                       
        //TabHost
        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);  
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent); 
        //mTabHost.geta
               
        //fragment个数
        int count = fragmentArray.length;     
                       
        for(int i = 0; i < count; i++){    
            //Ϊÿһ��Tab
            TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));  
            //��Tab
            mTabHost.addTab(tabSpec, fragmentArray[i], null);  
            //Tab背景图
            mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.foot_bt_bg02);  
        }  
    }  
                       
    /** 
     * 获得单项tabview 
     */
    private View getTabItemView(int index){  
        View view = layoutInflater.inflate(R.layout.tab_item_view, null);  
           
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);  
        imageView.setImageResource(mImageViewArray[index]);  
               
        TextView textView = (TextView) view.findViewById(R.id.textview);          
        textView.setText(mTextviewArray[index]);  
           
        return view;  
    }

}
