<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>微博危机数据处理</title>
    <meta name="decorator" content="default"/>
    <link rel="stylesheet" href="${ctxStatic}/css/crisisStyle.css">
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
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/weibo/weiBoStatusDispose/">危机数据处理列表</a></li>
    <li class="active"><a href="${ctx}/weibo/weiBoStatusDispose/replyForm?id=${weiBoStatusDispose.id}">评论</a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="weiBoStatusDispose" action="${ctx}/weibo/weiBoStatusDispose/replySave" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <span style="color:red">${errorMsg}</span>
    <div class="box">
        <div id="userInformation" style="width:190px;height:400px;background: #fff;">
            <ul class="userTop">
                <li><img src="${customer.avatarLarge}" class="img"></li>
                <li class="userName"><span>${customer.screenName}</span></li>
                <li class="userLabel">${customer.verifiedReason}</li>
            </ul>
            <table class="userMid">
                <tr>
                    <td>${customer.friendsCount}</td>
                    <td>${customer.followersCount}</td>
                    <td>${customer.statusesCount}</td>
                </tr>
                <tr>
                    <td>关注</td>
                    <td>粉丝</td>
                    <td>微博</td>
                </tr>
            </table>
            <div class="userLevel">
                <p class="userLevel_1">V微博认证</p>
                <p class="userLevel_2">LV.${customer.unrank}</p>
            </div>
            <ul class="userFooter">
                <li>${customer.location}</li>
                <li>${customer.verifiedReason}</li>
                <li>简介：${customer.description}</li>
                <li>标签：<span>${customer.abilityTags}</span></li>
            </ul>
        </div>
        <div style="position:absolute;left:248px;top:46px;" class="control-groupAll">
            <div class="control-group">
                <label class="control-label" style="width:90px;">热度：</label>
                <div class="controls">
                    <table>
                        <tr>
                            <td>转</td> <td>${weiBoStatusDispose.repostsCount}</td>
                            <td>赞</td> <td>${weiBoStatusDispose.attitudesCount}</td>
                            <td>评</td> <td>${weiBoStatusDispose.commentsCount}</td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" style="width:90px;">发文内容：</label>
                <div class="controls">${weiBoStatusDispose.weiBoStatus.text}</div>
            </div>

            <div class="control-group">
                <label class="control-label" style="width:90px;">评论列表：</label><br/>
                <div class="controls">
                    <table>
                        <c:forEach items="${commentList}" var="comment">
                            <tr>
                                <td>我：</td>
                                <td>${comment.commentText}</td>
                                <td style="width:200px;text-align: right">时间:</td>
                                <td><fmt:formatDate value="${comment.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            </tr>

                            <c:forEach items="${comment.replyList}" var="reply">
                                <tr>
                                    <td>${customer.screenName}：</td>
                                    <td>${reply.replyText}</td>
                                    <td style="width:200px;text-align: right">时间:</td>
                                    <td><fmt:formatDate value="${reply.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                </tr>
                            </c:forEach>

                        </c:forEach>
                    </table>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" style="width:90px;">评论内容：</label>
                <div class="controls">
                    <textarea name="reply" class="input-xlarge "
                              style="width:500px;height:200px;display:block;"></textarea>
                </div>
                <input type="hidden" name="weiBoStatus.weiboUserId" value="${customer.id}">

                <div class="form-actions">
                    <shiro:hasPermission name="nms:crisisDispose:edit"><input id="btnSubmit" class="btn btn-primary"
                                                                              type="submit" value="回 复"/>&nbsp;
                        <a href="${ctx}/weibo/weiBoStatusDispose/report?id=${weiBoStatusDispose.id}" role="button"
                           onclick="return confirmx('是否确定将该条信息进行上报？', this.href)" class="btn btn-primary btn-sm">上报</a>

                        <a href="${ctx}/weibo/weiBoStatusDispose/updateLevelForm?id=${weiBoStatusDispose.id}" role="button"
                           onclick=" " class="btn btn-primary btn-sm">调整危机级别</a>

                        <a href="${ctx}/weibo/weiBoMail/chart?senderId=${customer.id}&last=1" role="button"
                         class="btn btn-primary btn-sm">私信</a>
                    </shiro:hasPermission>
                    <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"
                           class="btn btn-success btn-sm"/>

                </div>
            </div>
        </div>
    </div>
</form:form>
<form:form id="${crisisDispose.id}" modelAttribute="crisisDispose" action="${ctx}/nms/crisisDispose/update"
           class="form-horizontal">
    <input type="hidden" name="id" value="${crisisDispose.id}"/>
    <input type="hidden" name="issueStatus" value="4"/>
</form:form>
</body>
</html>