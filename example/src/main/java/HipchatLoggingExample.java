import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HipchatLoggingExample {

    private static final Logger LOG = LoggerFactory.getLogger(HipchatLoggingExample.class);

    public static void main(String[] args) {
        System.out.println("In main");
        LOG.error("Start tracking errors in Hipchat!");
    }
}
