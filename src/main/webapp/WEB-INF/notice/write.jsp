<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE HTML>
<html>

<head>
   <meta name = "_csrf" content = "${_csrf.token}" />
   <meta name = "_csrf_header" content = "${_csrf.headerName}" />

</head>

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
  font-size: 20px;
 }

    TEXTAREA {
       width: 100%;
       height: 470px;
       font-size: 20px;
     
    };



</style>

<head>
    <title id="pageTitle">글쓰기</title>

</head>
<body>

<div class="container">
    <form:form action="/notice/write" method="post" modelAttribute = "boarddata">
        <div class = "errors">
            <form:errors path="*" element="div" cssClass="alert alert-error"/>
        </div>
        <fieldset>
            <legend>글쓰기</legend>
            <input type = "hidden" name = "${_csrf.parameterName}" value = "${_csrf.token}"/>
             <div id ="content">
                <label>이메일: ${member.email}</label>
            </div>
            <div id ="content">
               <label>이름: ${member.name}</label>
            </div>
            <div id ="content">
                <label id = "float" class="control-label" for="name">제목: </label>
                <div>
                    <input id = "float" type="text" name = "name" style = "width: 100%; height: 25px;" />
                </div>
            </div>
            <div id ="content" style = "height: 500px">
                <label class="control-label" for="detail">내용:</label>
                <div >
                    <textarea name = "detail"></textarea>
                </div>
            </div>
            <div id ="content">
                    <input type="submit" value="제출하기" style = "width: 100%"/>
             </div>

        </fieldset>
    </form:form>

</div>


</body>
</html>
