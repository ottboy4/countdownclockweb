<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="Grails"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <asset:stylesheet src="application.css"/>
    <g:layoutHead/>
</head>

<body id="ng-app" ng-app="${pageProperty(name: 'body.ng-app') ?: 'grails'}">

<g:layoutBody/>

<asset:script type="text/javascript">
    angular.module('grails.constants', [])
        .constant('RootUrl', '${request.contextPath}');
</asset:script>

<asset:javascript src="application.js"/>
<asset:deferredScripts/>
<g:pageProperty name="page.scripts" default=""/>
</body>
</html>
