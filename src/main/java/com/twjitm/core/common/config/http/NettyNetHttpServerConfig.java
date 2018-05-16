package com.twjitm.core.common.config.http;

/**
 * Created by 文江 on 2017/11/25.
 * http服务器配置类
 */
public final class NettyNetHttpServerConfig {
    private  static    DyHttpServiceConfig dyHttpServiceConfig;
//       public static void init() throws  Exception{
//           URL url =  FileUtil.getConfigURL(GlobalConstants.ConfigFile.HTTP_SERVER_CONFIG);
//               if(url!=null){
//                 //解析xml文件
//                   SAXReader sax=new SAXReader();//创建一个SAXReader对象
//                   Document document=sax.read( url.getFile());//获取document对象,如果文档无节点，则会抛出Exception提前结束
//                   Element root=document.getRootElement();//获取根节点
//                   dyHttpServiceConfig=new DyHttpServiceConfig();
//                   dyHttpServiceConfig.load(root);
//               }
//       }

    public NettyNetHttpServerConfig() {
    }

    public static void main(String[] args) {
        try {
           // init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
