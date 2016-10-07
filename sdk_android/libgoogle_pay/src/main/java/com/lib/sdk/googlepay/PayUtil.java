package com.lib.sdk.googlepay;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.text.SimpleDateFormat;
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

//    public static String getCpOrder(String _accountId, String _serverId, int _channelId, String _channelFlag) {
//        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
//        final String date = sDateFormat.format(new java.util.Date());
//        int random = (int)(Math.random() * (9999 - 1000)) + 1000;//四位随机数字
//        String appOrderId = String.format("%s--%s--%s--%s--%s--%s",
//                _accountId,
//                _serverId,
//                ""+_channelId,
//                date,
//                _channelFlag,
//                ""+random);
//        return appOrderId;
//    }

    public static String getDevOrder(GooglePayPurchase payPurchase) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        String devOrder = "mid=" + payPurchase.getGameUserId();
        devOrder += ("&geid=" + payPurchase.getOtherInfo("geid"));
        devOrder += ("&pcc_id=" + payPurchase.getOtherInfo("pcc_id"));
        devOrder += ("&cc_flag=" + payPurchase.getOtherInfo("cc_flag"));
        devOrder += ("&cc_id=" + payPurchase.getOtherInfo("cc_id"));
        devOrder += ("&pm_id=" + payPurchase.getOtherInfo("pm_id"));
        devOrder += ("&unit_price=" + payPurchase.getOtherInfo("unit_price"));
        devOrder += ("&points=" + payPurchase.getOtherInfo("points"));
        return devOrder;
    }
}
