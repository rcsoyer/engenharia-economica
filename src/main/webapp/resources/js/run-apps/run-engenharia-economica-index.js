require.config({
    paths: {
        jQuery: '/resources/bower_components/jquery/jquery-3.1.1.min',
        angular: '/resources/bower_components/angular/angular.min',
        angularMessages: '/resources/bower_components/angular-messages/angular-messages.min',
        csrfInterceptor: '/resources/bower_components/spring-security-csrf-token-interceptor/dist/spring-security-csrf-token-interceptor.min',
        lodash: '/resources/bower_components/lodash/dist/lodash.min',
        userService: '/resources/js/frontend-services/user-service',
        engenhariaEconomicaApp: '/resources/js/engenharia-economica-index',
        bootstrap: '/resources/bower_components/bootstrap-3.3.7-dist/js/bootstrap.min'
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
        userService: {
            deps: ['angular', 'lodash', 'csrfInterceptor']
        },
        bootstrap : {
        	deps : ['jQuery']
        },
        engenhariaEconomicaApp: {
            deps: [ 'lodash', 'angular', 'angularMessages', 'userService', 'bootstrap' ]
        }
    }
});

require(['engenhariaEconomicaApp'], function () {
    angular.bootstrap(document.getElementById('engenhariaEconomicaApp'), ['engenhariaEconomicaApp']);
});