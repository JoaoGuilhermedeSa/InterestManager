app.service("crudService", function ($http) {


var serviceUrl = "http://localhost:8000/api/client";

this.getRiskValues = function () {
	
			return $http.get("http://localhost:8000/api/tax");
	
		};


    this.getClients = function () {

        return $http.get(serviceUrl);

    };



	this.getClient = function(id) {

		var response = $http({

			method	: "GET",

			url		: serviceUrl + '/' + id


		});

		return response;

	};



	this.updateClient = function (client) {

		var response = $http({

			method : "PUT",

			url : serviceUrl,

			data : JSON.stringify(client)

		});



		return response;

	};



	this.addClient = function (client) {

		var response = $http({

			method  : "POST",

			url		: serviceUrl,

			data : JSON.stringify(client)

		});

		return response;

	};





	this.deleteClient = function (id) {

		var response = $http({

			method  : "DELETE",

			url		: serviceUrl + '/' + id

		});

		return response;

	};



});