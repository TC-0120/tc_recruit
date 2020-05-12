	//function conditionsFormSubmit() {
		//document.conditionsForm.submit();
	//}

	function submitAction(url) {
		document.updateForm.action = '/recruit/candidates' + url;
		document.updateForm.submit();
	}

	function loadRecruitmentManagement() {
		sortKeep();
		getSlcStatusDtlAll();
	}

	//チェックボックスの数でボタンの活性・非活性を判定
	function buttonCheck() {
	  //チェックされた数
	  var checkedNum = 0;
	  //チェックされた選考ステータスが被っているかどうか
	  var flag = true;
	  //表示されているすべての候補者ID（配列）
	  var cId = document.updateForm.checkbox;
	  //表示されている候補者の選考ステータス（配列）
	  var slcStatus = document.getElementsByClassName("slc_status");
	  //チェックされた候補者の選考ステータス
	  var slcStatusChecked = [];
	  //候補者情報変更ボタン
	  var cndButton = document.getElementById("candidateButton");
	  //選考情報変更ボタン
	  var alcButton = document.getElementById("selectionButton");
	  //一括管理ボタン
	  var mltButton = document.getElementById("multipleButton");

	  //ｃIdが配列の場合
	  if (cId.length > 1) {
		  //チェックされたかどうかの判定を表示している候補者の数繰り返す
		  for (var i = 0; i < cId.length; i++) {
		  if (cId[i].checked) {
	    		//チェックされている場合、チェック数をカウント
	    		checkedNum += 1;
	    		//チェックされた選考ステータスの配列に追加
	    		slcStatusChecked.push(slcStatus[i+1].textContent);
	    	}
	  	}

	  	for (var i = 0; i <slcStatusChecked.length - 1; i++) {
		  	if (slcStatusChecked[i] != slcStatusChecked[i+1]) {
	  			//チェックされた選考ステータスが被っていない場合
	  			flag = false;
	  			break;
	  		}
	  	}

	  } else {
		  if (cId.checked) {
			//チェックされている場合、チェック数をカウント
	    	checkedNum += 1;
		  }
	  }


	  if (checkedNum == 0) {
		  //チェック数が0の場合、全てのボタンを非活性化
		  cndButton.disabled = true;
		  alcButton.disabled = true;
		  mltButton.disabled = true;
	  } else if (checkedNum == 1) {
		  //チェック数が1の場合、全てのボタンを活性化
		  cndButton.disabled = false;
		  alcButton.disabled = false;
		  mltButton.disabled = false;
	  } else {
		  //チェック数が2以上の場合、候補者情報変更ボタン、選考情報変更ボタンを非活性化
		  cndButton.disabled = true;
		  alcButton.disabled = true;
		  if (flag) {
			  //選考ステータスが被っている場合、活性化
			  mltButton.disabled = false;
	  	  } else {
	  		  //選考ステータスが被っていない場合、非活性化
	  		  mltButton.disabled = true;
	  	  }
	  }

	}

	function checkManagement() {
		var cId = document.updateForm.checkbox;
		var checkAll = document.getElementById("checkAll");

		if (checkAll.checked) {
			if (cId.length > 1) {
				for (var i = 0; i < cId.length; i++) {
					cId[i].checked = true;
				}
			} else {
				cId.checked = true;
			}
		} else {
			if (cId.length > 1) {
				for (var i = 0; i < cId.length; i++) {
					cId[i].checked = false;
				}
			} else {
				cId.checked = false;
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

	function selectSlcResult(slcStatusDtlId) {
		if (slcStatusDtlId >= 3 && slcStatusDtlId <= 9) {
			document.getElementById('slcResult').value = slcStatusDtlId;
		} else {
			document.getElementById('slcResult').value = '0';
		}
	}

