<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微博用户管理</title>
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
		<li class="active"><a href="${ctx}/weibo/weiboUser/">微博用户列表</a></li>
		<shiro:hasPermission name="weibo:weiboUser:edit"><li><a href="${ctx}/weibo/weiboUser/form">微博用户添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="weiboUser" action="${ctx}/weibo/weiboUser/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<label>id：</label>
				<form:input path="id" htmlEscape="false" maxlength="32" class="input-medium"/>
				<label>name：</label>
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>id</th>
				<th>昵称</th>
				<th>粉丝量</th>
				<th>关注数</th>
				<th>微博数</th>
				<th>收藏数</th>
				<th>注册时间</th>
				<shiro:hasPermission name="weibo:weiboUser:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="weiboUser">
			<tr>
				<td>${weiboUser.id}</td>
				<td><a href="${ctx}/weibo/weiboUser/form?id=${weiboUser.id}">
					${weiboUser.screenName}
				</a></td>
				<td>
				    ${weiboUser.followersCount}
				</td>
				<td>
					${weiboUser.friendsCount}
				</td>
				<td>
						${weiboUser.statusesCount}
				</td>
				<td>
						${weiboUser.favouritesCount}
				</td>
				<td>
					<fmt:formatDate value="${weiboUser.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="weibo:weiboUser:edit"><td>
    				<a href="${ctx}/weibo/weiboUser/form?id=${weiboUser.id}">修改</a>
					<a href="${ctx}/weibo/weiboUser/delete?id=${weiboUser.id}" onclick="return confirmx('确认要删除该微博用户吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>