function timeSince(date) {

    var seconds = Math.floor((new Date() - date) / 1000);

    var interval = Math.floor(seconds / 31536000);

    if (interval > 1) {
        return interval + " yıl";
    }
    interval = Math.floor(seconds / 2592000);
    if (interval > 1) {
        return interval + " ay";
    }
    interval = Math.floor(seconds / 86400);
    if (interval > 1) {
        return interval + " gün";
    }
    interval = Math.floor(seconds / 3600);
    if (interval > 1) {
        return interval + " saat";
    }
    interval = Math.floor(seconds / 60);
    if (interval > 1) {
        return interval + " dakika";
    }
    return " az";
}

function formatDate(value) {
    if (value === undefined)
        return value;

    var date = new Date(value);
    var day = ("0" + date.getDate()).slice(-2);
    var month = ("0" + (date.getMonth() + 1)).slice(-2);
    return date.getFullYear() + "-" + (month) + "-" + (day);
}

function ajax(url, data, type, handleFunc = false, errorFunc = false) {
    return $.ajax({
        url: url,
        type: type,
        data: data,
        success: function (res) {
            if (handleFunc) {
                handleFunc(res);
            } else {
                processResponseData(res);
            }
        },
        error: function (data) {
            if (errorFunc) errorFunc(data.responseText);
        }
    });
}

function ajaxGet(url, callback) {
    ajax(url, {}, "GET", callback);
}

function ajaxPost(url, data, callback) {
    ajax(url, data, "POST", callback);
}

function alertBox(text, id = "") {
    return '<div id="' + id + '" class="alert alert-warning alert-dismissible fade show" role="alert">' +
        text +
        '  <button type="button" class="close" data-dismiss="alert" aria-label="Close">' +
        '    <span aria-hidden="true">&times;</span>' +
        '  </button>' +
        '</div>';
}

function confirmSwal(title, text, onDone, onCancel) {
    const swalWithBootstrapButtons = Swal.mixin({
        customClass: {
            confirmButton: 'btn btn-success',
            cancelButton: 'btn btn-danger'
        },
        buttonsStyling: false
    });
    swalWithBootstrapButtons.fire({
        title: title,
        text: text,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Evet',
        cancelButtonText: 'Hayır',
        reverseButtons: true
    }).then((result) => {
        if (result.value) {
            onDone();
        } else if (result.dismiss === Swal.DismissReason.cancel) {
            onCancel();
        }
    })
}

function alertSwal(text) {
    Swal.fire({
        text: text,
        confirmButtonText: 'Tamam',
    });
}

function errorSwal(message) {
    Swal.fire({
        type: 'error',
        text: message,
        confirmButtonText: 'Tamam',
    });
}

function processResponseData(data) {
    if (data.status === 'ok') {
        Swal.fire({
            type: 'success',
            text: data.message,
            confirmButtonText: 'Tamam',
        });
        return true;
    } else if (data.status === 'error') {
        Swal.fire({
            type: 'error',
            text: data.message,
            confirmButtonText: 'Tamam',
        });
        return false;
    } else if (data.status === 'redirect') {
        window.location = data.message;
        return true;
    } else if (data.status === 'reload') {
        location.reload();
        return true;
    } else if (data.status === 'okRedirect') {
        Swal.fire({
            type: 'success',
            text: data.message,
            allowOutsideClick: false,
            confirmButtonText: 'Tamam',
        }).then((result) => {
            if (result.value) {
                window.location = data.url;
            }
        });
        return true;
    } else if (data.status === 'okReload') {
        Swal.fire({
            type: 'success',
            text: data.message,
            allowOutsideClick: false,
            confirmButtonText: 'Tamam',
        }).then((result) => {
            if (result.value) {
                location.reload();
            }
        });
        return true;
    } else if (data.status === 'errorRedirect') {
        Swal.fire({
            type: 'error',
            text: data.message,
            allowOutsideClick: false,
            confirmButtonText: 'Tamam',
        }).then((result) => {
            if (result.value) {
                window.location = data.url;
            }
        });
        return false;
    } else {
        Swal.fire({
            type: 'error',
            text: "Bir hata oluştu !",
            confirmButtonText: 'Tamam',
        });
        return false;
    }
}

function formAjax(frm, beforeFunc = false, handleFunc = false) {
    frm.submit(function (e) {
        e.preventDefault();
        if (beforeFunc) beforeFunc();
        $.ajax({
            type: frm.attr('method'),
            url: frm.attr('action'),
            data: frm.serialize(),
            success: function (data) {
                processResponseData(data);
                if (handleFunc) handleFunc(data);
            },
            error: function (data) {
                Swal.fire({
                    type: 'error',
                    text: "Beklenmedik bir hata oluştu!",
                    confirmButtonText: 'Tamam',
                });
            },
        });
    });
}

function formAjaxWithFile(frm, beforeFunc = false, handleFunc = false) {
    frm.submit(function (e) {
        e.preventDefault();
        if (beforeFunc) beforeFunc();
        $.ajax({
            type: frm.attr('method'),
            url: frm.attr('action'),
            data: new FormData(frm[0]),
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                processResponseData(data);
                if (handleFunc) handleFunc(data);
            },
            error: function (data) {
                Swal.fire({
                    type: 'error',
                    text: "Beklenmedik bir hata oluştu!",
                    confirmButtonText: 'Tamam',
                });
            },
        });
    });
}

function $$(id) {
    return $('#' + id);
}

function formAjaxFile(frm, fileInput, beforeFunc = false, handleFunc = false) {
    frm = $$(frm);
    $$(fileInput).change(function () {
        if (beforeFunc) beforeFunc();
        if ($(this).val() == "") return;
        $.ajax({
            type: frm.attr('method'),
            url: frm.attr('action'),
            data: new FormData(frm[0]),
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                processResponseData(data);
                if (handleFunc) handleFunc(data);
            },
            error: function (data) {
                Swal.fire({
                    type: 'error',
                    text: "Beklenmedik bir hata oluştu!",
                    confirmButtonText: 'Tamam',
                });
            },
        });
    });
}

$(document).ready(function () {
    $('.select2').select2({
        theme: 'bootstrap4'
    });
    //////////////////////// Prevent closing from click inside dropdown
    $(document).on('click', '.dropdown-menu', function (e) {
        e.stopPropagation();
    });

    //////////////////////// Bootstrap tooltip
    if ($('[data-toggle="tooltip"]').length > 0) {  // check if element exists
        $('[data-toggle="tooltip"]').tooltip()
    } // end if

    if (typeof main !== 'undefined') main();

    /*let scrollValue = 0;
    $(window).on('scroll', function(event) {
        scrollValue = $(window).scrollTop();
        if (scrollValue > 70) {
            $('.navbar').addClass('fixed-top');
        }
    });*/
    // jquery ready end
    $('form').each(function (index) {
        formAjax($(this));
    });

});