var main = function() {
	console.log("main()");
		var $input = $("#comment");
		var comment = $input.val();
		fetch('http://localhost:8080/comment?comment='.concat(comment));

		if (comment !=""){
			var html=$("<li>").text(comment);
			html.prependTo('#comments');
            $input.val("");
		}
		return false;
}

function postComment() {
	var $input = $("#comment");
	var comment = $input.val();
	$.ajax({
		url: "http://localhost:8080/comment?comment=" + comment,
		success: function(result) {
			$("#comments").prependTo(comment);
			$input.val("");
		}
	});
}