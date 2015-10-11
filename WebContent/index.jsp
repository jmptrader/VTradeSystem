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
      <td align="left"><input type="text" id="symbol" /></td>
    </tr>
    <tr>
      <td align="right">Contract Expiry:</td>
      <td align="left"><input type="text" id="exp" /></td>
    </tr>
    <tr>
      <td align="right">Lots:</td>
      <td align="left"><input type="text" id="lots" /></td>
    </tr>
    <tr>
      <td align="right">Price:</td>
      <td align="left"><input type="text" id="price" /></td>
    </tr>
    <tr>
      <td align="right">Buy/Sell:</td>
      <td align="left"><input type="radio" id="buysell" value="buy" checked />Buy</td>
      <td align="left"><input type="radio" id="buysell" value="sell" />Sell</td>
    </tr>
    <tr>
      <td align="right">Trader:</td>
      <td align="left"><input type="text" id="trader" /></td>
    </tr>
    <tr>
      <td align="right">Transaction Date:</td>
      <td align="left"><input type="date" id="transDate"></td>
    </tr>
    <tr>
      <td align="right">Transaction Time:</td>
      <td align="left"><input type="time" id="transTime" /></td>
    </tr>
  </table>
</form>
</div>
<div>
	<input type="submit" onclick="getSubmitForm()" />
</div>
<p id="demo"></p>

</body>

</html>
<script >
function getSubmitForm(){
	
	/*  = '{"symbol":
		
		":[' +
	'{"firstName":"John","lastName":"Doe" },' +
	'{"firstName":"Anna","lastName":"Smith" },' +
	'{"firstName":"Peter","lastName":"Jones" }]}'; */
	var transInfo;
	price = document.getElementById("price").value;
	document.getElementById("demo").innerHTML = price;
	
}

</script>