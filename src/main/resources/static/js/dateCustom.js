// Get the current date
const currentDate = new Date();
const year = currentDate.getFullYear();
const month = (currentDate.getMonth() + 1).toString().padStart(2, '0');
const day = currentDate.getDate().toString().padStart(2, '0');

// Set the minimum date for the input element
const minDate = `${year}-${month}-${day}`;
document.getElementById('maintenanceScheduleTxtUpdate').min = minDate;
