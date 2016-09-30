package com.lib.x;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Nervecell on 2016/6/16.
 */
public class CoreMain {

    private static boolean sIsInit = false;

    public static void init(Activity activity)
    {
        if(sIsInit)
            return;
        SDKCenter.getInstance().init(activity);
    }

    public static void onCreate(Activity activity, final Bundle savedInstanceState)
    {
        SDKCenter.onCreate(activity, savedInstanceState);
    }

    public static void onResume()
    {
        SDKCenter.onResume();
    }

    public static void onPause()
    {
        SDKCenter.onPause();
    }


    public static void onDestroy()
    {
        SDKCenter.onDestroy();
    }


    public static  void onStart()
    {
        SDKCenter.onStart();
    }


    public static  void onStop()
    {
        SDKCenter.onStop();
    }


    public static void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        SDKCenter.onActivityResult(requestCode, resultCode, data);
    }

    public static void onSaveInstanceState(Bundle outState)
    {
        SDKCenter.onSaveInstanceState(outState);
    }
}
