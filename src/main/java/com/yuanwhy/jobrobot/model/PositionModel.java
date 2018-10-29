/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.yuanwhy.jobrobot.model;

/**
 * @author hongyuan.why
 * @version $Id: PositionModel.java, v 0.1 2018-10-11 10:24 PM hongyuan.why Exp $$
 */
public class PositionModel {

    /**
     * 职位ID
     */
    private String id;

    /**
     * 职位名称
     */
    private String name;

    /**
     * 工作地点
     */
    private String location;

    /**
     * 招聘人数
     */
    private String headCount;

    /**
     * 发布时间
     */
    private String publishDate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHeadCount() {
        return headCount;
    }

    public void setHeadCount(String headCount) {
        this.headCount = headCount;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
}
