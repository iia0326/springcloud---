package com.shop.service.log.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.service.log.entity.Result;
import com.shop.service.log.service.BaseService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class BaseServiceImpl implements BaseService {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public JSONObject setPageResult(List list, Page page) {
        Object listJson = JSONObject.toJSON(list);

        JSONObject pageJson = new JSONObject();
        pageJson.put("pno",page.getCurrent());
        pageJson.put("psize",page.getSize());
        pageJson.put("pCount",page.getPages());
        pageJson.put("totalElements",page.getTotal());
        JSONObject data = new JSONObject();
        data.put("list",listJson);
        data.put("page",pageJson);
        return data;
    }

    @Override
    public Result findById(Long id, String templateMethod) {
        Object res = sqlSessionTemplate.selectOne(templateMethod,id);
        return Result.end(200,res,"查询成功");
    }

    @Override
    public Result insertOne(Object obj, String templaetMethod) {
        int res = sqlSessionTemplate.insert(templaetMethod,obj);
        if(res>0){
            return Result.end(200,"","新增成功");
        }else{
            return Result.end(500,"","新增失败");
        }

    }

    @Override
    public Result updateOne(Object obj, String templateMethod) {
        int res = sqlSessionTemplate.update(templateMethod,obj);
        if(res>0){
            return Result.end(200,"","修改成功");
        }else{
            return Result.end(500,"","修改失败");
        }
    }

    @Override
    public Result deleteById(Long id, String templateMethod) {
        int res = sqlSessionTemplate.delete(templateMethod,id);
        if(res>0){
            return Result.end(200,"","删除成功");
        }else{
            return Result.end(500,"","删除失败");
        }
    }

    @Override
    public Result updateByMapper(Object object, Class MapperClass) {

            SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
            Object mapper = sqlSession.getMapper(MapperClass);
            Class<?> mapperClass = mapper.getClass();
             Method[] methods = mapperClass.getDeclaredMethods();
             Arrays.stream(methods).forEach(item -> {
                 if(item.getName().equals("updateById")){
                     try {
                         item.invoke(mapper,object);
                     } catch (IllegalAccessException e) {
                         e.printStackTrace();
                     } catch (InvocationTargetException e) {
                         e.printStackTrace();
                     }
                 }
             });
            sqlSession.commit();
            sqlSession.close();
            return Result.end(200,"","修改成功");

    }

    @Override
    public Result getListForPage(Page page, QueryWrapper q, Class MapperClass) {
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        Object mapper = sqlSession.getMapper(MapperClass);
        Class<?> mapperClass = mapper.getClass();
        Method[] methods = mapperClass.getDeclaredMethods();
        AtomicReference<Method> m = null;
        AtomicReference<JSONObject> data = new AtomicReference<>(new JSONObject());
        Arrays.stream(methods).forEach(item -> {
            if(item.getName().equals("selectPage")){
                try {
                    Page p = (Page)item.invoke(mapper,page,q);
                    data.set(this.setPageResult(p.getRecords(), p));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });


        sqlSession.commit();
        sqlSession.close();
        return Result.end(200,data.get(),"查询成功");

    }


}
