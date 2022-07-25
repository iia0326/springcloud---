package com.kangyi.service;

import com.kangyi.entity.LogEntity;
import com.kangyi.entity.Result;

public interface LogService2 extends BaseService {
    Result insertLog(LogEntity logEntity);
}
