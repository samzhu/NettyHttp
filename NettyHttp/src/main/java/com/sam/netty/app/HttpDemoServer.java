package com.sam.netty.app;

import io.netty.bootstrap.*;
import io.netty.channel.*;
import io.netty.channel.nio.*;
import io.netty.channel.socket.nio.*;

public class HttpDemoServer {
	private final int port;
    public static boolean isSSL;
 
    public HttpDemoServer(int port) {
        this.port = port;
    }
 
    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new HttpDemoServerInitializer());
 
            Channel ch = b.bind(port).sync().channel();
            System.out.println("HTTP Upload Server at port " + port + '.');
            System.out.println("Open your browser and navigate to http"+(isSSL?"s":"")+"://localhost:" + port + '/');
 
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
 
    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        if (args.length > 1) {
            isSSL = true;
        }
        isSSL = true;
        port = 8443;
        new HttpDemoServer(port).run();
    }
}
