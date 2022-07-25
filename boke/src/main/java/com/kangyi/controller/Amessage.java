package com.kangyi.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kangyi.pojo.Comment;
import com.kangyi.pojo.Guanzhu;
import com.kangyi.pojo.Jiaru;
import com.kangyi.pojo.Order;
import com.kangyi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/message")
//@CrossOrigin(origins = "*",allowCredentials = "true",allowedHeaders = {"X-Custom-Header"},
//        maxAge = 3600L)
public class Amessage {

    @Autowired
    OrderService orderService;

    @Autowired
    GuiJiService guiJiService;

    @Autowired
    HeSuanService heSuanService;

    @Autowired
    YiMiaoService yiMiaoService;

    @Autowired
    GeLiService geLiService;



    @Autowired
    GuanzhuService guanzhuService;

    @Autowired
    JiaruService jiaruService;

    @Autowired
    CommentService commentService;

    @GetMapping
    @PostMapping
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> getMessageList(
//            @RequestBody  Map<String, Object> data,
            @RequestParam(value = "pno",defaultValue = "1") Integer pno,
            @RequestParam(value = "psize",defaultValue = "10") Integer psize,
            @RequestParam(value = "type",defaultValue = "0") Integer type,
            @RequestParam(value = "parentRead",defaultValue = "") Integer parentRead,
            @RequestParam(value = "messageType",defaultValue = "2") Integer messageType,
            @RequestParam(value = "shenQingType",defaultValue = "2") Integer shenQingType,
            @RequestParam(value = "userId",defaultValue = "") Long userId,
            @RequestParam(value = "sortField",defaultValue = "createTime") String sortField,
            @RequestParam(value = "sortType",defaultValue = "desc") String sortType
    ){
//        Map<String, Object> map = new HashMap<>(3);
//        Integer type=(Integer)data.get( "type" );
//        Integer pno=(Integer)data.get( "pno" );
//        Integer psize=(Integer)data.get( "psize" );
//        Integer parentRead=(Integer)data.get( "parentRead" );
//        Integer messageType=(Integer)data.get( "messageType" );
//        Long userId= Long.valueOf( String.valueOf( data.get("userId" )));
//        if (pno == null){
//            pno=1;
//        }
//        if (psize==null){
//            psize=10;
//        }
//        if (type==null){
//            type=0;
//        }
//        String sortField="insertTime";
//        String sortType="desc";
        Map<String, Object> map = new HashMap<String, Object>();
        if (messageType==3) {
            //申请消息3
            if (shenQingType==2) {
                //我发出的申请
                Page<Jiaru> p = PageHelper.startPage( pno, psize );

                List<Jiaru> fromJiaruList = jiaruService.selectManyByStatusUserId( -1, userId, parentRead, sortField, sortType, type );
                map.put( "fromJiaruList" ,fromJiaruList);
                map.put( "count",p.getTotal() );


            }else {
                //别人发出的申请，我来审核
                Page<Jiaru> p = PageHelper.startPage( pno, psize );
                List<Jiaru> toJiaruList = jiaruService.selectManyByStatusToUserId( -1, userId, parentRead, sortField, sortType, type );
                map.put( "toJiaruList", toJiaruList );
                map.put( "count",p.getTotal() );
            }
        }else if (messageType==1) {
            //评论文章1
//        commentService.
            //加入的
            List<Jiaru> okJJiaruList = jiaruService.selectManyByStatusUserId( 2, userId, -1, sortField, sortType, type );
            List<Long> orderIdList = new ArrayList<>(  );
            if (okJJiaruList!=null&&okJJiaruList.size()>0) {
                for (Jiaru jiaru : okJJiaruList) {
                    orderIdList.add( jiaru.getOrderId() );
                }
            }
            //创建的
            List<Order> orders= orderService.selectManyByUserId(userId);
            if (orders!=null&&orders.size()>0) {
                for (Order order : orders) {
                    orderIdList.add( order.getOrderId() );
                }
            }

            Page<Comment> p = PageHelper.startPage( pno, psize );

            //评论
            List<Comment> pingLunCommentList = commentService.getMessageByOrserIdList( orderIdList, parentRead, sortField, sortType, type );
//            ("typeName"+pingLunCommentList.get( 0 ).getTypeName());
//            (pingLunCommentList.get( 0 ));
            map.put( "count",p.getTotal() );
            map.put( "pingLunCommentList" ,pingLunCommentList);

        }else if (messageType==2) {
            //2回复评论
            Page<Comment> p = PageHelper.startPage( pno, psize );

            List<Comment> huifuCommentList = commentService.getMessageByParetId( userId, parentRead, sortField, sortType, type, messageType );
//            ("typeName"+huifuCommentList.get( 0 ).getTypeName());
//            (huifuCommentList.get( 0 ));
            map.put( "count",p.getTotal() );
            map.put( "huifuCommentList" ,huifuCommentList);


        }else if (messageType==0) {
            //0系统
            Page<Comment> p = PageHelper.startPage( pno, psize );

            List<Comment> xiTongCommentList = commentService.getMessageByParetId( userId, parentRead, sortField, sortType, type, messageType );
//            ("typeName"+huifuCommentList.get( 0 ).getTypeName());
//            (huifuCommentList.get( 0 ));
            map.put( "count",p.getTotal() );
            map.put( "xiTongCommentList" ,xiTongCommentList);


        }


//        gLstForPage = orderService.getListForPageByIdList( type, btime, etime, pno, psize, gOrderList, sortField, sortType, "guanzhu" );
//        jListForPage = orderService.getListForPageByIdList( type, btime, etime, pno, psize, jOrderList, sortField, sortType, "jiaru" );





        map.put( "pno",pno );
        map.put( "psize",psize );
        map.put( "type",type );
        map.put( "parentRead",parentRead );
        map.put( "messageType",messageType );
        map.put( "shenQingType",shenQingType );

        return map;


    }



    @GetMapping
    @PostMapping
    @RequestMapping("/jiaruOk")
    @ResponseBody
    public String jiaruOk(
//            @RequestBody  Map<String, Object> data
            @RequestParam(value = "jiaruId",defaultValue = "") Long  jiaruId,
            @RequestParam(value = "pno",defaultValue = "") Long  userId
    ){
//        Long jiaruId = Long.valueOf( String.valueOf( data.get( "jiaruId" )));
//        Long userId = Long.valueOf( String.valueOf( data.get( "userId" )));
        if (jiaruId==null||"null".equals( jiaruId )){
            return "失败";
        }

        Jiaru jiaru = new Jiaru();
        jiaru.setJiaru( (byte)2 );
        int i=jiaruService.updateOne(jiaruId,jiaru);
        if (i<=0){
            return "失败";
        }else {
            return "成功";
        }
    }



    @GetMapping
    @PostMapping
    @RequestMapping("/jiaruNo")
    @ResponseBody
    public String jiaruNo(
//            @RequestBody  Map<String, Object> data
            @RequestParam(value = "jiaruId",defaultValue = "") Long  jiaruId,
            @RequestParam(value = "userId",defaultValue = "") Long  userId,
            @RequestParam(value = "adminRemark",defaultValue = "") String  adminRemark

            ){
//        Long jiaruId = Long.valueOf( String.valueOf( data.get( "jiaruId" )));
//        Long userId = Long.valueOf( String.valueOf( data.get( "userId")) );
//        String adminRemark = (String)data.get( "adminRemark" );
        if (jiaruId==null||"null".equals( jiaruId )){
            return "失败";
        }

        Jiaru jiaru = new Jiaru();
        jiaru.setJiaru( (byte)0 );
        jiaru.setAdminRemark( adminRemark );
        int i=jiaruService.updateOne(jiaruId,jiaru);
        if (i<=0){
            return "失败";
        }else {
            return "成功";
        }
    }

    @GetMapping
    @PostMapping
    @RequestMapping("/read")
    @ResponseBody
    public String updataComment(
//            @RequestBody  Map<String, Object> data
            @RequestParam(value = "jiaruId",defaultValue = "") Long  jiaruId,
            @RequestParam(value = "commentId",defaultValue = "") Long  commentId,
            @RequestParam(value = "userId",defaultValue = "") Long  userId,
            @RequestParam(value = "messageType",defaultValue = "") Integer  messageType

            ){
//        Long commentId = Long.valueOf( String.valueOf( data.get( "commentId")) );
//        Long jiaruId = Long.valueOf( String.valueOf( data.get( "jiaruId")) );
//        Long userId = Long.valueOf( String.valueOf( data.get( "userId" )));
//        Integer messageType = (Integer)data.get( "messageType" );
        if (userId==null||"null".equals( userId )){
            return "失败,userId失败";
        }

        int i=0;
        if (messageType==3){
            //jiaru的已读
            Jiaru jiaru = new Jiaru();
            jiaru.setJiaruId( jiaruId );
            jiaru.setParentRead( String.valueOf( 1 ) );
             i = jiaruService.updateOne( jiaruId, jiaru );

        }else if(messageType<3&&messageType>0){
            //0系统消息和1评论和2回复
            Comment comment = new Comment();
            comment.setParentRead( String.valueOf( 1 ) );
            comment.setCommentId( commentId );
            i=commentService.updataOneById(commentId,comment);

        }
//        int i=commentService.delectOne(commentId);
        if (i<=0){
            return "失败,sql";
        }else {
            return "成功";
        }
    }

    @GetMapping
    @PostMapping
    @RequestMapping("/jiaru")
    @ResponseBody
    public String jiaruBoke(
            @RequestBody  Map<String, Object> data
    ){
//        String content=(String)data.get( "content" );
//        Integer pno=(Integer)data.get( "parentId" );
//        Integer psize=(Integer)data.get( "psize" );
//        Long orderId= (Long) data.get( "orderId" );
//        Long parentId= (Long) data.get( "parentId" );
//        Long sendId= (Long) data.get( "sendId" );

        JSONObject.DEFFAULT_DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
        String st = JSONObject.toJSONString( data );
        Jiaru jiaru= JSON.parseObject( st, Jiaru.class);
        if (jiaru==null||"null".equals( jiaru )){
            return "失败";
        }
        int i=jiaruService.insertOne(jiaru);
        if (i<=0){
            return "失败";
        }else {
            return "成功";
        }
    }

    @GetMapping
    @PostMapping
    @RequestMapping("/guanzhu")
    @ResponseBody
    public String guanzhuBoke(
            @RequestBody  Map<String, Object> data
    ){
//        String content=(String)data.get( "content" );
//        Integer pno=(Integer)data.get( "parentId" );
//        Integer psize=(Integer)data.get( "psize" );
//        Long orderId= (Long) data.get( "orderId" );
//        Long parentId= (Long) data.get( "parentId" );
//        Long sendId= (Long) data.get( "sendId" );

        JSONObject.DEFFAULT_DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
        String st = JSONObject.toJSONString( data );
        Guanzhu guanzhu= JSON.parseObject( st, Guanzhu.class);
        if (guanzhu==null||"null".equals( guanzhu )){
            return "失败";
        }
        int i=guanzhuService.insertOne(guanzhu);
        if (i<=0){
            return "失败";
        }else {
            return "成功";
        }
    }

}
