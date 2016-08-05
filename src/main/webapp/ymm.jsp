<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<title>运满满</title>
<%@ include file="decorators.jsp" %>
<div class="page-header" align="center">
  <h1>
    <small>运满满相关</small>
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
        <div name="getverifycode" name="getverifycode" class="form-inline">
          <div align="center">
            <table>
              <tr>
                <td width="300" align="left"><h4>验证码查询</h4></td>
                <td width="750" align="left">
                  <div class="form-group">
                    <input id="telephoneCode" type="text" class="form-control" name="telephoneNum" placeholder="telephoneNum">
                  </div>
                </td>
                <td width="250" align="left">
                  <button class="btn btn-primary active col-sm-4" onclick="getverifycode()">查 看</button>
                  <button class="btn btn-default col-sm-4" type="reset">重 置</button>
                </td>
              </tr>
            </table>
          </div>
        </div>
      </td>
    </tr>

    <tr>
      <td>
       <div id="updateBalance" name="updateBalance" class="form-inline">
          <div align="center">
            <table>
              <tr>
                <td width="300" align="left"><h4>用户余额更新</h4></td>
                <td width="750" align="left">
                  <div class="form-group">
                    <input id="telephone" type="text" class="form-control" name="telephoneNum" placeholder="telephoneNum">
                  </div>
                  <div class="form-group">
                    <input id="balance" type="text" class="form-control" name="accountBalance" placeholder="accountBalance">
                  </div>
                </td>
                <td width="250" align="left">
                  <button class="btn btn-primary active col-sm-4" onclick="updateBalance()">提 交</button>
                  <button class="btn btn-default col-sm-4" type="reset">重 置</button>
                </td>
              </tr>
            </table>
          </div>
        </div>
      </td>
    </tr>
    </tbody>
  </table>
</div>

<script>
  function updateBalance() {
    var data = {
      telephoneNum:$("#telephone").val(),
      accountBalance:$("#balance").val()
    }
    $.ajax({
      cache: true,
      type: "POST",
      url:"ymm/updateBalance",
      data:$.param(data),// 你的formid
      dataType:"text",
      success : function(data) {
        alert("用户余额更新成功,当前余额为:" + data);
      }
    });
    return false;
  }


  function getverifycode() {
    var data = {
      telephoneNum:$("#telephoneCode").val()
    }
    $.ajax({
      cache: true,
      type: "POST",
      url:"ymm/getverifycode",
      data:$.param(data),// 你的formid
      dataType:"text",
      success : function(data) {
        alert("验证码为:" + data);
      }
    });
    return false;
  }
</script>

</body>
</html>