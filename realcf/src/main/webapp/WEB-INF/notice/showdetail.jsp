<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE HTML>
<html>



<style>

form {

 width: 1100px;
 margin: 10px 0 10px 40px;

}


#content {
  margin: 10px 0 10px 40px;
  width: 1000;
  border: thin solid #aaaaaa;
  -webkit-box-shadow: rgba(0, 0, 0, 0.5) 2px 2px 4px;
  -moz-box-shadow: rgba(0, 0, 0, 0.5) 2px 2px 4px;
  box-shadow: rgba(0, 0, 0, 0.5) 2px 2px 4px;
  overflow: hidden;

 }





</style>

<head>
    <title id="pageTitle">글쓰기</title>

</head>
<body>

<div class="container">
 
            <div id ="content">
                <label>이메일: ${member.email}</label>
            </div>
            <div id ="content">
               <label>이름: ${member.name}</label>
            </div>
            <div id ="content">
                <label >제목: </label>
                <div>
                    ${boarddata.name}
                </div>
            </div>
            <div id ="content" style = "height: 500px">
                <label class="control-label" for="detail">내용:</label>
                <div >
                    <div style = "width: 100%; height: 500px">
                        ${boarddata.detail}
                    </div>
                </div>
            </div>
 
 

</div>


</body>
</html>
