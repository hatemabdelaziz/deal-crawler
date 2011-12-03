<%-- 
    Document   : dealsearch
    Created on : 02/12/2011, 04:21:48 م
    Author     : SAMAR
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div id="searchContainer">
    <input type="hidden" id="page" name="page" value="Home"/>
    <c:choose>
    <c:when test="${! empty deals}">
 
    <c:forEach var="deal" items="${deals}" varStatus="j">
        <div class="deal-container f-r">

            <img src="${deal.photo}" class="f deal-image" width="200" height="121"/>
            <p class="f deal-breif">
                <span class="deal-title"><fmt:formatNumber value="${deal.discount}" pattern="#0" />% for <fmt:formatNumber value="${deal.value}" pattern="#0" />EGP</span><br />
                ${deal.description}
            </p>
            <div class="deal-details f-r">
                <div class="buy-button f-r">

                    <fmt:formatNumber value="${deal.price}" pattern="#0" />EGP
                    <a href="${deal.url}" target="_blanck"></a>
                </div>
                <div class="price_info f-r">
                <div class="f-r deal-price">
            	Was : ${deal.value} EGP <br />
	        save: ${deal.saving} EGP
                </div>

                <div class="f-r saved-money"><fmt:formatNumber value="${deal.discount}" pattern="#0" />%</div>
                <div class="nl"></div>
                <div class="deal_time_left">
                                <img src="/assets/frontend/dealoola/images/clock.png" width="23" height="23" />

                    ${deal.remainingHours} hrs ${deal.remainingMinutes} mins left

                </div>
            </div>

            </div>

            <div class="share-container f">

                <div class="sep f"></div>
               <div class="sep f"></div>
                <div class="share-element f">
                    <span class="title">Source</span>
                   ${deal.fromSite}
                </div>
                <div class="sep f"></div>
            </div>
        </div>
    </c:forEach>
    </c:when>
                <c:otherwise>
                    <div class="no_deal">
                      
                         There is currently no deal available for    ${deal.city.name}, please check later .
                    </div>
                </c:otherwise>
            </c:choose>
</div>
