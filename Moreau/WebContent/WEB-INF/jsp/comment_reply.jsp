<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
</head>
<body>
<a href="detail.do?content_id=${content_id }">back</a>
<form action="" method="post" id="comment_reply1">
<textarea id="textinput" name="reply0" form="comment_reply1"></textarea>
<input type="submit">
</form>
${message }
</body>
</html>