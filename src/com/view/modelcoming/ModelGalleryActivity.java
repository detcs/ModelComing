package com.view.modelcoming;

import java.util.List;

import com.data.modelcoming.MyApplication;
import com.dodowaterfall.FallActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

public class ModelGalleryActivity extends Activity {

	Gallery gallery;
	ImageView img;
	List<String> imgsUrl;
	List<String> bigimgsUrl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.model_gallery);
		Intent intent=getIntent();
		imgsUrl=intent.getStringArrayListExtra("galleryimgsurl");
		bigimgsUrl=intent.getStringArrayListExtra("gallerybigimgsurl");
		gallery=(Gallery)findViewById(R.id.gallery);
		img=(ImageView)findViewById(R.id.bigimg);
		
		///int imgWidth=(int) (getWindowManager().getDefaultDisplay().getWidth()*0.7);
		
		//img.setLayoutParams(new RelativeLayout.LayoutParams(imgWidth,imgWidth));
		//img.setScaleType(ScaleType.FIT_XY);
		gallery.setAdapter(new GalleryAdapter(this));
		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				//img.setImageResource(resId)
				MyApplication.imageLoader.displayImage(bigimgsUrl.get(arg2), img);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	class GalleryAdapter extends BaseAdapter
	{
		Context context;
		

		public GalleryAdapter(Context context) {
			super();
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imgsUrl.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			ImageView image=new ImageView(context);
			image.setScaleType(ImageView.ScaleType.FIT_XY);
			image.setLayoutParams(new Gallery.LayoutParams(150,150));
			 TypedArray a = obtainStyledAttributes(R.styleable.Gallery);
			 MyApplication.imageLoader.displayImage(imgsUrl.get(arg0), image);
//			 image.setBackgroundResource( a.getResourceId(
//	                    R.styleable.Gallery_android_galleryItemBackground, 0));
	            a.recycle();
			return image;
		}
		
	}
//	@Override
//	public void onBackPressed() {
//		// TODO Auto-generated method stub
//		super.onBackPressed();
//		  Intent intent = new Intent(ModelGalleryActivity.this, ModelPersonalActivity.class);  
//	         // intent.putExtra("modelid", id);
//	          //intent.putExtra("bigimgurl", bigImgUrl);
//	          // 把Activity转换成一个Window，然后转换成View  
//			  //ModelGroupTab.group.getLocalActivityManager()..removeAllActivities();
//			  String id=ModelGroupTab.group.getLocalActivityManager() .getCurrentId();
//
//	          Window w = ModelGroupTab.group.getLocalActivityManager()  
//	                  .startActivity("ModelPersonalActivity",intent);  
//	          View view = w.getDecorView();  
//	          
//	                    //设置要跳转的Activity显示为本ActivityGroup的内容  
//	          ModelGroupTab.group.setContentView(view);  
//	         // ModelGroupTab.group.getLocalActivityManager().
//	          System.out.println("activity "+id+" finsih");
//	          ModelGroupTab.group.getLocalActivityManager().destroyActivity(id, true);
//	}
//
	
}
