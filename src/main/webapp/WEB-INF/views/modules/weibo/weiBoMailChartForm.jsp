<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>微博私信管理</title>
    <meta name="decorator" content="default"/>
    <link rel="stylesheet" href="${ctxStatic}/css/mailchartStyle.css">
    <script src="${ctxStatic}/js/weibo_chart.js?version=2943" type="text/javascript"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
    <style>
        .box {
            width: 100%;
        }

        .midBubble {
            float: right;
            margin-right: 5%;
            height: 100%;
            min-height:400px;
            width:70%;
            border: 1px solid #CCCCCC;
            padding-top:10px;
            padding-bottom:10px;
            overflow: hidden;
        }

        .bubble {
            margin: 0px auto;
            width: 550px;
            height:100%;
        }

        .demo {
            margin-bottom: 20px;
            margin-left:-20px;
            margin-right:-20px;
            position: relative;
        }

        .triangle {
            position: absolute;
            top: 50%;
            margin-top: -8px;
            margin-right:-50px;
            left: -8px;
            display: block;
            width: 0;
            height: 0;
            overflow: hidden;
            line-height: 0;
            font-size: 0;
            border-bottom: 8px solid #FFF;
            border-top: 8px solid #FFF;
            border-left: none;
            border-right: 8px solid #CECEFF;
        }

        .demo .article {
            max-width:400px;
            float: left;
            color: #fff;
            display: inline-block;
            zoom: 1;
            word-wrap: break-word;
            padding: 5px 10px;
            border: 1px solid #3079ED;
            border-radius: 5px;
            background-color:#CECEFF;
        }
        .article a {
            color: #000;
            font-size: 15px;
            font-family:"黑体";
            text-decoration: none;
        }
        .fr {
            padding-left: 0px;
            padding-right:-20px;
        }

        .fr .triangle {
            left: auto;
            right: 42px;
            border-bottom: 8px solid #FFF;
            border-top: 8px solid #FFF;
            border-right: none;
            border-left: 8px solid #CECEFF;
        }

        .fr .article {
            float: right;
        }

        .top {
            font-size: 26px;
            text-align: center;
            margin-bottom: 20px;
            padding-left:120px;
        }
    </style>

</head>
<body>
<input id="ip" name="ip" type="hidden" value="${ip}"/>
<input id="port" name="port" type="hidden" value="${port}"/>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/weibo/weiBoMail/">微博私信列表</a></li>
    <li class="active"><a
            href="${ctx}/weibo/weiBoMail/chart?last=1&receiverId=${query.receiverId}&senderId=${query.senderId}&receiverName=${query.receiverName}&senderName=${query.senderName}">微博私信<shiro:hasPermission
            name="weibo:weiBoMail:edit">回复</shiro:hasPermission><shiro:lacksPermission
            name="weibo:weiBoMail:edit">查看</shiro:lacksPermission></a></li>
</ul>
<br/>
<div class="top">
    <input id="fansId" name="fansId" value="${fans.id}" type="hidden"/>
    和${fans.screenName}的聊天
</div>
<%--用户个人信息 start--%>
<div class="box clearfix">
    <div id="userInformation" style="width:190px;height:400px;background: #fff;float:left">
        <ul class="userTop">
            <li><img src="${fans.avatarLarge}" class="img"></li>
            <li class="userName"><span>${fans.screenName}</span></li>
            <li class="userLabel">${fans.verifiedReason}</li>
        </ul>
        <table class="userMid">
            <tr>
                <td>${fans.friendsCount}</td>
                <td>${fans.followersCount}</td>
                <td>${fans.statusesCount}</td>
            </tr>
            <tr>
                <td>关注</td>
                <td>粉丝</td>
                <td>微博</td>
            </tr>
        </table>
        <div class="userLevel">
            <c:if test="${fans.verified==true}">
                <p class="userLevel_1">V微博认证</p>
            </c:if>
            <p class="userLevel_2">LV.${fans.unrank}</p>
        </div>
        <ul class="userFooter">
            <li>${fans.location}</li>
            <li>${fans.verifiedReason}</li>
            <li>简介：${fans.description}</li>
            <li>标签：<span>${fans.abilityTags}</span></li>
        </ul>
    </div>
    <%--用户个人信息end--%>
    <div class="midBubble">
        <c:forEach items="${page.list}" var="weiBoMail">
            <div items="${page.list}" var="weiBoMail">
                <div class="bubble">

                    <c:if test="${query.receiverId==weiBoMail.senderId}">
                        <div class="demo clearfix fr">
                            <span class="triangle"></span>
                            <div class="article">
                                <a href="javascript:void(0);"
                                   title="${weiBoMail.senderName}&#10;${weiBoMail.createdAt}">${weiBoMail.text}</a>
                            </div>
                                <%--<div class="article">${weiBoMail.senderName}</div>--%>
                        </div>
                    </c:if>
                    <c:if test="${query.senderId==weiBoMail.senderId}">
                        <div class="demo clearfix">
                            <span class="triangle right"></span>
                            <div class="article">
                                <a href="javascript:void(0);"
                                   title="${weiBoMail.senderName}&#10;${weiBoMail.createdAt}">${weiBoMail.text}</a>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
        </c:forEach>
    </div>

</div>
<div class="footer" style="width:100%;height:60px;">
    <form:form id="charForm" modelAttribute="weiBoMail" action="${ctx}/weibo/weiBoMail/chartSave" method="post"
               class="form-horizontal">
        <sys:message content="${message}"/>
        <input id="receiverId" name="receiverId" value="${query.senderId}" type="hidden"/>
        <input id="receiverName" name="receiverName" value="${query.senderName}" type="hidden"/>
        <input id="senderId" name="senderId" value="${query.receiverId}" type="hidden"/>
        <input id="senderName" name="senderName" value="${query.receiverName}" type="hidden"/>
        <div class="control-group" style="margin-top:20px;">
            <div class="controls">
                <textarea id="text" type="text" name="text" htmlEscape="false" rows="4" maxlength="255"
                          class="input-xxlarge" style="height: 20px;margin-left:20%;"></textarea>
                <input class="btn btn-primary" onclick="chartCommit()" value="发送" style="width:40px;"/>
            </div>
        </div>
    </form:form>
</div>
<form:form id="searchForm" modelAttribute="weiBoMail" action="${ctx}/weibo/weiBoMail/chart" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="receiverId" name="receiverId" type="hidden" value="${query.receiverId}"/>
    <input id="receiverName" name="receiverName" type="hidden" value="${query.receiverName}"/>
    <input id="senderId" name="senderId" type="hidden" value="${query.senderId}"/>
    <input id="senderName" name="senderName" type="hidden" value="${query.senderName}"/>
</form:form>
<div class="pagination">${page}</div>
</body>
</html>