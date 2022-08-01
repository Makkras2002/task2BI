let file = document.getElementById('excelFile');
file.onchange = function(e) {
    let ext = this.value.match(/\.([^\.]+)$/)[1];
    switch (ext) {
        case 'xls':
            break;
        default:
            alert('Не поддерживаемый тип файла!!!');
            this.value = '';
    }
};