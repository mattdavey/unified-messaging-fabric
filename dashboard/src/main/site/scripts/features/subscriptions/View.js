/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/14/13
 * Time: 2:57 PM
 */

define(['text!./View.html'], function (template) {

    return Backbone.View.extend({

        _template: _.template(template),
        _rows: new Slick.Data.DataView(),

        initialize: function () {
            this.collection.on('reset', this._renderGrid, this);
        },

        render: function () {

            this.$el.html(this._template());

            var columns = [
                {id: "service", name: "Service", field: "service"},
                {id: "topic", name: "Topic", field: "topic"},
                {id: "value", name: "Value", field: "value"}
            ];

            var options = {enableColumnReorder: false};
            this._grid = new Slick.Grid("#myGrid", this._rows, columns, options);
            this._renderGrid();
        },

        _renderGrid: function () {
            var rows = this.collection.toJSON();
            this._rows.setItems(rows);
            this._grid.invalidate();
        }
    });
});