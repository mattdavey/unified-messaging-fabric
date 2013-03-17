/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/17/13
 * Time: 9:39 AM
 */

define(function () {
    return {
        create: function (options) {

            options = options || {};
            return function (row, cell, value, columnDef, dataContext) {
                var id = options.id || columnDef.field;
                var text = _.result(options, "text") || value;
                return '<a href="#/services/' + dataContext[id] + '">' + text + '</a>'
            };
        }
    };
});