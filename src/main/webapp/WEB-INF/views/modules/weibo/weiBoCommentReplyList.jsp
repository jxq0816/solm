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
		<li class="active"><a href="${ctx}/weibo/weiBoCommentReply/">微博评论列表</a></li>
		<shiro:hasPermission name="weibo:weiBoCommentReply:edit"><li><a href="${ctx}/weibo/weiBoCommentReply/form">微博评论添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="weiBoCommentReply" action="${ctx}/weibo/weiBoCommentReply/" method="post" class="breadcrumb form-search">
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
				<th>id</th>
				<th>评论外键ID</th>
				<th>回复人微博ID</th>
				<th>回复内容</th>
				<th>回复时间</th>
				<th>备注</th>
				<shiro:hasPermission name="weibo:weiBoCommentReply:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="weiBoCommentReply">
			<tr>
				<td>
						${weiBoCommentReply.id}
				</td>
				<td>
						${weiBoCommentReply.weiBoComment.id}
				</td>
				<td>
						${weiBoCommentReply.replyUserId}
				</td>
				<td>
						${weiBoCommentReply.replyText}
				</td>
				<td><a href="${ctx}/weibo/weiBoCommentReply/form?id=${weiBoCommentReply.id}">
					<fmt:formatDate value="${weiBoCommentReply.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${weiBoCommentReply.remarks}
				</td>
				<shiro:hasPermission name="weibo:weiBoCommentReply:edit"><td>
    				<a href="${ctx}/weibo/weiBoCommentReply/form?id=${weiBoCommentReply.id}">修改</a>
					<a href="${ctx}/weibo/weiBoCommentReply/delete?id=${weiBoCommentReply.id}" onclick="return confirmx('确认要删除该微博评论吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>