package con.norman.crawler;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.net.URL;
import java.util.Set;

/**
 * html解析器
 * 1、从URL，文件或字符串中解析HTML
 * 2、使用Dom或CSS徐阿泽漆来查找、取出数据
 * 3、可操作HTML元素、属性、文本
 */
public class JsoupHelper {
    public static final String filePath = "/Users/v_maqinglong/Documents/IdeaProjects/crawler/basis/src/main/resources/html.txt";

    /**
     * 解析URL地址
     */
    void parseUrl() throws Exception {
        Document doc = Jsoup.parse(new URL("http://www.itcast.cn/"), 1000);
        //使用标签选择器，获取title标签内容
        String title = doc.getElementsByTag("title").first().text();
        System.out.println(title);

    }

    /**
     * 解析字符串
     */
    void parseText() throws Exception {
        File file = new File(filePath);
        String content = FileUtils.readFileToString(file, "utf-8");
        Document doc = Jsoup.parse(content);
        String title = doc.getElementsByTag("title").first().text();
        System.out.println(title);
    }

    /**
     * 解析文件
     */
    void parseFile() throws Exception {
        File file = new File(filePath);
        Document doc = Jsoup.parse(file, "utf-8");
        String title = doc.getElementsByTag("title").first().text();
        System.out.println(title);
    }

    /**
     * dom解析文档
     */
    void parseDom() throws Exception {
        File file = new File(filePath);
        Document doc = Jsoup.parse(file, "utf-8");
        //1、根据id查询元素getElementsById
        Element city_bj = doc.getElementById("webimclosebutton");
        System.out.println("获取到的元素内容：" + city_bj.text());
        //2、根据标签获取元素getElementsByTag
        Element span = doc.getElementsByTag("span").first();
        System.out.println("获取到的元素内容：" + span.text());
        //3、根据class获取元素getElementsByClass
        Element salaryp = doc.getElementsByClass("salaryp").first();
        System.out.println("获取到的元素内容：" + salaryp.text());
        //4、根据属性获取元素getElementsByAttribute
        Element src = doc.getElementsByAttribute("href").first();
        System.out.println("获取到的元素内容：" + src.text());
        //5、根据属性&值获取元素getElementsByAttribute
        Element href = doc.getElementsByAttributeValue("href", "http://www.itcast.cn/news/20180804/16445124229.shtml").first();
        System.out.println("获取到的元素内容：" + href.text());
    }

    /**
     * 获取元素中的数据
     */
    void parseElement() throws Exception {
        File file = new File(filePath);
        Document doc = Jsoup.parse(file, "utf-8");
        //5、根据属性&值获取元素getElementsByAttribute
        Element element = doc.getElementsByAttributeValue("href", "http://www.itcast.cn/news/20180804/16445124229.shtml").first();
        //1、获取元素中的数据
        String id = element.id();
        System.out.println("获取到的元素id：" + id);
        //2、获取元素中className
        String className = element.className();
        Set<String> strings = element.classNames();
        System.out.println("获取到的元素 className：" + className);
        //3、从元素中获取所以属性的Attributes
        Attributes attributes = element.attributes();
        //根据属性名字获取属性的值
        String href = element.attr("href");
        System.out.println("获取到的元素 attributes：" + href);
        //4、从元素中获取文本内容text
        String text = element.text();
        System.out.println("获取到的元素 text：" + text);
    }

    /**
     * 使用选择器获取元素
     */
    void parseElementWithSelector() throws Exception {
        File file = new File(filePath);
        Document doc = Jsoup.parse(file, "utf-8");
        //1、tag_name 通过标签查找元素，比如span
        Elements spans = doc.select("span");
        for (Element span : spans) {
//            System.out.println("获取到的元素 span：" +span);
        }
        //2、#id：通过ID查找元素，比如#city_bj
        Element first = doc.select("#city_bj").first();
        //3、#class:通过class名称查找元素，比如: .class_a
        Elements a_gd = doc.select("salaryh2");
        for (Element element : a_gd) {
            System.out.println("获取到的元素  element.text()：" + element.text());

        }
        //4、[attribute]:利用属性查找元素，比如：【abc】
        Elements select = doc.select("[abc]");
        //5、利用属性值来查找元素，比如[class=s_name]
        Elements select1 = doc.select("[class=s_name]");
    }
    /** 使用组合选择器获取元素 */
    void parseElementWithSelectors() throws Exception{
        File file = new File(filePath);
        Document doc = Jsoup.parse(file, "utf-8");
        //el#id:元素+ID，比如：h3#city_bj
        Element first = doc.select("h3#city_bj").first();
        //el.class:元素+class ，比如： li.class_a
        Element first1 = doc.select("li.class_a").first();
        //el[attr]:元素+属性名，比如： span[abc]
        Element first2 = doc.select("span[abc]").first();
        //任意组合：比如：span[abc].s_name
        Element first3 = doc.select("span[abc].s_name").first();
        //ancestor child:查找某个元素下子元素，比如：.city_con li查找"city_con"下的所有li
        Elements select = doc.select(".city_con li");
        //parent > child :查找某个父元素下的直接子元素，比如：.city_con > ul > li查找city_con第一级（直接子元素）的ul，再找所有ul下的第一级li
        Elements select1 = doc.select(".city_con  > ul > li");
        //parent > * ：查找某个父元素下所有直接子元素
        Elements select2 = doc.select(".city_con > ul > *");
    }
}
