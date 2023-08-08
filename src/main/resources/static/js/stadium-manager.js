$(document).ready(function() {
    getData();
});
const bookingData = {
    bookingDate: "",
    stadiumID: "",
    userID : ""
}
function getData() {
    $.getJSON("/api/stadium/stadiumDetailsByCurrentDate", function(returnData) {
        console.log(returnData);
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
                {data: "stadiumName"},
                {data: "stadiumType"},
                {data: "stadiumTimeBlock"},
                {data: "stadiumPrice"},
                {data: "stadiumStatus"},
                {
                    data: null, // Use null for button column since it won't use a specific data property
                    render: function(data, type, row) {
                        // Render a button with a custom action
                        return '<button type="button" class="btn btn-dark" onclick="editStadium(' + row.id + ')" data-toggle="modal" data-target="#stadiumModal">View</button>';
                    }
                }
            ],
            order: [[2, 'desc']],
            "rowCallback": function( row, data, index ) {
            if(index%2 == 0){
                $(row).removeClass('myodd myeven');
                $(row).addClass('myodd');
            }else{
                $(row).removeClass('myodd myeven');
                 $(row).addClass('myeven');
            }
          }
        });
    });

}

function editStadium(rowID) {
    // Make a GET request to the Spring Boot REST endpoint
  bookingData.stadiumID = rowID;
  $.ajax({
    url: "/api/stadium/stadiumDetailsByCurrentDate/",
    type: "GET",
    dataType: "json",
    success: function(response) {
    console.log("Data:", response)
      $.each(response, function(index, s) {
          if(s.id === rowID) {
              $('#stadiumName').val(s.stadiumName);
              $('#stadiumType').val(s.stadiumType);
              $('#stadiumTime').val(s.stadiumTimeBlock);
              $('#stadiumStatus').val(s.stadiumStatus);
              var modal = $("#stadiumModal");
              modal.find("#confirmLink").empty();
              if(s.stadiumStatus === "WAITING") {

                  var button = $('<button type="button" class="btn btn-info" data-toggle="modal" onclick="showUserInformation(' + s.userID + ')" data-target="#userInformationModal">Chờ xác nhận</button>');
                  modal.find("#confirmLink").append(button);
              } else {
                modal.find("#confirmLink").empty();
              }
          }
       });
    }
  });
}

function showUserInformation(userID) {
    var currentDate = new Date();
    // Format the date to YYYY-MM-DD format
    var formattedDate = currentDate.toISOString().split('T')[0];

    // Pass bookingDate and userID to bookingData
    bookingData.bookingDate = formattedDate;
    bookingData.userID = userID;
    /* -----*/

    const myData = {
        bookingDate: formattedDate,
        stadiumID: bookingData.stadiumID
    };
    $.ajax({
        url: "/api/stadium/bookingInfo",
        type: "POST",
        data: JSON.stringify(myData),
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function(response) {
            console.log("User Data:", response.data);
            $("#userName").val(response.data[0].user.username);
            $("#userPhoneNumber").val(response.data[0].user.phoneNumber);
            $("#userEmail").val(response.data[0].user.email);
        },
        error: function(xhr, status, error) {
           console.error("Error:", error);
           window.location.replace("/error");

        }
    });
}

function confirmBooking() {
    console.log("Data confirm: ", bookingData);
    $.ajax({
        url: "/api/stadium/bookingConfirmsForUser",
        type: "PUT",
        data: JSON.stringify(bookingData),
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function(response) {
            alert("Confirm Successfully");
            location.reload(true);
        },
        error: function(xhr, status, error) {
            console.error("Error:", error);
            //reject(error); // Reject the promise if there's an error
        }
    });
}