package com.vinux.push.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//import org.springframework.boot.CommandLineRunner;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;

import com.vinux.push.entity.ChannelCache;
import com.vinux.push.entity.Config;
import com.vinux.push.entity.Message;
import com.vinux.push.handler.ConnectHandler;
import com.vinux.push.handler.HeartBeatHandler;
import com.vinux.push.handler.PushMsgHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

//@Component
//@Order(value=1)
public class ClientServer {//implements CommandLineRunner {
	
	private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    //换掉ip
    private String host = Config.SERVER_HOST;//"123.206.13.254";
    private int port = Config.SERVER_PORT;

    public void connect() throws Exception {
        try{
            EventLoopGroup group = new NioEventLoopGroup();
            Bootstrap bs = new Bootstrap();
            bs.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline p = channel.pipeline();
                            p.addLast(new IdleStateHandler(20, 10, 0));
                            p.addLast(new ObjectEncoder());
                            p.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                            p.addLast(new ReadTimeoutHandler(1000));
                            p.addLast(new ConnectHandler());
                            p.addLast(new HeartBeatHandler());
                            p.addLast(new PushMsgHandler());
                        }
                    });
            System.out.println("开始连接");
            ChannelFuture future = bs.connect(new InetSocketAddress(host, port)).sync();
            future.channel().closeFuture().sync();//这一步会阻塞住
            System.out.println("关闭后");
        } finally {
            //断错重连
            executor.execute(new Runnable() {
                public void run() {
                    System.out.println("Client 尝试重新连接-->>>>>>");
                    //等待InterVAl时间，重连
                    try {
                        TimeUnit.SECONDS.sleep(5);
                        //发起重连
                        connect();
                    } catch (Exception e) {
                    	System.out.println(e.getMessage());
                        //e.printStackTrace();
                    }
                }
            });
        }
    }
    
    //消息推送
    public void push(Message message){
    	if(ChannelCache.channel != null) {
    		System.out.println("client push 消息 + " + message.toString());
    		ChannelCache.channel.writeAndFlush(message);
    	}
    }

//	@Override
//	public void run(String... arg0) throws Exception {
//		connect();
//		
//	}

}
