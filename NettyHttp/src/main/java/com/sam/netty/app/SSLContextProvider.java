package com.sam.netty.app;

import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public final class SSLContextProvider {
	private static final String PROTOCOL = "TLSv1.2";
	//SelfSignedCertificate ssc = new SelfSignedCertificate();
	public static SSLContext get(){
		SSLContext sslContext = null;
		try {
			sslContext = SSLContext.getInstance(PROTOCOL);
			KeyStore ks = KeyStore.getInstance("jceks");    
			ks.load(new FileInputStream("C:/keytool/keystore.jks"), null);    
			KeyManagerFactory kf = KeyManagerFactory.getInstance("SunX509");    
			kf.init(ks, "654321".toCharArray());  
			sslContext.init(kf.getKeyManagers(), null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sslContext;
	}
}
