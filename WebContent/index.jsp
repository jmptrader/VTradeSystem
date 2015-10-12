<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="org.json.*" import="java.sql.*"%>
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
<script src="https://fb.me/react-0.14.0.js"></script>
<script src="https://fb.me/react-dom-0.14.0.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/babel-core/5.8.23/browser.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.11.3/jquery.min.js" charset="utf-8"></script>
</head>
<body>
<h2>VTrade System</h2>
<div id="tradeInfo"></div>
<button id="getTradeInfo">Get Trade Info</button>
<p>Please input your transaction information below:</p>
<div class="container">
<form action="addTrade" method="POST">
  <table>
    <tr>
      <td align="right">Future Symbol:</td>
      <td align="left"><input type="text" name="symbol" /></td>
    </tr>
    <tr>
      <td align="right">Contract Expiry:</td>
      <td align="left"><input type="text" name="exp" /></td>
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
  <input type="submit">
</form>
</div>

</body>

</html>

<script type="text/babel">
var TradeInfo = React.createClass({
	  render: function() {
	    return (
	      <div>
			Hi~~~~~
			{this.props.tradeDate.map(function(trade){
                return <div key={trade}>trade: {trade}</div>;
            })}
	      </div>
	    );
	  }
	});

ReactDOM.render(<TradeInfo tradeDate={[]}/>, document.getElementById("tradeInfo"));
$('#getTradeInfo').on('click', function(){
	$.get("http://localhost:8080/VTradeSystem/getTrade", function(result) {
	  ReactDOM.render(<TradeInfo tradeDate = {result['test']}/>, document.getElementById("tradeInfo"));
    });
});
</script>