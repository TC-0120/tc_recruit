	function conditionsFormSubmit() {
		document.conditionsForm.submit();
	}

	function submitAction(url) {
		document.updateForm.action = '/recruit/candidates' + url;
		document.updateForm.submit();
	}

	function buttonCheck() {
	  var checkedNum = 0;
	  var flag = true;
	  var cId = document.updateForm.checkbox;
	  var slcStatus = document.getElementsByClassName("slc_status");
	  var slcStatusChecked = [];
	  var cndButton = document.getElementById("candidateButton");
	  var alcButton = document.getElementById("selectionButton");
	  var mltButton = document.getElementById("multipleButton");


	  for (var i = 0; i < cId.length; i++) {
	    if (cId[i].checked) {
	    	checkedNum += 1;
	    	slcStatusChecked.push(slcStatus[i+1].textContent);
	    }
	  }
	  for (var i = 0; i <slcStatusChecked.length - 1; i++) {
	  	if (slcStatusChecked[i] != slcStatusChecked[i+1]) {
	  		flag = false;
	  		break;
	  	}
	  }

	  if (checkedNum == 0) {
		cndButton.disabled = true;
		alcButton.disabled = true;
		mltButton.disabled = true;
	  } else if (checkedNum == 1) {
		  cndButton.disabled = false;
		  alcButton.disabled = false;
		  mltButton.disabled = false;
	  } else {
		  cndButton.disabled = true;
		  alcButton.disabled = true;
		  if (flag) {
		  	mltButton.disabled = false;
	  	  } else {
	  		mltButton.disabled = true;
	  	  }
	  }

	}

	function checkManagement() {
		var cId = document.updateForm.checkbox;
		var checkAll = document.getElementById("checkAll");

		if (checkAll.checked) {
			for (var i = 0; i < cId.length; i++) {
				cId[i].checked = true;
			}
		} else {
			for (var i = 0; i < cId.length; i++) {
				cId[i].checked = false;
			}
		}

	}

	function sort(param) {
		var order =  document.conditionsForm.order;
		var direction = document.conditionsForm.direction;

		if (order.value == param) {
			if (direction.value == 1) {
				direction.value = 2;
			} else if (direction.value == 2) {
				direction.value = 1;
			}
		} else {
			order.value = param;
			direction.value = 1;
		}

		document.conditionsForm.submit();

	}

	function sortKeep() {
		var orderValue =  document.conditionsForm.order.value;
		var directionValue = document.conditionsForm.direction.value;
		var sort = document.getElementsByClassName("sort");

		if (directionValue == 1) {
			sort[orderValue - 1].textContent = "▼";
		} else if (directionValue == 2) {
			sort[orderValue - 1].textContent = "▲";
		}

	}