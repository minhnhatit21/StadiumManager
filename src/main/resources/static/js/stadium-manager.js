$(document).ready(function() {
    getData();
});
function getData() {
    $('#userTable').DataTable({
        ajax: '/objects.json',
        searchPanes: {
            columns: [5]
        },
        columnDefs: [{
            searchPanes: {
                show: false
            },
            targets: [5]
        }],
        dom: 'Plfrtip',
        columns: [
            {data: "stadium"},
            {data: "type"},
            {data: "time"},
            {data: "price"},
            {data: "status"},
            {
                data: null, // Use null for button column since it won't use a specific data property
                render: function(data, type, row) {
                    // Render a button with a custom action
                    return '<button type="button" class="btn btn-dark" onclick="editStadium(' + row.DT_RowId + ')" data-toggle="modal" data-target="#stadiumModal">Edit</button>';
                }
            }
        ]
    });
}

function editStadium(row) {
    // Make a GET request to the Spring Boot REST endpoint
  $.ajax({
    url: "/objects.json",
    type: "GET",
    dataType: "json",
    success: function(response) {
    var rowID = row.id;
      $.each(response.data, function(index, s) {
          if(s.DT_RowId === rowID) {
              $('#stadiumName').val(s.stadium);
              $('#stadiumType').val(s.type);
              $('#stadiumTime').val(s.time);
              $('#stadiumStatus').val(s.status);
              var modal = $("#stadiumModal");
              if(s.status === "Waiting") {

                  var button = $('<button type="button" class="btn btn-info" data-toggle="modal" data-target="#userInformationModal">Chờ xác nhận</button>');
                  modal.find("#confirmLink").append(button);
              } else {
                modal.find("#confirmLink").empty();
              }
          }
       });
    }
  });
}