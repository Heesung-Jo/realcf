<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<html> 
<head> 
<meta charset="UTF-8"> 
<title>Title</title> 
</head> 
<body> 

<h2>로그인</h2>

<p>안녕하세요. 게시글 항목 이외에 나머지 항목은 접근할 수 있습니다.</p>


<br/><br/> 
<div id = "content">
 <a href="javascript:;" class="btn_social" data-social="google">구글ID로 로그인</a><br/> 
</div>
 <script> let socials = document.getElementsByClassName("btn_social"); 
 for(let social of socials) { 
	 social.addEventListener('click', function(){ let socialType = this.getAttribute('data-social'); 
	 location.href="/oauth2/authorization/" + socialType; }) }
 </script> 
 </body> 
 </html>

<style>

 a {
  color: sky;
  text-decoration:none;
 }
 
 h2 {
   margin: 10px 0 10px 40px;
 }
 
 p {
   margin: 10px 0 10px 60px;
 }
 
#content {

  margin: 10px 0 10px 60px;
  width: 120px;
  border: thin solid #aaaaaa;
   -webkit-box-shadow: rgba(0, 0, 0, 0.5) 2px 2px 4px;
  -moz-box-shadow: rgba(0, 0, 0, 0.5) 2px 2px 4px;
   box-shadow: rgba(0, 0, 0, 0.5) 2px 2px 4px;
  -webkit-border-radius: 8px;
  -moz-border-radius: 8px;
  border-radius: 8px;
         }
         
</style>  