function alertValue($this) {
	document.getElementById('username').style.backgroundColor = 'skyblue';
	document.getElementById('lastName').style.backgroundColor = 'skyblue';
	document.getElementById('firstName').style.backgroundColor = 'skyblue';
	document.getElementById('authority').style.backgroundColor = 'skyblue';
	document.getElementById('status').style.backgroundColor = 'skyblue';
}

function buttonCheck() {
	var user = document.getElementById("user");
	var statusBoolean = document.getElementById("statusBoolean");
	var checkbox = document.getElementById("checkbox");

	for (var i = 0; i < user.length; i++) {
		if (checkbox.checked) {
			statusBoolean.value = "0";
		} else {
			statusBoolean.value = "1";
		}
	}
}