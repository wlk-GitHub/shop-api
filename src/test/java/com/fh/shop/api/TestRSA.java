package com.fh.shop.api;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;


public class TestRSA {

    @Test
    public void test1(){
        RSA rsa = new RSA();
        String privateKeyBase64 = rsa.getPrivateKeyBase64();//私钥
        String publicKeyBase64 = rsa.getPublicKeyBase64();//公钥


        System.out.println(privateKeyBase64);
        System.out.println(publicKeyBase64);
    }

    @Test
    public void test2() throws UnsupportedEncodingException {
        String privateKey = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBANK0VS3Et+Vnqdn66YzJZEKhVNT+eAv0v8xrmsSQUrl9lqFPC2cmFjZgQNk5F1UHO3cFUZk3cNJkmi9Un9fc1z74Qky9PoJYs7TT1IofBW1/Jesg4J57EiCdy79a8budoYHC3euR7k0W9HU7u+hiXF4nocVb5ckmGPUNKOMWGxftAgMBAAECgYEAkYnmXBrJOzgDZoGd2JUzH6L4SbcLjJad9MFOwf8+ZMmOxhUnNzRjm2OY53I734uDU47k8gQNdgXvSkoVrrDoK2DzuDWARwqxjC95hVaVwKQm3ROGANebzK8G9cB//tp/9juwVrDBfHl5DiWkKIujB3tZVyjZteStakhotiojLoECQQD52Xmy75pjNJzBfIOy5r0sliB1WFsRLxb/XOrb188MrcAoqYfhGXrR9CTrUYl3BInJB0ehOfuih3/swxv+UsFJAkEA1+QrUo5x6BoCArQE+VjOWipDLiw9XV+40upQE8Fpk9Trf4Unq+YkWARGRyDADej+EaSLECZ7kVkjNMjdcuRFhQJBANfU/IKM5Uup2XhzvblABk+L6MI174Vqrm0usVv0k9IaDpORz+WugyD3zSTTLPL5oqVceo5WboA6mVHIQlhy2LECQQCvZ9vnDILxoEfn6FluOcAWs+W3WwxUlBQ3kVaUZn2gNctM5WPRH4CB3p825R4FbzsWHIk8Cvg0pKadGWILGfp9AkEAi5WbOff8IHIXhOc8jBERVzrWdzoCAoDgWiSiHCubB2qVj+jKWvwpVHVdh8DlUtABbaciWhq74rHxMoH0C8B9vQ==";
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDStFUtxLflZ6nZ+umMyWRCoVTU/ngL9L/Ma5rEkFK5fZahTwtnJhY2YEDZORdVBzt3BVGZN3DSZJovVJ/X3Nc++EJMvT6CWLO009SKHwVtfyXrIOCeexIgncu/WvG7naGBwt3rke5NFvR1O7voYlxeJ6HFW+XJJhj1DSjjFhsX7QIDAQAB";
        RSA rsa = new RSA(privateKey,publicKey);
        String result = rsa.encryptBase64("zhangsan", KeyType.PublicKey);
        System.out.println("加密后："+result);
        byte[] res = rsa.decryptFromBase64(result, KeyType.PrivateKey);
        String info = new String(res, "utf-8");
        System.out.println("解密后："+info);
    }
}
