# job-robot
a robot that syncs job information to WeChat group

## Architecture
- crawler
  - data source is from [Alibaba专场链接](https://job.alibaba.com/zhaopin/positionList.htm?keyWord=JXU0RTEzJXU1NzNB&_input_charset=UTF-8&).
  - crawler should be triggered once a day.
  - parser is one part of the crawler, whose input is HTML and output is structured data such as JSON.
- storage
  - save the output from crawler layer into storage medium, such as DB or file system
  - the record must have flag which can stand for whether it is sent or not
- push
  - push module's responsibility is to load all job records which are not sent, and then send them to WeChat group
  - push module should be triggered once a day,  but after crawler is finished. one way is to do push immediately after storage.
  - WeChat API can be used here.
  

## Technology stack
 - Spring
 - Jsoup - Java HTML Parser
 - Quartz - Scheduler
 - h2database - small DB
