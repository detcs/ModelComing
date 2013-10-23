package com.view.modelcoming;

import com.dodowaterfall.FallActivity;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

public class ModelGroupTab extends ActivityGroup {

	LinearLayout container;
	//用于管理本Group中的所有Activity       
    public static ActivityGroup group;  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
      //  setContentView(R.layout.modeltabgroup);
       // container=(LinearLayout)findViewById(R.id.modeltabgroup);
        group = this;  
    }  
  
    @Override  
    public void onBackPressed() {         
        //把后退事件交给子Activity处理  
        group.getLocalActivityManager().getCurrentActivity().onBackPressed();  
    }  
  
    @Override  
    protected void onStart() {  
        super.onStart();  
        //要跳转的Activity  
        Intent intent = new Intent(this, FallActivity.class);  
        // 把Activity转换成一个Window，然后转换成View  
        Window w = group.getLocalActivityManager().startActivity(  
                "FirstActivity", intent);  
        View view = w.getDecorView();  
        //container.removeAllViews();
        //container.addView(view);
        //设置要跳转的Activity显示为本ActivityGroup的内容  
        group.setContentView(view);  
    }  
}
