<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微博评论管理</title>
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
		<li class="active"><a href="${ctx}/weibo/weiBoComment/">微博评论列表</a></li>
		<shiro:hasPermission name="weibo:weiBoComment:edit"><li><a href="${ctx}/weibo/weiBoComment/form">微博评论添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="weiBoComment" action="${ctx}/weibo/weiBoComment/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>微博ID</th>
				<th>微博发文人ID</th>
				<th>客服评论人ID</th>
				<th>客服评论</th>
				<th>回复时间</th>
				<shiro:hasPermission name="weibo:weiBoComment:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="weiBoComment">
			<tr>
				<td>
						${weiBoComment.weiBoId}
				</td>
				<td>
						${weiBoComment.weiBoUserId}
				</td>
				<td>
						${weiBoComment.commentUserId}
				</td>
				<td>
					${weiBoComment.commentText}
				</td>
				<td><a href="${ctx}/weibo/weiBoComment/form?id=${weiBoComment.id}">
					<fmt:formatDate value="${weiBoComment.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<shiro:hasPermission name="weibo:weiBoComment:edit"><td>
    				<a href="${ctx}/weibo/weiBoComment/form?id=${weiBoComment.id}">修改</a>
					<a href="${ctx}/weibo/weiBoComment/delete?id=${weiBoComment.id}" onclick="return confirmx('确认要删除该微博评论吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>