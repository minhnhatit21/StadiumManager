$(document).ready(function() {
    getData();
});

function getData() {
    $.getJSON("/api/beverage/getAll", function(returnData) {
        console.log(returnData);
        $('#beverageTable').DataTable({
            data: returnData,
            searchPanes: {
                columns: [3]
            },
            columnDefs: [{
                searchPanes: {
                    show: false
                },
                targets: [3]
            }],
            dom: 'Plfrtip',
            columns: [
                {data: "name"},
                {data: "price"},
                {data: "quantity"},
                {
                    data: null, // Use null for button column since it won't use a specific data property
                    render: function(data, type, row) {
                        // Render a button with a custom action
                        return '<button type="button" class="btn btn-primary" onclick="editBeverage(' + row.id + ')" data-toggle="modal" data-target="#beverageEditModal">Edit</button> '
                        + '<button type="button" class="btn btn btn-danger" onclick="deleteBeverage(' + row.id + ')" data-toggle="modal" data-target="#stadiumModal">Delete</button> ';
                    }
                }
            ],
            order: [[1, 'desc']],
            "rowCallback": function( row, data, index ) {
                /* Add ID for each row */
                $(row).attr('id', ('row-' + data.id));

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

function addBeverage() {
    var dataDrinks = {
        name: $("#nameBeverage").val(),
        price: $("#priceBeverage").val(),
        quantity: $("#quantityBeverage").val()
    }
    console.log("Add: ", dataDrinks);
    $.ajax({
        url: "/api/beverage/add",
        type: "POST",
        data: JSON.stringify(dataDrinks),
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function(response) {
            alert(response.responseMsg);
            location.reload(true);
        },
        error: function(xhr, status, error) {
           console.error("Error:", error);
           window.location.replace("/error");

        }
    });
}

function editBeverage(Id) {
    $.ajax({
        url : "/api/beverage/findById/" + Id,
        type : 'GET',
        dataType : 'json',
        contentType : 'application/json',
        success : function(responseData) {
            var beverageInfo = responseData.data;
            $('#indexBeverageForUpdate').val(beverageInfo.id);
            $('#nameBeverageForUpdate').val(beverageInfo.name);
            $('#priceBeverageForUpdate').val(beverageInfo.price);
            $('#quantityBeverageForUpdate').val(beverageInfo.quantity);
        }
    });
}

function updateBeverage() {
   var dataDrinks = {
       id: $("#indexBeverageForUpdate").val(),
       price: $("#priceBeverageForUpdate").val(),
       quantity: $("#quantityBeverageForUpdate").val()
   }
   console.log("Update: ", dataDrinks);
   $.ajax({
       url: "/api/beverage/update",
       type: "PUT",
       data: JSON.stringify(dataDrinks),
       dataType: "json",
       contentType: "application/json; charset=utf-8",
       success: function(response) {
           alert(response.responseMsg);
           location.reload(true);
       },
       error: function(xhr, status, error) {
          console.error("Error:", error);
          window.location.replace("/error");

       }
   });
}

function deleteBeverage(Id) {
    var beverageTable = $('#beverageTable').DataTable();
    $.ajax({
        url : "/api/beverage/delete/" + Id,
        type : 'DELETE',
        dataType : 'json',
        contentType : 'application/json',
        success : function(responseData) {
           alert(responseData.responseMsg);
            beverageTable.row('#row-'+Id).remove().draw();
        },
        error: function(xhr, status, error) {
          console.error("Error:", error);
          window.location.replace("/error");
       }
    });
}