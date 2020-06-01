function alertValue($this) {
	document.getElementById('username').style.backgroundColor = 'skyblue';
	document.getElementById('lastName').style.backgroundColor = 'skyblue';
	document.getElementById('firstName').style.backgroundColor = 'skyblue';
	document.getElementById('authority').style.backgroundColor = 'skyblue';
	document.getElementById('status').style.backgroundColor = 'skyblue';
}

function buttonChecked() {
	var user = document.getElementsByClassName("user");
	var statusBoolean = document.getElementsByClassName("statusBoolean");
	var checkbox = document.getElementsByClassName("checkbox")

	for (var i = 0; i < user.length; i++) {
		if (checkbox[i].checked) {
			statusBoolean[i].value = "0";
		} else {
			statusBoolean[i].value = "1";
		}
	}
}

function buttonCheckAuthorityAdmin() {
	var sarchAuthorityAdmin = document.getElementsByClassName("sarchAuthorityAdmin");
	var sarchCheckboxAdmin = document.getElementsByClassName("sarchCheckboxAdmin");

	if (sarchCheckboxAdmin[0].checked) {
		sarchAuthorityAdmin[0].value = 0;
	} else {
		sarchAuthorityAdmin[0].value = 1;
	}
}
function buttonCheckAuthorityUser() {
	var sarchAuthorityUser = document.getElementsByClassName("sarchAuthorityUser");
	var sarchCheckboxUser = document.getElementsByClassName("sarchCheckboxUser");

	if (sarchCheckboxUser[0].checked) {
		sarchAuthorityUser[0].value = 1;
	} else {
		sarchAuthorityUser[0].value = 0;
	}
}
function buttonCheckStatus() {
	var sarchStatusBoolean = document
			.getElementsByClassName("sarchStatusBoolean");
	var sarchCheckbox = document.getElementsByClassName("sarchCheckbox");

	if (sarchCheckbox[0].checked) {
		sarchStatusBoolean[0].value = 0;
	} else {
		sarchStatusBoolean[0].value = 1;
	}
}

function sort(param) {
	
}