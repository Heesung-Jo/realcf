<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t" %>
<%@ page session="false" %>
<style>
#loginout {

  
  width: 120px;
  height: 80%
  border: thin solid #aaaaaa;
  background: black;
   -webkit-box-shadow: rgba(0, 0, 0, 0.5) 2px 2px 4px;
  -moz-box-shadow: rgba(0, 0, 0, 0.5) 2px 2px 4px;
   box-shadow: rgba(0, 0, 0, 0.5) 2px 2px 4px;
   z-index: 100;
         }
         
</style>
         
    <nav id="main_gnb">
        <ul class="left">
            <li><a href="/notice/purpose">홈페이지 소개</a></li>
            <li><a href="/cashflow/purpose">원장가공</a></li>
            <li><a href="/company/purpose">상장회사분석</a></li>
      
            
            <li id = "loginout" style = "position:absolute; left:1000px">
               <a href="/notice/login">로그인</a>
            </li>
            <li id = "loginout" style = "position:absolute; left:1120px">
               <a href="/notice/logout">로그아웃</a>
            </li>            
        </ul>
    </nav>