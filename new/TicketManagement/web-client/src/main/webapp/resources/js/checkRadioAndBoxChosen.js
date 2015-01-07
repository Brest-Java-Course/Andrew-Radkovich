function isValuesChecked(form) {

    var customerRadio = form.customerId;
    var ticketCheckbox = form.ticketIdList;
    var customerRadioChecked = false;
    var ticketCheckboxChecked = false;

    if(customerRadio.checked) {
    customerRadioChecked = true;
    }
    else {
    for(var i = 0; i < customerRadio.length; i++) {
        if(customerRadio[i].checked) {
            customerRadioChecked = true;
            break;
        }
    }
    }
    if(!customerRadioChecked) {
    alert("Choose customer!");
    return false;
    }

    if(ticketCheckbox.checked) {
    ticketCheckboxChecked = true;
    }
    else {
    for(var i = 0; i < ticketCheckbox.length; i++) {
        if(ticketCheckbox[i].checked) {
            ticketCheckboxChecked = true;
            break;
        }
    }
    }
    if(!ticketCheckboxChecked) {
    alert("Choose at least one ticket!");
    return false;
    }

    return true;
}