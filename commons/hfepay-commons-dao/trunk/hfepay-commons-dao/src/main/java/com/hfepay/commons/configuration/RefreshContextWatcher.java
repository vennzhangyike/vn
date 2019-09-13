package com.hfepay.commons.configuration;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;

import com.hfepay.commons.configuration.ZookeeperResource.ReloadContext;



/**
 * ClassName: RefreshContextWatcher <br/>
 * Reason: 容器刷新监听器. <br/>
 * date: 2013-11-25 上午09:09:28 <br/>
 *
 * @author XieJunfeng
 * @version 
 * @since JDK 1.6
 */
public class RefreshContextWatcher implements Watcher {

    private static Logger log = LoggerFactory.getLogger(RefreshContextWatcher.class);

    private AbstractApplicationContext ctx;
    private boolean regressionZnodes;
    private ReloadContext reloadContext;

    public RefreshContextWatcher(AbstractApplicationContext ctx, boolean regressionZnodes, ReloadContext reloadContext) {
        this.ctx = ctx;
        this.reloadContext = reloadContext;
    }

    public void process(WatchedEvent event) {
        switch (event.getType()) {
        case NodeChildrenChanged:
            if (!regressionZnodes) {
                break;
            }
        case NodeDataChanged:
            log.info("Detected ZNode or sub ZNode changed.");
            switch (reloadContext) {
            case AUTO:
                log.info("Refresh spring context.");
                ctx.refresh();
                break;
            case HOLD:
                log.info("Keep context unchange according to configuration.");
                break;
            }
            break;
        case NodeDeleted:
            log.warn("Warnning! ZK Node for application config has been removed!");
            break;
        default:
            log.info("Zk Node changed, type" + event.getType() + " Stat:" + event.getState() + ".");
            break;
        }
    }
}
