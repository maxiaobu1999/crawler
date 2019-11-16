package com.norman.job51.task;

import com.norman.job51.SpringDataPipeline;
import com.norman.job51.pojo.JobInfo;
import org.apache.http.util.TextUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

@Component
public class JobProcessor implements PageProcessor {
    public final String INIT_URL = "https://search.51job.com/list/010000,000000,0000,32,9,99,Java%2B%25E5%2589%258D%25E7%25AB%25AF,2,1.html?lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=1&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=";
    /**
     * 配置抓取间隔，重试。。
     */
    private Site mSite = Site.me().setCharset("gbk")//解决乱码：页面用什么，指定什么编码
            .setTimeOut(10 * 1000)//超时时间
            .setRetrySleepTime(3 * 1000)//重试间隔时间
            .setRetryTimes(3);//重试次数

    @Autowired
    private SpringDataPipeline mSpringDataPipeline;

    @Override
    public void process(Page page) {
        //解析页面，获取招聘信息详情的地址
        List<Selectable> list = page.getHtml().css("div#resultList div.el").nodes();

        //判断获取到的集合是否为null
        if (list.size() == 0) {
            //空招聘详情页，解析页面，获取招聘详细信息，保存数据
            saveJobinfo(page);

        } else {
            //非null,招聘列表页，解析出详情页的URL地址，放入任务队列中
            for (Selectable selectable : list) {
//            Selectable selectable = list.get(1);
            String jobInfoUrl = selectable.links().toString();
//                System.out.println(jobInfoUrl);
            //把获取到的URL地址放到队列中
            page.addTargetRequest(jobInfoUrl);
            }

            //获取下一页的URL
            String bkUrl = page.getHtml().css("div.p_in li.bk").nodes().get(1).links().toString();
            System.out.println("++" + bkUrl);
            //把下一页的URL放入任务队列中
            page.addTargetRequest(bkUrl);

        }


    }

    /**
     * 解析页面，获取招聘详细信息，保存数据
     */
    private void saveJobinfo(Page page) {
        //创建招聘详情对象，
        JobInfo jobInfo = new JobInfo();
        // 解析页面
        Html html = page.getHtml();
//        String text1 = page.getUrl().toString();
        System.out.println("+++" + "");

        //获取数据，封装到对象中
        jobInfo.setCompanyName(html.css("div.cn p.cname a", "text").toString());
        jobInfo.setCompanyAddr(Jsoup.parse(html.css("div.bmsg").nodes().get(1).toString()).text());
        jobInfo.setCompanyInfo(Jsoup.parse(html.css("div.tmsg").toString()).text());//jsoup处理多元素
        jobInfo.setJobName(html.css("div.cn h1", "text").toString());
        jobInfo.setJobAddr(html.css("div.cn p.msg", "text").toString());
        jobInfo.setJobInfo(Jsoup.parse(html.css("div.job_msg").toString()).text());
        jobInfo.setUrl(page.getUrl().toString());
        jobInfo.setSalaryMin(1);//html.css("div.cn strong", "text").toString();
        jobInfo.setSalaryMax(2);
        String jobTime = "";
        try {
            String jobMsg = html.css("div.cn p.msg", "text").toString();
            String[] split = jobMsg.split("    ");
            jobTime = split[split.length - 1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        jobInfo.setTime(jobTime);

        //把处理的结果保存起来
        page.putField("jobInfo", jobInfo);
    }

    @Override
    public Site getSite() {
        return mSite;
    }

    /**
     * 定时任务启动函数
     * initialDelay:任务启动后等待多久启动方法
     * fixedDelay：每过多久执行一次任务
     */
    @Scheduled(initialDelay = 1000, fixedDelay = 100 * 1000 * 100000000)
    public void process() {
        Spider.create(new JobProcessor())
                .addUrl(INIT_URL)//初始url地址
                .setScheduler(new QueueScheduler().setDuplicateRemover
                        (new BloomFilterDuplicateRemover(100 * 1000)))//内存 布隆去重
                .thread(1)//线程数
                .addPipeline(mSpringDataPipeline)//保存数据
                .run();

    }
}
