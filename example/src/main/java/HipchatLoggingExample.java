import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HipchatLoggingExample {

    private static final Logger LOG = LoggerFactory.getLogger(HipchatLoggingExample.class);

    public static void main(String[] args) throws InterruptedException {
        LOG.error("Start tracking errors in Hipchat!");
        Thread.sleep(10000);
        for (int i = 0; i < 50; i++) {
            LOG.error("log again! " + i);
        }
        Thread.sleep(10000);
        for (int i = 0; i < 50; i++) {
            LOG.error("and again! " + i);
        }
        Thread.sleep(10000);
        LOG.error("Done");
        Thread.sleep(10000);
    }
}
