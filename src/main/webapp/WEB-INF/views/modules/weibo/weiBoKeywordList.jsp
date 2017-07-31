<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微博关键字管理</title>
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
		<li class="active"><a href="${ctx}/weibo/weiBoKeyword/">微博关键字列表</a></li>
		<shiro:hasPermission name="weibo:weiBoKeyword:edit"><li><a href="${ctx}/weibo/weiBoKeyword/form">微博关键字添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="weiBoKeyword" action="${ctx}/weibo/weiBoKeyword/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<label>关键字：</label>
				<form:input path="keyword" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li>微博关键字只支持小写英文</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>关键字</th>
				<th>修改时间</th>
				<shiro:hasPermission name="weibo:weiBoKeyword:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="weiBoKeyword">
			<tr>
				<td>
						${weiBoKeyword.keyword}
				</td>
				<td><a href="${ctx}/weibo/weiBoKeyword/form?id=${weiBoKeyword.id}">
					<fmt:formatDate value="${weiBoKeyword.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>

				<shiro:hasPermission name="weibo:weiBoKeyword:edit"><td>
    				<a href="${ctx}/weibo/weiBoKeyword/form?id=${weiBoKeyword.id}">修改</a>
					<a href="${ctx}/weibo/weiBoKeyword/delete?id=${weiBoKeyword.id}" onclick="return confirmx('确认要删除该微博关键字吗？由于其他平台有可能使用该关键字，所以删除并不会取消订阅该微博关键字，仅仅本地删除，当微博推送至本平台，没有匹配的关键字，便不执行入库操作', this.href)">删除</a>
					<a href="${ctx}/weibo/weiBoKeyword/unsubscribe?id=${weiBoKeyword.id}" onclick="return confirmx('确认取消订阅微博关键字吗？取消后，该关键字也将从列表中删除', this.href)">取消订阅</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>