<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>危机数据处理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/nms/crisisDispose/">危机数据处理列表</a></li>
		<li class="active"><a href="${ctx}/nms/crisisDispose/form?id=${crisisDispose.id}">危机数据处理<shiro:hasPermission name="nms:crisisDispose:edit">${not empty crisisDispose.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="nms:crisisDispose:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="crisisDispose" action="${ctx}/nms/crisisDispose/update" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<%--<div class="control-group">
			<label class="control-label">fk_crisis_data_id：</label>
			<div class="controls">
				<form:input path="crisisData.id" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">外键客服编号：</label>
			<div class="controls">
				<form:input path="user.id" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">动作状态：</label>
			<div class="controls">
				<select name="actionStatus">
					<option value="">请选择</option>
					<option value="0" <c:if test="${ crisisDispose.actionStatus ==0}">selected = "selected"</c:if>>未处理</option>
					<option value="1" <c:if test="${ crisisDispose.actionStatus ==1}">selected = "selected"</c:if>>已处理</option>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">问题状态：</label>
			<div class="controls">
				<select name="issueStatus">
					<option value="">请选择</option>
					<option value="0" <c:if test="${ crisisDispose.issueStatus ==0}">selected = "selected"</c:if>>未处理</option>
					<option value="1" <c:if test="${ crisisDispose.issueStatus ==1}">selected = "selected"</c:if>>已处理</option>
					<option value="2" <c:if test="${ crisisDispose.issueStatus ==2}">selected = "selected"</c:if>>已上报</option>
					<option value="3" <c:if test="${ crisisDispose.issueStatus ==3}">selected = "selected"</c:if>>已转出</option>
					<option value="4" <c:if test="${ crisisDispose.issueStatus ==4}">selected = "selected"</c:if>>已关闭</option>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户状态：</label>
			<div class="controls">
				<select name="customerStatus">
					<option value="">请选择</option>
					<option value="0" <c:if test="${ crisisDispose.customerStatus ==0}">selected = "selected"</c:if>>未解决</option>
					<option value="1" <c:if test="${ crisisDispose.customerStatus ==1}">selected = "selected"</c:if>>已解决</option>
				</select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="nms:crisisDispose:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>