<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/sframe/jsp/head.jsp"%>
<title>代码生成</title>
<script type="text/javascript" src="sample/sample1/allUser.js"></script>
</head>
<body>
	<h6>
		<a href="<%=basePath%>user/toAddUser.do">添加用户</a>
	</h6>

	<div class="table-responsive">
		<form action="" name="userFormQuery" id="userFormQuery">
			<div class="container-fluid">
				<div class="row">
					<ah:input name="tableName" label="表名" required="true" />
					<ah:input name="age" label="年龄" required="true" />
					<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3 form-inline">
						<div class="form-group">
							<label for="inputSex1" class="control-label"
								style="white-space:nowrap;">性别：</label>
							<div class="input-group">
								<select id="inputSex1" name="sex"
									class="selectpicker show-tick form-control">
									<option value="">全部</option>
									<option value="m">男</option>
									<option value="f">女</option>
								</select>
							</div>
						</div>
					</div>

					<ah:date name="entyDate" label="入职日期" />
 
					<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3  form-inline">
						<div class="form-group">
							<label for="inputPost1" class="control-label"
								style="white-space:nowrap;">岗位：</label>
							<div class="input-group">
								<select id="inputPost1" name="post"
									class="selectpicker show-tick form-control"
									data-live-search="true">
									<option value="">全部</option>
									<optgroup label="管理序列" data-subtext="another test"
										class="get-class">
										<option value="101">总经理</option>
										<option value="102">部门经理</option>
										<option value="103">项目经理</option>
									</optgroup>
									<optgroup label="技术序列" data-subtext="another test"
										class="get-class">
										<option value="201">行业专家</option>
										<option value="202">架构师</option>
										<option value="203">技术专家</option>
									</optgroup>
									<option value="301">高级工程师</option>
									<option value="302">工程师</option>
									<option value="303">助力工程师</option>
								</select>
							</div>
						</div>
					</div>

					<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
						<div class="form-group">
							<div class="input-group">
								<button type="button" class="btn btn-default" onclick='query();'>查询</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
		 
		<table id="DataGridTable2" class="table table-bordered"
			data-list="{sex:[{Y:是},{N:否}]}"
			data-url="<%=basePath%>coder/getColunmList.do" data-striped=false>
			<thead>
				<tr>
					<th data-field="columnName">字段名称</th>
					<th data-field="columnComment">字段描述</th>
					<th data-field="columnType">字段类型</th>
					<th data-field="columnSize">字段长度</th>
					<th data-field="defaultValue">默认值</th>
					<th data-field="requiredFlag" data-formatter="getList">是否必输</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar"></div>
	</div>
	<jsp:include page="/sframe/jsp/boot.jsp" flush="true" /><!--动态包含-->