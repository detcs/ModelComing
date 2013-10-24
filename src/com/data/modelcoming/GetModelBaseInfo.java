package com.data.modelcoming;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetModelBaseInfo {
	HttpClient httpClient;
	String requestUrl=ConstantsData.modelBaseInfoUrl;
	int page=1;
	int count=18;//一页的车模图片数
	public GetModelBaseInfo() {
		super();
		// TODO Auto-generated constructor stub
		httpClient=new DefaultHttpClient();
		//System.out.println("before thrade");
		
	}
	public List<ModelBaseInfo> requestModelBaseInfo()
	{
		List<ModelBaseInfo> infos=null;
		String result=getMethod();
		System.out.println("result "+result);
		try {
			if(result!=null)
			infos=parseJson(result);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(infos==null)
			System.out.println("parse null");
		//System.out.println("in requestModelBaseInfo");
		//for(ModelBaseInfo info:infos)
		//	info.show();
		
		return infos;
		//new Thread(new RequestThread()).start();
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getMethod()
	{
	
		HttpGet getMethod=new HttpGet(requestUrl+"?"+"app_key=1318586047&page="+page+"&count="+count);
		try {
			HttpResponse response=httpClient.execute(getMethod);
			//System.out.println("before ");
			String result=EntityUtils.toString(response.getEntity(),"utf-8");
			//System.out.println("result "+result);
			return result;
	
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public void postMethod()
	{
		
	}
	class RequestThread implements Runnable
	{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String result=getMethod();
			try {
				parseJson(result);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public List<ModelBaseInfo> parseJson(String jsonresult) throws JSONException
	{
		JSONObject jsonobj=new JSONObject(jsonresult);
		//System.out.println("before parse result");
		JSONObject result=jsonobj.getJSONObject("result");
		JSONArray infos=result.getJSONArray("data");
		List<ModelBaseInfo> modelBaseInfos=new ArrayList<ModelBaseInfo>();
		ModelBaseInfo temp;
		for(int i=0;i<infos.length();i++)
		{
			JSONObject info=(JSONObject) infos.opt(i);
			temp=new ModelBaseInfo();
			temp.setId(Integer.parseInt(info.getString("modelid")));
			temp.setName(info.getString("name"));
			temp.setWeibo(info.getString("weiboid"));
			//有大小两种图片
			JSONObject imgs=info.getJSONObject("focus_info");
			//JSONObject img=imgs.getJSONObject("img");
			temp.setAlbum_pic(imgs.getString("img"));
			modelBaseInfos.add(temp);
		}
		
		return modelBaseInfos;
	}

}
