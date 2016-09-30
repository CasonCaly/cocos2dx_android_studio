package com.lib.sdk.googlepay;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class PayUtil {

    public final static String GooglePlayPackageName = "com.android.vending";

    //检查是否安装了google play
    public static boolean haveGooglePlay(Context context, String packageName)  {
        //Get PackageManager
        final PackageManager packageManager = context.getPackageManager();
        //Get The All Install App Package Name
        List<PackageInfo> pInfo = packageManager.getInstalledPackages(0);

        //Create Name List
        List<String> pName = new ArrayList<String>();

        //Add Package Name into Name List
        if(pInfo != null){
            for(int i=0; i<pInfo.size(); i++){
                String pn = pInfo.get(i).packageName;
                pName.add(pn);
            }
        }

        //Check
        return pName.contains(packageName);
    }
}
