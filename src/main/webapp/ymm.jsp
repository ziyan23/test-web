<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<title>Other</title>
<%@ include file="decorators.jsp" %>
<div class="page-header" align="center">
  <h1>
    <small>云马相关</small>
  </h1>
</div>
<div align="center">
  <table class="table table-hover">
    <thead>
    <tr>
      <td>
        <table align="center">
          <tr>
            <th width="300"><h4>功能简介</h4></th>
            <th width="750"><h4>输入参数</h4></th>
            <th width="250"><h4>操作</h4></th>
          </tr>
        </table>
      </td>
    </tr>
    </thead>

    <tbody>

    <tr>
      <td>
        <form target="_blank" class="form-inline" role="form" action="/getverifycode" method="post">
          <div align="center">
            <table>
              <tr>
                <td width="300" align="left"><h4>验证码查询</h4></td>
                <td width="750" align="left">
                  <div class="form-group">
                    <input type="text" class="form-control" name="telephoneNum" placeholder="telephoneNum">
                  </div>
                </td>
                <td width="250" align="left">
                  <button class="btn btn-primary active col-sm-4" type="submit">查 看</button>
                  <button class="btn btn-default col-sm-4" type="reset">重 置</button>
                </td>
              </tr>
            </table>
          </div>
        </form>
      </td>
    </tr>

    </tbody>
  </table>
</div>


</body>
</html>