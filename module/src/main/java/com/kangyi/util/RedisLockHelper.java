package com.kangyi.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RedisLockHelper {

    //锁名称
    public static final String LOCK_PREFIX = "redis_lock";
    //加锁失效时间，毫秒
    public static final int LOCK_EXPIRE = 10000; // ms

    @Autowired
    RedisTemplate redisTemplate;


    /**
     *  最终加强分布式锁
     *
     * @param key key值
     * @return 是否获取到
     */
    public boolean lock(String key){
        String lock = LOCK_PREFIX + key;
        // 利用lambda表达式
        return (Boolean) redisTemplate.execute((RedisCallback) connection -> {
//            设置过期时间
            long expireAt = System.currentTimeMillis() + LOCK_EXPIRE + 1;
//            设置原子锁
            Boolean acquire = connection.setNX(lock.getBytes(), String.valueOf(expireAt).getBytes());


            if (acquire) {
//                加锁成功
//                System.out.println("加锁成功");
                return true;
            } else {
                //redis中已存在该锁
                byte[] value = connection.get(lock.getBytes());
                //获取锁查看过期时间
                if (Objects.nonNull(value) && value.length > 0) {
                    //获取当前锁的过期时间
                    long expireTime = Long.parseLong(new String(value));
                    //判断如果过期
                    if (expireTime < System.currentTimeMillis()) {
                        //如果过期就是用getSet进行原子操作更新过期时间
                        byte[] oldValue = connection.getSet(lock.getBytes(), String.valueOf(System.currentTimeMillis() + LOCK_EXPIRE + 1).getBytes());
                        //如果返回值和get返回的值相同说明更新过期时间成功设置新锁成功
                        boolean res = Long.parseLong(new String(oldValue)) == expireTime;
                        //更新锁成功
                        return res;
                    }else{
                        //如果未过期说明锁还在用，获取失败
                        return false;
                    }
                }else{
                    //如果原子变量中存在key并且无返回值则删除该锁重新创建
                    this.delete(key);
                    return this.lock(key);
                }
            }
        });
    }

    /**
     * 删除锁
     *
     * @param key
     */
    public void delete(String key) {
//        System.out.println("删除锁");
        String lock = LOCK_PREFIX + key;
//        System.out.println(res);
         redisTemplate.execute((RedisCallback) connection -> {
            return connection.del(lock.getBytes());
         });
    }
}
