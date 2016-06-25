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

/**
 * Created by Nervecell on 2016/6/15.
 */
public class FacebookAccount extends AccountSDK implements FacebookCallback<LoginResult>{

    public FacebookAccount()
    {
    }

    @Override
    public void onCreate(final Bundle instance)
    {
        if(FacebookSdk.isInitialized())
            return;

        FacebookSdk.sdkInitialize(Cocos2dxActivity.getContext());
        mCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(mCallbackManager, this);

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

    public void setProfileImageSize(int size)
    {
        m_profileImageSize = size;
    }

    public void setIsNeedFetchFriends(boolean isNeed)
    {
        m_needFetchFriends = isNeed;
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
            this.setName(profile.getName());
            this.setProfileImage(profile.getProfilePictureUri(m_profileImageSize, m_profileImageSize).toString());
            this.onFetchMeSuccess();
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
        if(null == mAccessToken)
        {
            this.useManagerLogin();
        }
        else
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


            GraphRequest request = GraphRequest.newMeRequest(mAccessToken, callback);
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
    public void onDestroy() {
        if(null != mAccessTokenTracker)
            mAccessTokenTracker.stopTracking();

        if(null != mProfileTracker)
            mProfileTracker.stopTracking();
    }

    protected  void getInfoFromJson(JSONObject object) throws JSONException {
        this.setAccountId(object.getString("id"));
        this.setFirstName(object.getString("first_name"));
        this.setLastName(object.getString("last_name"));
        this.setName(object.getString("name"));
        Profile profile = Profile.getCurrentProfile();
        if(null != profile)
            this.setProfileImage(profile.getProfilePictureUri(m_profileImageSize, m_profileImageSize).toString());
    }

    protected  void useManagerLogin()
    {
        LoginManager loginManager = LoginManager.getInstance();
        loginManager.logInWithReadPermissions((Activity) Cocos2dxActivity.getContext(), Arrays.asList("public_profile", "user_friends"));
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
                    Log.d("FacebookAccount ", error.getErrorMessage());
                }
                else
                {
                    FacebookAccount.this.parseInvitalbeFriends(response.getJSONObject());
                }
                FacebookAccount.this.notifLoginFinished(null);
            }
        };

        String path = String.format("me/invitable_friends?fields=id,name,first_name,last_name,pitcure.width(%d).height(%d)", m_profileImageSize, m_profileImageSize );
        GraphRequest request = GraphRequest.newGraphPathRequest(mAccessToken, path, callback);
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
                        JSONObject pircuteData = friendData.getJSONObject("data");
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

    protected CallbackManager mCallbackManager;

    protected AccessTokenTracker mAccessTokenTracker;

    protected ProfileTracker mProfileTracker;

    protected AccessToken mAccessToken;

    protected Profile mProfile;

    protected int m_profileImageSize = 120;

    protected boolean m_needFetchFriends = true;
}
