<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home Page</title>
	<style type="text/css">
    .container {
        width: 500px;
        clear: both;
    }
/*     .container input {
        width: 100%;
        clear: both;
    } */
   </style>
</head>
<body>
<h2>VTrade System</h2>

<p>Please input your transaction information below:</p>
<div class="container">
<form>
  <table>
    <tr>
      <td align="right">Future Symbol:</td>
      <td align="left"><input type="text" name="symbol" /></td>
    </tr>
    <tr>
      <td align="right">Contract Expiry:</td>
      <td align="left"><input type="text" name="contract" /></td>
    </tr>
    <tr>
      <td align="right">Lots:</td>
      <td align="left"><input type="text" name="lots" /></td>
    </tr>
    <tr>
      <td align="right">Price:</td>
      <td align="left"><input type="text" name="price" /></td>
    </tr>
    <tr>
      <td align="right">Buy/Sell:</td>
      <td align="left"><input type="radio" name="buysell" value="buy" checked />Buy</td>
      <td align="left"><input type="radio" name="buysell" value="sell" />Sell</td>
    </tr>
    <tr>
      <td align="right">Trader:</td>
      <td align="left"><input type="text" name="trader" /></td>
    </tr>
    <tr>
      <td align="right">Transaction Date:</td>
      <td align="left"><input type="date" name="transDate"></td>
    </tr>
    <tr>
      <td align="right">Transaction Time:</td>
      <td align="left"><input type="time" name="transTime" /></td>
    </tr>
  </table>
</form>
</div>
<form>
	<input type="submit"> 
</form>


</body>

</html>