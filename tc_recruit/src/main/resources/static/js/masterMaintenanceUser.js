/*function alertValue($this) {
	document.getElementById('username').style.backgroundColor = 'skyblue';
	document.getElementById('lastName').style.backgroundColor = 'skyblue';
	document.getElementById('firstName').style.backgroundColor = 'skyblue';
	document.getElementById('authority').style.backgroundColor = 'skyblue';
	document.getElementById('status').style.backgroundColor = 'skyblue';
}*/

function buttonChecked() {
	const user = document.getElementsByClassName("user");
	const statusBoolean = document.getElementsByClassName("statusBoolean");
	const checkbox = document.getElementsByClassName("checkbox")

	for (const i = 0; i < user.length; i++) {
		if (checkbox[i].checked) {
			statusBoolean[i].value = "0";
		} else {
			statusBoolean[i].value = "1";
		}
	}
}

function buttonCheckAuthorityAdmin() {
	const sarchCheckboxAdmin = document.getElementsByName("sarchCheckboxAdmin");
	const sarchAuthorityAdmin = document.getElementsByName("sarchAuthorityAdmin");

	console.log(sarchAuthorityAdmin[0].value);
	console.log(sarchCheckboxAdmin[0].checked);
	if (sarchCheckboxAdmin[0].checked) {
		sarchAuthorityAdmin[0].value = 1;
	} else {
		sarchAuthorityAdmin[0].value = 0;
	}
	console.log(sarchAuthorityAdmin[0].value);
}
function buttonCheckAuthorityUser() {
	const sarchAuthorityUser = document.getElementsByName("sarchAuthorityUser");
	const sarchCheckboxUser = document.getElementsByName("sarchCheckboxUser");

	if (sarchCheckboxUser[0].checked) {
		sarchAuthorityUser[0].value = 1;
	} else {
		sarchAuthorityUser[0].value = 0;
	}
}
function buttonCheckStatus() {
	const sarchStatusBoolean = document.getElementsByName("sarchStatusBoolean");
	const sarchCheckbox = document.getElementsByName("sarchCheckbox");

	console.log(sarchStatusBoolean[0].value);
	console.log(sarchCheckbox[0].checked);

	if (sarchCheckbox[0].checked) {
		sarchStatusBoolean[0].value = 1;
	} else {
		sarchStatusBoolean[0].value = 0;
	}
}

function sort(param) {
	const sortUsername = document.getElementsByName("sortUsername");
	const sortLastName = document.getElementsByName("sortLastName");
	const sortFirstName = document.getElementsByName("sortFirstName");
	const sortAuthority = document.getElementsByName("sortAuthority");
	const sortStatus = document.getElementsByName("sortStatus");

	if(param == 1){
		sortUsername[1].value = 1;
	} else if(param == 2){
		sortLastName[1].value = 2;
	} else if(param == 3){
		sortFirstName[1].value = 3;
	} else if(param == 4){
		sortAuthority[1].value = 4;
	} else if(param == 5){
		sortStatus[1].value = 5;
	}

	console.log(sortUsername[0].value);
}