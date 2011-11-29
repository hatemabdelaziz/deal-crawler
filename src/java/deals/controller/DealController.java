/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deals.controller;

import deals.dao.DealDao;
import deals.entity.Deals;
import deals.entity.Language;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.stat.CategorizedStatistics;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.CleanerTransformations;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.TagTransformation;
import org.htmlcleaner.XPatherException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author SAMAR
 */
public class DealController extends MultiActionController {

    private DealDao dealDao;

    public void setDealDao(DealDao dealDao) {
        this.dealDao = dealDao;
    }

    public ModelAndView dealHeader(HttpServletRequest request, HttpServletResponse response) throws IOException, XPatherException {
        String FEBestDeal = request.getParameter("FEBestDeal");
        ModelAndView mv = new ModelAndView("dealHeader");
        setDeal(request, response);
        Deals deal = new Deals();
        deal.setBestDeal(1);
        deal.setTitle("sssss");
        Language en=(Language) dealDao.get(Language.class , 2);
        deal.setLanguage(en);
        deal.setCurrency("EGP");
        deal.setBestDeal(0);
        deal.setViews(0);
        dealDao.save(deal);
        return mv;
    }

    public void setDeal(HttpServletRequest request, HttpServletResponse response) throws IOException, XPatherException {
        Deals deal = new Deals();
        HtmlCleaner cleaner = new HtmlCleaner();
        CleanerProperties props = new CleanerProperties();
        CleanerTransformations transformations = new CleanerTransformations();
        TagNode tagNode = new HtmlCleaner(props).clean(new URL("http://dealoola.activedd.com/front/core/home.htm"));

      Object[]  descListss = tagNode.evaluateXPath("//div[@class='accordion_contant no_p']/div");
      TagNode categoryNode = (TagNode) descListss[0];
    System.out.println("----------htmllle div contains input category  and ids-----"+cleaner.getInnerHtml(categoryNode));

        }
    }
//      public void saveDeal(HttpServletRequest request, HttpServletResponse response){
//      }


