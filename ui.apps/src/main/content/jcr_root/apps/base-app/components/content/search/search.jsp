
<%--
Copyright 1997-2008 Day Management AG
Barfuesserplatz 6, 4001 Basel, Switzerland
All Rights Reserved.

This software is the confidential and proprietary information of
Day Management AG, ("Confidential Information"). You shall not
disclose such Confidential Information and shall use it only in
accordance with the terms of the license agreement you entered into
with Day.

==============================================================================

Search component

Draws the search form and evaluates a query

--%><%@include file="/libs/foundation/global.jsp" %><%
%><%@page session="false"%><%@ page import="java.text.NumberFormat,
                                        org.apache.commons.lang.ArrayUtils,
                                        com.day.cq.i18n.I18n,
                                        com.day.cq.tagging.TagManager,
                                        com.day.cq.wcm.foundation.Search" %><%
%><cq:setContentBundle source="page" /><%
  Search search = new Search(slingRequest);

  I18n i18n = new I18n(slingRequest);
  String searchIn = (String) properties.get("searchIn");
  final String[] nodeTypes = properties.get("nodeTypes", String[].class);
  String requestSearchPath = request.getParameter("path");
  if (searchIn != null) {
      // only allow the "path" request parameter to be used if it
      // is within the searchIn path configured
      if (requestSearchPath != null && requestSearchPath.startsWith(searchIn)) {
          search.setSearchIn(requestSearchPath);
      } else {
          search.setSearchIn(searchIn);
      }
  } else if (requestSearchPath != null) {
      search.setSearchIn(requestSearchPath);
  }

  final String escapedQuery = xssAPI.encodeForHTML(search.getQuery());
  final String escapedQueryForAttr = xssAPI.encodeForHTMLAttr(search.getQuery());
  final String escapedQueryForHref = xssAPI.getValidHref(search.getQuery());

  pageContext.setAttribute("escapedQuery", escapedQuery);
  pageContext.setAttribute("escapedQueryForAttr", escapedQueryForAttr);
  pageContext.setAttribute("escapedQueryForHref", escapedQueryForHref);

  pageContext.setAttribute("search", search);
  TagManager tm = resourceResolver.adaptTo(TagManager.class);

  Search.Result result = null;
  try {
      result = search.getResult();
  } catch (RepositoryException e) {
      log.error("Unable to get search results", e);
  }
  pageContext.setAttribute("result", result);

  String nextText = properties.get("nextText", i18n.get("Next", "Next page"));
  String noResultsText = properties.get("noResultsText", i18n.get("Your search - <b>{0}</b> - did not match any documents.", null, escapedQuery));
  String previousText = properties.get("previousText", i18n.get("Previous", "Previous page"));
  String relatedSearchesText = properties.get("relatedSearchesText", i18n.get("Related searches:"));
  String resultPagesText = properties.get("resultPagesText", i18n.get("Results", "Search results"));
  String searchButtonText = properties.get("searchButtonText", i18n.get("Search", "Search button text"));
  String searchTrendsText = properties.get("searchTrendsText", i18n.get("Search Trends"));
  String similarPagesText = properties.get("similarPagesText", i18n.get("Similar Pages"));
  String spellcheckText = properties.get("spellcheckText", i18n.get("Did you mean:", "Spellcheck text if typo in search term"));

  NumberFormat nf = NumberFormat.getNumberInstance(slingRequest.getLocale());

  pageContext.setAttribute("currentPagePath", xssAPI.getValidHref(currentPage.getPath()));
  if (result != null) {
      if (result.getPreviousPage() != null) {
          pageContext.setAttribute("previousPageURL", xssAPI.getValidHref(result.getPreviousPage().getURL()));
      }
      if (result.getNextPage() != null) {
          pageContext.setAttribute("nextPageURL", xssAPI.getValidHref(result.getNextPage().getURL()));
      }
      pageContext.setAttribute("spellcheck", xssAPI.filterHTML(result.getSpellcheck()));
  }

%><c:set var="trends" value="${search.trends}"/><c:set var="result" value="${result}"/><%
%>

<div class="grid-container">
      <div class="grid-x">
              <div class="cell">
                      <div class="title-1">Search Results</div>
<form action="${currentPagePath}.html" class="columns">
  <div class="input-group">

     <input size="41" type="text" maxlength="2048" name="q" value="${escapedQueryForAttr}" class="input-group-field"/><%

  if (ArrayUtils.isNotEmpty(nodeTypes)) {
      for (String type : nodeTypes) {

          %><input type="hidden" name="nodeType" value="<%= xssAPI.encodeForHTMLAttr(type) %>"/><%

      }
  }

%>
<div class="input-group-button">
  <input value="<%= xssAPI.encodeForHTMLAttr(searchButtonText) %>" type="submit" class="button "/>
</div>
</div>

</form>
              </div>                
     </div>
  <div class="grid-x">
      <div class="cell">
<c:choose>
<c:when test="${empty result && empty escapedQuery}">
</c:when>
<c:when test="${empty result.hits}">
  ${result.trackerScript}
  <c:if test="${result.spellcheck != null}">
    <p><%= xssAPI.encodeForHTML(spellcheckText) %> <a href="<c:url value="${currentPagePath}.html"><c:param name="q" value="${spellcheck}"/></c:url>"><b><c:out value="${spellcheck}"/></b></a></p>
  </c:if>
  <%= xssAPI.filterHTML(noResultsText) %>
  <span data-tracking="{event:'noresults', values:{'keyword': '<c:out value="${escapedQuery}"/>', 'results':'zero', 'executionTime':'${result.executionTime}'}, componentPath:'<%=xssAPI.getValidHref(resource.getResourceType())%>'}"></span>
</c:when>
<c:otherwise>
  <span data-tracking="{event:'search', values:{'keyword': '<c:out value="${escapedQuery}"/>', 'results':'${result.totalMatches}', 'executionTime':'${result.executionTime}'}, componentPath:'<%=xssAPI.getValidHref(resource.getResourceType())%>'}"></span>
  ${result.trackerScript}
  <%= xssAPI.filterHTML(properties.get("statisticsText", i18n.get("Results {0} - {1} of {2} for <b>{3}</b> ({4} seconds)", "Search query information", result.getStartIndex() + 1, result.getStartIndex() + result.getHits().size(), result.getTotalMatches(), escapedQuery, nf.format(Double.parseDouble(result.getExecutionTime().replace(",",".")))))) %>


    <div >
      <c:if test="${fn:length(search.relatedQueries) > 0}">
          <br/><br/>
          <!-- <%= xssAPI.encodeForHTML(relatedSearchesText) %> -->
          <c:forEach var="rq" items="${search.relatedQueries}">
              <a style="margin-right:10px" href="<c:url value="${currentPagePath}.html?q=${rq}"/>"><c:out value="${rq}"/></a>
          </c:forEach>
      </c:if>
      <br/>
      <c:forEach var="hit" items="${result.hits}" varStatus="status">
          <br/>
          <c:if test="${hit.extension != \"\" && hit.extension != \"html\"}">
              <span class="icon type_${hit.extension}"><img src="/etc/designs/default/0.gif" alt="*"></span>
          </c:if>
          <a class="body-1" href="<c:url value="${hit.URL}" context="/"/>" >${hit.title}</a>
          <div>${hit.excerpt}</div>
          <!-- <c:out value="${hit.URL}"/><c:if test="${!empty hit.properties['cq:lastModified']}"> - <c:catch><fmt:formatDate value="${hit.properties['cq:lastModified'].time}" dateStyle="medium"/></c:catch></c:if> - <a href="<c:url value="${hit.similarURL}" context="/"/>"><%= xssAPI.encodeForHTML(similarPagesText) %></a> -->
        <!-- <c:out value="${hit.URL}"/><c:if test="${!empty hit.properties['cq:lastModified']}"> - <c:catch><fmt:formatDate value="${hit.properties['cq:lastModified'].time}" dateStyle="medium"/></c:catch></c:if> -->
          <br/>
      </c:forEach>
  </div>
    <br/>
  <div class="wyn-aem-search__pagination wyn-l-margin-xxsmall--bottom">
    <c:if test="${fn:length(result.resultPages) > 1}">
      <!-- <%= xssAPI.encodeForHTML(resultPagesText) %> -->
      <c:if test="${result.previousPage != null}">
        <a href="<c:url value="${previousPageURL}" context="/"/>"><%= xssAPI.encodeForHTML(previousText) %></a>
      </c:if>
      <c:forEach var="page" items="${result.resultPages}"><%
          Search.Page searchPage = (Search.Page)pageContext.getAttribute("page");
          pageContext.setAttribute("searchPageURL", xssAPI.getValidHref(searchPage.getURL()));
          pageContext.setAttribute("searchPageIndex", xssAPI.encodeForHTML("" + (searchPage.getIndex() + 1)));
      %><c:choose>
              <c:when test="${page.currentPage}"><span class="is-active">${searchPageIndex}</span></c:when>
          <c:otherwise>
            <a href="<c:url value="${searchPageURL}" context="/"/>">${searchPageIndex}</a>
          </c:otherwise>
        </c:choose>
      </c:forEach>
      <c:if test="${result.nextPage != null}">
        <a href="<c:url value="${nextPageURL}" context="/"/>"><%= xssAPI.encodeForHTML(nextText) %></a>
      </c:if>
    </c:if>
  </div>
</c:otherwise>
</c:choose>
      </div>
  </div>
</div>






