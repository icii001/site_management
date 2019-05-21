jQuery(function($) {
	jQuery("#selectFileSample2").click(function() {
		var selectFileSample1 = document.getElementById("selectFileSample1").value;
		document.getElementById("csv").value = selectFileSample1;
	});
});
