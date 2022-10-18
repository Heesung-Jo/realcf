<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t" %>
<%@ page session="false" %>
<style>
#loginout {

  
  width: 10%;
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
            
            <li><a href="/company/purpose">상장회사분석</a></li>
            <li><a href="/cashflow/purpose">현금정산표</a></li>            
            <li><a href="/internal/explanation1">내부회계고도화</a></li>
            <li><a href="/game/puzzlefactory">게임</a></li>
        </ul>
    </nav>