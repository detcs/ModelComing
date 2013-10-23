package com.data.modelcoming;

public class ModelBaseInfo {

	int id;
	String name;
	String album_pic;//封面图片地址
	String weibo;
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
	public String getAlbum_pic() {
		return album_pic;
	}
	public void setAlbum_pic(String album_pic) {
		this.album_pic = album_pic;
	}
	public String getWeibo() {
		return weibo;
	}
	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}
	public void show()
	{
		System.out.println("id:"+id+" name:"+name+" weibo:"+id+" album_pic:"+album_pic);
	}
}
