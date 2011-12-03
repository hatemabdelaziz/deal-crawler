<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>



    <c:choose>
    <c:when test="${! empty deal}">
 <input type="hidden" id="page" name="page" value="Home"/>
<div id="container">
    <div id="center_page_main">
        <div class="main_side f-r">
            <div class="detailed-deal f-r">
                <img alt=""   src="${deal.photo}" width="443" height="213" class="f"/>
                <a  href="${deal.url}" target="_blanck" class="press-buy f-r"><fmt:formatNumber value="${deal.price}" pattern="#0" />EGP</a>
                <div class="price_info f-r">
                <div class="f deal-price">
                   value :${deal.value} EGP
                    <br />
                    save: ${deal.saving}EGP
                </div>

                <div class="f-r saved-money"><fmt:formatNumber value="${deal.discount}" pattern="#0" />%</div>
                   <div class="nl"></div>
                <div class="deal_time_left">
                                <img src="/assets/frontend/dealoola/images/clock.png" width="23" height="23" />

                    ${deal.remainingHours} hrs ${deal.remainingMinutes} mins left

                </div>
            </div>
                <p class="f deal-text">
                    <span class="deal-discount">-<fmt:formatNumber value="${deal.discount}" pattern="#0" />% For a <fmt:formatNumber value="${deal.value}" pattern="#0" /> EGP</span>
                    <br />
                    ${deal.description}
                </p>
                <div class="vertical-separator f"></div>
            </div>

        </div>

    </c:when>
                <c:otherwise>
                    <div class="no_deal">

                         There is currently no deal available for ${deal.city.name} , please check later .
                    </div>
                </c:otherwise>
            </c:choose>
        <div class="nl"></div>
    </div>
  
</div>
</body>
</html>