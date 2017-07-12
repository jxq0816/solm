<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>危机数据处理管理</title>
    <meta name="decorator" content="default"/>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/nms/crisisDispose/">危机数据处理列表</a></li>
    <li class="active"><a href="${ctx}/nms/crisisDispose/form?id=${crisisDispose.id}">危机数据处理<shiro:hasPermission name="nms:crisisDispose:edit">${not empty crisisDispose.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="nms:crisisDispose:edit">查看</shiro:lacksPermission></a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="crisisDispose" action="${ctx}/nms/crisisDispose/updateLevel" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>

    <div class="controls">
            调整危机级别：
            <select name="crisisData.crisisLevel" style="width:80px;text-align:center;">
                <option value="">请选择</option>
                <option value="1" <c:if test="${ crisisDispose.crisisData.crisisLevel ==1}">selected = "selected"</c:if>> 一级</option>
                <option value="2" <c:if test="${ crisisDispose.crisisData.crisisLevel ==2}">selected = "selected"</c:if>> 二级</option>
                <option value="3" <c:if test="${ crisisDispose.crisisData.crisisLevel ==3}">selected = "selected"</c:if>> 三级</option>
                <option value="4" <c:if test="${ crisisDispose.crisisData.crisisLevel ==4}">selected = "selected"</c:if>> 四级</option>
                <option value="5" <c:if test="${ crisisDispose.crisisData.crisisLevel ==5}">selected = "selected"</c:if>> 五级</option>
                <option value="0" <c:if test="${ crisisDispose.crisisData.crisisLevel ==0}">selected = "selected"</c:if>> 非危机</option>
            </select>
    </div>
    <div class="form-actions" style="margin-left:15px;">
        <shiro:hasPermission name="nms:crisisDispose:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
    </form:form>
</body>
</html>
