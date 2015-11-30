var ServerDate = React.createClass({
	getInitialState: function() {
	    return {loading: true};
	},
	render: function() {
		if (this.state.loading) {
			return (
					<div>
					 	Loading Server Date
					</div>
				);
	    }
	    return (
	    		<div>
	          		<div>Current Server Date: {this.state.date}</div>
	          		<form action="resetServerDate" method="POST">
	          			<input type="submit" value="Reset Server Date"/>
	          		</form>
	          		<form action="goToNextBusinessDay" method="POST">
	          			<input type="submit" value="Next Bussness day"/>
	          		</form>
	          </div>
	    );
	 },
     componentDidMount : function() {
    	 $.get("getServerDate", function(result) {
   	      this.setState(
   	    		  {
   	    			  loading: false,
   	    			  date:result['date']
   	    		  }
   	      );
        }.bind(this));
	 },
});

ReactDOM.render(
		<ServerDate/>,
		document.getElementById('serverDate')
	);