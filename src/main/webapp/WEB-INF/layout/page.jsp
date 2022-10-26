<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t" %>
<%@ page session="false" %>
<html>
<head>
    <title>원장분석</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="naver-site-verification" content="6b2b1e163182752c9ca47fff9d693a7a2c84160e" />
    <meta name="google-site-verification" content="sJ1nOhbpS_11x-gH2IgxJYH0hvaDfqqhWdIdobhupNY" />
    
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
   
    <style>
        * { margin: 0; padding: 0; }
        body { font-family: 'Helvetica', sans-serif; }
        li { list-style: none; }
        a { text-decoration: none; }
    </style>
    <style>

    </style>
    <style>
        
        #main_gnb {
            overflow: hidden;
            border-bottom: 1px solid black;
            background: #32394A;
        }
        
        #main_gnb > ul.left {
            overflow: hidden;
            float: left;
        }
        
        #main_gnb > ul.right {
            overflow: hidden;
            float: right;
        }
        
        #main_gnb > ul.left > li { float: left; }
        #main_gnb > ul.right > li { float: left; }

        #main_gnb a {
            display: block;
            padding: 10px 20px;

            border-left: 1px solid #5F6673;
            border-right: 1px solid #242A37;
            color: white;
            font-weight: bold;
        }
        body { min-width: 760px; }
    </style>
    <!-- ì½íì¸  -->
    <style>
        #wrap { 
            overflow: hidden; 
            margin: 0 auto;
            position: relative;
            height: 95%;
            width: 100%;
       }
  
        #wrap > #content_wrap {
            float: left;
            left: 14%;
            height: 100%;
            width: 86%;
            background: white;
            position: absolute;
            margin: 0 auto;
           
        }

    </style>
    <style>
        #wrap {  }
        #main_lnb {
        
            background: #71B1D1;
            z-index: 100;
            float: left;
            width: 14%;
            height: 100%;
            position: absolute;
            left:0;
            top:0;
            }
            
        #main_lnb > ul > li > a {
            display: block;
            height:40px; line-height: 40px;
            padding-left: 15px;
            border-top: 1px solid #96D6F6;
            border-bottom: 1px solid #6298B2;
            color: white;
            font-weight: bold;
        }
        
    </style>
    <style>
        #content {
            background: white;
            
        }
        article { padding: 10px; }
    </style>
    <!-- í¸í° -->
    <style>
        #main_footer {
            padding: 10px;
            border-top: 3px solid black;
            text-align: center;
        }

 
  * {
       margin: 0; padding: 0;
       
   }
  a { text-decoration: none; }
  img { border: 0; }
  ul { list-style: none; }

  body {
    width: 100%;
    margin: 0 auto; 

     font-size: 15px;

    line-height: 2;

color: #333;

background-color: #fff;

  }
  

    
    </style>
</head>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
    
    <t:insertAttribute name="header" />
    

    <div id="wrap">
        <div id="main_lnb" class = "d-flex flex-column flex-shrink-0 p-3 text-white bg-dark">
            <ul class = "nav nav-pills flex-column mb-auto">
               <t:insertAttribute name="menu" />  
            </ul>
         </div>
       <div id="content_wrap">
            <t:insertAttribute name="body" />
       </div>
    </div>

 
</body>
</html>
