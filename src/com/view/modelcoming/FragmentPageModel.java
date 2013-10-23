package com.view.modelcoming;

import org.apache.http.impl.client.RequestWrapper;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import android.app.Activity;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class FragmentPageModel extends Fragment{
	EditText falls;
	


	@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {  
		 	View rootView= inflater.inflate(R.layout.fragment_models, container,false);       
		 	falls=(EditText)rootView.findViewById(R.id.falls);
		 	falls.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					System.out.println("in edit");
				}
			});
//		 	ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
//	        .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
//	        .discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75, null)
//	     //   .taskExecutor(...)
//	       // .taskExecutorForCachedImages(...)
//	        .threadPoolSize(3) // default
//	        .threadPriority(Thread.NORM_PRIORITY - 1) // default
//	        .tasksProcessingOrder(QueueProcessingType.FIFO) // default
//	        .denyCacheImageMultipleSizesInMemory()
//	        .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
//	        .memoryCacheSize(2 * 1024 * 1024)
//	        .memoryCacheSizePercentage(13) // default
//	        //.discCache(new UnlimitedDiscCache(cacheDir)) // default
//	        .discCacheSize(50 * 1024 * 1024)
//	        .discCacheFileCount(100)
//	        .discCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
//	        .imageDownloader(new BaseImageDownloader(getActivity())) // default
//	       // .imageDecoder(new BaseImageDecoder(mAdded)) // default
//	        .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
//	        .writeDebugLogs()
//	        .build();
		 	Button b=(Button)rootView.findViewById(R.id.button);
		 	b.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					System.out.println("click");
					Fragment f=new FragmentPageRank();
					FragmentTransaction ft=getFragmentManager().beginTransaction();
					System.out.println(ft.toString());
					ft.replace(R.id.root,f );
					ft.addToBackStack(null);
					ft.commit();
				}
			});
	        return rootView;       
	    }     
}
