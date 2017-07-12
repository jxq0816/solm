<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>危机数据处理管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#btnExport").click(function(){
                top.$.jBox.confirm("确认要导出危机数据处理列表吗？","系统提示",function(v,h,f){
                    if(v=="ok"){
                        $("#searchForm").attr("action","${ctx}/nms/crisisDispose/export");
                        $("#searchForm").submit();
                    }
                },{buttonsFocus:1});
                top.$('.jbox-body .jbox-icon').css('top','55px');
            });
        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
        function onsolvebtn(id){
            if( confirm("您确认要修改客户状态?")){
                var v="#"+id;
                $(v).submit();
            }
        }
    </script>
    <style>
        #contentTable th{
            text-align: center;
        }
        #contentTable tr td{
            text-align: center;
        }
        .ul-form select{
            font-weight:bold;font-size:18px;
        }
        .ul-form td{
            padding-left:20px;
            white-space:nowrap;
        }
    </style>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/nms/crisisDispose/">危机数据处理列表</a></li>
    <li><a href="${ctx}/nms/crisisData/form">危机数据添加</a></li>
</ul>
<form:form id="searchForm" modelAttribute="crisisDispose" action="${ctx}/nms/crisisDispose/" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
    <table class="ul-form">
        <td>编号:</td>
        <td><input name="crisisData.autoId" type="text" style="width:40px;" value="${query.crisisData.autoId}"/></td>
        <td><select name="crisisData.crisisLevel" style="width:80px;text-align:center;">
                <option value="">级别</option>
                <option value="1" <c:if test="${ query.crisisData.crisisLevel ==1}">selected = "selected"</c:if>> 一级</option>
                <option value="2" <c:if test="${ query.crisisData.crisisLevel ==2}">selected = "selected"</c:if>> 二级</option>
                <option value="3" <c:if test="${ query.crisisData.crisisLevel ==3}">selected = "selected"</c:if>> 三级</option>
                <option value="4" <c:if test="${ query.crisisData.crisisLevel ==4}">selected = "selected"</c:if>> 四级</option>
                <option value="5" <c:if test="${ query.crisisData.crisisLevel ==5}">selected = "selected"</c:if>> 五级</option>
                <option value="0" <c:if test="${ query.crisisData.crisisLevel ==0}">selected = "selected"</c:if>> 非危机</option>
            </select>
        </td>
        <td>
            <select name="issueStatus">
                <option value="">问题状态</option>
                <option value="0" <c:if test="${ query.issueStatus =='0'}">selected = "true"</c:if>>未处理</option>
                <option value="1" <c:if test="${ query.issueStatus =='1'}">selected = "true"</c:if>>已处理</option>
                <option value="2" <c:if test="${ query.issueStatus =='2'}">selected = "true"</c:if>>已上报</option>
              <%--  <option value="3" <c:if test="${ query.issueStatus =='3'}">selected = "true"</c:if>>已转出</option>--%>
                <option value="4" <c:if test="${ query.issueStatus =='4'}">selected = "true"</c:if>>已关闭</option>
            </select>
        </td>
        <td>
            <select name="customerStatus">
                <option value="">客户状态</option>
                <option value="0" <c:if test="${ query.customerStatus =='0'}">selected = "true"</c:if>>未解决</option>
                <option value="1" <c:if test="${ query.customerStatus =='1'}">selected = "true"</c:if>>已解决</option>
            </select>
        </td>
        <td>开始时间:</td>
        <td>
        <input name="startDate" type="text" readonly="readonly" style="width:80px;" class="Wdate"
           value="<fmt:formatDate value="${query.startDate}" pattern="yyyy-MM-dd"/>"
           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </td>
        <td>结束时间:</td>
        <td>
            <input name="endDate" type="text" readonly="readonly" maxlength="20" style="width:80px;" class="Wdate"
                   value="<fmt:formatDate value="${query.endDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </td>
        <td><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" /></td>
        <td><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></td>
    </table>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>编号</th>
        <th>来源</th>
        <th>危机级别</th>
        <th>客服</th>
        <th>原文</th>
        <th class="sort-column d.comment_time">发文时间</th>
       <%-- <th>动作状态</th>--%>
        <th>问题状态</th>
        <th>客户状态</th>
        <th>回复内容</th>
        <shiro:hasPermission name="nms:crisisDispose:edit">
            <th>操作</th>
        </shiro:hasPermission>
        <th>客户状态记录</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="i" varStatus="status">
        <tr>
            <td>${i.crisisData.autoId}</td>
            <td>${i.crisisData.sourceHostName}</td>
            <td>
                    ${fns:getDictLabel(i.crisisData.crisisLevel,'crisis_level','')}
            </td>
            <td>
                    ${i.user.name}
            </td>
            <td>
                <a target="_blank" href="${i.crisisData.pageUrl}">
                    ${i.crisisData.content}
                </a>
            </td>
            <td>
                <fmt:formatDate value="${i.crisisData.commentTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
           <%-- <td>
                <c:if test="${ i.actionStatus ==0}">未处理</c:if>
                <c:if test="${ i.actionStatus ==1}">已处理</c:if>
            </td>--%>
            <td>
                    ${fns:getDictLabel(i.issueStatus,'issue_status','')}
            </td>
            <td>
                    ${fns:getDictLabel(i.customerStatus,'customer_status','')}
            </td>
            <td>
                    ${i.reply}
            </td>

            <shiro:hasPermission name="nms:crisisDispose:edit">
                <td>
                        <a  class='btn btn-primary active' type="submit" style='width:40px' href="${ctx}/nms/crisisDispose/replyForm?id=${i.id}">处理</a>
                </td>
            </shiro:hasPermission>
            <td>
                <c:if test="${i.customerStatus ==0}"><input  class='btn btn-primary active' role='button' name='resolve' value='解决' style='width:40px' onclick=' onsolvebtn("${i.id}")'/></c:if>
                <c:if test="${i.customerStatus ==1}"><input  class='btn btn-primary active' role='button' name='resolve' value='已解决' style='width:40px' disabled='disabled' /></c:if>
                <form:form id="${i.id}" modelAttribute="crisisDispose" action="${ctx}/nms/crisisDispose/update" class="form-horizontal">
                    <input type="hidden" name="id" value="${i.id}"/>
                    <input type="hidden" name="customerStatus" value="1"/>
                </form:form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
<div id="divtest" style="display: none;"></div>
</body>
</html>