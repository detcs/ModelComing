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

public class GetModelPersonInfo {

	HttpClient httpClient;
	String requestUrl=ConstantsData.modelPersonalInfoUrl;
	public GetModelPersonInfo() {
		super();
		httpClient=new DefaultHttpClient();
	}
	public String getMethod(int modelId)
	{
	
		HttpGet getMethod=new HttpGet(requestUrl+"?"+ConstantsData.appKey+"&modelid="+modelId);
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
			for(int i=0;i<imgs.length();i++)
			{
				JSONObject photo=(JSONObject) imgs.opt(i);
				photosUrl.add(photo.getString("img_small"));
			}
			modelPersonalInfo.setPicsUrl(photosUrl);
			//JSONObject img=imgs.getJSONObject("img");
			//temp.setAlbum_pic(imgs.getString("img"));
			
		
		
		return modelPersonalInfo;
	}
	
}
