require.config({
    paths: {
        angular: '/resources/bower_components/angular/angular.min',
        angularMessages: '/resources/bower_components/angular-messages/angular-messages.min',
        csrfInterceptor: '/resources/bower_components/spring-security-csrf-token-interceptor/dist/spring-security-csrf-token-interceptor.min',
        lodash: '/resources/bower_components/lodash/dist/lodash.min',
        jQuery: '/resources/bower_components/jquery/jquery-3.1.1.min', 
        maskMoney : '/resources/bower_components/jquery-maskmoney-master/dist/jquery.maskMoney.min',
        userService: '/resources/js/frontend-services/user-service',
        seriePagamentosVlrFuturoService: '/resources/js/frontend-services/serie-pagamentos-vlr-futuro-services',
        seriePagamentosVlrFuturoApp: '/resources/js/apps/serie-pagamentos-valor-futuro-app',
        bootstrap: '/resources/bower_components/bootstrap-3.3.7-dist/js/bootstrap.min',
        economiaCommons : '/resources/js/utils/economiaCommons'
    },
    shim: {
        jQuery: {
            exports: 'jQuery'
        },
        angular: {
            exports: 'angular'
        },
        csrfInterceptor: {
            deps: ['angular']
        },
        angularMessages: {
            deps: ['angular']
        },
        userService: {
            deps: ['angular', 'lodash', 'csrfInterceptor']
        },
        seriePagamentosVlrFuturoService: {
            deps: ['angular', 'lodash', 'csrfInterceptor']
        },
        maskMoney : {
        	deps: ['jQuery'],
            exports: 'jQuery.fn.maskMoney'
        },
        bootstrap : {
        	deps : ['jQuery']
        },
        economiaCommons : {
        	deps : ['jQuery']
        },
        seriePagamentosVlrFuturoApp: {
            deps: [ 'lodash', 'angular', 'angularMessages', 'userService', 'seriePagamentosVlrFuturoService', 'maskMoney', 'bootstrap', 'economiaCommons' ]
        }
    }
});

require(['seriePagamentosVlrFuturoApp'], function () {
    angular.bootstrap(document.getElementById('seriePagamentosVlrFuturoApp'), ['seriePagamentosVlrFuturoApp']);
});