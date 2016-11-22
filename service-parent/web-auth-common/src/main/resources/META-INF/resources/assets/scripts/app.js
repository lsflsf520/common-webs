var App = function() {
	var openMenu = function() {

		jQuery(".nav-list").on("click", "li > a", function(Q) {
			if ($(this).next().hasClass("sub-menu.always-open")) {
				return
			}
			var subDiv = jQuery(this).next();
			if (subDiv.is(":visible")) {
				jQuery(".arrow1", jQuery(this)).removeClass("open");
			} else {
				jQuery(".arrow1", jQuery(this)).addClass("open");
			}
		})
	};
	return {
		init : function() {
			openMenu()
		}
	}
}();