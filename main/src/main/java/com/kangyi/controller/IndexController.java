package com.kangyi.controller;

import com.kangyi.mapper.UserMapper;
import com.kangyi.pojo.Menu;
import com.kangyi.pojo.User;
import com.kangyi.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.kangyi.constant.Constant.YOUKEID;

//@RequestMapping("/a")
@Controller
public class IndexController {



        @Autowired
    private UserMapper userMapper;
//
//
//    @GetMapping//查
//    public List<User> getList() {
//        UserExample userEx = new UserExample();
//        UserExample.Criteria criteria = userEx.createCriteria();
//        criteria.andUsernameLike("admin");
//        List<User> list = userMapper.selectByExample(userEx);
//        return list;
//    }


    @Autowired
    private MenuService menuService;

//    @Controller(urlPatterns = "/booksServlet",loadOnStartup = 1)
//    public class BooksServlet extends HttpServlet {
//
//        @Override
//        public void init(ServletConfig config) throws ServletException {
//            //在执行一次,须先加载数据
//            Pager<Books> pager = booksService.getBooksByPriceWithLimit(null, null, 1 ,Constants.PAGE_SIZE);
//
//            //ServletContext
//            ServletContext sc = config.getServletContext();
//
//            sc.setAttribute("pager",pager);
//        }


    @RequestMapping(value = {"/","/index"})
    public String index(
            HttpSession session1, Model model

    ){


        User user = (User) session1.getAttribute("userInfo");

        Long roleId ;
        if (user!=null){
            roleId=user.getRoleId();
        }else {roleId = YOUKEID;}

        List<Menu> menuList = menuService.selectMenuListByRoleId(roleId);
        session1.setAttribute("menuList",menuList);
        return "jsp/index";
    }
    @RequestMapping("/erro")
    public String error(){
        return "jsp/error";
    }
}
