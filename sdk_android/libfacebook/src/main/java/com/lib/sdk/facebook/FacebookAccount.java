package com.lib.sdk.facebook;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.lib.x.AccountSDK;
import com.facebook.*;

import org.cocos2dx.lib.Cocos2dxActivity;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.Arrays;

/**
 * Created by Nervecell on 2016/6/15.
 */
public class FacebookAccount extends AccountSDK implements FacebookCallback<LoginResult>{

    @Override
    public void prepare()
    {
        if(FacebookSdk.isInitialized())
            return;


        FacebookSdk.sdkInitialize(Cocos2dxActivity.getContext());
        mCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(mCallbackManager, this);

        mAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };

        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

            }
        };
    }

    @Override
    /**
     *  FacebookCallback<LoginResult>
     */
    public void onSuccess(LoginResult loginResult)
    {
        //AccessToken accessToken = AccessToken.getCurrentAccessToken();
        Profile profile = Profile.getCurrentProfile();
        if(null != profile)
        {
            this.setAccountId(profile.getId());
            this.setFirstName(profile.getFirstName());
            this.setLastName(profile.getLastName());
            this.setProfileImage(profile.getProfilePictureUri(120, 120).toString());
            this.notifLoginFinished(null);
        }
        else
        {
            this.notifLoginFinished("Profile null");
        }
    }

    @Override
    /**
     *  FacebookCallback<LoginResult>
     */
    public void onCancel() {
        this.notifLoginCancel();
    }

    @Override
    /**
     *  FacebookCallback<LoginResult>
     */
    public void onError(FacebookException error) {
        this.notifLoginFinished(error.getLocalizedMessage());
    }

    @Override
    public void login()
    {
        LoginManager loginManager = LoginManager.getInstance();
        loginManager.logInWithReadPermissions((Activity) Cocos2dxActivity.getContext(), Arrays.asList("public_profile", "user_friends"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(null == mCallbackManager)
            return;

        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy()
    {
        if(null != mAccessTokenTracker)
            mAccessTokenTracker.stopTracking();

        if(null != mProfileTracker)
            mProfileTracker.stopTracking();
    }

    protected CallbackManager mCallbackManager;

    protected AccessTokenTracker mAccessTokenTracker;

    protected ProfileTracker mProfileTracker;
}
