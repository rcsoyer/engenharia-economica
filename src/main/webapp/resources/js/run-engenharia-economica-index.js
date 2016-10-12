require.config({
    paths: {
        jQuery: '../bower_components/jquery/jquery-3.1.1.min',
        angular: '../bower_components/angular/angular.min',
        angularMessages: '../bower_components/angular-messages/angular-messages.min',
        csrfInterceptor: '../bower_components/spring-security-csrf-token-interceptor/dist/spring-security-csrf-token-interceptor.min',
        lodash: '../bower_components/lodash/dist/lodash.min',
        frontendServices: 'frontend-services',
        engenhariaEconomicaApp: 'engenharia-economica-index',
        bootstrap: '../bower_components/bootstrap-3.3.7-dist/js/bootstrap.min'
    },
    shim: {
        jQuery: {
            exports: 'jQuery'
        },
        angular: {
            exports: 'angular'
        },
        angularMessages: {
            deps: ['angular']
        },
        csrfInterceptor: {
            deps: ['angular']
        },
        frontendServices: {
            deps: ['angular', 'lodash', 'csrfInterceptor']
        },
        bootstrap : {
        	deps : ['jQuery']
        },
        engenhariaEconomicaApp: {
            deps: [ 'lodash', 'angular', 'angularMessages', 'frontendServices', 'bootstrap' ]
        }
    }
});

require(['engenhariaEconomicaApp'], function () {
    angular.bootstrap(document.getElementById('engenhariaEconomicaApp'), ['engenhariaEconomicaApp']);
});