function OpenSideBar() {
	const checkbox = document.getElementById("openSidebarMenu");
	const sideMenu = document.getElementById("side_menu");
	const main = document.getElementById("main");

	if(checkbox.checked){
		sideMenu.style.width = "15%";
		main.style.width = "84%";
	} else {
		sideMenu.style.width = "5%";
		main.style.width = "94%";
	}
};