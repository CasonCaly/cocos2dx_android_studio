package com.lib.x;

import android.os.Bundle;
import android.os.Message;

/**
 * Created by Nervecell on 2016/6/26.
 */
public class ShareSDK extends ISDK{

    public boolean isDefault()
    {
        String className = this.getClass().getName();
        if(className.equals(SDKCenter.DefaultShareSDKName))
            return true;
        else
            return false;
    }

    //分享文字
    public void shareText(String text, int scene)
    {
    }

    //分享图片
    public void shareImage(String shareImage,  String thumbImage, String tagName, String description, String title, String messageExt,  String action, int scene, int shareType)
    {
    }

    // 分享音乐
    public void shareMusicURL(String musicURL, String dataURL, String description, String title, String thumbImage, int scene)
    {
    }

    // 分享视频
    public void shareVideoURL(String videoURL, String description, String title, String thumbImage,int scene)
    {
    }

    // 分享网页
    public void shareLinkURL(String urlString, String tagName, String description, String title, String thumbImage, int scene)
    {
    }

    /**
     * 在gl线程调用分享文字
     */
    protected void shareTextInGLThread(String text, int scene){
        Message msg = new Message();
        msg.what = ShareSDK.ShareTextMsg;

        Bundle data = new Bundle();
        data.putString("text", text == null ? "" : text);
        data.putInt("scene", scene);
        this.sendMessage(msg);
    }

    /**
     * 在gl线程调用分享图片
     */
    protected void shareImageInGLThread(String shareImage,  String thumbImage, String tagName, String description, String title, String messageExt,  String action, int scene, int shareType){
        Message msg = new Message();
        msg.what = ShareSDK.ShareImageMsg;

        Bundle data = new Bundle();
        data.putString("shareImage", shareImage == null ? "" : shareImage);
        data.putString("thumbImage", thumbImage == null ? "" : thumbImage);
        data.putString("tagName", tagName == null ? "" : tagName);
        data.putString("description", description == null ? "" : description);
        data.putString("title", title == null ? "" : title);
        data.putString("messageExt", messageExt == null ? "" : messageExt);
        data.putString("action", action == null ? "" : action);
        data.putInt("scene", scene);
        data.putInt("shareType", shareType);

        msg.setData(data);
        this.sendMessage(msg);
    }

    /**
     * 在gl线程调用分享音乐
     */
    protected void shareMusicURLInGLThread(String musicURL, String dataURL, String description, String title, String thumbImage, int scene){
        Message msg = new Message();
        msg.what = ShareSDK.ShareMusicURLtMsg;

        Bundle data = new Bundle();
        data.putString("musicURL", musicURL == null ? "" : musicURL);
        data.putString("dataURL", dataURL == null ? "" : dataURL);
        data.putString("description", description == null ? "" : description);
        data.putString("title", title == null ? "" : title);
        data.putString("thumbImage", thumbImage == null ? "" : thumbImage);
        data.putInt("scene", scene);

        msg.setData(data);
        this.sendMessage(msg);
    }

    /**
     * 在gl线程调用分享视频
     */
    protected void shareVideoURLInGLThread(String videoURL, String description, String title, String thumbImage,int scene){
        Message msg = new Message();
        msg.what = ShareSDK.ShareVideoURLMsg;

        Bundle data = new Bundle();
        data.putString("videoURL", videoURL == null ? "" : videoURL);
        data.putString("description", description == null ? "" : description);
        data.putString("title", title == null ? "" : title);
        data.putString("thumbImage", thumbImage == null ? "" : thumbImage);
        data.putInt("scene", scene);

        msg.setData(data);
        this.sendMessage(msg);
    }

    /**
     * 在gl线程调分享网页
     */
    protected void shareLinkURLInGLThread(String urlString, String tagName, String description, String title, String thumbImage, int scene){
        Message msg = new Message();
        msg.what = ShareSDK.ShareLinkURLMsg;

        Bundle data = new Bundle();
        data.putString("urlString", urlString == null ? "" : urlString);
        data.putString("tagName", tagName == null ? "" : tagName);
        data.putString("title", title == null ? "" : title);
        data.putString("thumbImage", thumbImage == null ? "" : thumbImage);
        data.putInt("scene", scene);
        msg.setData(data);
        this.sendMessage(msg);
    }

    /**
     * 用于处理从gl线程抛到主线程的消息
     */
    public void handleMessage(Message msg){
        super.handleMessage(msg);
        int what = msg.what;
        Bundle bundle = msg.getData();

        switch(what)
        {
            case ShareSDK.ShareTextMsg:{
                this.shareText(bundle.getString("text"), bundle.getInt("scene"));
                break;
            }
            case ShareSDK.ShareImageMsg:{
                this.shareImage(bundle.getString("shareImage"), bundle.getString("thumbImage"), bundle.getString("tagName"), bundle.getString("description"), bundle.getString("title"), bundle.getString("messageExt"), bundle.getString("action"), bundle.getInt("scene"), bundle.getInt("shareType"));
                break;
            }
            case ShareSDK.ShareMusicURLtMsg:{
                this.shareMusicURL(bundle.getString("musicURL"), bundle.getString("dataURL"), bundle.getString("description"), bundle.getString("title"), bundle.getString("thumbImage"), bundle.getInt("scene"));
                break;
            }
            case ShareSDK.ShareVideoURLMsg:{
               this.shareVideoURL(bundle.getString("videoURL"), bundle.getString("description"), bundle.getString("title"), bundle.getString("thumbImage"),  bundle.getInt("scene"));
                break;
            }
            case ShareSDK.ShareLinkURLMsg:{
                this.shareLinkURL(bundle.getString("urlString"), bundle.getString("tagName"), bundle.getString("description"), bundle.getString("title"),  bundle.getString("thumbImage"), bundle.getInt("scene"));
                break;
            }
        }
    }

    //以下都是和安卓的Handler send message有关的
    private final static int ShareTextMsg = 1;      //分享文字

    private final static int ShareImageMsg = 2;		//分享图片

    private final static int ShareMusicURLtMsg = 3; //分享音乐

    private final static int ShareVideoURLMsg = 4; //分享视频

    private final static int ShareLinkURLMsg = 5;	// 分享网页

}
