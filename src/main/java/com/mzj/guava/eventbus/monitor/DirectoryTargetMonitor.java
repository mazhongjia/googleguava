package com.mzj.guava.eventbus.monitor;

import com.google.common.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.*;

public class DirectoryTargetMonitor implements TargetMonitor {

    private final static Logger LOGGER = LoggerFactory.getLogger(DirectoryTargetMonitor.class);

    private WatchService watchService;

    private final EventBus eventBus;

    private final Path path;

    private volatile boolean start = false;

    public DirectoryTargetMonitor(final EventBus eventBus, final String targetPath) {
        this(eventBus, targetPath, "");
    }

    public DirectoryTargetMonitor(final EventBus eventBus, final String targetPath, final String... morePaths) {
        this.eventBus = eventBus;
        this.path = Paths.get(targetPath, morePaths);
    }

    @Override
    public void startMonitor() throws Exception {
        //1、创建WatchService
        this.watchService = FileSystems.getDefault().newWatchService();
        //2、把watchService注册给Path
        this.path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_CREATE);
        LOGGER.info("The directory [{}] is monitoring...", path);

        this.start = true;
        while (start) {
            WatchKey watchKey = null;
            //有事件到来时，阻塞获取

            try {
                //3、等待事件产生：启动监听后，调用线程阻塞等待monitor监听的事件产生
                watchKey = watchService.take();
                //4、可能一次产生多个事件
                watchKey.pollEvents().forEach(watchEvent -> {
                    //5、处理多个产生的事件
                    //获取事件类型
                    WatchEvent.Kind<?> kind = watchEvent.kind();
                    //获取事件文件路径，这里获取到的是相对路径
                    Path path = (Path) watchEvent.context();

                    /**
                     * Path接口中resolve方法的作用相当于把当前路径当成父目录，而把参数中的路径当成子目录或是其中的文件，进行解析之后得到一个新路径；
                     *       Path path1 = Paths.get("folder1", "sub1");
                     *     Path path2 = Paths.get("folder2", "sub2");
                     *     path1.resolve(path2); //folder1\sub1\folder2\sub2
                     *
                     */
                    Path child = DirectoryTargetMonitor.this.path.resolve(path);//拼接成绝对路径
                    //当monitor监听到fileChange时，向eventBus发送个事件
                    eventBus.post(new FileChangeEvent(child, kind));
                });
            } catch (Exception e) {
                //如遇异常，则停止monitor
                this.start = false;
            } finally {
                //watchKey用完后，一定要进行reset否则下次事件不会监听到
                if (watchKey != null) {
                    watchKey.reset();
                }
            }

        }
    }

    @Override
    public void stopMonitor() throws Exception {
        LOGGER.info("The directory [{}] monitor will be stop....", path);
        this.start = false;
        this.watchService.close();
        LOGGER.info("The directory [{}] monitor will be stop done.", path);
    }
}
