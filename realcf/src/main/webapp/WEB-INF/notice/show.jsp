<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>게시글</title>
</head>


<style>

table.main {
    border: 1px solid #444444;
    border-collapse: collapse;
    
    margin: 20px 0 5px 200px;
  }
  
table.main th {
  border: 1px solid #444444;
  font-weight: bold;
  background: #dcdcd1;
  

  }

table.main td {
    border: 1px solid #444444;
    background: white;
    height: 25px;
    padding: 0 0 0 5px;
  }

table.sub {
    left: 500px;
    margin: 0 0 0 450px;
  }

table.sub td{
    text-align:center; 
    width: 40px;
  }


h1 {
 margin: 20px 0 5px 500px;
}

</style>

<script>




</script>

<body>
    
    <h1> 게시판 </h1>
    <table class = "main">
           <tr>
            <th style = "width : 100px">글쓴이</td>
            <th style = "width : 200px">작성일자</td>
            <th style = "width : 400px">제목</td>
           </tr>
      
      <c:set var="num" value="0" />

    

      <c:forEach var = "q2" items = "${boardlist.content}" >
         <c:set var="num" value="${num + 1}" />
           <tr>
            <td style = "width : 100px">${q2.member.name}</td>
            <td style = "width : 200px">${q2.when}</td>
            <td style = "width : 400px"><a href = "/notice/showdetail?id=${q2.id}"> ${q2.name}</a></td>
           </tr>
      </c:forEach>
    </table>
    
    <table class = "sub">
      <tr>
        <c:if test = "${!boardlist.first}">
           <td><a href = "/notice/show/?page=${boardlist.number-1}">이전</a></td> 
        </c:if>

          <c:forEach begin="${boardlist.number}" end="${lastpage}" step="1" varStatus="status">
             <td><a href = "/notice/show/?page=${status.index}">${status.index}</a></td> 
          </c:forEach>

        <c:if test = "${!boardlist.last}">
           <td><a href = "/notice/show/?page=${boardlist.number+1}">다음</a></td> 
        </c:if>

      </tr>
    </table>
    
    
    
    

</body>
</html>
