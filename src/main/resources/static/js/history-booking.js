
$(document).ready(function() {
    var userId = $('#user-id').text();
    localStorage.setItem('userId', userId);
    getData();
});
function getData() {
    console.log("UserID: ", localStorage.getItem('userId'));
    var urls = "/api/stadium/users/history/" + localStorage.getItem('userId');
    $.getJSON(urls, function(returnData) {

        $('#userTable').DataTable({
            data: returnData.data,
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
                {data: "stadiumPrice.stadium.stadiumName"},
                {data: "stadiumPrice.stadiumType"},
                {data: "stadiumPrice.time"},
                {data: "stadiumPrice.price"},
                {data: "estatus"},
                {data: "bookingDate",
                     render: function(data, type, row) {
                         if (type === 'display' || type === 'filter') {
                             // Assuming data is in ISO 8601 format (e.g., "2023-08-15T12:34:56Z")
                             var date = new Date(data);
                             var formattedDate = date.toLocaleString('vi', {
                                 year: 'numeric',
                                 month: 'short',
                                 day: 'numeric',
                                 hour: '2-digit',
                                 minute: '2-digit',
                                 second: '2-digit',
                                 timeZoneName: 'short'
                             });
                             return formattedDate;
                         }
                         return data; // For sorting and other purposes, return original data
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