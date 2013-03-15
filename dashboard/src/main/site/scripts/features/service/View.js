/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/14/13
 * Time: 2:57 PM
 */

define(['text!./View.html'], function (template) {

    return Backbone.View.extend({

        _template: _.template(template),

        initialize: function () {
        },

        render: function () {

            this.$el.html(this._template());
            rivets.bind(this.el, {model: this.model})

            return this;
        }

    });
});