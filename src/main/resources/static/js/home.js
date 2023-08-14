
$(document).ready(function() {
// Get the user ID from the Thymeleaf model
    var userId = $('#user-id').text();
    localStorage.setItem('userId', userId);

    showCard();
});

function searchStadium() {
$.ajax({
        url: "/api/stadium/search/?keyword=" + $("#search-content").val(),
        type: "POST",
        dataType: "json",
        success: function(response) {
            //console.log("Data:", response)
            $('#card-container').empty();
            if(response != null) {
                $.each(response, function(index, s) {
                    var imageUrl = '/images/img_1.jpg';
                    var cardID = s.id;
                    var cardTile = s.stadium.stadiumName;
                    var cardType = s.stadiumType;
                    var cardTime = s.time
                    var cardPrice = s.price
                    var cardDescription = s.stadium.description;
                    $.ajax({
                        url: imageUrl,
                        method: 'GET',
                        xhrFields: {
                            responseType: 'blob'
                        },
                        success: function(data) {
                            var imageUrl = URL.createObjectURL(data);

                            var cardHtml = `
                                         <div class="col-md-3">
                                            <div id="'card-${cardID}" class="card">
                                                <img class="card-img-top" src="${imageUrl}" alt="Card Image">
                                                <div class="card-body">
                                                    <h4 class="card-title">${cardTile}</h4>
                                                    <h6 class="card-text">${cardType}</h6>
                                                    <p class="card-text">
                                                        <strong>Khung giờ: </strong><span> ${cardTime}</span>
                                                     </p>
                                                    <p class="price card-text">
                                                        <strong>Giá: </strong><span> ${cardPrice} VND </span>
                                                    </p>
                                                    <p class="card-text">
                                                        <strong>Mô tả: </strong><span> ${cardDescription} </span>
                                                    </p>
                                                    <button type="button" class="btn btn-dark" onclick="editStadium(${cardID})" data-toggle="modal" data-target="#stadiumModal">Đặt sân</button>
                                                </div>

                                            </div>
                                        </div>`;

                            // Append the card to the card-container
                            $('#card-container').append(cardHtml);
                        }
                    });
                });
            } else {
                showCard();
            }

        }
    });
}

function showCard() {
    $.ajax({
        url: "/api/stadium/stadiumDetails/",
        type: "GET",
        dataType: "json",
        success: function(response) {
            console.log("Data:", response)
            $.each(response, function(index, s) {
                var imageUrl = '/images/img_1.jpg';
                var cardID = s.id;
                var cardTile = s.stadium.stadiumName;
                var cardType = s.stadiumType;
                var cardTime = s.time
                var cardPrice = s.price
                var cardDescription = s.stadium.description;
                $.ajax({
                    url: imageUrl,
                    method: 'GET',
                    xhrFields: {
                        responseType: 'blob'
                    },
                    success: function(data) {
                        var imageUrl = URL.createObjectURL(data);

                        var cardHtml = `
                                     <div class="col-md-3">
                                        <div id="'card-${cardID}" class="card">
                                            <img class="card-img-top" src="${imageUrl}" alt="Card Image">
                                            <div class="card-body">
                                                <h4 class="card-title">${cardTile}</h4>
                                                <h6 class="card-text">${cardType}</h6>
                                                <p class="card-text">
                                                    <strong>Khung giờ: </strong><span> ${cardTime}</span>
                                                 </p>
                                                <p class="price card-text">
                                                    <strong>Giá: </strong><span> ${cardPrice} VND </span>
                                                </p>
                                                <p class="card-text">
                                                    <strong>Mô tả: </strong><span> ${cardDescription} </span>
                                                </p>
                                                <button type="button" class="btn btn-dark" onclick="editStadium(${cardID})" data-toggle="modal" data-target="#stadiumModal">Đặt sân</button>
                                            </div>

                                        </div>
                                    </div>`;

                        // Append the card to the card-container
                        $('#card-container').append(cardHtml);
                    }
                });
            });
        }
    });

}
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

var responseData;

function checkDate(date, stadiumID) {
    $('#error-modal-body').empty();
    const myData = {
        bookingDate: date,
        stadiumID: stadiumID
    };

    return new Promise(function(resolve, reject) {
        $.ajax({
            url: "/api/stadium/bookingInfo",
            type: "POST",
            data: JSON.stringify(myData),
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function(response) {
                console.log("Data:", response.data);
                console.log("Data length:", response.data.length);
                if (response.data.length > 0) {
                    responseData = response.data;
                    resolve(response.data); // Resolve the promise with the response data
                } else {
                    //localStorage.setItem('responseStatus', null);
                    responseData = null;
                    resolve([]); // Resolve with an empty array if no data found
                }
            },
            error: function(xhr, status, error) {
               console.error("Error:", error);
                  if (xhr.status === 401) {
                       window.location.replace("/error");
                  }
            }
        });
    });
}
function editStadium(rowID) {
// $("#stadiumModal").modal('show');
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
              stadiumID = s.id;
          }
       });
       $("#bookingBtn").on("click", function() {
           var dataBooking = {};
           dataBooking.bookingDate = $("#dateBooking").val();
           dataBooking.stadiumID = rowID;
           dataBooking.userID = localStorage.getItem('userId');;

           checkDate(dataBooking.bookingDate, rowID)
               .then(function(responseData) {
                   console.log("Trang thai sau khi kiem tra: ", responseData);
                   if (responseData.length > 0) {
                       alert("Thời gian này sân đã được đặt");
                   } else {
                       bookingStadium(dataBooking);
                   }
               })
               .catch(function(xhr, status, error) {
                   console.error("Error:", error);
               });
       });
    }
  });
}

function bookingStadium(data) {
    const bookingData = {
        bookingDate: data.bookingDate,
        stadiumID: data.stadiumID,
        userID : data.userID
    }
    console.log("Data booking: ", bookingData);
    $.ajax({
        url: "/api/stadium/booking/",
        type: "PUT",
        data: JSON.stringify(bookingData),
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function(response) {
            if(response.data != null) {
                 alert("Booking Successfully");
            } else {
                console.log(response.responseMsg);
            }
            location.reload(true);
        },
        error: function(xhr, status, error) {
            console.error("Error:", error);
        }
    });
}

// Function to delete data from localStorage
function deleteDataFromLocalStorage() {
  localStorage.removeItem('responseStatus');
  console.log('Data deleted from localStorage.');
}