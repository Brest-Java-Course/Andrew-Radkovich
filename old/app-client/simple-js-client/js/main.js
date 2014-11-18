var REST_URL = "http://localhost:8080/users";

findAll();

function findAll() {
    console.log(findAll());
    $.ajax({
        type: 'GET',
        url: REST_URL,
        data_type: "json",
        success: renderList,
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('findAll: ' + textStatus);
        }
    });
}

function renderList(data) {

    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#userList li').remove();
    $.each(list, function (index, user) {
        $('#userList').append('<li>' + user.login + ' ' + user.name + '</li>');
        });
}