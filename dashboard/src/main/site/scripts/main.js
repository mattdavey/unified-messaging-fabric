/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/12/13
 * Time: 7:29 PM
 */

define(['./ApplicationRouter'], function (ApplicationRouter) {

    var router = new ApplicationRouter();
    Backbone.history.start();
});