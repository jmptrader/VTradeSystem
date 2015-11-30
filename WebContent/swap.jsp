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
		Swap page, go to <a href="index.jsp">future</a> page.
	</h3>
	<h3 align="center" id="serverDate"></h3>
	<div class="container">
		<div class="col-md-4">
			<div class="panel panel-default">
				<div class="panel-heading">Please input your transaction
					information below:</div>
				<div class="panel-body">
					<div class="col-md-12">
						<form action="addSwap" method="POST" class="form-horizontal"
							role="form">
							<div class="form-group">
								<label>Start Date:</label> <input required type="date"
									class="form-control" name="start"
									placeholder="Enter start date">
							</div>
							<div class="form-group">
								<label>Termination Date:</label> <input required type="date"
									class="form-control" name="termination"
									placeholder="Enter termination date">
							</div>
							<div class="form-group">
								<label>Float Rate:</label> <input type="radio" name="floatRate"
									value="Libor" checked> LIBOR <input type="radio"
									name="floatRate" value="Shibor"> SHIBOR

							</div>
							<div class="form-group">
								<label>Spread</label> <input required type="number" min="-0.1"
									max="0.1" step="0.0001" class="form-control" name="spread"
									placeholder="Enter Spread">
							</div>
							<div class="form-group">
								<label>Fixed rate</label> <input required type="number" min="0"
									max="0.1" step="0.0001" class="form-control" name="fixedRate"
									placeholder="Enter fixed rate">
							</div>

							<div class="form-group">
								<label>Fixed Payer:</label> <input type="radio"
									name="fixedPayer" value="You" checked> You <input
									type="radio" name="fixedPayer" value="CME"> CME <input
									type="radio" name="fixedPayer" value="LCH"> LCH
							</div>
							<div class="form-group">
								<label>Par Value</label> <input required type="number" min="0"
									max="100000000" step="1" class="form-control" name="parValue"
									placeholder="Par Value in $">
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
			<div class="panel panel-default">
				<div class="panel-heading">Swaps</div>
				<div id="swapInfo"></div>
			</div>
		</div>
	</div>
</body>

</html>

<script type="text/babel">
var GetSwapButton = React.createClass({
	 handleClick : function(event) {
    	$.get("getSwap", function(result) {
	      ReactDOM.render(<Griddle results = {result['test']}/>, document.getElementById("swapInfo"));
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
ReactDOM.render(<GetSwapButton/>, document.getElementById("swapInfo"));
</script>