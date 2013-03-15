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
                {id: "name", name: "Name", field: "name"},
                {id: "id", name: "ID", field: "id"},
                {id: "address", name: "Address", field: "address"},
                {id: "payload", name: "Payload", field: "payload"},
                {id: "registrationTimeUTC", name: "Registered", field: "registrationTimeUTC"},
                {id: "serviceType", name: "Service Type", field: "serviceType"},
                {id: "uriSpec", name: "URI Spec", field: "uriSpec"}
            ];

            var options = {enableColumnReorder: false};
            this._grid = new Slick.Grid("#myGrid", this._rows, columns, options);
            this._renderGrid();

            return this;
        },

        _renderGrid: function () {
            var rows = this.collection.toJSON();
            this._rows.setItems(rows);
            this._grid.invalidate();
        }
    });
});