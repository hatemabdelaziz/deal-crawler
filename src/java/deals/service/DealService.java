/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deals.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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


        dealDao.deleteAllDealsByFromSite();

        Iterator<Cities> cities = dealDao.findAll(Cities.class).iterator();
        Language en = (Language) dealDao.get(Language.class, 2);
        while (cities.hasNext()) {
            Cities city = cities.next();
            String cityid = "&cityId=" + city.getId();
            for (int i = 0; i < 30; i++) {
                int pageNumber = 0;
                boolean getMorePages = true;
                while(getMorePages)
                {
                    String firstUrl = "http://www.dealoola.com/front/deals/searchdeals.htm?"
                        + "allCategories=1&allParents=0&categoriesList=9&"
                        + "categoriesList=7&categoriesList=8&categoriesList=4&"
                        + "categoriesList=5&categoriesList=6&categoriesList=10&"
                        + "categoriesList=1&categoriesList=2&categoriesList=3&"
                        + "minPrice=0&maxPrice=15000&minDiscount=0&maxDiscount=100&maxTime=maxValue"
                        + "&langId=2&page="+pageNumber+"&partnersList="+i
                        + "&sortBy=discount&sortDir=DESC"
                        +cityid;
                        /// test something

                    System.out.println("firstUrl "+ firstUrl);
                    System.out.println("page number "+ pageNumber);
                    String response  = sendGetRequest(firstUrl, null);
                    JsonElement json = new JsonParser().parse(response); 
                    JsonArray array= json.getAsJsonArray();
                    if (array.size()>0) {
                        pageNumber++;
                    } else {
                        getMorePages = false;
                        continue;
                    }
                    Iterator iterator = array.iterator();
                    //List<Deals> details = new ArrayList<Deals>();
                     List<Deals> deals =  new ArrayList<Deals>();
                    while(iterator.hasNext()){
                        JsonElement json2 = (JsonElement)iterator.next();
                        Gson gson = new Gson();
                        Deals deal = gson.fromJson(json2, Deals.class);
                        JsonObject jsonO = json2.getAsJsonObject();
                        JsonElement siteName = jsonO.get("site").getAsJsonObject().get("site");
                        String siteNameString = siteName.getAsString();
                        //can set some values in contact, if required 
                        deal.setCity(city);
                        deal.setLanguage(en);
                        deal.setFromSite(siteNameString);
                        //deal.setViews(0);
                        deal.setBestDeal(0);
                        deal.setId(null);
                        deals.add(deal);
                    }
                    System.out.println("number of deals "+ deals.size());
                    for (Iterator<Deals> it = deals.iterator(); it.hasNext();) {
                        Deals deal = it.next();
                        dealDao.save(deal);
                    }
                }
            }
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