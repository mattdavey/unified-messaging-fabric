/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/14/13
 * Time: 3:00 PM
 */

define(['reconnecting-websocket'], function (Socket) {

    return Backbone.Collection.extend({

        initialize: function () {

            var self = this;
            var sock = Socket.create('http://localhost:8080/realtime/services');

            sock.onopen = function () {
                console.log('open');
            };

            sock.onmessage = function (e) {
                console.log('message', e.data);
                var event = JSON.parse(e.data);
                var rows = event.payload.map(function (row) {
                    return _.extend({registrationTime: new Date(row.registrationTimeUTC)}, row);
                });

                self.reset(rows);
            };

            sock.onclose = function () {
                console.log('close');
            };

        }
    });
});