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
import  android.os.Bundle;
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
                Log.d("FacebookAccount", "onCurrentProfileChanged");
            }
        };
    }

    @Override
    /**
     *  FacebookCallback<LoginResult>
     */
    public void onSuccess(LoginResult loginResult)
    {
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
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        Profile profile = Profile.getCurrentProfile();
        if(null == accessToken)
        {
            LoginManager loginManager = LoginManager.getInstance();
            loginManager.logInWithReadPermissions((Activity) Cocos2dxActivity.getContext(), Arrays.asList("public_profile", "user_friends"));
        }
        else
        {

            String imageUrl = profile.getProfilePictureUri(120, 120).toString();

            Log.d("FacebookAccount", imageUrl);

            GraphRequest.GraphJSONObjectCallback callback = new GraphRequest.GraphJSONObjectCallback()
            {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response)
                {
                    FacebookRequestError error = response.getError();
                    if(null == error)
                    {
                        Log.d("FacebookAccount", object.toString());
                        FacebookAccount.this.notifLoginFinished(null);
                    }
                    else
                    {
                        FacebookRequestError.Category category = error.getCategory();
                        if(FacebookRequestError.Category.LOGIN_RECOVERABLE == category)
                        {   //认证失败了需要重新采用登陆框登陆
                            LoginManager loginManager = LoginManager.getInstance();
                            loginManager.logInWithReadPermissions((Activity) Cocos2dxActivity.getContext(), Arrays.asList("public_profile", "user_friends"));
                        }
                        else
                        {
                            FacebookAccount.this.notifLoginFinished(error.getErrorMessage());
                        }
                    }
                }
            };


            GraphRequest request = GraphRequest.newMeRequest(accessToken, callback);
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,gender,first_name,last_name,link");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(null == mCallbackManager)
            return;

        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {}

    protected CallbackManager mCallbackManager;

    protected AccessTokenTracker mAccessTokenTracker;

    protected ProfileTracker mProfileTracker;
}
