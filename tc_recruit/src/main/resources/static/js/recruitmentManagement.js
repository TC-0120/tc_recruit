/**
	 * フォーム送信し、変更画面を新規ウィンドウで開く
	 *
	 * @param url フォーム送信先URL
	 */
	function submitAndOpenUpdateWindow(url) {
		//新規ウィンドウを開く
		if (url == "/multiple") {
			window.open("", "update_window", "width=1000, height=600");
		} else {
			window.open("", "update_window", "width=500, height=500");
		}

		//フォーム送信先を設定
		document.updateForm.action = '/recruit/candidates' + url;
		//フォーム送信後の画面を表示するウィンドウを指定
		document.updateForm.target = "update_window";
		//フォーム送信
		document.updateForm.submit();
	}

	/**
	 * 採用管理画面ロード時、いくつかの関数を実行
	 *
	 */
	function loadRecruitmentManagement() {
		//ソート情報保持
		showSort();
		//選考ステータス詳細検索の初期設定
		setFirstSlcStatusDtl();
		//条件に応じてスクロールバーを除去
		removeScrollbar();
		//各変更ボタンの活性・非活性の切り替え
		switchActivityOfUpdateButton();
		//自由検索窓の入力可/不可の切り替え
		canFreeSearch()
	}


	/**
	 * 各変更ボタンの活性・非活性の切り替え
	 *
	 */
	function switchActivityOfUpdateButton() {
	  //チェックされた数
	  var checkedNum = 0;
	  //チェックされた選考ステータスが被っているかどうか
	  var isSameSlcStatus = true;
	  //表示されている候補者のチェックボックス
	  var candidatesCb = document.updateForm.checkbox;
	  //表示されている候補者の選考ステータス
	  var slcStatus = document.getElementsByClassName("slc_status");
	  //チェックされた候補者の選考ステータス
	  var slcStatusChecked = [];
	  //候補者情報変更ボタン
	  var cndButton = document.getElementById("candidateButton");
	  //選考情報変更ボタン
	  var alcButton = document.getElementById("selectionButton");
	  //一括管理ボタン
	  var mltButton = document.getElementById("multipleButton");

	  //チェックボックスのチェック数、同一選考ステータスかどうか(isSameSlcStatus)を取得
	  if (candidatesCb.length > 1) {
		  //表示された候補者が複数の場合、すなわち、cIdが配列の場合
		  //チェックされたかどうかの判定を表示している候補者の数繰り返す
		  for (var i = 0; i < candidatesCb.length; i++) {
		  if (candidatesCb[i].checked) {
	    		//チェックされている場合、チェック数をカウント
	    		checkedNum += 1;
	    		//チェックされた選考ステータスの配列に追加
	    		slcStatusChecked.push(slcStatus[i+1].textContent);
	    	}
	  	}

		//チェックされた選考ステータスの配列の長さだけ繰り返し
	  	for (var i = 0; i <slcStatusChecked.length - 1; i++) {
		  	if (slcStatusChecked[i] != slcStatusChecked[i+1]) {
	  			//チェックされた選考ステータスが被っていない場合
		  		isSameSlcStatus = false;
	  			break;
	  		}
	  	}
	  } else {
		//表示された候補者が1人の場合、すなわち、cIdが配列でない場合
		  if (candidatesCb.checked) {
			//チェックされている場合、チェック数をカウント
	    	checkedNum += 1;
		  }
	  }


	  //チェック数とisSameSlcStatusで各変更ボタンの活性・非活性の切り替え
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
		  if (isSameSlcStatus) {
			  //選考ステータスが被っている場合、活性化
			  mltButton.disabled = false;
	  	  } else {
	  		  //選考ステータスが被っていない場合、非活性化
	  		  mltButton.disabled = true;
	  	  }
	  }

	}

	/**
	 * チェックボックスを全選択または全解除
	 *
	 */
	function checkAllOrNone() {
		//表示されている候補者のチェックボックス
		var candidatesCb = document.updateForm.checkbox;
		//全選択または全解除させるチェックボックス
		var checkAll = document.getElementById("checkAll");

		if (checkAll.checked) {
			//checkAllがチェックされた場合、チェックボックスを全選択
			//cIdが配列の場合、配列でない場合で分岐
			if (candidatesCb.length > 1) {
				for (var i = 0; i < candidatesCb.length; i++) {
					candidatesCb[i].checked = true;
				}
			} else {
				candidatesCb.checked = true;
			}
		} else {
			//checkAllがチェック解除された場合、チェックボックスを全解除
			//cIdが配列の場合、配列でない場合で分岐
			if (candidatesCb.length > 1) {
				for (var i = 0; i < candidatesCb.length; i++) {
					candidatesCb[i].checked = false;
				}
			} else {
				candidatesCb.checked = false;
			}
		}

	}

	/**
	 * ソート送信
	 *
	 * @param pushedOrder 押下されたソート項目
	 */
	function sort(pushedOrder) {
		//ソート項目
		var order =  document.conditionsForm.order;
		//ソート方向
		var direction = document.conditionsForm.direction;

		if (order.value == pushedOrder) {
			//現在のソート項目と同じカラムが押された場合
			//昇順、降順を逆にする
			if (direction.value == 1) {
				direction.value = 2;
			} else if (direction.value == 2) {
				direction.value = 1;
			}
		} else {
			//現在のソート項目と違うカラムが押された場合
			//ソート項目、方向を昇順に設定
			order.value = pushedOrder;
			direction.value = 1;
		}

		//フォーム送信
		document.conditionsForm.submit();

	}

	/**
	 * ロード時、ソート状態の表示
	 *
	 */
	function showSort() {
		//ソート項目の値
		var orderValue =  document.conditionsForm.order.value;
		//ソート方向の値
		var directionValue = document.conditionsForm.direction.value;
		//カラム名
		var sort = document.getElementsByClassName("sort");

		//ソート状態に応じてカラム名の横に方向を表示
		if (directionValue == 1) {
			sort[orderValue - 1].textContent = "▼";
		} else if (directionValue == 2) {
			sort[orderValue - 1].textContent = "▲";
		}

	}

	/**
	 * 自由検索窓の入力可/不可の切り替え
	 *
	 */
	function canFreeSearch() {
		//自由検索ID
		var freeSearchId = document.getElementById("freeSearchId").value;
		//自由検索窓
		var freeSearchWord = document.getElementById("freeSearchWord");

		if (freeSearchId == 0) {
			//「選択してください」の場合、検索窓に入力不可、テキスト消去
			freeSearchWord.disabled = true;
			freeSearchWord.value = null;
		} else {
			//検索カラムをしている場合、検索窓に入力可
			freeSearchWord.disabled = false;
		}
	}

