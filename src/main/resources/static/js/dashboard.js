$(document).ready(function() {
    // Get the token from localStorage
    var token = localStorage.getItem("authToken");
    console.log(token);
    // Make an Ajax request with the token
    $.ajax({
      url: "http://localhost:8080/page/dashboard",
      type: "GET",
      headers: {
        "Authorization": "Bearer " + token
      },
      success: function(response) {
        // Handle the success response
        console.log(response);
      },
      error: function(xhr, status, error) {
        // Handle the error
        console.log(error);
      }
    });
})