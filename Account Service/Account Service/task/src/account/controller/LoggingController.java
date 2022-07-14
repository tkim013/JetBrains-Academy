package account.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingController.class);

    public void log() {
        LOGGER.info("This is an INFO level log message!");
    }
}