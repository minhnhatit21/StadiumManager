$(document).ready(function() {
    getData();
});

function getData() {
    $.getJSON("/api/material/getAll", function(returnData) {
        console.log(returnData);
        $('#materialTable').DataTable({
            data: returnData,
            searchPanes: {
                columns: [4]
            },
            columnDefs: [{
                searchPanes: {
                    show: false
                },
                targets: [1]
            },
              {
                  targets: [2], // Column 3 (maintenanceSchedule)
                  render: function(data, type, row) {
                      // Format the date to "Do MMM YYYY" using moment.js or any date formatting library you prefer
                      return moment(data).format('Do MMM YYYY');
                  }
              },
             {
                 targets: [3], // Column 3 (maintenanceSchedule)
                 render: function(data, type, row) {
                     // Format the date to "Do MMM YYYY" using moment.js or any date formatting library you prefer
                     return moment(data).format('Do MMM YYYY');
                 }
             }
            ],
            dom: 'Plfrtip',
            columns: [
                {data: "materialName"},
                {data: "quantityOnHand"},
                {data: "maintenanceSchedule"},
                {data: "lastMaintenanceDate"},
                {
                    data: null, // Use null for button column since it won't use a specific data property
                    render: function(data, type, row) {
                        // Render a button with a custom action
                        return '<button type="button" class="btn btn-primary" onclick="editEquipment(' + row.id + ')" data-toggle="modal" data-target="#beverageEditModal">Edit</button> '
                        + '<button type="button" class="btn btn btn-danger" onclick="deleteEquipment(' + row.id + ')" data-toggle="modal" data-target="#stadiumModal">Delete</button> ';
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

function addMaterial() {
    dataEquipment = {
                        materialName: $("#nameMaterialTxtAdd").val(),
                        quantityOnHand: $("#quantityOnHandTxtAdd").val(),
                        maintenanceSchedule: $("#maintenanceScheduleTxtAdd").val(),
                        lastMaintenanceDate: $("#lastMaintenanceDateTxtAdd").val()
                    }
    console.log("Add: ", dataEquipment);
        $.ajax({
            url: "/api/material/add",
            type: "POST",
            data: JSON.stringify(dataEquipment),
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

function editEquipment(Id) {
   $.ajax({
       url : "/api/material/findById/" + Id,
       type : 'GET',
       dataType : 'json',
       contentType : 'application/json',
       success : function(responseData) {
           var materialInfo = responseData.data;
           var maintenanceSchedule = new Date(materialInfo.maintenanceSchedule);
           var lastMaintenanceDate = new Date(materialInfo.lastMaintenanceDate);
           $('#indexMaterialForUpdate').val(materialInfo.id);
           $('#nameMaterialTxtUpdate').val(materialInfo.materialName);
           $('#quantityOnHandTxtUpdate').val(materialInfo.quantityOnHand);
           $('#maintenanceScheduleTxtUpdate').val(maintenanceSchedule.toISOString().substr(0, 10));
           $('#lastMaintenanceDateTxtUpdate').val(lastMaintenanceDate.toISOString().substr(0, 10));
       }
   });
}

function updateEquipment() {
   var dataEquipment = {
       id: 2,
       quantityOnHand: $('#quantityOnHandTxtUpdate').val(),
       maintenanceSchedule: $('#maintenanceScheduleTxtUpdate').val(),
       lastMaintenanceDate: $('#lastMaintenanceDateTxtUpdate').val()
   }
   console.log("Update: ", dataEquipment);
   $.ajax({
       url: "/api/material/update",
       type: "PUT",
       data: JSON.stringify(dataEquipment),
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

function deleteEquipment(Id) {
    var materialTable = $('#materialTable').DataTable();
    $.ajax({
        url : "/api/material/delete/" + Id,
        type : 'DELETE',
        dataType : 'json',
        contentType : 'application/json',
        success : function(responseData) {
           alert(responseData.responseMsg);
            materialTable.row('#row-'+Id).remove().draw();
        },
        error: function(xhr, status, error) {
          console.error("Error:", error);
          window.location.replace("/error");
       }
    });
}