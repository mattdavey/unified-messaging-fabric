/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/16/13
 * Time: 9:43 PM
 */

define([], function () {

    return Backbone.Collection.extend({

        url: "/services",

        buildModel: function (attrs) {
            return new this.model(attrs, {collection: this});
        }
    });
});