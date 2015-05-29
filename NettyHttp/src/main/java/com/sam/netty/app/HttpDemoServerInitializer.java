package com.sam.netty.app;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import javax.net.ssl.SSLEngine;

public class HttpDemoServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        // Create a default pipeline implementation.
        ChannelPipeline pipeline = ch.pipeline();
        //final SslContext sslCtx;
        if (HttpDemoServer.isSSL) {
            SSLEngine engine = new SSLContextProvider().get().createSSLEngine();
            engine.setNeedClientAuth(false); //ssl雙向認證
            engine.setUseClientMode(false);
            engine.setWantClientAuth(true);
            engine.setEnabledProtocols(new String[]{"TLSv1.2"});
            pipeline.addLast("ssl", new SslHandler(engine));
        }
 
        /**
         * http-request解碼器
         * http服務器端對request解碼
         */
        pipeline.addLast("decoder", new HttpRequestDecoder());
        /**
         * http-response解碼器
         * http服務器端對response編碼
         */
        pipeline.addLast("encoder", new HttpResponseEncoder());
 
        /**
         * 壓縮
         * Compresses an HttpMessage and an HttpContent in gzip or deflate encoding
         * while respecting the "Accept-Encoding" header.
         * If there is no matching encoding, no compression is done.
         */
        pipeline.addLast("deflater", new HttpContentCompressor());
 
        pipeline.addLast("handler", new HttpDemoServerHandler());
    }
}
