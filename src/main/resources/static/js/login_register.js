$(document).ready(function() {
    login();
    register();
    //validateForm();
});
/*function validateForm() {
    $("#add-form").validate({
        rules: {
            "username": {
                required: true
            },
            "password": {
                required: true,
                password: true
            },
            "email": {
                required: true,
                email: true
            },
            "phoneNumber":{
                required: true,
                phoneNumber: true
            }
        },
        messages: {
            "username": {
                 required: "Không được để trống username"
            },
            "password": {
                required: "Không được để trống mật khẩu",
                password: "Mật khẩu không hợp lệ"
            },
            "email": {
                required: "Không được để trống email",
                email: "Email không hợp lệ"
            },
            "phoneNumber":{
                required: "Không được để trống số điện thoại",
                phoneNumber: "Số điện thoại không hợp lệ"
            }
        }
    });
}*/
function openRegisterPage() {
    window.location.replace("/page/register");
}
function login() {
    $('#login-form').submit(function() {
        event.preventDefault();
        $('#error-modal-body').empty();
        var username = $('#username').val();
        var password = $('#password').val();
        $.ajax({
          url: '/api/auth/signin', // Replace with your server-side authentication endpoint
          method: 'POST',
          contentType: 'application/json',
          data: JSON.stringify({
            username: username,
            password: password
          }),
          success: function(response) {

            var role = response.authorities[0].authority;
            var roleParam = '';
            if(role != null) {
                if(role === "ROLE_ADMIN") roleParam = "admin";
                else if(role === "ROLE_USER") roleParam = "user";
            }
            //console.log(">>>", role);
            // Step 1: Get the current URL
            const currentURL = window.location.href;
            const urlObject = new URL(currentURL);
            urlObject.search = '';
            window.location.replace("/info/" + roleParam);
          },
          error: function(xhr, status, error) {
            // Login failed, handle the error
            $('#error-modal-body').append("Error: " + xhr.responseText);
            $("#error-modal").modal("show");
            console.log("Error: " + xhr.responseText);
          }
        });
    });
}

function register() {
    $('#register-form').submit(function() {
        event.preventDefault();
        $('#error-modal-body').empty();
        var username = $('#username').val();
        var password = $('#password').val();
        var email = $('#email').val();
        var phoneNumber = $('#phoneNumber').val()
        $.ajax({
          url: '/api/auth/signup', // Replace with your server-side authentication endpoint
          method: 'POST',
          contentType: 'application/json',
          data: JSON.stringify({
            username: username,
            password: password,
            email: email,
            phoneNumber: phoneNumber
          }),
          success: function(response) {
            alert(response)
            window.location.replace("/page/login");
          },
          error: function(xhr, status, error) {
            // Login failed, handle the error
            $('#error-modal-body').append(xhr.responseText);
             $("#error-modal").modal("show");
            console.log("Error: " + xhr.responseText);
          }
        });
    });
}

