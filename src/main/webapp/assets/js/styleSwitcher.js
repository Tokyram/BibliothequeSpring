!function(t){"use strict";let a=t("#theme_version"),e=t("#theme_layout"),o=t("#sidebar_style"),i=t("#sidebar_position"),n=t("#header_position"),r=t("#container_layout"),d=t("#theme_direction");a.on("change",function(){t("body").attr("data-theme-version",this.value)}),i.on("change",function(){t("body").attr("data-sidebar-position",this.value)}),n.on("change",function(){t("body").attr("data-header-position",this.value)}),d.on("change",function(){t("html").attr("dir",this.value),t("html").attr("class",""),t("html").addClass(this.value),t("body").attr("direction",this.value)}),e.on("change",function(){if("overlay"===t("body").attr("data-sidebar-style")){t("body").attr("data-sidebar-style","full"),t("body").attr("data-layout",this.value);return}t("body").attr("data-layout",this.value)}),r.on("change",function(){if("boxed"===this.value&&"vertical"===t("body").attr("data-layout")&&"full"===t("body").attr("data-sidebar-style")){t("body").attr("data-sidebar-style","overlay"),t("body").attr("data-container",this.value);return}t("body").attr("data-container",this.value)}),o.on("change",function(){if("horizontal"===t("body").attr("data-layout")&&"overlay"===this.value){alert("Sorry! Overlay is not possible in Horizontal layout.");return}if("vertical"===t("body").attr("data-layout")&&"boxed"===t("body").attr("data-container")&&"full"===this.value){alert("Sorry! Full menu is not available in Vertical Boxed layout.");return}t("body").attr("data-sidebar-style",this.value)}),t('input[name="navigation_header"]').on("click",function(){t("body").attr("data-nav-headerbg",this.value)}),t('input[name="header_bg"]').on("click",function(){t("body").attr("data-headerbg",this.value)}),t('input[name="sidebar_bg"]').on("click",function(){t("body").attr("data-sibebarbg",this.value)})}(jQuery);