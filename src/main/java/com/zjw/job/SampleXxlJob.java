package com.zjw.job;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * xxl-job 示例任务
 */
@Component
public class SampleXxlJob {

  private static final Logger log = LoggerFactory.getLogger(SampleXxlJob.class);

  /**
   * 示例任务处理器
   */
  @XxlJob("sampleJobHandler")
  public void sampleJobHandler() {
    XxlJobHelper.log("XXL-JOB, 示例任务开始执行");
    log.info("xxl-job 示例任务执行中...");
    XxlJobHelper.handleSuccess("执行成功");
  }
}
