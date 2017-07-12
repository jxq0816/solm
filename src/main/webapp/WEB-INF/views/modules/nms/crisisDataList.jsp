<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>危机数据管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出危机数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/nms/crisisData/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
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
		<li class="active"><a href="${ctx}/nms/crisisData/">危机数据列表</a></li>
		<shiro:hasPermission name="nms:crisisData:edit"><li><a href="${ctx}/nms/crisisData/form">危机数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="crisisData" action="${ctx}/nms/crisisData/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table class="ul-form">
			<td>编号:</td>
			<td><input name="autoId" type="text" maxlength="20" class="input-medium" value="${query.autoId}"/></td>
			<td>微博ID:</td>
			<td><input name="weiBoId" type="text" maxlength="20" class="input-medium" value="${query.weiBoId}"/></td>
			<td><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></td>
			<td><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></td>
		</table>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编号</th>
				<th>微博ID</th>
				<th>原文</th>
				<th>危机级别</th>
				<th>转</th>
				<th>赞</th>
				<th>评</th>
				<th>发文时间</th>
				<shiro:hasPermission name="nms:crisisData:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="crisisData">
			<tr>
				<td>
						${crisisData.autoId}
				</td>
				<td>
						${crisisData.weiBoId}
				</td>
				<td>
						${crisisData.content}
				</td>
				<td>
						${crisisData.crisisLevel}
				</td>
				<td>
						${crisisData.repostsCount}
				</td>
				<td>
						${crisisData.attitudesCount}
				</td>
				<td>
						${crisisData.commentsCount}
				</td>

				<td><a href="${ctx}/nms/crisisData/form?id=${crisisData.id}">
					<fmt:formatDate value="${crisisData.commentTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<shiro:hasPermission name="nms:crisisData:edit"><td>
    				<a href="${ctx}/nms/crisisData/form?id=${crisisData.id}">修改</a>
					<a href="${ctx}/nms/crisisData/delete?id=${crisisData.id}" onclick="return confirmx('确认要删除该危机数据吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>