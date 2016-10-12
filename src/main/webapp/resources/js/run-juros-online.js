require.config({
    paths: {
        angular: '../bower_components/angular/angular.min',
        angularMessages: '../bower_components/angular-messages/angular-messages.min',
        csrfInterceptor: '../bower_components/spring-security-csrf-token-interceptor/dist/spring-security-csrf-token-interceptor.min',
        lodash: '../bower_components/lodash/dist/lodash.min',
        jQuery: '../bower_components/jquery/jquery-3.1.1.min', 
        maskMoney : '../bower_components/jquery-maskmoney-master/dist/jquery.maskMoney.min',
        frontendServices: 'frontend-services',
        jurosOnlineApp: 'juros-online-app',
        bootstrap: '../bower_components/bootstrap-3.3.7-dist/js/bootstrap.min',
        economiaCommons : '../js/utils/economiaCommons'
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
        frontendServices: {
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
        jurosOnlineApp: {
            deps: [ 'lodash', 'angular', 'angularMessages', 'frontendServices', 'maskMoney', 'bootstrap', 'economiaCommons' ]
        }
    }
});

require(['jurosOnlineApp'], function () {
    angular.bootstrap(document.getElementById('jurosOnlineApp'), ['jurosOnlineApp']);
});