angular.module( window.globals.module + '.example', [
          window.globals.module
        , 'ui.calendar'
        , 'ui.map'

        , window.globals.module + '.ApiResource'
    ])
    .config(function($stateProvider){
        $stateProvider
            .state('example',{
                url: '/example',
                templateUrl: window.globals.jsBaseUrl + 'example/example.tpl.html',
                controller: 'ExampleController'
            })
        ;
    })
    .controller('ExampleController', function($scope, User){
        console.log( 'Controller loaded...' );
        window.test = {
            scope: $scope,
            User: User
        }
    });