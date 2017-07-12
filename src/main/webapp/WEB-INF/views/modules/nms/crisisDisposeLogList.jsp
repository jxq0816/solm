<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>危机数据处理记录管理</title>
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
		<li class="active"><a href="${ctx}/nms/crisisDisposeLog/">危机数据处理记录列表</a></li>
		<shiro:hasPermission name="nms:crisisDisposeLog:edit"><li><a href="${ctx}/nms/crisisDisposeLog/form">危机数据处理记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="crisisDisposeLog" action="${ctx}/nms/crisisDisposeLog/" method="post" class="breadcrumb form-search">
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
				<th>客服</th>
				<th>正在处理危机</th>
				<th>已处理危机</th>
				<th>最后更新时间</th>
				<shiro:hasPermission name="nms:crisisDisposeLog:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="crisisDisposeLog">
			<tr>
				<td>
						${crisisDisposeLog.user.name}
				</td>
				<td>
						${crisisDisposeLog.disposingCnt}
				</td>
				<td>
						${crisisDisposeLog.disposedCnt}
				</td>
				<td><a href="${ctx}/nms/crisisDisposeLog/form?id=${crisisDisposeLog.id}">
					<fmt:formatDate value="${crisisDisposeLog.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<shiro:hasPermission name="nms:crisisDisposeLog:edit"><td>
    				<a href="${ctx}/nms/crisisDisposeLog/form?id=${crisisDisposeLog.id}">修改</a>
					<a href="${ctx}/nms/crisisDisposeLog/delete?id=${crisisDisposeLog.id}" onclick="return confirmx('确认要删除该危机数据处理记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>