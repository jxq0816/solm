<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>危机数据处理管理</title>
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
    <li class="active"><a href="${ctx}/nms/crisisDispose/countNum">危机数据处理总量统计</a></li>
    <li><a href="${ctx}/nms/crisisDispose/monthCnt">危机数据处理月统计</a></li>
</ul>
<form:form id="searchForm" modelAttribute="crisisDispose" action="${ctx}/nms/crisisDispose/countNum" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

    <ul class="ul-form">
        <td>
           发文时间
        </td>
        <td>开始:</td>
        <td>
        <input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
           value="<fmt:formatDate value="${crisisDispose.startDate}" pattern="yyyy-MM-dd"/>"
           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </td>
        <td>结束:</td>
        <td>
            <input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="<fmt:formatDate value="${crisisDispose.endDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </td>
        <td><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></td>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>部门</th>
        <th>客服</th>
        <th>发文日期</th>
        <th>已处理</th>
        <th>待处理</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${list}" var="i">
        <tr>
            <td>
                    ${i.officeName}
            </td>
            <td>
                    ${i.name}
            </td>
            <td>
                        ${i.commentDay}
            </td>
            <td>
                    ${i.solvedCnt}
            </td>
            <td>
                    ${i.unsolvedCnt}
            </td>

        </tr>
    </c:forEach>
    <c:forEach items="${officeList}" var="i">
        <tr>
            <td>
                    ${i.officeName}
            </td>
            <td>
                   合计
            </td>

                <td>
                    ${i.commentDay}
                </td>

            <td>
                    ${i.solvedCnt}
            </td>
            <td>
                    ${i.unsolvedCnt}
            </td>

        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>