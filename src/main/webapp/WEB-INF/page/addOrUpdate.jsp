<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<title>归档任务编辑</title>
<head>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/archive.css" rel="stylesheet">
    <script type="text/javascript" src="/js/jquery-1.6.4.min.js"></script>
    <script type="text/javascript" src="/js/jquery.validate.min.js"></script>
    <script type="text/javascript" src="/js/validate.messages_cn.js"></script>
    <script type="text/javascript" src="/js/addOrUpdate.js" charset="UTF-8"></script>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>
<div id='content' class="container">
    <div class="row">
    </div>
    <form action="addOrUpdate" id="" method="post" class="form-horizontal">
        <input type="hidden" name="id" value="${entity.id}"/>
        <div class="control-group">
            <label class="control-label" for="sourceDatabase">源数据库</label>
            <div class="controls">
                <select id="sourceDatabase" name="sourceDatabase">
                    <c:forEach items="${dataSourceInfo}" var="dataSource">
                        <option value="${dataSource}"
                                <c:if test="${dataSource==entity.sourceDatabase}"> selected </c:if>
                                >${dataSource}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="sourceTable">源数据库表</label>
            <div class="controls">
                <%--<input required type="text" value="${entity.sourceTable}" id="sourceTable" name="sourceTable" placeholder="源数据库表"/>--%>
                <select id="sourceTable" name="sourceTable" data-table="${entity.sourceTable}"></select>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="targetDatabase">目标数据库</label>
            <div class="controls">
                <select id="targetDatabase" name="targetDatabase">
                    <c:forEach items="${dataSourceInfo}" var="dataSource">
                        <option value="${dataSource}"
                                <c:if test="${dataSource==entity.targetDatabase}"> selected </c:if>
                                >${dataSource}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="targetTable">目标据库表</label>
            <div class="controls">
                <%--<input required type="text" value="${entity.targetTable}" id="targetTable" name="targetTable" placeholder="目标数据库表"/>--%>
                <select id="targetTable" name="targetTable" data-table="${entity.targetTable}"></select>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="archiveType">归档类型</label>
            <div class="controls">
                <select id="archiveType" name="archiveType">
                    <c:forEach items="${archiveTypeInfo}" var="archiveType">
                        <option value="${archiveType.code}"
                                <c:if test="${archiveType.code==entity.archiveType}"> selected </c:if>
                                >${archiveType.desc}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="keyColumn">主键列</label>
            <div class="controls">
                <input required type="text" value="${entity.keyColumn}" id="keyColumn" name="keyColumn" placeholder="主键列"/>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="dayNumber">归档天数(多少天前)</label>
            <div class="controls">
                <input required type="text" value="${entity.dayNumber}" id="dayNumber" name="dayNumber" placeholder="归档天数"/>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="dateColumn">日期列</label>
            <div class="controls">
                <input required type="text" value="${entity.dateColumn}" id="dateColumn"
                       name="dateColumn" placeholder="日期列"/>
                <span class="help-inline">用于比较时间的日期列</span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="archiveColumns">所有归档列</label>
            <div class="controls">
                <input required type="text" value="${entity.archiveColumns}" id="archiveColumns"
                       name="archiveColumns" placeholder="所有归档列"/>
                <span class="help-inline">要符合sql select规范</span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="perSaveNum">归档每次记录数</label>
            <div class="controls">
                <input required type="text" value="${entity.perSaveNum}" id="perSaveNum"
                       name="perSaveNum" placeholder="归档每次记录数"/>
                <span class="help-inline">归档每次取和存的条数</span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="cronExpression">cronExpression表达式</label>
            <div class="controls">
                <input required type="text" value="${entity.cronExpression}" id="cronExpression"
                       name="cronExpression" placeholder="cronExpression表达式"/>
                <span class="help-inline">归档任务执行时间</span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="conditionSql">归档其他sql条件</label>
            <div class="controls">
                <input type="text" value="${entity.conditionSql}" id="conditionSql"
                       name="conditionSql" placeholder="归档其他sql条件"/>
                <span class="help-inline">需要以' and '开头</span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="perDeleteNum">删除每次记录数</label>
            <div class="controls">
                <input type="text" value="${entity.perDeleteNum}" id="perDeleteNum"
                       name="perDeleteNum" placeholder="删除每次记录数"/>
                <span class="help-inline">用于归档类型为删除时</span>
            </div>
        </div>

        <div class="control-group">
            <div class="controls">
                <button type="submit" class="btn">保存</button>
                <button type="reset" class="btn">取消</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>
