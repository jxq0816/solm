<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微博管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出微博数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/weibo/weiBoStatus/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
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
		<li class="active"><a href="${ctx}/weibo/weiBoStatus/">微博列表</a></li>
		<shiro:hasPermission name="weibo:weiBoStatus:edit"><li><a href="${ctx}/weibo/weiBoStatus/form">微博添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="weiBoStatus" action="${ctx}/weibo/weiBoStatus/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<label>微博ID：</label>
				<form:input path="id" htmlEscape="false" maxlength="32" class="input-medium"/>
				<label>关键字：</label>
				<form:input path="keyword" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>微博ID</th>
				<th>所匹配的关键字</th>
				<th>昵称</th>
				<th>正文</th>
				<th>发文时间</th>
				<th>修改时间</th>
				<shiro:hasPermission name="weibo:weiBoStatus:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="weiBoStatus">
			<tr>
				<td>
						${weiBoStatus.id}
				</td>
				<td>
						${weiBoStatus.keyword}
				</td>
				<td>
						${weiBoStatus.screenName}
				</td>
				<td>
						${weiBoStatus.text}
				</td>
				<td>
					<fmt:formatDate value="${weiBoStatus.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td><a href="${ctx}/weibo/weiBoStatus/form?id=${weiBoStatus.id}">
					<fmt:formatDate value="${weiBoStatus.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>

				<shiro:hasPermission name="weibo:weiBoStatus:edit"><td>
    				<a href="${ctx}/weibo/weiBoStatus/form?id=${weiBoStatus.id}">修改</a>
					<a href="${ctx}/weibo/weiBoStatus/delete?id=${weiBoStatus.id}" onclick="return confirmx('确认要删除该微博吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>