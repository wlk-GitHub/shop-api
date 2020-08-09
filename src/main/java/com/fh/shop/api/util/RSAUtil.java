package com.fh.shop.api.util;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

import java.io.UnsupportedEncodingException;

public class RSAUtil {


    public static final String PUBLICKKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCvfTM2qaSBBb0m7JDnlbuOvLHx5eLSZSajzDarDpKIQchLnntJT7VKReQO+70s/as7Xq2FLdvhRCNR7sYPyms9kcI//oUvZyG3b9etrNAZJ87nfVuaIXkGhR+6RAxxq3eRS5csMyCnQIcfkJ3SPLnRh/zYla6P92GLLwGCmbQE1wIDAQAB\n";
    public static final String PRIVATEKEY="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAK99MzappIEFvSbskOeVu468sfHl4tJlJqPMNqsOkohByEuee0lPtUpF5A77vSz9qzterYUt2+FEI1Huxg/Kaz2Rwj/+hS9nIbdv162s0Bknzud9W5oheQaFH7pEDHGrd5FLlywzIKdAhx+QndI8udGH/NiVro/3YYsvAYKZtATXAgMBAAECgYBHmhnvT8BjNGg9HYDyPNHOJ4ejX1zw43ykkIotuUBogA6ta/h5rSAXMfUfd+90D/xxuOurz05L13Vh1L7Tao/eXj/GP1Z+UK1sDZdGYhFQrcFWOXIdfV7ubFZ9HOmt6evRnEm5tVdlNAFh+fPqZuMs3TYrPgAduUreSpKzlfu9gQJBANgDIRzuHm6Yuh8pPXFBnys1uFQ6pkzzOUXVAYF6TTtDv/LU+hzELlaebNmI052+a1kri9GWVzqtmpEoWwJERScCQQDP+asf9w4lHNab0m+Hzd3A1uCNpibOKWYMz41W76GDN+GowzXjMF7q13JPUtZO5IojTNWNkbHtI9dKPc+LpfDRAkEAlqsYHaW5dErzB2ksgiLTcsA1ogqGlIXSHPNF6Zn05LGM4UPMcOQlriUyp0jY0d5P4hKNo9LJZRRSC42qbRpRYQJBAJuUR+F3rYsx0ZFrkKdk55iUphMQ0kKzUXYP5cm9JXPF3/ehYLR9wOOpHAgfDdaivHu/xJlM/m9Juon9Rz9OeaECQHQlTUKKJ2murOu8n5yWmQOGfnzD5k7KYOVA8Uc+IC5qjedWisVW3mCegRnC2Dbh+s0wrXV/9zMp8T6VTX2kJPc=";
    public static String decrypt(String data) {
        RSA rsa = new RSA(PRIVATEKEY,PUBLICKKEY);
        byte[] bytes = rsa.decryptFromBase64(data, KeyType.PrivateKey);
        try {
            String result = new String(bytes, "utf-8");
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
            }
        }
    }
