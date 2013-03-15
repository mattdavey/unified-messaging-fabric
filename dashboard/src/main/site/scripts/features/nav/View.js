/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/15/13
 * Time: 6:20 PM
 */

define(['text!./View.html'], function (template) {
    return Backbone.View.extend({

        _template: _.template(template),

        render: function () {
            this.$el.html(this._template());
        }
    });
});