//$(document).ready(function() {
	/// here...
//});

$(function() {
    if ($(".my-special-auto-updating-entity").length) {
        setTimeout(function() {HomePageViewModel.reload();}, 5000); // 1000 is 5 sec
    }
});