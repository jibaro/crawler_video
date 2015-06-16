package RunSpider;

import Download.OutputUlr;
import PornHub.PornHub;
import hunantv.HunanSpider;
import porn91.Porn91;
import sohu.SohuSpider;
import Download.OutputVideo;
import tools.UserInfo;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.management.JMException;

/**
 * Created by tailong on 2015/6/11.
 */
public class RunSpider {
    private static PageProcessor platform = null;
    public static Spider video = null;
//    .addUrl("http://91porn.com/v.php?next=watch&page=2")
    public static void main(String[] args) throws JMException {
        UserInfo info = new UserInfo();
        if (args.length == 0) {
            video = Spider.create(RunSpider.getPlatform(info.getUserInfo().get(0)))
                    .addUrl(info.Urls().toArray(new String[info.Urls().size()]))
                    .addPipeline(new OutputUlr(info.getFilePath()));
//                .addPipeline(new OutputVideo(info.getFilePath()));
            SpiderMonitor.instance().register(video);
            video.thread(5).run();
        } else {
            video = Spider.create(RunSpider.getPlatform(args[0]))
                    .addUrl(args)
                    .addPipeline(new OutputUlr("/var/www/url/"))
                    .addPipeline(new OutputVideo(info.getFilePath()));
            SpiderMonitor.instance().register(video);
            video.run();
        }
    }

    private static PageProcessor getPlatform(String data) {
        if (data.contains("sohu")) {
            platform = new SohuSpider();
        } else if (data.contains("hunantv")) {
            platform = new HunanSpider();
        } else if (data.contains("pornhub")) {
            platform = new PornHub();
        } else if (data.contains("91porn")) {
            platform = new Porn91();
        } else {
            System.exit(-1);
        }
        return platform;
    }


}
