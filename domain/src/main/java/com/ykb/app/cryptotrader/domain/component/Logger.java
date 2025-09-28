package com.ykb.app.cryptotrader.domain.component;

import com.ykb.app.cryptotrader.data.dao.ErrorLogDao;
import com.ykb.app.cryptotrader.data.model.ErrorLog;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Logger {

    private final org.slf4j.Logger log;

    @Autowired
    private ErrorLogDao errorLogDao;

    private Logger(Class<?> clazz) {
        log = LoggerFactory.getLogger(clazz);
    }

    public static Logger get(Class<?> clazz) {
        return new Logger(clazz);
    }

    public void info(String msg) {
        log.info(msg);
    }

    public void error(String msg) {
        log.error(msg);
        errorLogDao.save(new ErrorLog(msg));
    }

    public void error(String msg, Throwable t) {
        log.error(msg, t);
        if(t instanceof Exception)
            errorLogDao.save(new ErrorLog(msg, (Exception) t));
    }

    public void error(Throwable t) {
        log.error(t.getMessage(), t);
        if(t instanceof Exception)
            errorLogDao.save(new ErrorLog((Exception) t));
    }

}
