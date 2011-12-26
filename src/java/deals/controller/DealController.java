/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deals.controller;

import deals.service.DealService;
import deals.command.CommandList;
import deals.command.CommandObject;
import deals.dao.DealDao;
import deals.entity.Deals;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.htmlcleaner.XPatherException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author SAMAR
 */
 @Controller
public class DealController extends MultiActionController {

    @Autowired
    private DealDao dealDao;
    @Autowired
    private DealService dealService;

    public void setDealDao(DealDao dealDao) {
        this.dealDao = dealDao;
    }

    public void setDealService(DealService dealService) {
        this.dealService = dealService;
    }
    

    @RequestMapping("dealHeader.htm")
    public ModelAndView dealHeader(HttpServletRequest request, HttpServletResponse response) throws IOException, XPatherException, ParseException {
        //String FEBestDeal = request.getParameter("FEBestDeal");
        ModelAndView mv = new ModelAndView("dealHeader");
        
        dealService.setDeal();
        return mv;
    }

    
    /*
     * link to search deals by city parameters cityId if cityId is null of empty cet cairo default city
     */

    @RequestMapping("searchdeals.htm")
    public 
    @ResponseBody
    Object searchdeals(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException, ParseException {
        String cityName ="Riyadh";
        String siteName ="Cobone";
        if (request.getParameter("cityName") != null && request.getParameter("cityName") != "") {
            cityName = request.getParameter("cityName");
        }
        if (request.getParameter("siteName") != null && request.getParameter("siteName") != "") {
            siteName = request.getParameter("siteName");
        }

        CommandList list = new CommandList();
        List<Object> deals = dealDao.getCityDeals(2, cityName, "end", "ASC",siteName);
        System.out.println("------b list size" +deals.size());
        list.setList(deals);
        list.setCurrentServerDate(new Date());
        //request.setAttribute("deals", deals);
        //System.out.println("--------- deal Size ---------" + deals.size());
        return list;
    }

    @RequestMapping("getDeal.htm")
    public 
    @ResponseBody
    Object getDeal(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException, ParseException {
        CommandObject object = new CommandObject();
        if (request.getParameter("id") != null && request.getParameter("id") != "") {
            Integer dealId = Integer.parseInt(request.getParameter("id"));
            Deals deal = (Deals) dealDao.get(Deals.class, dealId);
            //request.setAttribute("deal", deal);
            object.setObject(deal);
        }
        object.setCurrentServerDate(new Date());

        return object;
    }
}
