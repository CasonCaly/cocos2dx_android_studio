package com.lib.sdk.facebook;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.lib.x.AccountSDK;
import com.facebook.*;

import org.cocos2dx.lib.Cocos2dxActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import  android.os.Bundle;
import java.util.Arrays;
import java.util.Set;

/**
 * Created by Nervecell on 2016/6/15.
 */
public class FacebookAccount extends AccountSDK {

    public FacebookAccount()
    {
    }

    public void setProfileImageSize(int size)
    {
        m_profileImageSize = size;
    }

    public void setIsNeedFetchFriends(boolean isNeed)
    {
        m_needFetchFriends = isNeed;
    }

    @Override
    public void onCreate(final Bundle instance)
    {
        if(FacebookSdk.isInitialized())
            return;

        FacebookSdk.sdkInitialize(Cocos2dxActivity.getContext());
        mCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>()
            {
                public void onSuccess(LoginResult loginResult)
                {
                    //当授权成功之后我们再用图谱api登陆一遍，因为那样能获取更多个人信息
                    FacebookAccount.this.useGraphLogin();
                }

                public void onCancel() {
                    FacebookAccount.this.notifLoginCancel();
                }

                public void onError(FacebookException error) {
                    FacebookAccount.this.notifLoginFinished(error.getLocalizedMessage());
                }
            }
        );

        mAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                FacebookAccount.this.mAccessToken = currentAccessToken;
                Log.d("FacebookAccount", "onCurrentAccessTokenChanged");
            }
        };

        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                FacebookAccount.this.mProfile = currentProfile;
                Log.d("FacebookAccount", "onCurrentProfileChanged");
            }
        };

    }

    @Override
    public void prepare()
    {
    }

    @Override
    public void login()
    {
        if(null == mAccessToken)
        {
            this.useManagerLogin();
        }
        else
        {
            Set<String> permissions = mAccessToken.getPermissions();
            if(m_needFetchEmail)
            {
                if(!permissions.contains("email"))
                {
                    //需要获取邮箱，但又没有邮箱权限，那么需要去重新授权
                    this.useManagerLogin();
                    return;
                }
            }
            this.useGraphLogin();
        }
    }

    protected  void useManagerLogin()
    {
        LoginManager loginManager = LoginManager.getInstance();
        if(m_needFetchEmail)
            loginManager.logInWithReadPermissions((Activity) Cocos2dxActivity.getContext(), Arrays.asList("public_profile", "user_friends, email"));
        else
            loginManager.logInWithReadPermissions((Activity) Cocos2dxActivity.getContext(), Arrays.asList("public_profile", "user_friends"));
    }

    protected void useGraphLogin()
    {
        GraphRequest.GraphJSONObjectCallback callback = new GraphRequest.GraphJSONObjectCallback()
        {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response)
            {
                FacebookRequestError error = response.getError();
                if(null == error)
                {
                    try {
                        FacebookAccount.this.getInfoFromJson(object);
                        FacebookAccount.this.onFetchMeSuccess();
                    }
                    catch (JSONException e) {
                        FacebookAccount.this.notifLoginFinished(e.getLocalizedMessage());
                    }
                }
                else
                {
                    FacebookRequestError.Category category = error.getCategory();
                    if(FacebookRequestError.Category.LOGIN_RECOVERABLE == category)
                    {   //认证失败了需要重新采用登陆框登陆
                        FacebookAccount.this.useManagerLogin();
                    }
                    else
                    {
                        FacebookAccount.this.notifLoginFinished(error.getErrorMessage());
                    }
                }
            }
        };

        AccessToken curAccessToken = mAccessToken == null ? AccessToken.getCurrentAccessToken() : mAccessToken;
        GraphRequest request = GraphRequest.newMeRequest(curAccessToken, callback);
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,gender,first_name,last_name,link,email");
        request.setParameters(parameters);
        request.executeAsync();
    }


    protected  void getInfoFromJson(JSONObject object) throws JSONException {
        this.setAccountId(object.getString("id"));
        this.setFirstName(object.getString("first_name"));
        this.setLastName(object.getString("last_name"));
        this.setName(object.getString("name"));
        if(object.has("email"))
            this.setEmail(object.getString("email"));
        Profile profile = Profile.getCurrentProfile();
        if(null != profile)
            this.setProfileImage(profile.getProfilePictureUri(m_profileImageSize, m_profileImageSize).toString());
    }


    protected void onFetchMeSuccess()
    {
        if(m_needFetchFriends)
        {
            this.fetchMyInvitableFriends();
        }
        else
        {
            this.notifLoginFinished(null);
        }
    }

    protected void fetchMyInvitableFriends()
    {
        GraphRequest.Callback callback = new GraphRequest.Callback()
        {
            public void onCompleted(GraphResponse response)
            {
                FacebookRequestError error = response.getError();
                if (null == error)
                {
                    FacebookAccount.this.parseInvitalbeFriends(response.getJSONObject());
                }
                else
                {
                    Log.d("FacebookAccount ", error.getErrorMessage());
                }
                FacebookAccount.this.notifLoginFinished(null);
            }
        };

        String path = "me/invitable_friends";
        GraphRequest request = GraphRequest.newGraphPathRequest(mAccessToken, path, callback);
        Bundle bundle = new Bundle();
        String fields = String.format("picture.width(120).height(120),id,name,first_name,last_name", m_profileImageSize, m_profileImageSize);
        bundle.putString("fields", fields);
        request.setParameters(bundle);
        request.executeAsync();
    }

    protected void parseInvitalbeFriends(JSONObject friendsJson)
    {
        try {
           if(!friendsJson.has("data"))
               return;
           JSONArray dataArray = friendsJson.getJSONArray("data");
            for(int i = 0; i < dataArray.length(); i++)
            {
                JSONObject friendData = dataArray.getJSONObject(i);
                Friend friend = new Friend();
                if(friendData.has("id"))
                    friend.setId(friendData.getString("id"));

                if(friendData.has("name"))
                    friend.setName(friendData.getString("name"));

                if(friendData.has("first_name"))
                    friend.setFirstName(friendData.getString("first_name"));

                if(friendData.has("last_name"))
                    friend.setLastName(friendData.getString("last_name"));

                if(friendData.has("picture"))
                {
                    JSONObject picture = friendData.getJSONObject("picture");
                    if(picture.has("data"))
                    {
                        JSONObject pircuteData = picture.getJSONObject("data");
                        if(pircuteData.has("url"))
                            friend.setProfileImage(pircuteData.getString("url"));
                    }
                }
                this.addFriend(friend);
            }
        }
        catch (JSONException e)
        {
            Log.d("FacebookAccount ", e.getLocalizedMessage());
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
    public void onDestroy() {
        if(null != mAccessTokenTracker)
            mAccessTokenTracker.stopTracking();

        if(null != mProfileTracker)
            mProfileTracker.stopTracking();
    }

    protected CallbackManager mCallbackManager;

    protected AccessTokenTracker mAccessTokenTracker;

    protected ProfileTracker mProfileTracker;

    protected AccessToken mAccessToken;

    protected Profile mProfile;

    protected int m_profileImageSize = 120;

    protected boolean m_needFetchFriends = true; //是否要抓取好友信息

    protected boolean m_needFetchEmail = true;//是否要抓取邮箱信息
}
