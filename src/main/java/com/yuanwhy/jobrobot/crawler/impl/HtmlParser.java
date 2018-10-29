/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.yuanwhy.jobrobot.crawler.impl;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.yuanwhy.jobrobot.crawler.JobReader;
import com.yuanwhy.jobrobot.crawler.Parser;
import com.yuanwhy.jobrobot.model.PositionModel;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author hongyuan.why
 * @version $Id: HtmlParser.java, v 0.1 2018-10-11 10:28 PM hongyuan.why Exp $$
 */
@Service
public class HtmlParser implements Parser {

    private static final Logger logger = Logger.getLogger(HtmlParser.class);

    @Resource
    private JobReader jobReader;

    /**
     * 将 html 信息解析成标准的职位信息封装成对象
     * @param content
     * @return
     */
    @Override
    public List<PositionModel> parse(String content) {

        try {
            Preconditions.checkNotNull(content);
            Document doc = Jsoup.parse(content);
            Elements temp = doc.getElementsByClass("right-list-box");
            Elements links = temp.tagName("li");
            List<String> urls = links.stream().flatMap(link -> link.tagName("a")
                    .getElementsByAttribute("href")
                    .stream())
                    .map((Element item) -> item.attr("href"))
                    .collect(Collectors.toList());
            return workDetailParse(urls);
        } catch (Exception e) {
            logger.error("解析内容：html 为空", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 解析每个职位详情的 html 内容
     *
     * @param urls
     * @return
     */
    @Override
    public List<PositionModel> workDetailParse(List<String> urls) {
        try {
            Preconditions.checkNotNull(urls);
            List<String> contents = urls.stream()
                    .map(url -> jobReader.read(url))
                    .collect(Collectors.toList());
            Preconditions.checkNotNull(contents);
            return contents.stream().map(content -> infoParse(content)).collect(Collectors.toList());
        } catch (NullPointerException e) {
            logger.error("请确保urls不为空且正确！", e);
            throw new NullPointerException();
        } catch (Exception e) {
            logger.error("获取职位对应的 html 内容失败！", e);
            throw new RuntimeException(e);
        }
    }

    public PositionModel infoParse(String content) {
        PositionModel positionModel = new PositionModel();
        Document doc = Jsoup.parse(content);
        //解析职位名称
        String bgTitleTempA = doc.getElementsByClass("bg-title").stream()
                .findFirst()
                .get().toString();
        Iterable<String> bagTitleTempB = Splitter.on(">").trimResults().omitEmptyStrings().split(bgTitleTempA);
        List<String> temps = Lists.newArrayList();
        bagTitleTempB.forEach(x -> temps.add(x));
        Iterable<String> bagTitleTempC = Splitter.on("<").trimResults().omitEmptyStrings().split(temps.get(1));
        temps.clear();
        Preconditions.checkNotNull(bagTitleTempC);
        bagTitleTempC.forEach(x -> temps.add(x));
        positionModel.setName(temps.get(0));

        //解析职位其它相关信息
        Optional<Element> other = doc.getElementsByClass("box-border").stream().findFirst();
        try {
            if (other.isPresent()) {
                List<String> infos = other.get().getElementsByTag("td").stream().map(x -> x.text()).collect(Collectors.toList());
                positionModel.setPublishDate(infos.get(1));
                positionModel.setLocation(infos.get(3));
                positionModel.setHeadCount(infos.get(11));
            }
        } catch (Exception e) {
            logger.error("通过下标提取职位相关信息失败：", e);
            throw new RuntimeException(e);
        }
        return positionModel;
    }
}
