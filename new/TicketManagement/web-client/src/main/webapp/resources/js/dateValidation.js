function isValidDate(dateToValidate) {

    var re = /(\d{4})-(\d{1,2})-(\d{1,2})/;
    var tokens;
    if(dateToValidate != '') {
        if(tokens = dateToValidate.match(re)) {

            var day = tokens[3];
            var month = tokens[2];
            var year = tokens[1];

            if(day < 1 || day > 31) {
                alert("Invalid value for day: " + day);
                return false;
            }
            if(month < 1 || month > 12) {
                alert("Invalid value for month: " + month);
                return false;
            }
            if(year < 1902 || year > (new Date()).getFullYear()) {
                alert("Invalid value for year: " + year + " - must be between 1902 and " + (new Date()).getFullYear());
                return false;
            }
            if(month == 2) {
                if(year % 4 == 0) {
                    if(day > 29) {
                        alert("Invalid day for year: " + year + " and february. Must be less or equals than 29");
                        return false;
                    }
                }
                else {
                    if(day > 28) {
                        alert("Invalid day for year: " + year + " and february. Must be less or equals than 28");
                        return false;
                    }
                }
            }
        } else {
            alert("Invalid date format: " + dateToValidate);
            return false;
        }
    }
    return true;
}
function checkForm(form) {

    if("" == form.dateFirst.value || "" == form.dateLast.value) {
        form.dateFirst.value = form.dateLast.value = "";
        return true;
    }
    return isValidDate(form.dateFirst.value) && isValidDate(form.dateLast.value);
}