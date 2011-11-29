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
        TagNode tagNode = new HtmlCleaner(props).clean(new URL("http://www.cobone.com/deals/destinations/luxor-hurghada-hotel-stay/3816"));
        Object[] titleList = tagNode.evaluateXPath("//a[@class='main-deal-title-header-link']");

        if (titleList == null || titleList.length < 1) {
            System.out.println("-------------------empty ");
            deal.setTitle("Today Deal Title");
        } else {
            TagNode aNode = (TagNode) titleList[0];
            String todayDealTitle = aNode.getText().toString();
            deal.setTitle(todayDealTitle);
        }
      Object[]  descList = tagNode.evaluateXPath("//div[@class='main-deal-description-specifics-info']");
        if (descList != null && descList.length >= 1) {
            TagNode aNode = (TagNode) descList[0];
            Object[] unusedCobonedata = tagNode.evaluateXPath("//div[@class='bold']");
            if (unusedCobonedata != null && unusedCobonedata.length >= 1) {
                ((TagNode) unusedCobonedata[0]).removeFromTree();
            }
            Object[] footer = tagNode.evaluateXPath("//b/i");
            if (footer != null && footer.length >= 1) {
                ((TagNode) footer[0]).removeFromTree();
            }
            if (footer != null && footer.length >= 1) {
                ((TagNode) footer[0]).removeFromTree();
            }
//            aNode.removeAttribute("image");
//            aNode.removeAttribute("br");
//            aNode.getElementsByName("image", false);
//            aNode.getElementsByName("br", false);
//            String todayDealDesc = aNode.getText().toString();
//            String content = todayDealDesc.replaceAll("<!--.*?-->", "").replaceAll("<[^>]+>", "");
//            TagTransformation imageTt = new TagTransformation("image");
//            TagTransformation brTt = new TagTransformation("br");
//            transformations.addTransformation(imageTt);
//            transformations.addTransformation(brTt);
//            cleaner.setTransformations(transformations);
//            deal.setTitle(content);
//             TagNode decNode=cleaner.clean(content);
//             String DealDesc = decNode.getText().toString().trim();
//            deal.setTitle(DealDesc);
//            System.out.println(cleaner.getInnerHtml(decNode));
        }
    }
}
