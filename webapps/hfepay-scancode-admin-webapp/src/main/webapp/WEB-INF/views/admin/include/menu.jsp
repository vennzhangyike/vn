<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- BEGIN SIDEBAR -->
<nav id="center_nav">
    <ul class="ucenterNav accordion" id="accordion">
    	<li class="personalCenter cur" id="personalCenter"><a href="#"><i class="icon-home"></i>主页中心</a></li>
		<c:forEach var="rootScanAdminMenu" items="${rootScanAdminMenu}" varStatus="statusroot">
			<li class="navInfoLi">
				<span class="link"><i class="${rootScanAdminMenu.icon }">&nbsp;</i>${rootScanAdminMenu.menuName }&nbsp;<i class="fa fa-chevron-down"></i></span>
				<c:if test="${fn:length(secondScanAdminMenu)>0 }">
					<ul class="ucenterSub submenu">
						<c:forEach var="second" items="${secondScanAdminMenu}" varStatus="statusSecond">
							 <c:if test="${rootScanAdminMenu.id==second.key}">
								<c:forEach var="secondScanAdminMenu" items="${second.value}" varStatus="status"> 
									<li>
										<a href="#" id="${secondScanAdminMenu.url}" url="${path }${secondScanAdminMenu.url}" 
											addtabs="${secondScanAdminMenu.menuName}" title="${secondScanAdminMenu.menuName}">								
											<i class="${secondScanAdminMenu.icon }"></i>
											${secondScanAdminMenu.menuName}</a>
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
