/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deals.controller;

import deals.command.CommandList;
import deals.command.CommandObject;
import deals.dao.DealDao;
import deals.entity.Cities;
import deals.entity.Deals;
import deals.entity.Language;
import java.io.IOException;
import java.math.BigDecimal;
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
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    public void setDealDao(DealDao dealDao) {
        this.dealDao = dealDao;
    }

    @RequestMapping("dealHeader.htm")
    public ModelAndView dealHeader(HttpServletRequest request, HttpServletResponse response) throws IOException, XPatherException, ParseException {
        String FEBestDeal = request.getParameter("FEBestDeal");
        ModelAndView mv = new ModelAndView("dealHeader");
//        dealDao.deleteAllDealsByFromSite(Deals.FromSite.cobone.name());
        setDeal(request, response);
        return mv;
    }

    public void setDeal(HttpServletRequest request, HttpServletResponse response) throws IOException, XPatherException, ParseException {
//        Deals.FromSite[] fromSites = Deals.FromSite.values();
        String url = "http://dealoola.activedd.com/front/deals/searchdeals.htm?allCategories=1&allParents=0&langId=2&maxDiscount=100&maxPrice=2000&maxTime=maxValue&minDiscount=0&minPrice=0&startNumber=0&sortBy=discount&sortDir=ASC";
//             String url = "http://dealoola.activedd.com/front/deals/searchdeals.htm?allCategories=1&allParents=0&langId=2&maxDiscount=100&maxPrice=2000&maxTime=maxValue&minDiscount=0&minPrice=0&partnersList=3&startNumber=0&sortBy=discount&sortDir=ASC";

        String categorylistIds = "";
        HtmlCleaner cleaner = new HtmlCleaner();
        CleanerProperties props = new CleanerProperties();
        Language en = (Language) dealDao.get(Language.class, 2);
        String partnersListIds="";

        /*
         *  get all categories in dealoola search
         */
        TagNode categoriesNode = new HtmlCleaner(props).clean(new URL("http://dealoola.activedd.com/front/core/home.htm"));
              Object[] categoriesDivObjs = categoriesNode.evaluateXPath("//div[@class='accordion_contant no_p']/div");

        Object[] partenerDivObjs = categoriesNode.evaluateXPath("//div[@class='accordion_contant no_p']/div/ul");
           TagNode partenerNode = (TagNode) partenerDivObjs[0];
            List<TagNode> partnerCheckboxInputs = partenerNode.getElementListByName("input", true);
            for (TagNode theInput : partnerCheckboxInputs) {
                String thepartnerId = theInput.getAttributeByName("value");
                partnersListIds = partnersListIds + "&partnersList=" + thepartnerId;
            }
            url = url + partnersListIds;

        if (categoriesDivObjs != null && categoriesDivObjs.length >= 1) {
            TagNode categoryNode = (TagNode) categoriesDivObjs[0];
            List<TagNode> checkboxInputs = categoryNode.getElementListByName("input", true);
            for (TagNode theInput : checkboxInputs) {
                String theCategoryId = theInput.getAttributeByName("value");
                categorylistIds = categorylistIds + "&categoriesList=" + theCategoryId;
            }
            
            url = url + categorylistIds;
              
            /*
             *  get all cities in our site and then get deal sfor each city
             */
       

            Iterator<Cities> cities = dealDao.findAll(Cities.class).iterator();

            while (cities.hasNext()) {
                Cities city = cities.next();
                String cityid = "&cityId=" + city.getId();
                String cityUrl = url + cityid;
                /*
                 *   when we get all deals from search page we will get deal link to get  deal details
                 */
                    System.out.println("cityUrl "+ cityUrl);
                Iterator<Cities> Sities = dealDao.findAll(Cities.class).iterator();
                Deals.FromSite[] fromSites = Deals.FromSite.values();
//                for(Deals.FromSite fromSite : fromSites){
           
//                  Deals.FromSite
//                System.out.println("+city url"+cityUrl);
                TagNode searchDealNode = new HtmlCleaner(props).clean(new URL(cityUrl));

                Object[] searchDealsDiv = searchDealNode.evaluateXPath("//p[@class='f deal-breif']");
                /*
                 * searchDealsDiv.length  means iterat in all deals in search deal page  and get  deal link in dealoola
                 */
                for (int dealNo = 0; dealNo < searchDealsDiv.length; dealNo++) {
                    Deals deal = new Deals();
                    BigDecimal dealPrice = BigDecimal.ZERO;
                    BigDecimal dealVal = BigDecimal.ZERO;
                    BigDecimal dealSave = BigDecimal.ZERO;
                    BigDecimal dealdisc = BigDecimal.ZERO;
                    TagNode dealNode = (TagNode) searchDealsDiv[dealNo];
                    deal.setCurrency(city.getCurrency());
//                    deal.setFromSite(fromSite.name());
                    List<TagNode> readLinkNode = dealNode.getElementListByName("a", true);
                    if (categoriesDivObjs != null && categoriesDivObjs.length >= 1) {
                        /*
                         * deal link
                         */
                        String dealUrl = "http://dealoola.activedd.com" + readLinkNode.get(0).getAttributeByName("href");
                        System.out.println("-------url" + dealUrl);
                        TagNode dealPageNode = new HtmlCleaner(props).clean(new URL(dealUrl));
                        ///////// ////////////////////////////////////// deal title /////////////////////////////////////////
                        Object[] dealTitleObjs = dealPageNode.evaluateXPath("//span[@class='deal-discount']/a");
                        if (dealTitleObjs != null && dealTitleObjs.length >= 1) {
                            TagNode titleNode = (TagNode) dealTitleObjs[0];
                            String dealtitle = titleNode.getText().toString().trim();
                            deal.setTitle(dealtitle);
                        }
                       ///////// ////////////////////////////////////// deal title /////////////////////////////////////////
                        Object[] dealSiteObjs = dealPageNode.evaluateXPath("//div[@class='share-element f']/span[@class='f offer-source-result comment']");
                        if (dealSiteObjs != null && dealSiteObjs.length >= 1) {
                            TagNode siteNode = (TagNode) dealSiteObjs[0];
                            String dealSite = siteNode.getText().toString().trim();
                            deal.setFromSite(dealSite);
                        }
//                        ///////// ////////////////////////////////////// deal title /////////////////////////////////////////
//                        Object[] dealTitleObjs = dealPageNode.evaluateXPath("//span[@class='deal-discount']/a");
//                        if (dealTitleObjs != null && dealTitleObjs.length >= 1) {
//                            TagNode titleNode = (TagNode) dealTitleObjs[0];
//                            String dealtitle = titleNode.getText().toString().trim();
//                            deal.setTitle(dealtitle);
//                        }
                        ///////// ////////////////////////////////////// deal Url on real site  /////////////////////////////////////////
                        Pattern pUrl = Pattern.compile("-?\\d+");
                        Matcher mUrl = pUrl.matcher(dealUrl);
                        int dealoolaDealId = 0;
                        System.out.println("----------dealUrl"+dealUrl);
                        if (mUrl.find()) {
                            dealoolaDealId = Integer.parseInt(mUrl.group());

                            if (dealoolaDealId != 0) {
                                String BuyDealUrl = "http://dealoola.activedd.com/front/dealoola/buffer.htm?id=" + dealoolaDealId;
                                TagNode dealBuyUrlNode = new HtmlCleaner(props).clean(new URL(BuyDealUrl));
                                Object[] dealbuyUrlObjs = dealBuyUrlNode.evaluateXPath("//div[@class='message']");
                               System.out.println("-------- dealbuyUrlObjs.length -----"+dealbuyUrlObjs.length+"BuyDealUrl-"+BuyDealUrl);
                                if (dealbuyUrlObjs != null && dealbuyUrlObjs.length >= 1) {

                                    TagNode dealUrlNode = (TagNode) dealbuyUrlObjs[0];
                                    List<TagNode> urlNode = dealUrlNode.getElementListByName("a", true);
                                    String dealBuyUrl =  urlNode.get(0).getAttributeByName("href");
                                    deal.setUrl(dealBuyUrl);
                                }
                            }
                        }

                        ////////////////////////////////////////// deal desc/////////////////////////////////////////

//                        Object[] dealDescObjs = dealPageNode.evaluateXPath("//p[@class='f deal-text']");
//
//                        if (dealDescObjs != null && dealDescObjs.length >= 1) {
//                            TagNode descNode = (TagNode) dealDescObjs[0];
//                            if (dealTitleObjs != null && dealTitleObjs.length >= 1) {
//                                TagNode titleNode = (TagNode) dealTitleObjs[0];
//                                titleNode.removeFromTree();
//                            }
//                            String dealDesc = descNode.getText().toString().trim();
//                            deal.setDescription(dealDesc);
//
//                        }
                        /////////////////////////////////////////////// deal image/////////////////////////////////////////

                        Object[] dealIamgeObs = dealPageNode.evaluateXPath("//div[@class='deal_page_image f']");

                        if (dealIamgeObs != null && dealIamgeObs.length >= 1) {
                            TagNode dealIamgeNode = (TagNode) dealIamgeObs[0];
                            List<TagNode> ImageLinkNode = dealIamgeNode.getElementListByName("img", true);
                            String dealdImage = ImageLinkNode.get(0).getAttributeByName("src");
                            deal.setPhoto(dealdImage);

                        }
                        ////////////////////////////////////// deal value means first price before discount /////////////////////////////////////////

                        Object[] dealvalueObjs = dealPageNode.evaluateXPath("//div[@class='f deal-price']");
                        if (dealvalueObjs != null && dealvalueObjs.length >= 1) {
                            TagNode dealvalues = (TagNode) dealvalueObjs[0];
                            String dealvals = dealvalues.getText().toString().trim();
                            Pattern p = Pattern.compile("-?\\d+");
                            Matcher m = p.matcher(dealvals);
                            if (m.find()) {
                                dealVal = new BigDecimal(m.group());
                                deal.setValue(dealVal);

                            }

                        }
                        ////////////////////////////////////// deal price means  price after discount /////////////////////////////////////////

                        Object[] dealpriceObjs = dealPageNode.evaluateXPath("//a[@class='press-buy f-r']");
                        if (dealpriceObjs != null && dealpriceObjs.length >= 1) {
                            TagNode dealpriceNode = (TagNode) dealpriceObjs[0];
                            String dealprice = dealpriceNode.getText().toString().trim();
                            Pattern p = Pattern.compile("-?\\d+");
                            Matcher m = p.matcher(dealprice);
                            if (m.find()) {
                                dealPrice = new BigDecimal(m.group());
                                deal.setPrice(dealPrice);

                            }
                        }
                        dealSave = dealVal.subtract(dealPrice);
                        deal.setSaving(dealSave);
                        ////////////////////////////////////// deal  discount /////////////////////////////////////////

                        Object[] dealDiscObs = dealPageNode.evaluateXPath("//div[@class='f-r saved-money']");
                        if (dealDiscObs != null && dealDiscObs.length >= 1) {
                            TagNode dealdiscNode = (TagNode) dealDiscObs[0];
                            String dealdiscount = dealdiscNode.getText().toString().trim();
                            Pattern p = Pattern.compile("-?\\d+");
                            Matcher m = p.matcher(dealdiscount);
                            if (m.find()) {
                                dealdisc = new BigDecimal(m.group());
                                deal.setDiscount(dealdisc);

                            }
                        }
                        ////////////////////////////////////// deal  end date when deal closed /////////////////////////////////////////

                        Object[] dealDateObs = dealPageNode.evaluateXPath("//div[@class='deal_time_left']");
                        if (dealDateObs != null && dealDateObs.length >= 1) {
                            TagNode dealDateNode = (TagNode) dealDateObs[0];
                            String dealdate = dealDateNode.getText().toString();
                            Pattern p = Pattern.compile("-?\\d+");
                            Matcher m = p.matcher(dealdate);
                            int No = 1;
                            Calendar currentDate = Calendar.getInstance();
                            Date endDate = new Date();
                            while (m.find()) {

                                if (No == 1) {
                                    int dealhoursNo = Integer.parseInt(m.group());
                                    currentDate.add(Calendar.HOUR, dealhoursNo);
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    String dateNow = formatter.format(currentDate.getTime());

                                    endDate = formatter.parse(dateNow);
                                }
                                if (No == 2) {
                                    int dealMinuteNo = Integer.parseInt(m.group());
                                    currentDate.add(Calendar.MINUTE, dealMinuteNo);

                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    String end = formatter.format(currentDate.getTime());

                                    endDate = formatter.parse(end);

                                }
                                No = No + 1;
                            }

                            deal.setEnd(endDate);

                        }

                    }

                    deal.setCity(city);
                    deal.setLanguage(en);
                    deal.setViews(0);
                    deal.setBestDeal(0);
                    dealDao.save(deal);

                }
//            }
            }

        }
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
