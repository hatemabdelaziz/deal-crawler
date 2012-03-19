package deals.entity;

import java.math.BigDecimal;
import java.util.Date;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonProperty;


/**
 * Deals generated by hbm2java
 */
@JsonAutoDetect(fieldVisibility = Visibility.NONE , getterVisibility = Visibility.NONE , isGetterVisibility = Visibility.NONE , setterVisibility = Visibility.NONE)
public class Deals implements java.io.Serializable {
    
    public enum FromSite {

        Cobone, Darendeal, Offerna, Souq, Dealgobbler, Dealsouk, Dealwaty, Dubib, El7a2, Fustuq,Haladeals,joyoffer,kobonaty,Livingsocial,Makhsoom,Moosavings,Nailthedeal,Thescoupon,Yallabanana,yallabuyit,sheeel
    }
    
    
    
    
    
    
    
    @JsonProperty()
    private Integer id;
    @JsonProperty()
    private Cities city;
    @JsonProperty()
    private Language language;
    @JsonProperty()
    private String simpleTitle;
    @JsonProperty()
    private String simpleDescription;
    @JsonProperty()
    private BigDecimal value;
    @JsonProperty()
    private BigDecimal discount;
    @JsonProperty()
    private BigDecimal price;
    @JsonProperty()
    private BigDecimal saving;
    @JsonProperty()
    private long end;
    @JsonProperty()
    private String url;
    @JsonProperty()
    private String photo;
    @JsonProperty()
    private int views;
    @JsonProperty()
    private int bestDeal;
    @JsonProperty()
    private String currency;
    @JsonProperty()
    private String fromSite;
    @JsonProperty()
    private long remainingHours;
    @JsonProperty()
    private long remainingMinutes;

    static final long ONE_HOUR = 60 * 60 * 1000L;


    public Deals() {
    }

    public Deals(Language language, int views, int bestDeal, String currency) {
        this.language = language;
        this.views = views;
        this.bestDeal = bestDeal;
        this.currency = currency;
    }

    public Deals(Cities city, Language language, String title, String description, BigDecimal value, BigDecimal discount, BigDecimal price, BigDecimal saving, long end, String url, String photo, int views, int bestDeal, String currency, long remainingMinutes, long remainingHours) {
        this.city = city;
        this.language = language;
        this.simpleTitle = title;
        this.simpleDescription = description;
        this.value = value;
        this.discount = discount;
        this.price = price;
        this.saving = saving;
        this.end = end;
        this.url = url;
        this.photo = photo;
        this.views = views;
        this.bestDeal = bestDeal;
        this.currency = currency;
        this.remainingMinutes = remainingMinutes;
        this.remainingHours= remainingHours;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cities getCity() {
        return city;
    }

    public void setCity(Cities city) {
        this.city = city;
    }

    public Language getLanguage() {
        return this.language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getSimpleDescription() {
        return simpleDescription;
    }

    public void setSimpleDescription(String simpleDescription) {
        this.simpleDescription = simpleDescription;
    }

    public String getSimpleTitle() {
        return simpleTitle;
    }

    public void setSimpleTitle(String simpleTitle) {
        this.simpleTitle = simpleTitle;
    }

    public BigDecimal getValue() {
        return this.value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getDiscount() {
        return this.discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSaving() {
        return this.saving;
    }

    public void setSaving(BigDecimal saving) {
        this.saving = saving;
    }

    public long getEnd() {
        return this.end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getViews() {
        return this.views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getBestDeal() {
        return this.bestDeal;
    }

    public void setBestDeal(int bestDeal) {
        this.bestDeal = bestDeal;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getFromSite() {
        return fromSite;
    }

    public void setFromSite(String fromSite) {
        this.fromSite = fromSite;
    }

        public long getRemainingHours() {
        Date today = new Date();
        long diff = end - today.getTime();
        diff = (diff + ONE_HOUR) / (ONE_HOUR);
        return diff;
    }

    public long getRemainingMinutes() {

        Date today = new Date();
        long diff = end - today.getTime();
        long hours = (diff + ONE_HOUR) / (ONE_HOUR);
        long mintues = (long) ((((double) (diff + ONE_HOUR) / (ONE_HOUR)) - hours) * 60);
        return mintues;
    }
}
