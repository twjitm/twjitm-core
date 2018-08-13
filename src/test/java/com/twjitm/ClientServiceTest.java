package com.twjitm;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by twjitm on 2018/5/14.
 * netty客户端
 */
public class ClientServiceTest {
    static String ip = "127.0.0.1";
    static int port = 9090;

    public static void main(String[] args) {
        TestSpring.initSpring();
         startup(ip,port);
    }


    public static void startup(String ip, int port) {
        EventLoopGroup eventExecutors = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        // bootstrap.bind(ip,port);
        bootstrap.group(eventExecutors);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.handler(new TestChannelInitializer());
        try {
            ChannelFuture future = bootstrap.connect(ip, port).sync();
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * md5У���ļ�
     *
     * @return
     */
    public static boolean md5() {

        File file = new File("E:\\���2018-05-34.txt");
        if (!file.isFile()) {
            return false;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());

        System.out.println(bigInt.toString(16));
        return true;
    }

}
