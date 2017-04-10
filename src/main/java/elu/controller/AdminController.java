package elu.controller;

import elu.model.Car;
import elu.model.User;
import elu.model.UserLicence;
import elu.service.UserService;
import elu.util.RRUtil;
import elu.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ch on 17/4/6.
 */
@Controller
@RequestMapping(value = "admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "queryUserCarLicence", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String queryUserCarLicence(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> resMap= RRUtil.getStandardMap();
//        Map<String,Object> map=RRUtil.para2Map(request);
        String type = request.getParameter("type");
        String status = request.getParameter("status");
        Map<String,Object> queryMap=new HashMap<String,Object>();
        queryMap.put("status",status);
        if(type.equals("user")){
            List<UserLicence> list = userService.queryUserLicenceByMap(queryMap);
            resMap.put("list",list);
        }else{
            List<Car> list = userService.queryCarLicenceByMap(queryMap);
            resMap.put("list",list);
        }
        return RRUtil.getJsonRes(request,resMap);

    }

    @RequestMapping(value = "checkCarLicence", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String checkCarLicence(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> resMap=RRUtil.getStandardMap();

        Map<String,Object> map=RRUtil.para2LimitMap(request);
//        String uid=(String)request.getSession().getAttribute("uid");
        String uid=request.getParameter("uid");
        String status = request.getParameter("status");
        String remark = (String)map.get("remark");

        User user=userService.queryUserByUId(uid);
        Integer id = user.getId();
        UserLicence userLicence = userService.queryUserLicenceByUId(id);
        Car car = userService.queryCarByUId(id);

        Car carStatus = new Car();
        carStatus.setStatus(Integer.parseInt(status));
        carStatus.setId(car.getId());
        carStatus.setRemark(remark);
        userService.updateCar(carStatus);

        UserLicence licence = new UserLicence();
        licence.setId(userLicence.getId());
        licence.setStatus(Integer.parseInt(status));
        licence.setRemark(remark);
        userService.updateUserLicence(licence);

        resMap.put("retCode", "200");
        resMap.put("retMsg", "审核成功！");

        return RRUtil.getJsonRes(request,resMap);
    }
}
