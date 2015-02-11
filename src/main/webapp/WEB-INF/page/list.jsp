<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/archive.css" rel="stylesheet">
    <script>
        function check(){
            if(!confirm('确定要删除选择的信息吗？\n此操作不可以恢复！')){
                return false;
            }
        }
    </script>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>
<div class="container" id="content">
    <p>
        <a href="<%=path%>/archive/addOrUpdate" class="btn btn-primary">添加新的归档任务</a>
    </p>
    <table class="table table-bordered table-hover  table-condensed">
        <tr>
            <td width="8%" >
                <div>
                    编号
                </div>
            </td>
            <td width="10%" >
                <div>
                    源库
                </div>
            </td>
            <td width="10%" >
                <div>
                    源表
                </div>
            </td>
            <td width="10%" >
                <div>
                    目标库
                </div>
            </td>
            <td width="10%" >
                <div>
                    目标表
                </div>
            </td>
            <td width="7%" >
                <div>
                    归档类型
                </div>
            </td>
            <td width="7%" >
                <div>
                    天数
                </div>
            </td>
            <td width="10%" >
                <div>
                    cronExpression
                </div>
            </td>
            <td width="7%" >
                <div>
                    是否允许
                </div>
            </td>
            <td width="21%" >
                <div>
                    操作
                </div>
            </td>
        </tr>
        <c:forEach items="${archiveTaskList }" var="entity">
            <tr>
                <td >
                    <div>
                        ${entity.id}
                    </div>
                </td>
                <td >
                    <div>
                        ${entity.sourceDatabase}
                    </div>
                </td>
                <td >
                    <div>
                        ${entity.sourceTable}
                    </div>
                </td>
                <td >
                    <div>
                        ${entity.targetDatabase}
                    </div>
                </td>
                <td >
                    <div>
                        ${entity.targetTable}
                    </div>
                </td>
                <td >
                    <div>
                        <c:if test="${entity.archiveType==0}">拷贝删除</c:if>
                        <c:if test="${entity.archiveType==1}">只拷贝</c:if>
                        <c:if test="${entity.archiveType==2}">只删除</c:if>
                    </div>
                </td>
                <td >
                    <div>
                        ${entity.dayNumber}
                    </div>
                </td>
                <td >
                    <div>
                        ${entity.cronExpression}
                    </div>
                </td>
                <td >
                    <div>
                        <c:choose>
                            <c:when test="${entity.isEnable}">
                               是
                            </c:when>
                            <c:otherwise>
                                否
                            </c:otherwise>
                        </c:choose>
                    </div>
                </td>
                <td >
                    <div>
                        <c:choose>
                            <c:when test="${entity.isEnable}">
                                <a href="<%=path%>/archive/disEnable.do?id=${entity.id}" class="btn btn-mini btn-success">不允许</a>
                                <a href="<%=path%>/archive/start?id=${entity.id}" class="btn btn-mini btn-danger">启动</a>
                            </c:when>
                            <c:otherwise>
                                <a href="<%=path%>/archive/enable.do?id=${entity.id}" class="btn btn-mini btn-warning">允许</a>
                            </c:otherwise>
                        </c:choose>
                        <a href="<%=path%>/archive/addOrUpdate?id=${entity.id}" class="btn btn-mini btn-success">编辑</a>
                        <a	onclick="javascript:return check();" class="btn btn-mini btn-danger"
                              href="<%=path%>/archive/delete?id=${entity.id}">删除</a>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
