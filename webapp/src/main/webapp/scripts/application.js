//$(document).ready(function() {
	/// here...
//});

$(function() {
    if ($(".my-special-auto-updating-entity").length) {
        setTimeout(function() {
		document.location.reload(true);
        document.location.reload();}, 5000); // 1000 is 5 sec
    }
});