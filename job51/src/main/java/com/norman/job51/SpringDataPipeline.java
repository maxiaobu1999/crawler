package com.norman.job51;


import com.norman.job51.pojo.JobInfo;
import com.norman.job51.service.JobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * 把抓取的数据保存到数据库
 */
@Component
public class SpringDataPipeline implements Pipeline {
    @Autowired
    private JobInfoService mJobInfoService;

    /**
     * 处理抓取的数据
     *
     * @param resultItems page.putField("jobinfo",jobInfo);保存的结果
     * @param task
     */
    @Override
    public void process(ResultItems resultItems, Task task) {
        //获取封装好的招聘详情对象
        JobInfo jobInfo = resultItems.get("jobInfo");
        if (jobInfo != null) {
            mJobInfoService.save(jobInfo);
        }

    }
}
