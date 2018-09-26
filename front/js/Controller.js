app.controller("crudController", function ($scope, crudService) {


	$scope.ClientFormContainer = false;
	$scope.riskValues=[];

	getRiskValues();
	GetAllClients();
	$scope.risk = 'A';

	function GetAllClients() {



		var getClientData = crudService.getClients();

		getClientData.then(function (client) {
			$scope.clients = client.data;



		}, function () {

			alert('Erro ao buscar lista de clientes');

		});



	}



	$scope.editClient = function (client) {



		var getClientData = crudService.getClient(client.id);



		getClientData.then(function (_client) {

			$scope.client = _client.data;

			$scope.id = client.id

			$scope.name = client.name;

			$scope.creditLimit = client.creditLimit;

			$scope.risk = client.risk;





			$scope.Action = "Atualizar";

			$scope.ClientFormContainer = true;

		}, function () {

			alert('Erro ao editar cliente');

		});

	}


	$scope.addClient = function () {

		ClearFields();

		$scope.Action = "Adicionar";

		$scope.ClientFormContainer = true;

	}



	function ClearFields() {

		$scope.id = "";

		$scope.name = "";

		$scope.creditLimit = "";

		$scope.risk = "A";

	}



	// Hide Add / Update Client Form

	$scope.closeFrmBtn = function () {

		$scope.ClientFormContainer = false;

	}



	$scope.Cancel = function () {

		$scope.ClientFormContainer = false;

	}

	$scope.AddUpdateClient = function () {



		var client = {

			name: $scope.name,

			creditLimit: $scope.creditLimit,

			risk: $scope.risk

		};

		var getClientAction = $scope.Action;


		if (getClientAction == "Atualizar") {

			client.clientid = $scope.id;

			var getClientData = crudService.updateClient(client);


			getClientData.then(function (response) {

				GetAllClients();


			}, function () {

				alert('Erro ao atualizar registro de cliente');

			});


		} else {


			var addClientData = crudService.addClient(client);

			addClientData.then(function (response) {

				GetAllClients();

			}, function () {

				alert('Erro ao adicionar cliente');

			}

			);

		}

		$scope.ClientFormContainer = false;


	}


	function getRiskValues() {	
		
				var getRiskValues = crudService.getRiskValues();
		
				getRiskValues.then(function (result) {
					$scope.riskValues = result.data;		
		
				}, function () {
		
					alert('Erro ao buscar lista de opções de risco');
		
				});
		
		
		
			}


	$scope.deleteClient = function (client) {



		var ans = confirm('Você tem certeza que deseja deletar este registro?');

		if (ans) {

			var delClientData = crudService.deleteClient(client.id);

			GetAllClients();

		}

	}

});