package com.yuanwhy.jobrobot;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.yuanwhy.jobrobot.crawler.JobReader;
import com.yuanwhy.jobrobot.crawler.impl.HttpJobReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JobRobotApplicationTests {

	@Autowired
	private JobReader jobReader;

	@Before
	public void contextLoads() {
	}

	@Test
	public void testJobReader() {
		System.out.println(jobReader.read(HttpJobReader.ALIBAB_JOB_URL));
	}

	@Test
	public void testHtmlParse() {
		String content = jobReader.read(HttpJobReader.ALIBAB_JOB_URL);
		Document doc = Jsoup.parse(content);
		Elements temp = doc.getElementsByClass("right-list-box");
		Elements links = temp.tagName("li");
//        for (Element link : links) {
//            link.tagName("a").getElementsByAttribute("href").
//                    stream().map(item -> item.attr("href")).forEach(System.out::println);
//        }
		List<String> urls = links.stream().flatMap(link -> link.tagName("a")
				.getElementsByAttribute("href")
				.stream())
				.map((Element item) -> item.attr("href"))
				.collect(Collectors.toList());
		System.out.println(urls.size());
	}

	@Test
	public void testWorkDetailParse() {
		String url = "https://job.alibaba.com/zhaopin/PositionDetail.htm?spm=a2obv.11410903.0.0.5f0a44f6RHyULT&positionId=58664";
		String content = jobReader.read(url);
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
		bagTitleTempC.forEach(x->temps.add(x));
		System.out.println(String.format("职位名称：%s",temps.get(0)));

		//解析职位其它相关信息
		Optional<Element> other = doc.getElementsByClass("box-border").stream().findFirst();
		List<String> infos = Lists.newArrayList();
		if(other.isPresent()){
			infos = other.get().getElementsByTag("td").stream().map(x->x.text()).collect(Collectors.toList());
		}
		//取index:1-发布时间,3-工作地点，7-所属部门，11-招聘人数
		System.out.println(infos.toString());
	}
}
