package com.lib.sdk.facebook;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.lib.x.ShareSDK;

import org.cocos2dx.lib.Cocos2dxActivity;

/**
 * Created by Nervecell on 2016/6/26.
 */
public class FacebookShare extends ShareSDK {

    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        mCanPresentShareDialog = ShareDialog.canShow(ShareLinkContent.class);
        mCanPresentShareDialogWithPhotos = ShareDialog.canShow(SharePhotoContent.class);
        mShareDialog = new ShareDialog((Activity)Cocos2dxActivity.getContext());

        CallbackManager callbackManager = CallbackManager.Factory.create();
        mShareCallback = new FacebookCallback<Sharer.Result>() {
            @Override
            public void onCancel() {
                Log.d("FacebookShare", "Canceled");
            }

            @Override
            public void onError(FacebookException error) {

            }

            @Override
            public void onSuccess(Sharer.Result result) {

            }
        };


        mShareDialog.registerCallback(callbackManager, mShareCallback);
    }

    @Override
    public void prepare()
    {

    }

    //分享文字
    @Override
    public void shareText(String text, int scene)
    {

    }

    //分享图片
    @Override
    public void shareImage(String shareImage,  String thumbImage, String tagName, String description, String title, String messageExt,  String action, int scene, int shareType)
    {
        Bitmap image = BitmapFactory.decodeFile(shareImage);
        SharePhoto photo = new SharePhoto.Builder().setBitmap(image).build();
        SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(photo).build();
        if(mCanPresentShareDialog)
            mShareDialog.show(content);
        else
            ShareApi.share(content, mShareCallback);
    }

    // 分享音乐
    @Override
    public void shareMusicURL(String musicURL, String dataURL, String description, String title, String thumbImage, int scene)
    {

    }

    // 分享视频
    @Override
    public void shareVideoURL(String videoURL, String description, String title, String thumbImage,int scene)
    {

    }

    // 分享网页
    public void shareLinkURL(String urlString, String tagName, String description, String title, String thumbImage, int scene)
    {
        ShareLinkContent linkContent = new ShareLinkContent.Builder().setContentTitle(title).setContentDescription(description).setContentUrl(Uri.parse(urlString)).build();
        if(mCanPresentShareDialog)
            mShareDialog.show(linkContent);
        else
            ShareApi.share(linkContent, mShareCallback);
    }

    protected boolean mCanPresentShareDialog;

    protected boolean mCanPresentShareDialogWithPhotos;

    protected ShareDialog mShareDialog;

    protected FacebookCallback<Sharer.Result> mShareCallback;
}
