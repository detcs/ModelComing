package com.view.modelcoming;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabWidget;
import android.widget.TextView;

import com.api.TabHostActivity;
import com.api.example.app.TabItem;
import com.dodowaterfall.FallActivity;

public class MainTabActivity extends TabHostActivity{

	List<TabItem> mItems;
    private LayoutInflater mLayoutInflater;
    /**在初始化TabWidget前调用
     * 和TabWidget有关的必须在这里初始化*/
    @Override
    protected void prepare() {
        TabItem home = new TabItem(
                "车模",                                    // title
                R.drawable.chemo_icon_02,                    // icon
                R.drawable.foot_bt_bg01,            // background
                new Intent(this, FallActivity.class));    // intent
        
        TabItem info = new TabItem(
                "微博",
                R.drawable.weibo_icon_01,
                R.drawable.foot_bt_bg02,
                new Intent(this, WeiboActivity.class));
        
        TabItem msg = new TabItem(
                "排行",
                R.drawable.paihang_icon_01,
                R.drawable.foot_bt_bg02,
                new Intent(this, RankActivity.class));
        
        TabItem square = new TabItem(
                "设置",
                R.drawable.shezhi_icon_01,
                R.drawable.foot_bt_bg02,
                new Intent(this, SettingActivity.class));
        
        
        mItems = new ArrayList<TabItem>();
        mItems.add(home);
        mItems.add(info);
        mItems.add(msg);
        mItems.add(square);
       
        // 设置分割线
        TabWidget tabWidget = getTabWidget();
        tabWidget.setDividerDrawable(R.drawable.tab_divider);
        
        mLayoutInflater = getLayoutInflater();
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCurrentTab(0);
    }
    /**tab的title，icon，边距设定等等*/
	@Override
	protected void setTabItemTextView(TextView textView, int position) {
		// TODO Auto-generated method stub
		textView.setPadding(3, 3, 3, 3);
        textView.setText(( mItems.get(position)).getTitle());
        textView.setBackgroundResource(((TabItem) mItems.get(position)).getBg());
        textView.setCompoundDrawablesWithIntrinsicBounds(0, ((TabItem) mItems.get(position)).getIcon(), 0, 0);
        
	}
	@Override
	protected void setSelectedTabItemTextView(TextView textView, int position) {
		// TODO Auto-generated method stub
		//textView.setPadding(3, 3, 3, 3);
		System.out.println(" select "+position);
		//View v=()
	//	textView.setText(( mItems.get(position)).getTitle());
		
//		textView.setBackgroundResource(R.drawable.foot_bt_bg02);
//    	textView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.chemo_icon_01, 0, 0);
//    	
//    	textView.setBackgroundResource(R.drawable.foot_bt_bg02);
//    	textView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.weibo_icon_01, 0, 0);
//	
//    	textView.setBackgroundResource(R.drawable.foot_bt_bg02);
//    	textView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.paihang_icon_01, 0, 0);
//	
//    	textView.setBackgroundResource(R.drawable.foot_bt_bg02);
//    	textView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.shezhi_icon_01, 0, 0);
//		
		if(position==0)
		{
        	textView.setBackgroundResource(R.drawable.foot_bt_bg01);
        	textView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.chemo_icon_02, 0, 0);
		}
		else if(position==1)
		{
			System.out.println("position==1");
        	textView.setBackgroundResource(R.drawable.foot_bt_bg01);
        	textView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.weibo_icon_02, 0, 0);
		}
		else if(position==2)
		{
        	textView.setBackgroundResource(R.drawable.foot_bt_bg01);
        	textView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.paihang_icon_02, 0, 0);
		}
		else if(position==3)
		{
        	textView.setBackgroundResource(R.drawable.foot_bt_bg01);
        	textView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.shezhi_icon_02, 0, 0);
		}
        
	}
	@Override
	protected void setDeselectedTabItemTextView(TextView textView, int position) {
		// TODO Auto-generated method stub
		if(position==0)
		{
        	textView.setBackgroundResource(R.drawable.foot_bt_bg02);
        	textView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.chemo_icon_01, 0, 0);
		}
		else if(position==1)
		{
			//System.out.println("position==1");
        	textView.setBackgroundResource(R.drawable.foot_bt_bg02);
        	textView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.weibo_icon_01, 0, 0);
		}
		else if(position==2)
		{
        	textView.setBackgroundResource(R.drawable.foot_bt_bg02);
        	textView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.paihang_icon_01, 0, 0);
		}
		else if(position==3)
		{
        	textView.setBackgroundResource(R.drawable.foot_bt_bg02);
        	textView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.shezhi_icon_01, 0, 0);
		}
	}
	@Override
	protected String getTabItemId(int position) {
		// TODO Auto-generated method stub
		return (String) (mItems.get(position)).getTitle();    // 我们使用title来作为id，你也可以自定

	}
	/**点击tab时触发的事件*/
    @Override
    protected Intent getTabItemIntent(int position) {
    //	System.out.println("choose "+position);
    	
    	
    	
//    		mItems.get(0).setIcon(R.drawable.chemo_icon_01);
//    		mItems.get(0).setBg(R.drawable.foot_bt_bg02);
//    		mItems.get(1).setIcon(R.drawable.weibo_icon_01);
//    		mItems.get(1).setBg(R.drawable.foot_bt_bg02);
//    		mItems.get(2).setIcon(R.drawable.paihang_icon_01);
//    		mItems.get(2).setBg(R.drawable.foot_bt_bg02);
//    		mItems.get(3).setIcon(R.drawable.shezhi_icon_01);
//    		mItems.get(3).setBg(R.drawable.foot_bt_bg02);
//    		
//    		mItems.get(position).setBg(R.drawable.foot_bt_bg01);
//    		//选中车模
//        	if(position==0)
//        		mItems.get(position).setIcon(R.drawable.chemo_icon_02);	
//    	//选中weibo
//    	if(position==1)
//    		mItems.get(position).setIcon(R.drawable.weibo_icon_02);   	
//    	//选中排行
//    	if(position==2)
//    		mItems.get(position).setIcon(R.drawable.paihang_icon_02);    	  		
//    	//选中设置
//    	if(position==3)
//    		mItems.get(position).setIcon(R.drawable.shezhi_icon_02);
//    		
//    		
        return ( mItems.get(position)).getIntent();
    }
    @Override
    protected int getTabItemCount() {
        return mItems.size();
    }
    
    /**自定义头部文件*/
    @Override
    protected View getTop() {
        //return mLayoutInflater.inflate(R.layout.example_top, null);
    	return null;
    }

	

	


	
}
