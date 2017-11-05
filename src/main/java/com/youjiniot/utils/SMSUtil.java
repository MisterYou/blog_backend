package com.youjiniot.utils;

import java.util.Random;

/**
 * Created by lenovo on 2016/4/22.
 */
public class SMSUtil {

    /**
     * 发送短信
     * @param tel 手机号
     * @param type 类型 0注册 1找回密码
     *             因需要区分大墨投资或者大墨财富，故增加type=10表示大墨投资注册，type=11表示大墨投资找回密码
     * @return
     * @throws Exception
     */
    public static String sendCode(String tel,int type) throws Exception {
        String code = SMSUtil.getCode();
        String tpl_id = "";
        //原来的代码
        /*if (type == 0) {
            tpl_id = "16490";   //tpl_id 短信模版ID 大墨投资
        } else {
            tpl_id = "16490";
        }*/
        //修改之后
        if (type==0 || type==1){
            tpl_id = "13452";   //大墨财富
        }
        if (type==10 || type==11){
            tpl_id = "16490";   //大墨投资
        }else {
            tpl_id = "13452";   //大墨财富
        }
        /**
        String key = "5577f452db46504d29d338b5691729c3";  //appkey

        String url = "http://v.juhe.cn/sms/send?mobile="+tel+"&tpl_id="+tpl_id+"&tpl_value=%23code%23%3D"+code+"&key="+key;
        String result = HttpUtil.sendGet(url);
        Gson gson =new Gson();
        Map map = gson.fromJson(result,Map.class);
        Double error_code = (Double) map.get("error_code");
        if (error_code<1){
            return code;
        }else {
            return null;
        }
        暂时注释
         */
        //// TODO: 2016/9/28 验证码上生产改回来
      return "123456";
    }

    /**
     * 生成验证码
     * @return
     */
    private static String getCode(){
       //生成验证码
        Random random = new Random();
        String code = "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }

        code = sb.toString();

        return code;


        //return "111111";

    }

    public static void main(String[] args) throws Exception {
//        String tel = "17713544621";
        String tel = "17340102208";

        String code = "852364";

        String tpl_id = "13441";

        String url = "http://v.juhe.cn/sms/send?mobile="+tel+"&tpl_id="+tpl_id+"&tpl_value=%23code%23%3D"+code+"&key=0ae418fad59828a3d262fe63e6f3fc47";
//        String url = "http://v.juhe.cn/sms/send?mobile=17340102208&tpl_id=13441&tpl_value=%23code%23%3D99999&key=0ae418fad59828a3d262fe63e6f3fc47";

        //String result = HttpUtil.sendGet(url);

        //System.out.println(result);

        //{"reason":"发送成功","result":{"sid":"9000429135545200900","fee":1,"count":1},"error_code":0}

        String result = HttpClient1Util.getInstance().get(url);
        System.out.println(result);


//        String json = "{\"reason\":\"发送成功\",\"result\":{\"sid\":\"9000429135545200900\",\"fee\":1,\"count\":1},\"error_code\":0}";
//
//        Gson gson =new Gson();
//
//        Map map = gson.fromJson(json,Map.class);
//
//
//        Double error_code = (Double) map.get("error_code");
//        if (error_code<1){
//            System.out.println("发送成功");
//        }

    }


    public static String sendToken(String username, String phoneToken) {
//        String tel = "17340102208";
//
//        String code = "852364";
//
//        String tpl_id = "13441";
//        String tpl_id = "22539";
        String tpl_id = "22584";
//        String url = "http://v.juhe.cn/sms/send?mobile="+username+"&tpl_id="+tpl_id+"&tpl_value=%23code%23%3D"+phoneToken+"&key=0ae418fad59828a3d262fe63e6f3fc47";
        String url = "http://v.juhe.cn/sms/send?mobile="+username+"&tpl_id="+tpl_id+"&tpl_value=%23code%23%3D"+phoneToken+"&key=6263a7c8ce39333719f176c8aeb44fd9";
        String res = HttpClient1Util.getInstance().get(url);
        return res;
    }
}
