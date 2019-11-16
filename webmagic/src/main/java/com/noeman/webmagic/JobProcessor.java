package com.noeman.webmagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

//<body>
//<!--shortcut start-->
//<div id="shortcut-2014">
//<div class="w">
//<ul class="fl">
//<li id="ttbar-home"><i class="iconfont">&#xe608;</i><a href="//www.jd.com/" target="_blank">京东首页</a></li>
//<li class="dorpdown" id="ttbar-mycity"></li>
//</ul>
//<ul class="fr">
//<li class="fore1" id="ttbar-login">
//<a href="javascript:login();" class="link-login">你好，请登录</a>&nbsp;&nbsp;<a href="javascript:regist();" class="link-regist style-red">免费注册</a>
//</li>
//<li class="spacer"></li>
//<li class="fore2">
//<div class="dt">
//<a target="_blank" href="//order.jd.com/center/list.action">我的订单</a>
//</div>
//</li>
//<li class="spacer"></li>
//<li class="fore3 dorpdown" id="ttbar-myjd">
//<div class="dt cw-icon">
//<a target="_blank" href="//home.jd.com/">我的京东</a><i class="iconfont">&#xe605;</i>
//</div>
//<div class="dd dorpdown-layer"></div>
//</li>
//<li class="spacer"></li>
//<li class="fore4">
//<div class="dt">
//<a target="_blank" href="//vip.jd.com/">京东会员</a>
//</div>
//</li>
//<li class="spacer"></li>
//<li class="fore5">
//<div class="dt">
//<a target="_blank" href="//b.jd.com/">企业采购</a>
//</div>
//</li>
//<li class="spacer"></li>
//<li class="fore6 dorpdown" id="ttbar-serv">
//<div class="dt cw-icon">
//        客户服务<i class="iconfont">&#xe605;</i>
//</div>
//<div class="dd dorpdown-layer"></div>
//</li>
//<li class="spacer"></li>
//<li class="fore7 dorpdown" id="ttbar-navs">
//<div class="dt cw-icon">
//        网站导航<i class="iconfont">&#xe605;</i>
//</div>
//<div class="dd dorpdown-layer"></div>
//</li>
//<li class="spacer"></li>
//<li class="fore8 dorpdown" id="ttbar-apps">
//<div class="dt cw-icon">
//<a target="_blank" href="//app.jd.com/">手机京东</a>
//</div>
//</li>
//</ul>
//<span class="clr"></span>
//</div>
//</div>
//<div id="o-header-2013"><div id="header-2013" style="display:none;"></div></div>
//<!--shortcut end-->
//<!-- S 主体内容 -->
//<div class="mod_conatiner">
//<script>
//    (function(window){
//            window.data = window.data || {};
//            window.data['cms_header'] = {
//            setting: [{"NAME":"家用电器","URL":"//","ANCHOR":"","property":"1662","CUSTOM1":"#dd4545","CUSTOM2":"","CUSTOM3":""}]
//            };
//            })(window);
//</script>
//<div class="o2data-lazyload cms_header" style="min-height:100px" id="cms_header" data-id="cms_header" data-tpl="//static.360buyimg.com/mtd/pc/cms/floors/cms_header_tpl.min.js"></div>
//<div id="root" class="o2cms"></div>
//</div>
//<!-- E 主体内容 -->
//<div class="o2data-lazyload" id="cmsfooter" data-tpl="//static.360buyimg.com/mtd/pc/cms/js/cmsfooter_tpl.min.js" data-script="seajs.use('//misc.360buyimg.com/??jdf/1.0.0/unit/global-footer/1.0.0/global-footer.css,jdf/1.0.0/unit/service/1.0.0/service.css')"></div>
//<script type="text/javascript">
//        if (!!window.ActiveXObject || "ActiveXObject" in window) {
//        document.write('<script src="//storage.360buyimg.com/o2cms/lib/polyfill.1.0.0.js"><\/script>')
//        }
//</script>
//<script type="text/javascript" src="//static.360buyimg.com/mtd/pc/base/1.0.0/base.js" charset="UTF-8"></script>
//<script type="text/javascript">
//        window.pageConfig = window.pageConfig || {};
//        window.pageConfig.o2JSConfig = {
//        useTplInJs: true,
//        pathRule: function (path) {
//        return '//static.360buyimg.com/mtd/pc/cms' + '/floors/' + path + '.min.js';
//        }
//        };
//        seajs.use(['//static.360buyimg.com/mtd/pc/base/1.0.1/channel.js']);
//        seajs.use(['//wl.jd.com/wl.js']);
//</script>
//<script>
//    /* window.o2PageConfig = {
//      pageid: '42114',
//      data:
//    } */
//</script>
//<script type="text/javascript" src="//storage.360buyimg.com/portalstatic/static/pc.config.c7792575.js" charset="UTF-8"></script>
//<script type="text/javascript" src="//storage.360buyimg.com/portalstatic/static/vendor.dll.04633498.js" charset="UTF-8"></script>
//<script type="text/javascript" src="//static.360buyimg.com/webstatic/getprice/1.0.0/js/price.min.js"></script>
//<script type="text/javascript" src="//storage.360buyimg.com/portalstatic/static/pc.main.2fe46652.js" charset="UTF-8"></script></body>
public class JobProcessor implements PageProcessor {
    /**
     * 解析页面
     */
    public void process(Page page) {
        //抽取元素
        //1、css选择器 解析page 返回的数据，把解析得到的数据放到ResultItems里面
//        List<String> all = page.getHtml().css("div.dt a").all();
//        page.putField("div",all);

        //2、XPath语法  w3c有教程 xml解析语言
//        page.putField("div2",page.getHtml().xpath("//div[@id=shortcut-2014]/div/ul/li[@id=ttbar-login]/a"));

        //3、正则表达式
        List<String> regex = page.getHtml().css("div a")
                .regex(".*京东.*")//正则
                .all();
        page.putField("regex", regex);

        //获取链接,然后抓取该链接
        page.addTargetRequest(page.getHtml().css("div.dt a").links().get());
        page.putField("div2", page.getHtml().css("title"));
    }

    /** 配置抓取间隔，重试。。 */
    private Site site = Site.me()
            .setCharset("utf-8")//编码
            .setTimeOut(1000)//超时时间
            .setRetrySleepTime(1000)//重试间隔时间
            .setRetryTimes(2)//重试次数
            ;


    public Site getSite() {
        return site;
    }

    //执行爬虫
    public static void main(String[] args) {
        //Spider 组合组件
        Spider spider = Spider.create(new JobProcessor())
                .addUrl("https://jiadian.jd.com/")//设置爬取数据的url
                .addPipeline(new FilePipeline("/Users/v_maqinglong/Desktop/05.WebMagic"))//输出成文件
                .thread(2);//线程数
        spider.run();//执行
    }
}
