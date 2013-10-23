package com.data.modelcoming;

import java.util.List;

public class ModelPersonalInfo {
	int id;
	String name;
	String weibo;
	String height;
	String weight;
	String bwh;//三围 bust waist hips
	String job;
	String city;
	String birthday;
	String info;
	String carExperience;//参加车展经历
	List<String> picsUrl;
	List<String> bigpicsUrl;
	public List<String> getBigpicsUrl() {
		return bigpicsUrl;
	}
	public void setBigpicsUrl(List<String> bigpicsUrl) {
		this.bigpicsUrl = bigpicsUrl;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWeibo() {
		return weibo;
	}
	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getBwh() {
		return bwh;
	}
	public void setBwh(String bwh) {
		this.bwh = bwh;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getCarExperience() {
		return carExperience;
	}
	public void setCarExperience(String carExperience) {
		this.carExperience = carExperience;
	}
	public List<String> getPicsUrl() {
		return picsUrl;
	}
	public void setPicsUrl(List<String> picsUrl) {
		this.picsUrl = picsUrl;
	}
	public void show()
	{
		System.out.println(id+","+this.name+","+this.birthday+","+this.height+","+this.weight+","+this.bwh+","+this.job+","+this.info+","+this.carExperience);
		for(String url:this.picsUrl)
			System.out.println(url);
	}
}
