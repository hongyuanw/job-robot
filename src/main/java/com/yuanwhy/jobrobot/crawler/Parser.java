/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.yuanwhy.jobrobot.crawler;

import com.yuanwhy.jobrobot.model.PositionModel;

import java.util.List;

/**
 * @author hongyuan.why
 * @version $Id: Parser.java, v 0.1 2018-10-11 9:57 PM hongyuan.why Exp $$
 */
public interface Parser {

    List<PositionModel>  parse(String content);

    List<PositionModel>  workDetailParse(List<String> urls);
}
