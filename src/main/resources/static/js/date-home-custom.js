document.addEventListener("DOMContentLoaded", function() {
    // Set the minimum date for the input element
    const currentDate = new Date();
    const minDate = formatDate(currentDate);
    document.getElementById('dateBooking').min = minDate;

    // Set the maximum date for the input element (min date + 7 days)
    const maxDate = new Date(currentDate);
    maxDate.setDate(currentDate.getDate() + 7);
    const formattedMaxDate = formatDate(maxDate);
    document.getElementById('dateBooking').max = formattedMaxDate;
});

function formatDate(date) {
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;
}