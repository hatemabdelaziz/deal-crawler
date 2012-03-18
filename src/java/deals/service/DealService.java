/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deals.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import deals.dao.DealDao;
import deals.entity.Cities;
import deals.entity.Deals;
import deals.entity.Language;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

/**
 *
 * @author hatem
 */
public class DealService {
    
    private DealDao dealDao;

    public void setDealDao(DealDao dealDao) {
        this.dealDao = dealDao;
    }

    
        public void setDeal() throws IOException, XPatherException, ParseException {
//        Deals.FromSite[] fromSites = Deals.FromSite.values();
        //dealDao.deleteAllDealsByFromSite();
        //String params="&sortBy=discount&sortDir=DESC";
        //String url = "http://www.dealoola.com/Vouchers/";
//        String url = "http://dealoola.activedd.com/Vouchers/?allCategories=1&allParents=0&langId=2&maxDiscount=100&maxPrice=2000&maxTime=maxValue&minDiscount=0&minPrice=0&startNumber=0&sortBy=discount&sortDir=ASC";
//             String url = "http://dealoola.activedd.com/front/deals/searchdeals.htm?allCategories=1&allParents=0&langId=2&maxDiscount=100&maxPrice=2000&maxTime=maxValue&minDiscount=0&minPrice=0&partnersList=3&startNumber=0&sortBy=discount&sortDir=ASC";

        //String categorylistIds = "";
        //HtmlCleaner cleaner = new HtmlCleaner();
        //CleanerProperties props = new CleanerProperties();
        
//        String partnersListIds="";

        /*
         *  get all categories in dealoola search
         */
//        TagNode categoriesNode = new HtmlCleaner(props).clean(new URL("http://dealoola.activedd.com/front/core/home.htm"));
//              Object[] categoriesDivObjs = categoriesNode.evaluateXPath("//div[@class='accordion_contant no_p']/div");

//        Object[] partenerDivObjs = categoriesNode.evaluateXPath("//div[@class='accordion_contant no_p']/div/ul");
//           TagNode partenerNode = (TagNode) partenerDivObjs[0];
//            List<TagNode> partnerCheckboxInputs = partenerNode.getElementListByName("input", true);
//            for (TagNode theInput : partnerCheckboxInputs) {
//                String thepartnerId = theInput.getAttributeByName("value");
//                partnersListIds = partnersListIds + "&partnersList=" + thepartnerId;
//            }
//            url = url + partnersListIds;

//        if (categoriesDivObjs != null && categoriesDivObjs.length >= 1) {
//            TagNode categoryNode = (TagNode) categoriesDivObjs[0];
//            List<TagNode> checkboxInputs = categoryNode.getElementListByName("input", true);
//            for (TagNode theInput : checkboxInputs) {
//                String theCategoryId = theInput.getAttributeByName("value");
//                categorylistIds = categorylistIds + "&categoriesList=" + theCategoryId;
//            }
//
//            url = url + categorylistIds;
              
            /*
             *  get all cities in our site and then get deal sfor each city
             */
       

    Iterator<Cities> cities = dealDao.findAll(Cities.class).iterator();
    Language en = (Language) dealDao.get(Language.class, 2);
    while (cities.hasNext()) {
        Cities city = cities.next();
        String cityName=city.getName();
        String cityid = "cityId=" + city.getId();
        //String cityUrl = url +cityName+".htm?"+cityid+params;

        String firstUrl = "http://www.dealoola.com/front/deals/searchdeals.htm?"
                + "allCategories=1&allParents=0&categoriesList=9&"
                + "categoriesList=7&categoriesList=8&categoriesList=4&"
                + "categoriesList=5&categoriesList=6&categoriesList=10&"
                + "categoriesList=1&categoriesList=2&categoriesList=3"
                + "&langId=2&page=2&partnersList=8&partnersList=15&"
                + "partnersList=17&partnersList=9&partnersList=23&partnersList=7&"
                + "partnersList=12&partnersList=18&partnersList=5&partnersList=13&"
                + "partnersList=16&partnersList=14&partnersList=27&partnersList=6&"
                + "partnersList=4&partnersList=19&partnersList=20&partnersList=26&"
                + "partnersList=24&partnersList=25&partnersList=1&partnersList=3&"
                + "sortBy=discount&sortDir=DESC"
                +cityid;
                /// test something
                
        System.out.println("firstUrl "+ firstUrl);
        //System.out.println("secondUrl "+ secondUrl);
        //String response1  = sendGetRequest(firstUrl, null);
        //System.out.println("response1 "+ response1);
        String response  = sendGetRequest(firstUrl, null);
        System.out.println("response "+ response);
         JsonElement json = new JsonParser().parse(response);
	JsonArray array= json.getAsJsonArray();
	Iterator iterator = array.iterator();
	//List<Deals> details = new ArrayList<Deals>();
	 List<Deals> deals =  new ArrayList<Deals>();
	while(iterator.hasNext()){
	    JsonElement json2 = (JsonElement)iterator.next();
	    Gson gson = new Gson();
	    Deals deal = gson.fromJson(json2, Deals.class);
	    //can set some values in contact, if required 
	    //details.add(deals);
            deal.setCity(city);
            
            deal.setLanguage(en);
    //        deal.setViews(0);
            deal.setBestDeal(0);
            deals.add(deal);
            //dealDao.save(deal);
	}

        for (Iterator<Deals> it = deals.iterator(); it.hasNext();) {
            Deals deal = it.next();
            dealDao.save(deal);
        }

        
                
                /*
                 *   when we get all deals from search page we will get deal link to get  deal details
                 */
                    
//                Iterator<Cities> Sities = dealDao.findAll(Cities.class).iterator();
//                Deals.FromSite[] fromSites = Deals.FromSite.values();
//                for(Deals.FromSite fromSite : fromSites){
           
//                  Deals.FromSite
//                System.out.println("+city url"+cityUrl);
//                TagNode searchDealNode = new HtmlCleaner(props).clean(new URL(cityUrl));

//                Object[] searchDealsDiv = searchDealNode.evaluateXPath("//p[@class='f deal-breif']");
                /*
                 * searchDealsDiv.length  means iterat in all deals in search deal page  and get  deal link in dealoola
                 */
//                                    System.out.println("cityUrl "+ searchDealsDiv.length);

//                for (int dealNo = 0; dealNo < searchDealsDiv.length; dealNo++) {
//                    Deals deal = new Deals();
//                    BigDecimal dealPrice = BigDecimal.ZERO;
//                    BigDecimal dealVal = BigDecimal.ZERO;
//                    BigDecimal dealSave = BigDecimal.ZERO;
//                    BigDecimal dealdisc = BigDecimal.ZERO;
//                    TagNode dealNode = (TagNode) searchDealsDiv[dealNo];
//                    deal.setCurrency(city.getCurrency());
////                    deal.setFromSite(fromSite.name());
//                    List<TagNode> readLinkNode = dealNode.getElementListByName("a", true);
////                    if (categoriesDivObjs != null && categoriesDivObjs.length >= 1) {
//                        /*
//                         * deal link
//                         */
//                        String dealUrl = "http://dealoola.activedd.com" + readLinkNode.get(0).getAttributeByName("href");
//                        System.out.println("-------url" + dealUrl);
//                        TagNode dealPageNode = new HtmlCleaner(props).clean(new URL(dealUrl));
//                        ///////// ////////////////////////////////////// deal title /////////////////////////////////////////
//                        Object[] dealTitleObjs = dealPageNode.evaluateXPath("//span[@class='deal-discount']/a");
//                        if (dealTitleObjs != null && dealTitleObjs.length >= 1) {
//                            TagNode titleNode = (TagNode) dealTitleObjs[0];
//                            String dealtitle = titleNode.getText().toString().trim();
//                            deal.setTitle(dealtitle);
//                        }
//                       ///////// ////////////////////////////////////// deal title /////////////////////////////////////////
//                        Object[] dealSiteObjs = dealPageNode.evaluateXPath("//div[@class='share-element f']/span[@class='f offer-source-result comment']");
//                        if (dealSiteObjs != null && dealSiteObjs.length >= 1) {
//                            TagNode siteNode = (TagNode) dealSiteObjs[0];
//                            String dealSite = siteNode.getText().toString().trim();
//                            deal.setFromSite(dealSite);
//                        }
//                        ///////// ////////////////////////////////////// deal title /////////////////////////////////////////
//                        Object[] dealTitleObjs = dealPageNode.evaluateXPath("//span[@class='deal-discount']/a");
//                        if (dealTitleObjs != null && dealTitleObjs.length >= 1) {
//                            TagNode titleNode = (TagNode) dealTitleObjs[0];
//                            String dealtitle = titleNode.getText().toString().trim();
//                            deal.setTitle(dealtitle);
//                        }
                        ///////// ////////////////////////////////////// deal Url on real site  /////////////////////////////////////////
//                        Pattern pUrl = Pattern.compile("-?\\d+");
//                        Matcher mUrl = pUrl.matcher(dealUrl);
//                        int dealoolaDealId = 0;
//                        System.out.println("----------dealUrl"+dealUrl);
//                        if (mUrl.find()) {
//                            dealoolaDealId = Integer.parseInt(mUrl.group());
//
//                            if (dealoolaDealId != 0) {
//                                String BuyDealUrl = "http://dealoola.activedd.com/front/dealoola/buffer.htm?id=" + dealoolaDealId;
//                                TagNode dealBuyUrlNode = new HtmlCleaner(props).clean(new URL(BuyDealUrl));
//                                Object[] dealbuyUrlObjs = dealBuyUrlNode.evaluateXPath("//div[@class='message']");
//                               System.out.println("-------- dealbuyUrlObjs.length -----"+dealbuyUrlObjs.length+"BuyDealUrl-"+BuyDealUrl);
//                                if (dealbuyUrlObjs != null && dealbuyUrlObjs.length >= 1) {
//
//                                    TagNode dealUrlNode = (TagNode) dealbuyUrlObjs[0];
//                                    List<TagNode> urlNode = dealUrlNode.getElementListByName("a", true);
//                                    String dealBuyUrl =  urlNode.get(0).getAttributeByName("href");
//                                    deal.setUrl(dealBuyUrl);
//                                }
//                            }
//                        }

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

//                        Object[] dealIamgeObs = dealPageNode.evaluateXPath("//div[@class='deal_page_image f']");
//
//                        if (dealIamgeObs != null && dealIamgeObs.length >= 1) {
//                            TagNode dealIamgeNode = (TagNode) dealIamgeObs[0];
//                            List<TagNode> ImageLinkNode = dealIamgeNode.getElementListByName("img", true);
//                            String dealdImage = ImageLinkNode.get(0).getAttributeByName("src");
//                            deal.setPhoto(dealdImage);
//
//                        }
                        ////////////////////////////////////// deal value means first price before discount /////////////////////////////////////////

//                        Object[] dealvalueObjs = dealPageNode.evaluateXPath("//div[@class='f deal-price']");
//                        if (dealvalueObjs != null && dealvalueObjs.length >= 1) {
//                            TagNode dealvalues = (TagNode) dealvalueObjs[0];
//                            String dealvals = dealvalues.getText().toString().trim();
//                            Pattern p = Pattern.compile("-?\\d+");
//                            Matcher m = p.matcher(dealvals);
//                            if (m.find()) {
//                                dealVal = new BigDecimal(m.group());
//                                deal.setValue(dealVal);
//
//                            }
//
//                        }
                        ////////////////////////////////////// deal price means  price after discount /////////////////////////////////////////

//                        Object[] dealpriceObjs = dealPageNode.evaluateXPath("//a[@class='press-buy f-r']");
//                        if (dealpriceObjs != null && dealpriceObjs.length >= 1) {
//                            TagNode dealpriceNode = (TagNode) dealpriceObjs[0];
//                            String dealprice = dealpriceNode.getText().toString().trim();
//                            Pattern p = Pattern.compile("-?\\d+");
//                            Matcher m = p.matcher(dealprice);
//                            if (m.find()) {
//                                dealPrice = new BigDecimal(m.group());
//                                deal.setPrice(dealPrice);
//
//                            }
//                        }
//                        dealSave = dealVal.subtract(dealPrice);
//                        deal.setSaving(dealSave);
//                        ////////////////////////////////////// deal  discount /////////////////////////////////////////
//
//                        Object[] dealDiscObs = dealPageNode.evaluateXPath("//div[@class='f-r saved-money']");
//                        if (dealDiscObs != null && dealDiscObs.length >= 1) {
//                            TagNode dealdiscNode = (TagNode) dealDiscObs[0];
//                            String dealdiscount = dealdiscNode.getText().toString().trim();
//                            Pattern p = Pattern.compile("-?\\d+");
//                            Matcher m = p.matcher(dealdiscount);
//                            if (m.find()) {
//                                dealdisc = new BigDecimal(m.group());
//                                deal.setDiscount(dealdisc);
//
//                            }
//                        }
                        ////////////////////////////////////// deal  end date when deal closed /////////////////////////////////////////

//                        Object[] dealDateObs = dealPageNode.evaluateXPath("//div[@class='deal_time_left']");
//                        if (dealDateObs != null && dealDateObs.length >= 1) {
//                            TagNode dealDateNode = (TagNode) dealDateObs[0];
//                            String dealdate = dealDateNode.getText().toString();
//                            Pattern p = Pattern.compile("-?\\d+");
//                            Matcher m = p.matcher(dealdate);
//                            int No = 1;
//                            Calendar currentDate = Calendar.getInstance();
//                            Date endDate = new Date();
//                            while (m.find()) {
//
//                                if (No == 1) {
//                                    int dealhoursNo = Integer.parseInt(m.group());
//                                    currentDate.add(Calendar.HOUR, dealhoursNo);
//                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                                    String dateNow = formatter.format(currentDate.getTime());
//
//                                    endDate = formatter.parse(dateNow);
//                                }
//                                if (No == 2) {
//                                    int dealMinuteNo = Integer.parseInt(m.group());
//                                    currentDate.add(Calendar.MINUTE, dealMinuteNo);
//
//                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                                    String end = formatter.format(currentDate.getTime());
//
//                                    endDate = formatter.parse(end);
//
//                                }
//                                No = No + 1;
//                            }
//
//                            deal.setEnd(endDate);
//
//                        }

//                    }

//                    deal.setCity(city);
//                    deal.setLanguage(en);
//                    deal.setViews(0);
//                    deal.setBestDeal(0);
//                    dealDao.save(deal);

//                }
//            }
            }

        }
        
        public static String sendGetRequest(String endpoint, String requestParameters)
            {
            String result = null;
            if (endpoint.startsWith("http://"))
            {
            // Send a GET request to the servlet
            try
            {
            // Construct data
            StringBuffer data = new StringBuffer();

            // Send data
            String urlStr = endpoint;
            if (requestParameters != null && requestParameters.length () > 0)
            {
            urlStr += "?" + requestParameters;
            }
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection ();

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null)
            {
            sb.append(line);
            }
            rd.close();
            result = sb.toString();
            } catch (Exception e)
            {
            e.printStackTrace();
            }
            }
            return result;
            }
}