package RunSpider;

import PornHub.PornHub;
import hunantv.HunanSpider;
import porn91.Porn91;
import sohu.SohuSpider;
import tools.DownloadVideo;
import tools.OutToFile;
import tools.UserInfo;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.management.JMException;
import java.util.List;

/**
 * Created by tailong on 2015/6/11.
 */
public class RunSpider {
    private static PageProcessor platform = null;

    public static void main(String[] args) throws JMException {
        UserInfo info = new UserInfo();
        Spider video = Spider.create(RunSpider.getPlatform(info.getUserInfo()))
                .addUrl(info.Urls().toArray(new String[info.Urls().size()]))
                .addPipeline(new OutToFile(info.getFilePath()))
                .addPipeline(new DownloadVideo(info.getFilePath()));
        SpiderMonitor.instance().register(video);
        video.run();
    }

    private static PageProcessor getPlatform(List<String> data) {
        if (data.get(0).equals("sohu")) {
            platform = new SohuSpider();
        } else if (data.get(0).equals("hunantv")) {
            platform = new HunanSpider();
        } else if (data.get(0).equals("pornhub")) {
            platform = new PornHub();
        } else if (data.get(0).equals("91")){
            platform = new Porn91();
        }else {
            System.exit(-1);
        }
        return platform;
    }


}
