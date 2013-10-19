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
                "首页",                                    // title
                R.drawable.icon_home,                    // icon
                R.drawable.example_tab_item_bg,            // background
                new Intent(this, FallActivity.class));    // intent
        
        TabItem info = new TabItem(
                "资料",
                R.drawable.icon_selfinfo,
                R.drawable.example_tab_item_bg,
                new Intent(this, WeiboActivity.class));
        
        TabItem msg = new TabItem(
                "信息",
                R.drawable.icon_meassage,
                R.drawable.example_tab_item_bg,
                new Intent(this, RankActivity.class));
        
        TabItem square = new TabItem(
                "广场",
                R.drawable.icon_square,
                R.drawable.example_tab_item_bg,
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
	protected String getTabItemId(int position) {
		// TODO Auto-generated method stub
		return (String) (mItems.get(position)).getTitle();    // 我们使用title来作为id，你也可以自定

	}
	/**点击tab时触发的事件*/
    @Override
    protected Intent getTabItemIntent(int position) {
        return ( mItems.get(position)).getIntent();
    }
    @Override
    protected int getTabItemCount() {
        return mItems.size();
    }
    
    /**自定义头部文件*/
    @Override
    protected View getTop() {
        return mLayoutInflater.inflate(R.layout.example_top, null);
    }
	
}
