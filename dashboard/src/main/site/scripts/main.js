/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/12/13
 * Time: 7:29 PM
 */

define(['reconnecting-websocket'], function (Socket) {
    var sock = Socket.create('http://localhost:8080/services');

    sock.onopen = function () {
        console.log('open');
    };
    sock.onmessage = function (e) {
        console.log('message', e.data);
        var event = JSON.parse(e.data);
        var rows = event.payload;
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
        var grid = new Slick.Grid("#myGrid", rows, columns, options);
    };
    sock.onclose = function () {
        console.log('close');
    };

});