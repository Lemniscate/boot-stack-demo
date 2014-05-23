var mainApp = angular.module(window.globals.module, [
          'ngProgress'
        , 'ui.bootstrap'
        , 'ui.router'

        , window.globals.module + '.example'
    ])
    .constant('BASE_URL', window.globals.jsBaseUrl);

mainApp.run(function($rootScope, $state, $stateParams, $timeout, ngProgress){
    $rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;

    $rootScope.$on('$stateChangeStart', function(){
        ngProgress.stop(); //stop any uncompleted loader
        ngProgress.start();

        //after 3 seconds just go away
        $timeout(function(){
            ngProgress.complete();
        }, 3 * 1000);
    });

    $rootScope.$on('$stateChangeError', function(){
        ngProgress.complete();
    });

    $rootScope.$on('$stateChangeSuccess', function(){
        ngProgress.complete();
    });
});

mainApp.config(function($urlRouterProvider, ngProgressProvider){
    ngProgressProvider.color = '#006d91';
    $urlRouterProvider.otherwise('/example');
});

mainApp.controller('navController', function($scope){
    $scope.isCollapsed = true;
});