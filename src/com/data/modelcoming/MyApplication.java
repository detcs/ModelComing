package com.data.modelcoming;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.view.modelcoming.R;

import android.app.Application;

public class MyApplication extends Application {

	public static ImageLoader imageLoader=ImageLoader.getInstance();
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		DisplayImageOptions defaultOptions=new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.loading)//载入时显示的图片
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher)
			    .cacheInMemory(true)
			    .cacheOnDisc(true)
				.build();
		
		ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(getApplicationContext())
		.defaultDisplayImageOptions(defaultOptions)
		.threadPriority(Thread.NORM_PRIORITY-2)
		.denyCacheImageMultipleSizesInMemory()
		.discCacheFileNameGenerator(new Md5FileNameGenerator())
		.tasksProcessingOrder(QueueProcessingType.LIFO)
		.build();
		imageLoader.init(config);
		
	}

	
}
