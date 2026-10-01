<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<nav class="pagination" aria-label="Phân trang">
<c:choose>
<c:when test="${page > 1}">
<a href="${pageContext.request.contextPath}${pageUrl}?page=${page-1}">Previous</a>
</c:when>
<c:otherwise>
<span class="disabled">Previous</span>
</c:otherwise>
</c:choose>
<c:forEach begin="1" end="${pages}" var="p">
<a class="${p == page ? 'active' : ''}" href="${pageContext.request.contextPath}${pageUrl}?page=${p}" aria-label="Trang ${p}">${p}</a>
</c:forEach>
<c:choose>
<c:when test="${page < pages}">
<a href="${pageContext.request.contextPath}${pageUrl}?page=${page+1}">Next</a>
</c:when>
<c:otherwise>
<span class="disabled">Next</span>
</c:otherwise>
</c:choose>
</nav>
<p class="muted center">Trang ${page} / ${pages} · ${total} kết quả · 6 mục/trang</p>
