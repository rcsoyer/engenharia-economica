angular.module('jurosService', [])
    .service('JurosService', ['$http', '$q', function($http, $q) {
        return {
            calcularJuros : function(dadosCalcJuros) {
            	var deferred = $q.defer();

                $http.post('/juros', dadosCalcJuros)
                	.then(function (response) {
	                    if (response.status == 200) {
	                        return deferred.resolve(response.data);
	                    }
	                    else {
	                        deferred.reject('Erro calculando juros');
	                    }
	                }
	           );

                return deferred.promise;
            }
        }
    }]);