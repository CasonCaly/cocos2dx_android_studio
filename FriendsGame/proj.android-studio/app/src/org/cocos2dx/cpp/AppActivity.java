/****************************************************************************
Copyright (c) 2015 Chukong Technologies Inc.
 
http://www.cocos2d-x.org

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
****************************************************************************/
package org.cocos2dx.cpp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.cocos2dx.lib.Cocos2dxActivity;
import com.lib.x.*;
import com.lib.sdk.facebook.*;


public class AppActivity extends Cocos2dxActivity {

    protected void onCreate(final Bundle savedInstanceState)
    {
        CoreMain.init();
        if(!SDKCenter.hasThisSDK(FacebookAccount.class.getName()))
        {
            FacebookAccount account = new FacebookAccount();
            SDKCenter.setDefaultAccountSDK(account);
        }

        if(!SDKCenter.hasThisSDK(FacebookShare.class.getName()))
        {
            FacebookShare share = new FacebookShare();
            SDKCenter.setDefaultShareSDK(share);
        }

        super.onCreate(savedInstanceState);
        CoreMain.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        CoreMain.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        CoreMain.onPause();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        CoreMain.onDestroy();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        CoreMain.onStart();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        CoreMain.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        CoreMain.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        CoreMain.onSaveInstanceState(outState);
    }
}
