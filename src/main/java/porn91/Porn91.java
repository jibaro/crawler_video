package porn91;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.logging.Logger;

/**
 * Created by yongqiang on 2015/2/1.
 */
public class Porn91 implements PageProcessor {
    public Logger log = Logger.getLogger(this.getClass().getName());
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000)
            .addCookie("91p.vido.ws", "91username", "sex462")
            .addCookie("91p.vido.ws", "DUID", "34bcWG7bidS6eN4v7aQoeN1czALcGNRq0brIWw5S%2B52562aE")
            .addCookie("91p.vido.ws", "EMAILVERIFIED", "no")
            .addCookie("91p.vido.ws", "USERNAME", "32984irY6nkvTf9L2QxEZSa5ApSJYyGEV91Qbe2DGq1t2e8")
            .addCookie("91p.vido.ws", "remainclosed", "1")
            .addCookie("91p.vido.ws", "user_level", "6")
            .addCookie("91p.vido.ws", "watch_times", "5")
            .addCookie("91p.vido.ws", "AJSTAT_ok_pages", "100")
            .addCookie("91p.vido.ws", "AJSTAT_ok_times", "1")
            .addCookie("91p.vido.ws", "CLIPSHARE", "1u96je6tu4ov3qomri77t8htt0")
            ;
    String listUrl = "http://91p\\.vido\\.ws/v\\.php\\w*";
    String videoUrl = "http://91p\\.vido\\.ws/view_video\\.php\\w*";
    String downloadUrl="http://91p\\.vido\\.ws/getfile\\.php\\w*";

    @Override
    public void process(Page page) {
        System.out.println(page.getUrl().toString());
        if (page.getUrl().regex(listUrl).match()) {
            page.addTargetRequests(page.getHtml().xpath("//div[@id='paging']//a/@href").all());
            page.addTargetRequests(page.getHtml().xpath("//div[@class='imagechannel']/a/@href").all());
        }
        if(page.getUrl().regex(videoUrl).match()){
            String name=null;
            String VID=null;
            String seccode=null;
            String max_vid=null;
            String other="&mp4=1";
            Html currentHtml=page.getHtml();
            name=currentHtml.xpath("//div[@id='viewvideo-title']/text()").toString();
            VID=currentHtml.regex("\\w*file\',\'(\\d*)\'").toString();
            seccode=currentHtml.regex("\\w*seccode\\',\\'(\\w*)\'").toString();
            max_vid=currentHtml.regex("\\w*max_vid\',\'(\\d*)\'").toString();
            if(!VID.equals("")){
                page.putField(name,VID);
                page.addTargetRequest("http://91p.vido.ws/getfile.php?VID="+VID+"&seccode="+seccode+"&max_vid="+max_vid+other);
            }else{
                log.info("没有获取到视频ID"+page.getUrl());
            }
        }
        if (page.getUrl().regex(downloadUrl).match()){
            page.putField(page.getUrl().regex("\\w*VID=(\\d*)").toString(),page.getHtml().xpath("//body/text()").toString().split("&")[0].split("=")[1]);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

}
