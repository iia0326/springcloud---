package com.kangyi.service.impl;

//import com.shop.service.log.service.LogService;
import com.kangyi.entity.LogEntity;
import com.kangyi.entity.Result;
//import com.kangyi.mapper.LogMapper;
import com.kangyi.mapper.LogMapper2;
import com.kangyi.service.LogService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl2 extends BaseServiceImpl implements LogService2 {
    @Autowired
    private LogMapper2 logMapper2;

    @Override
    public Result insertLog(LogEntity logEntity) {

        int updateCount = logMapper2.insert(logEntity);
        if(updateCount>0){
            return Result.end(200,"","本地插入日志成功");
        }else{
            return Result.end(500,"","本地插入日志失败");
        }
    }
}
