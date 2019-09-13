<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
   String path =  request.getContextPath();
   request.setAttribute("path", path);
%>
<header class="clearfix">
    <a href="${path }/adminManage/index" class="centerLogo"  style="position:relative;">
    	<img style="position:relative; left:-5px;height: 46px;" src="${currentChannelInfo.indexTopImg }" alt="${currentChannelInfo.nickName }数据">
    </a>
<!--     <div class="centerSearch"> -->
<!--         <input class="searchText" name="q" type="text" placeholder="搜索"> -->
<!--         <button id="searchBtn" type="button" class="searchBtn centerIcon"></button> -->
<!--     </div> -->
    <ul class="headerUl clearfix">
    	<li class="headerLi1">
            <a class="headerFoucs" href="#" id="/adminManage/usermessage" url="/adminManage/usermessage" 
						addtabs="用户站内信" title="用户站内信">
						<img src="${path}/resources/images/message.png">
						<span id="message"></span>
						<%-- 站内信(${messageSize}) --%>
					</a>
        </li>
        <li class="headerLi1">
            <div class="headerFoucs"><span>${currentUser.shortName }</span></div>
            <div class="centerLogout" ><span id="dealWithPwd">修改密码</span></div>
        </li>
        <li>
            <a class="headerInfo" href="${path }/index/logout">退出登录</a>
        </li>
    </ul>
</header>