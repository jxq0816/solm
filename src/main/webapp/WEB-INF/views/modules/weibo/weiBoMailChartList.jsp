<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微博私信管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/weibo/weiBoMail/">微博私信列表</a></li>
		<shiro:hasPermission name="weibo:weiBoMail:edit"><li><a href="${ctx}/weibo/weiBoMail/form">微博私信添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="weiBoMail" action="${ctx}/weibo/weiBoMail/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<label>发信人：</label>
				<form:input path="senderName" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>发信人ID</th>
				<th>发信人</th>
				<th>收信人ID</th>
				<th>收信人</th>
				<th>内容</th>
				<th>时间</th>
				<shiro:hasPermission name="weibo:weiBoMail:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="weiBoMail">
			<tr>
				<td>${weiBoMail.senderId}</td>
				<td>${weiBoMail.senderName}</td>
				<td>${weiBoMail.receiverId}</td>
				<td>${weiBoMail.receiverName}</td>
				<td>${weiBoMail.text}</td>
				<td>
					<fmt:formatDate value="${weiBoMail.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<a  class='btn btn-primary active' href="${ctx}/weibo/weiBoMail/chart?last=1&receiverId=${weiBoMail.receiverId}&senderId=${weiBoMail.senderId}&receiverName=${weiBoMail.receiverName}&senderName=${weiBoMail.senderName}">对话</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>