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
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<script src="https://fb.me/react-0.14.0.js"></script>
<script src="https://fb.me/react-dom-0.14.0.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/babel-core/5.8.23/browser.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.11.3/jquery.min.js"
	charset="utf-8"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.6.0/underscore-min.js"></script>
<script type="text/javascript" src="js/griddle.js"></script>
<script type="text/babel" src="js/util.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</head>
<body>
	<h2 align="center">VTrade System</h2>
	<h3 align="center">
		Futrue page, go to <a href="swap.jsp">swap</a> page.
	</h3>
	<h3 align="center" id="serverDate"></h3>
	<div class="container">
		<div class="col-md-4">
			<div class="panel panel-default">
				<div class="panel-heading">Please input your transaction
					information below:</div>
				<div class="panel-body">
					<div class="col-md-12">
						<form action="addTrade" method="POST" class="form-horizontal"
							role="form">
							<div class="form-group">
								<label>Order Type: </label> <input type="radio" name="orderType"
									value="Market" checked> Market <input type="radio"
									name="orderType" value="Limit"> Limit <input
									type="radio" name="orderType" value="Pegged"> Pegged
							</div>
							<div class="form-group">
								<label for="exampleInputEmail1">Future Symbol:</label> <input
									required type="text" class="form-control" name="symbol"
									placeholder="Enter Symbol">
							</div>
							<div class="form-group">
								<label>Contract Expiry:</label> <input required type="month"
									class="form-control" name="exp" placeholder="Enter expiry date">
							</div>
							<div class="form-group">
								<label>Lots</label> <input required type="number" min="2"
									max="1000000" step="1" class="form-control" name="lots"
									placeholder="Enter lots">
							</div>
							<div class="form-group">
								<label>Prices</label> <input required type="number" min="0"
									max="50000" step="0.01" class="form-control" name="price"
									placeholder="Enter Prices">
							</div>
							<div class="form-group">
								<label>Buy/Sell:</label> <input type="radio" name="buysell"
									value="buy" checked> Buy <input type="radio"
									name="buysell" value="sell"> Sell

							</div>
							<div class="form-group">
								<label>Trader</label> <input required type="number"
									class="form-control" name="trader"
									placeholder="Enter trader id">
							</div>
							<input class="btn btn-lg btn-primary btn-block" type="submit">
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-8">
			<input class="btn btn-lg btn-primary btn-block"
				value="Get Future expire today"
				onClick="window.open('${pageContext.request.contextPath}/getEODFutureTodayCSV');">
			<input class="btn btn-lg btn-primary btn-block"
				value="Get Future expire after today"
				onClick="window.open('${pageContext.request.contextPath}/getEODFutureCSV');">
			<div class="panel panel-default">
				<div class="panel-heading">Transactions</div>
				<div id="transactionInfo"></div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">Download data</div>
				<div class="panel-body">
					<div class="col-md-12">
						<input class="btn btn-lg btn-primary btn-block"
							value="Output porfit and loss report"
							onClick="window.open('${pageContext.request.contextPath}/getAllTransactionCSV');">
						<form target="_blank" action="getTransactionCSVWithCondition"
							method="GET" class="form-horizontal" role="form">
							<div class="form-group">
								<label for="exampleInputEmail1">Trader id:</label> <input
									required type="number" min="1" class="form-control"
									placeholder="Enter trader id" name="traderId">
							</div>
							<input class="btn btn-lg btn-primary btn-block" type="submit"
								value="Output p&l details for given trader id">
						</form>
						<form target="_blank" action="getTransactionCSVWithPrice"
							method="GET" class="form-horizontal" role="form">
							<div class="form-group">
								<label for="exampleInputEmail1">Trader id:</label> <input
									required type="number" min="1" class="form-control"
									placeholder="Enter trader id" name="traderId">
							</div>
							<input class="btn btn-lg btn-primary btn-block" type="submit"
								value="Output aggregation position considering price">
						</form>
					</div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">Orders</div>
				<div id="tradeInfo"></div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">Download data</div>
				<div class="panel-body">
					<div class="col-md-12">
						<input class="btn btn-lg btn-primary btn-block"
							value="Download all data as a csv file"
							onClick="window.open('${pageContext.request.contextPath}/getCSV');">
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>

<script type="text/babel">
$('#getCSVWithCondtionButton').onclick = function(){
	var traderid = document.getElementById("getCSVWithCondtionTraderId").value;
	window.open('${pageContext.request.contextPath}/getCSVWithCondition/${traderid}');
}


var Lots_custom = React.createClass({
  render: function(){
	if (this.props.data < 0 ) {
		return <div>{-this.props.data}</div>
	} else {
		return <div>{this.props.data}</div>
	}
  }
});

var columnData =[
{
    "columnName": "orderId",
    "order": 0,
    "displayName": "Order_id"
  },
  {
    "columnName": "traderId",
    "order": 1,
    "displayName": "Trader_id"
  },
  {
    "columnName": "action",
    "order": 2,
    "displayName": "Buy/Sell"
  },
  {
    "columnName": "symbol",
    "order": 3,
    "displayName": "Symbol"
  },
  {
    "columnName": "expire_date",
    "order": 4,
    "displayName": "Expire"
  },
  {
    "columnName": "lots",
    "order": 5,
    "displayName": "Lots",
	"customComponent": Lots_custom,
  },
  {
    "columnName": "price",
    "order": 6,
    "displayName": "Price",
  },
  {
    "columnName": "orderType",
    "order": 7,
    "displayName": "OrderType"
  },
  {
    "columnName": "date",
    "order": 8,
    "displayName": "Date"
  },
  {
    "columnName": "time",
    "order": 9,
    "displayName": "Time"
  }
];

var GetTradeButton = React.createClass({
	 handleClick : function(event) {
    	$.get("getTrade", function(result) {
	      ReactDOM.render(<Griddle columnMetadata={columnData} results = {result['test']}/>, document.getElementById("tradeInfo"));
     	});
  	 },

     componentDidMount : function() {
		this.handleClick();
	 },
	 
	 
     render : function() {
	    return (
      	<div>Loading data...</div>
    	);
	  }
	});
ReactDOM.render(<GetTradeButton/>, document.getElementById("tradeInfo"));

var GetTransactionButton = React.createClass({
	 handleClick : function(event) {
    	$.get("getTransaction", function(result) {
	      ReactDOM.render(<Griddle results = {result['test']}/>, document.getElementById("transactionInfo"));
     	});
  	 },

     componentDidMount : function() {
		this.handleClick();
	 },
	 
	 
     render : function() {
	    return (
      	<div>Loading data...</div>
    	);
	  }
	});
ReactDOM.render(<GetTransactionButton/>, document.getElementById("transactionInfo"));
</script>