package com.example.dxm.easyandroid.spider;

import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by duxiaoming on 16/8/20.
 */
public class DataUtil {

//    public static String doGet(String urlStr) throws CommonException {
//        StringBuffer sb = new StringBuffer();
//
//        try {
//            URL url = new URL(urlStr);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            conn.setConnectTimeout(5000);
//            conn.setDoInput(true);
//            conn.setDoOutput(true);
//            if (conn.getResponseCode() == 200) {
//                InputStream is = conn.getInputStream();
//                int len = 0;
//                byte[] buf = new byte[4096];
//                while ((len = is.read(buf)) != -1) {
////                    Logger.d("sbsbsbsb>>>>>>"+new String(buf, 0, len, "utf-8"));
//                    sb.append(new String(buf, 0, len, "utf-8"));
//                }
//                is.close();
//            } else {
//                throw new CommonException("读取数据过程失败");
//            }
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            throw new CommonException("请求url失败");
//        }
//        return sb.toString();
//
//    }

    public String doGet_ok(String url) throws CommonException, IOException {
        Response response= null;
        try {
            response = OkHttpUtils.get().url(url).tag(this).build().execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.body().string();

    }
}
