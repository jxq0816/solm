<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>微博私信管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {

        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/weibo/weiBoMail/list">微博私信列表</a></li>
    <shiro:hasPermission name="weibo:weiBoMail:edit">
        <li><a href="${ctx}/weibo/weiBoMail/form">微博私信添加</a></li>
    </shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="weiBoMail" action="${ctx}/weibo/weiBoMail/list" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li>
            <label>发信人：</label>
            <form:input path="senderName" htmlEscape="false" maxlength="32" class="input-medium"/>
        </li>
        <li>
            <label>收信人：</label>
            <form:input path="receiverName" htmlEscape="false" maxlength="32" class="input-medium"/>
        </li>

        <li>
            <label>开始时间：</label>
            <input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="<fmt:formatDate value="${query.startDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>

        </li>
        <li>
            <label>结束时间：</label>
            <input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="<fmt:formatDate value="${query.endDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>

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
        <shiro:hasPermission name="weibo:weiBoMail:edit">
            <th>操作</th>
        </shiro:hasPermission>
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
            <td><a href="${ctx}/weibo/weiBoMail/form?id=${weiBoMail.id}">
                <fmt:formatDate value="${weiBoMail.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </a></td>
            <shiro:hasPermission name="weibo:weiBoMail:edit">
                <td>
                    <a href="${ctx}/weibo/weiBoMail/form?id=${weiBoMail.id}">修改</a>
                    <a href="${ctx}/weibo/weiBoMail/delete?id=${weiBoMail.id}"
                       onclick="return confirmx('确认要删除该微博私信吗？', this.href)">删除</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>