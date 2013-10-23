package com.dodowaterfall;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalBitmap.ImageLoadCompleteListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sqq.ScrollViewPull.widget.TryPullToRefreshScrollView;
import sqq.ScrollViewPull.widget.TryPullToRefreshScrollView.OnScrollListener;
import sqq.ScrollViewPull.widget.TryRefreshableView;
import sqq.ScrollViewPull.widget.TryRefreshableView.OnBottomListener;
import sqq.ScrollViewPull.widget.TryRefreshableView.RefreshListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.data.modelcoming.ConstantsData;
import com.data.modelcoming.GetModelBaseInfo;
import com.data.modelcoming.ModelBaseInfo;
import com.dodola.model.DuitangInfo;
import com.dodowaterfall.widget.FlowView;
import com.view.modelcoming.ModelGroupTab;
import com.view.modelcoming.ModelPersonalActivity;
import com.view.modelcoming.R;

public class FallActivity extends Activity implements ImageLoadCompleteListener {
	private static final int DOWNREFRESH = 1;
	private static final int UPREFRESH = 2;
	private int currentLoadCount = 0;
	private int totalDataCount = 0;
	protected static final String TAG = "MainActivity";
	private TryPullToRefreshScrollView waterfall_scroll;
	private LinearLayout waterfall_container;
	private ArrayList<LinearLayout> waterfall_items;
	private Display display;
	private int item_width;
	private FinalBitmap fb;
	private int column_count = Constants.COLUMN_COUNT;// 显示列数
	private int page_count = Constants.PICTURE_COUNT_PER_LOAD;// 每次加载30张图片
	private int current_page = 1;// 当前页数
	private int[] topIndex;
	private int[] bottomIndex;
	private int[] lineIndex;
	private int[] column_height;// 每列的高度
	private HashMap<Integer, Integer>[] pin_mark = null;
	private Context context;
	private int refreshType = UPREFRESH;
	int scroll_height;
	private TryRefreshableView rv;
	private List<LinkedList<View>> all_screen_view; // 封装每屏View集合的集合
	private View firstView;
	private static List<ModelBaseInfo> modelBaseInfos=null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		all_screen_view = new ArrayList<LinkedList<View>>();

		display = this.getWindowManager().getDefaultDisplay();
		// 根据屏幕大小计算每列大小
		item_width = display.getWidth() / column_count + 2;

		column_height = new int[column_count];
		context = this;
		pin_mark = new HashMap[column_count];

		fb = new FinalBitmap(this).init();// 必须调用init初始化FinalBitmap模块
		fb.setCompleteListener(this);

		this.lineIndex = new int[column_count];
		this.bottomIndex = new int[column_count];
		this.topIndex = new int[column_count];

		for (int i = 0; i < column_count; i++) {
			lineIndex[i] = -1;
			bottomIndex[i] = -1;
			pin_mark[i] = new HashMap();
		}

		InitLayout();
//		new Thread(new RequestModelBaseInfoThread()).start();
//		System.out.println("after reqeusett");
//		if(modelBaseInfos!=null)
//		for(ModelBaseInfo base:modelBaseInfos)
//			base.show();
	}
	private void InitLayout() {
		waterfall_scroll = (TryPullToRefreshScrollView) findViewById(R.id.waterfall_scroll);
		rv = (TryRefreshableView) findViewById(R.id.trymyRV);
		rv.sv = waterfall_scroll;
		// 隐藏mfooterView
		rv.setRefreshListener(new RefreshListener() {

			@Override
			public void onDownRefresh() {
				if (rv.mRefreshState == TryRefreshableView.READYTOREFRESH) {
					// 记录第一个view的位置
					firstView = waterfall_items.get(0).getChildAt(0);
					refreshType = DOWNREFRESH;
					System.out.println("ondownrefresh");
					AddItemToContainer(++current_page, page_count);
				}
			}
		});
		rv.setOnBottomListener(new OnBottomListener() {

			@Override
			public void onBottom() {
				if (rv.mRefreshState != TryRefreshableView.REFRESHING) {
					refreshType = UPREFRESH;
					System.out.println("onbottomlistener");
					AddItemToContainer(++current_page, page_count);
				}
			}
		});

		waterfall_scroll.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onAutoScroll(int l, int t, int oldl, int oldt) {

				// Log.d("MainActivity",
				// String.format("%d  %d  %d  %d", l, t, oldl, oldt));

				// Log.d("MainActivity", "range:" + range);
				// Log.d("MainActivity", "range-t:" + (range - t));

				if (pin_mark.length <= 0) {
					return;
				}
				scroll_height = waterfall_scroll.getMeasuredHeight();
				//("MainActivity", "scroll_height:" + scroll_height);

				if (t > oldt) {// 向下滚动
					if (t > 3 * scroll_height) {// 超过两屏幕后

						for (int k = 0; k < column_count; k++) {

							LinearLayout localLinearLayout = waterfall_items
									.get(k);

							if (pin_mark[k].get(Math.min(bottomIndex[k] + 1,
									lineIndex[k])) <= t + 3 * scroll_height) {// 最底部的图片位置小于当前t+3*屏幕高度
								View childAt = localLinearLayout
										.getChildAt(Math.min(
												1 + bottomIndex[k],
												lineIndex[k]));
								if(childAt != null){
									FlowView picView = (FlowView) childAt
											.findViewById(R.id.news_pic);
									if (picView.bitmap == null
											&& !TextUtils.isEmpty(picView.get_url())) {
										fb.reload(picView.get_url(), picView);
									}
	
									bottomIndex[k] = Math.min(1 + bottomIndex[k],
											lineIndex[k]);
								}
							}
							// Log.d("MainActivity",
							// "headIndex:" + topIndex[k] + "  footIndex:"
							// + bottomIndex[k] + "  headHeight:"
							// + pin_mark[k].get(topIndex[k]));
							if (pin_mark[k].get(topIndex[k]) < t - 2
									* scroll_height) {// 未回收图片的最高位置<t-两倍屏幕高度

								int i1 = topIndex[k];
								topIndex[k]++;
								((FlowView) localLinearLayout.getChildAt(i1)
										.findViewById(R.id.news_pic)).recycle();
								//Log.d("MainActivity", "recycle,k:" + k
								//		+ " headindex:" + topIndex[k]);

							}
						}

					}
				} else {// 向上滚动
					if (t > 3 * scroll_height) {// 超过两屏幕后
						for (int k = 0; k < column_count; k++) {
							LinearLayout localLinearLayout = waterfall_items
									.get(k);
							if (pin_mark[k].get(bottomIndex[k]) > t + 3
									* scroll_height) {
								((FlowView) localLinearLayout.getChildAt(
										bottomIndex[k]).findViewById(
										R.id.news_pic)).recycle();
								//Log.d("MainActivity", "recycle,k:" + k
								//		+ " headindex:" + topIndex[k]);

								bottomIndex[k]--;
							}

							if (pin_mark[k].get(Math.max(topIndex[k] - 1, 0)) >= t
									- 2 * scroll_height) {
								FlowView picView = ((FlowView) localLinearLayout
										.getChildAt(
												Math.max(-1 + topIndex[k], 0))
										.findViewById(R.id.news_pic));

								if (picView.bitmap == null
										&& !TextUtils.isEmpty(picView.get_url())) {
									fb.reload(picView.get_url(), picView);
								}

								topIndex[k] = Math.max(topIndex[k] - 1, 0);
							}
						}
					}

				}
			}
		});

		waterfall_container = (LinearLayout) this
				.findViewById(R.id.waterfall_container);

		waterfall_items = new ArrayList<LinearLayout>();

		for (int i = 0; i < column_count; i++) {
			LinearLayout itemLayout = new LinearLayout(this);
			LinearLayout.LayoutParams itemParam = new LinearLayout.LayoutParams(
					item_width, LayoutParams.WRAP_CONTENT);

			itemLayout.setPadding(2, 0, 2, 2);
			itemLayout.setOrientation(LinearLayout.VERTICAL);

			itemLayout.setLayoutParams(itemParam);
			waterfall_items.add(itemLayout);
			waterfall_container.addView(itemLayout);
		}

		// 第一次加载
		AddItemToContainer(current_page, page_count);
	}

	ContentTask task = new ContentTask(this);

	private void AddItemToContainer(int pageindex, int pagecount) {
		if (task.getStatus() != Status.RUNNING) {
			if (currentLoadCount >= totalDataCount) {
				currentLoadCount = 0;
				System.out.println("pageindex "+pageindex);
				String url=ConstantsData.modelBaseInfoUrl+"?app_key=1318586047&page="+pageindex+"&count=18";
				//String url = "http://www.duitang.com/album/1733789/masn/p/"
				//		+ pageindex + "/24/";
				//("MainActivity", "current url:" + url);
				ContentTask task = new ContentTask(this);
				task.execute(url);
			}
		}
	}

	private int GetMinValue(int[] array) {
		int m = 0;
		int length = array.length;
		for (int i = 0; i < length; ++i) {

			if (array[i] < array[m]) {
				m = i;
			}
		}
		return m;
	}

	@Override
	public synchronized void onLoadComplete(Bitmap bitmap, ModelBaseInfo _info) {
		++currentLoadCount;
		if (currentLoadCount >= totalDataCount) {
			rv.finishRefresh();
		}
		
		View convertView = LayoutInflater.from(context).inflate(
				R.layout.infos_list, null);
		LayoutParams layoutParams = new LayoutParams(item_width,
				LayoutParams.WRAP_CONTENT);
		convertView.setLayoutParams(layoutParams);
		//TextView timeView = (TextView) convertView.findViewById(R.id.news_time);
		TextView titleView = (TextView) convertView
				.findViewById(R.id.news_title);
		FlowView picv = (FlowView) convertView.findViewById(R.id.news_pic);
		
		//bitmap=resizePic(bitmap);
		int layoutHeight = (bitmap.getHeight() * item_width)
				/ bitmap.getWidth();// 调整高度
		Random random=new Random();
		float s = random.nextInt(4) + 7;
		layoutHeight=(int) (layoutHeight*(s/10));
		//System.out.println("layoutHeight "+layoutHeight);
		//设定图片显示的长度
		LinearLayout.LayoutParams picParams = new LinearLayout.LayoutParams(
				item_width, layoutHeight);
		picv.set_url(_info.getAlbum_pic());
		picv.setLayoutParams(picParams);
		picv.setImageBitmap(bitmap);

	picv.setOnClickListener(new ImageClickListener(_info.getId(),_info.getAlbum_pic())); 
	//OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated //要跳转的Activity  
//                Intent intent = new Intent(FallActivity.this, ModelPersonalActivity.class);  
//                intent.putExtra("modelid", _info.getId());
//                // 把Activity转换成一个Window，然后转换成View  
//                Window w = ModelGroupTab.group.getLocalActivityManager()  
//                        .startActivity("ModelPersonalActivity",intent);  
//                View view = w.getDecorView();  
//                //设置要跳转的Activity显示为本ActivityGroup的内容  
//                ModelGroupTab.group.setContentView(view);     
//				
//			}
//		});
		
		titleView.setText(_info.getName() );
		//timeView.setText(SimpleDateFormat.getDateInstance().format(new Date()));
		int wspec = MeasureSpec
				.makeMeasureSpec(item_width, MeasureSpec.EXACTLY);
		convertView.measure(wspec, 0);

		//Log.d("MainActivity",
		//		"titleView.getMeasuredHeight():" + titleView.getMeasuredWidth());

		int h = convertView.getMeasuredHeight();
		int w = convertView.getMeasuredWidth();
		//("MainActivity", "w:" + w + ",h:" + h);

		// 此处计算列值
		int columnIndex = GetMinValue(column_height);

		picv.setColumnIndex(columnIndex);
		lineIndex[columnIndex]++;
		column_height[columnIndex] += h;
		HashMap<Integer, Integer> hashMap = pin_mark[columnIndex];

		if (refreshType == UPREFRESH) {
			hashMap.put(lineIndex[columnIndex], column_height[columnIndex]);// 第index个view所在的高度
			waterfall_items.get(columnIndex).addView(convertView);
		} else {
			for (int i = lineIndex[columnIndex] - 1; i >= 0; i--) {
				hashMap.put(i + 1, hashMap.get(i) + h);
			}

			hashMap.put(0, h);
			waterfall_items.get(columnIndex).addView(convertView, 0);

		}

		bottomIndex[columnIndex] = lineIndex[columnIndex];
	}

	private class ContentTask extends
			AsyncTask<String, Integer, List<ModelBaseInfo>> {

		private Context mContext;

		public ContentTask(Context context) {
			super();
			mContext = context;
		}

		@Override
		protected List<ModelBaseInfo> doInBackground(String... params) {
			try {
				System.out.println("in doinbackground");
				return parseNewsJSON(params[0]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		

		@Override
		protected void onPostExecute(List<ModelBaseInfo> result) {
			if(result == null || result.size() <= 0){//有可能因为网络或者数据源本身无数据，如果没有此处逻辑会导致下拉刷新bar不被隐藏滨且无法刷新新数据
				totalDataCount = 0;
			}
			totalDataCount = result.size();
			System.out.println("in onPostExecute");
			for (ModelBaseInfo info : result) {
				info.show();
				fb.display(info);
			}
		}
		/*
		@Override
		protected void onPostExecute1(List<DuitangInfo> result) {
			if(result == null || result.size() <= 0){//有可能因为网络或者数据源本身无数据，如果没有此处逻辑会导致下拉刷新bar不被隐藏滨且无法刷新新数据
				totalDataCount = 0;
			}
			totalDataCount = result.size();
			for (DuitangInfo info : result) {
				fb.display(info);
			}
		}
		*/

		@Override
		protected void onPreExecute() {
		}

		public List<ModelBaseInfo> parseNewsJSON(String url) throws IOException 
		{
			GetModelBaseInfo g=new GetModelBaseInfo();
			g.setPage(current_page);
			System.out.println("current_page "+current_page);
			List<ModelBaseInfo> modelBaseInfos=g.requestModelBaseInfo();
			//.println("in parseNewsJSON(String url) "+modelBaseInfos.size());
			return modelBaseInfos;
		}
		
		protected List<DuitangInfo> doInBackground1(String... params) {
			try {
				return parseNewsJSON1(params[0]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		public List<DuitangInfo> parseNewsJSON1(String url) throws IOException {
			List<DuitangInfo> duitangs = new ArrayList<DuitangInfo>();
			String json = "";
			if (Helper.checkConnection(mContext)) {
				try {
					json = Helper.getStringFromUrl(url);

				} catch (IOException e) {
					Log.e("IOException is : ", e.toString());
					e.printStackTrace();
					return duitangs;
				}
			}
			//Log.d("MainActiivty", "json:" + json);

			try {
				if (null != json) {
					JSONObject newsObject = new JSONObject(json);
					JSONObject jsonObject = newsObject.getJSONObject("data");
					JSONArray blogsJson = jsonObject.getJSONArray("blogs");

					for (int i = 0; i < blogsJson.length(); i++) {
						JSONObject newsInfoLeftObject = blogsJson
								.getJSONObject(i);
						DuitangInfo newsInfo1 = new DuitangInfo();
						newsInfo1
								.setAlbid(newsInfoLeftObject.isNull("albid") ? ""
										: newsInfoLeftObject.getString("albid"));
						newsInfo1
								.setIsrc(newsInfoLeftObject.isNull("isrc") ? ""
										: newsInfoLeftObject.getString("isrc"));
						newsInfo1.setMsg(newsInfoLeftObject.isNull("msg") ? ""
								: newsInfoLeftObject.getString("msg"));
						duitangs.add(newsInfo1);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return duitangs;
		}
	}
	class ImageClickListener implements OnClickListener
	{
		int id;
		String bigImgUrl;
		public ImageClickListener(int id,String imgUrl) {
			super();
			this.id = id;
			bigImgUrl=imgUrl;
		}

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			   Intent intent = new Intent();  
               intent.putExtra("modelid", id);
               intent.putExtra("bigimgurl", bigImgUrl);
               intent.setClass(FallActivity.this, ModelPersonalActivity.class);
               startActivity(intent);
               // 把Activity转换成一个Window，然后转换成View  
//               Window w = ModelGroupTab.group.getLocalActivityManager()  
//                       .startActivity("ModelPersonalActivity",intent);  
//               View view = w.getDecorView();
//               LinearLayout container=(LinearLayout) ModelGroupTab.group.getWindow().findViewById(R.id.modeltabgroup);
//             // container.removeAllViews();
//             // container.addView(view);
//               //设置要跳转的Activity显示为本ActivityGroup的内容  
//               ModelGroupTab.group.setContentView(view);  
		}
		
	}}
