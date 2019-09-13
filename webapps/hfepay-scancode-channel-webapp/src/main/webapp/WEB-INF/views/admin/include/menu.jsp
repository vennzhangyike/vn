<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- BEGIN SIDEBAR -->
<nav id="center_nav">
    <ul class="ucenterNav accordion" id="accordion">
    	<li class="personalCenter cur" id="personalCenter"><a href="#"><i class="icon-home"></i>主页中心</a></li>
		<c:forEach var="rootMenu" items="${rootMenu}" varStatus="statusroot">
			<li class="navInfoLi">
				<span class="link"><i class="${rootMenu.icon }">&nbsp;</i>${rootMenu.menuName }&nbsp;<i class="fa fa-chevron-down"></i></span>
				<c:if test="${fn:length(secondMenu)>0 }">
					<ul class="ucenterSub submenu">
						<c:forEach var="second" items="${secondMenu}" varStatus="statusSecond">
							 <c:if test="${rootMenu.id==second.key}">
								<c:forEach var="secondMenu" items="${second.value}" varStatus="status"> 
									<li>
										<a href="#" id="${secondMenu.url}" url="${path }${secondMenu.url}" 
											addtabs="${secondMenu.menuName}" title="${secondMenu.menuName}">
											<i class="${secondMenu.icon }"></i>
											${secondMenu.menuName}</a>
									</li>	
								 </c:forEach>
							</c:if> 
						</c:forEach>
					</ul>
				</c:if>
			</li>
		</c:forEach>
	</ul>
</nav>
<!-- END SIDEBAR -->