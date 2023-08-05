$(document).ready(function() {
    getData();
});
function getData() {
    $.getJSON("/api/stadium/stadiumDetails/", function(returnData) {
        $('#userTable').DataTable({
            data: returnData,
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
                {data: "stadium.stadiumName"},
                {data: "stadiumType"},
                {data: "time"},
                {data: "price"},
                {data: "status"},
                {
                    data: null, // Use null for button column since it won't use a specific data property
                    render: function(data, type, row) {
                        // Render a button with a custom action
                        return '<button type="button" class="btn btn-dark" onclick="editStadium(' + row.id + ')" data-toggle="modal" data-target="#stadiumModal">Edit</button>';
                    }
                }
            ]
        });
    });

}

function editStadium(rowID) {
    // Make a GET request to the Spring Boot REST endpoint
  $.ajax({
    url: "/api/stadium/stadiumDetails/",
    type: "GET",
    dataType: "json",
    success: function(response) {
    console.log("Data:", response)
      $.each(response, function(index, s) {
          if(s.id === rowID) {
              $('#stadiumName').val(s.stadium.stadiumName);
              $('#stadiumType').val(s.stadiumType);
              $('#stadiumTime').val(s.time);
              $('#stadiumStatus').val(s.status);
              var modal = $("#stadiumModal");
              modal.find("#confirmLink").empty();
              if(s.status === "WAITING") {

                  var button = $('<button type="button" class="btn btn-info" data-toggle="modal" onclick="showUserInformation(' + s.id + ')" data-target="#userInformationModal">Chờ xác nhận</button>');
                  modal.find("#confirmLink").append(button);
              } else {
                modal.find("#confirmLink").empty();
              }
          }
       });
    }
  });
}

function showUserInformation(rowID) {
    $.ajax({
        url: "/api/stadium/users/" + rowID,
        type: "GET",
        dataType: 'json',
        success: function(response) {
          $.each(response.data, function(index, s) {
              console.log(response.data);
              if(s.id === rowID) {
                  $('#userName').val(s.stadium.stadiumName);
                  $('#userPhoneNumber').val(s.stadiumType);
                  $('#userEmail').val(s.time);
//                  var modal = $("#userInformationModal");
//                  modal.find("#confirmLink").empty();
//                  if(s.status === "WAITING") {
//
//                      var button = $('<button type="button" class="btn btn-info" data-toggle="modal" data-target="#userInformationModal">Chờ xác nhận</button>');
//                      modal.find("#confirmLink").append(button);
//                  } else {
//                    modal.find("#confirmLink").empty();
//                  }
              }
           });
        }
      });
}