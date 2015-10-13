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
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</head>
<body>
	<h2 align="center">VTrade System</h2>
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
								<label for="exampleInputEmail1">Future Symbol:</label> <input
									required type="text" class="form-control" name="symbol"
									placeholder="Enter Symbol">
							</div>
							<div class="form-group">
								<label>Contract Expiry:</label> <input required type="date"
									class="form-control" name="exp" placeholder="Enter expiry date">
							</div>
							<div class="form-group">
								<label>Lots</label> <input required type="number" min="1"
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
							<div class="form-group">
								<label>Transaction Date:</label> <input required type="date"
									class="form-control" name="transDate">
							</div>
							<div class="form-group">
								<label>Transaction Time:</label> <input required type="time"
									class="form-control" name="transTime">
							</div>
							<input class="btn btn-lg btn-primary btn-block" type="submit">
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-8">
			<div class="panel panel-default">
				<div class="panel-heading">Transaction</div>
				<div id="tradeInfo"></div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">Download data</div>
				<div class="panel-body">
					<div class="col-md-12">
						<input class="btn btn-lg btn-primary btn-block"
							value="Download all data as a csv file"
							onClick="window.open('${pageContext.request.contextPath}/getCSV');">
						<form target="_blank" action="getCSVWithCondition" method="GET"
							class="form-horizontal" role="form">
							<div class="form-group">
								<label for="exampleInputEmail1">Trader id:</label> <input
									required type="number" min="1" max="50000" class="form-control"
									placeholder="Enter trader id" name="traderId">
							</div>
							<input class="btn btn-lg btn-primary btn-block" type="submit"
								value="Download aggregation data as a csv file given trader id">
						</form>
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

var GetTradeButton = React.createClass({
	 handleClick : function(event) {
    	$.get("getTrade", function(result) {
	      ReactDOM.render(<Griddle results = {result['test']}/>, document.getElementById("tradeInfo"));
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
</script>