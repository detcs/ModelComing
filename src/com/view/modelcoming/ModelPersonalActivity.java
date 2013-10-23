package com.view.modelcoming;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.data.modelcoming.ConstantsData;
import com.data.modelcoming.GetModelPersonInfo;
import com.data.modelcoming.ModelPersonalInfo;
import com.data.modelcoming.MyApplication;
import com.dodowaterfall.FallActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ModelPersonalActivity extends Activity{
	int modelid;
	int screenWidth;
	String bigImgUrl;
	String requestUrl=ConstantsData.modelPersonalInfoUrl+"?"+ConstantsData.appKey;
	ModelPersonalInfo mp;
	
	ImageView modelBigImg;
	TextView modelInfo;
	GridView gridview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modelpersoninfo);
		Intent intent=getIntent();
		modelid=intent.getIntExtra("modelid", 1);
		bigImgUrl=intent.getStringExtra("bigimgurl");
		
		modelBigImg=(ImageView)findViewById(R.id.modelimg);
		modelInfo=(TextView)findViewById(R.id.modelinfo);
		gridview=(GridView)findViewById(R.id.gridview);

		screenWidth=this.getWindowManager().getDefaultDisplay().getWidth();
		
		GetModelPersonalInfoTask task=new GetModelPersonalInfoTask();
		task.execute(requestUrl+"&modelid="+modelid);
		
	}
	class GridViewAdapter extends BaseAdapter
	{
	
		Context context;
		public GridViewAdapter(Context context) {
			super();
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mp.getPicsUrl().size();
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
			ImageView img;
			img=new ImageView(context);
			// TODO Auto-generated method stub
			img.setLayoutParams(new AbsListView.LayoutParams(screenWidth/3, screenWidth/3));
			MyApplication.imageLoader.displayImage(mp.getPicsUrl().get(arg0), img);
			img.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					 Intent intent = new Intent(ModelPersonalActivity.this, ModelGalleryActivity.class);  
			          intent.putStringArrayListExtra("galleryimgsurl", (ArrayList<String>)mp.getPicsUrl());
			          intent.putStringArrayListExtra("gallerybigimgsurl", (ArrayList<String>)mp.getBigpicsUrl());
			          startActivity(intent);
			          //intent.putExtra("bigimgurl", bigImgUrl);
			          // 把Activity转换成一个Window，然后转换成View  
//					  //ModelGroupTab.group.getLocalActivityManager()..removeAllActivities();
//					  String id=ModelGroupTab.group.getLocalActivityManager() .getCurrentId();
//
//			          Window w = ModelGroupTab.group.getLocalActivityManager()  
//			                  .startActivity("ModelGalleryActivity",intent);  
//			          View view = w.getDecorView();  
//			          
//			                    //设置要跳转的Activity显示为本ActivityGroup的内容  
//			          ModelGroupTab.group.setContentView(view);  
				}
			});

			return img;
							}
		
	}

	class GetModelPersonalInfoTask extends AsyncTask<String,Integer,ModelPersonalInfo> {//继承AsyncTask  
		  
        @Override  
        protected ModelPersonalInfo doInBackground(String... params) {//处理后台执行的任务，在后台线程执行  
        	
            publishProgress(0);//将会调用onProgressUpdate(Integer... progress)方法  
            HttpClient hc = new DefaultHttpClient();  
            publishProgress(30);  
            HttpGet hg = new HttpGet(params[0]);//获取csdn的logo  
      
            try {  
                HttpResponse hr = hc.execute(hg);  
                String result=EntityUtils.toString(hr.getEntity(),"utf-8");
                mp=parseJson(result);
            } catch (Exception e) {  
                  
                return null;  
            }  
            publishProgress(100);  
            //mImageView.setImageBitmap(result); 不能在后台线程操作ui  
            return mp;  
        }  
          
        protected void onProgressUpdate(Integer... progress) {//在调用publishProgress之后被调用，在ui线程执行  
            //mProgressBar.setProgress(progress[0]);//更新进度条的进度  
         }  
  
         protected void onPostExecute(ModelPersonalInfo result) {//后台任务执行完之后被调用，在ui线程执行  
             if(result != null) {  
                // Toast.makeText(AsyncTaskActivity.this, "成功获取图片", Toast.LENGTH_LONG).show();  
                 //mImageView.setImageBitmap(result);  
            	 result.show();
            	 modelInfo.setText(result.getName()+"\n"
            			 					+"微博 ："+result.getWeibo()+"\n"
            			 					+"生日 ："+result.getBirthday()+"\n"
            			 					+"身高 ："+result.getHeight()+"\n"
            			 					+"体重 ："+result.getWeight()+"\n"
            			 					+"三围 ："+result.getBwh()+"\n"
            			 					+"职业 ："+result.getJob()+"\n"
            			 					+"所在城市 ："+result.getCity());
            	 int width=(int) (screenWidth*0.7);
            	 //int height=width
            	 modelBigImg.setLayoutParams(new LinearLayout.LayoutParams(width,screenWidth));
            	 modelBigImg.setScaleType(ScaleType.FIT_XY);
     			modelBigImg.setPadding(5, 5, 5, 5);
            	MyApplication.imageLoader.displayImage(bigImgUrl, modelBigImg);
            	gridview.setAdapter(new GridViewAdapter(ModelPersonalActivity.this));
             }else {  
                 Toast.makeText(ModelPersonalActivity.this, "获取图片失败", Toast.LENGTH_LONG).show();  
             }  
         }  
           
         protected void onPreExecute () {//在 doInBackground(Params...)之前被调用，在ui线程执行  
            // mImageView.setImageBitmap(null);  
            // mProgressBar.setProgress(0);//进度条复位  
         }  
           
         protected void onCancelled () {//在ui线程执行  
             //mProgressBar.setProgress(0);//进度条复位  
         }  
          
    }  
	public ModelPersonalInfo parseJson(String jsonresult) throws JSONException
	{
		JSONObject jsonobj=new JSONObject(jsonresult);
		//System.out.println("before parse result");
		JSONObject result=jsonobj.getJSONObject("result");
		JSONObject data=result.getJSONObject("data");
		//List<ModelPersonalInfo> modelPersonInfos=new ArrayList<ModelPersonalInfo>();
		ModelPersonalInfo modelPersonalInfo = null;	
			modelPersonalInfo=new ModelPersonalInfo();
			modelPersonalInfo.setId(Integer.parseInt(data.getString("modelid")));
			modelPersonalInfo.setName(data.getString("name"));
			modelPersonalInfo.setWeibo(data.getString("weiboid"));
			modelPersonalInfo.setBirthday(data.getString("birthyear")+";"+data.getString("birthmonth")+";"+data.getString("birthdate"));
			modelPersonalInfo.setHeight(data.getString("height"));
			modelPersonalInfo.setCity(data.getString("city"));
			modelPersonalInfo.setCarExperience(data.getString("car"));
			modelPersonalInfo.setInfo(data.getString("info"));
			modelPersonalInfo.setJob(data.getString("jobs"));
			modelPersonalInfo.setWeight(data.getString("weight"));
			modelPersonalInfo.setBwh(data.getString("bust")+";"+data.getString("waist")+";"+data.getString("hips"));
			
			
			//图片url
			JSONArray imgs=data.getJSONArray("recommend_photo");
			List<String> photosUrl=new ArrayList<String>();
			List<String> bigphotosUrl=new ArrayList<String>();
			for(int i=0;i<imgs.length();i++)
			{
				JSONObject photo=(JSONObject) imgs.opt(i);
				photosUrl.add(photo.getString("img_small"));
				bigphotosUrl.add(photo.getString("img"));
			}
			modelPersonalInfo.setPicsUrl(photosUrl);
			modelPersonalInfo.setBigpicsUrl(bigphotosUrl);
			//JSONObject img=imgs.getJSONObject("img");
			//temp.setAlbum_pic(imgs.getString("img"));
			
		
		
		return modelPersonalInfo;
	}
	
//	@Override
//	public void onBackPressed() {
//		// TODO Auto-generated method stub
//		//super.onBackPressed();
//		System.out.println("modelperson activity back");
//		  Intent intent = new Intent(ModelPersonalActivity.this, FallActivity.class);  
//          // 把Activity转换成一个Window，然后转换成View  
//		  //ModelGroupTab.group.getLocalActivityManager()..removeAllActivities();
//		  String id=ModelGroupTab.group.getLocalActivityManager() .getCurrentId();
//          Window w = ModelGroupTab.group.getLocalActivityManager()  
//                  .startActivity("FalllActivity",intent);  
//          View view = w.getDecorView();  
//          
//                    //设置要跳转的Activity显示为本ActivityGroup的内容  
//          ModelGroupTab.group.setContentView(view);  
//          System.out.println("activity "+id+" finsih");
//          ModelGroupTab.group.getLocalActivityManager().destroyActivity(id, true);
//
//          //ModelPersonalActivity.this.finish();
//		//finish();
//	}
      
}
