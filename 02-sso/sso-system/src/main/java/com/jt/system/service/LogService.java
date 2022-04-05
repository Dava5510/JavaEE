package com.jt.system.service;

import com.jt.system.pojo.Log;

public interface LogService {
    /**
     * 向表中记录用户行为日志
     * @param log
     */
    void insertLog(Log log);
}
