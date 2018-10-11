/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.yuanwhy.jobrobot.crawler.impl;

import com.yuanwhy.jobrobot.crawler.JobReader;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author hongyuan.why
 * @version $Id: JobReader.java, v 0.1 2018-10-11 9:57 PM hongyuan.why Exp $$
 */
@Component
public class HttpJobReader implements JobReader{

    //专场职位URL
    public static String ALIBAB_JOB_URL = "https://job.alibaba.com/zhaopin/positionList.htm?keyWord=JXU0RTEzJXU1NzNB&_input_charset=UTF-8&";

    /**
     * 通过URL读取职位信息，输出是HTML string
     * @param url
     * @return
     */
    @Override
    public String read(String url) {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            return EntityUtils.toString(entity);


        } catch (IOException e) {

            throw new RuntimeException(e);

        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {

                    //do nothing;

                }
            }
        }



    }

}
