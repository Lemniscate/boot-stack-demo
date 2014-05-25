angular.module( window.globals.module + '.example', [
          window.globals.module
        , 'ui.calendar'
        , 'ui.map'

        , window.globals.module + '.models'
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
    .controller('ExampleController', function($scope, Users, Organizations){
        console.log( 'Controller loaded...' );

        $scope.view = {
              users: Users.findAll()
            , organizations: Organizations.findAll()
        };

        window.test = arguments;
    });