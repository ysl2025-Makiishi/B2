

         document.getElementById("quiz-form").addEventListener("submit", function(event) {
            event.preventDefault();

            //ここで宣言した変数にそれぞれの性格タイプの合計点を格納
            let Openness = 0;       //開放性
            let Diligence = 0;      //勤勉性
            let Neuroticism = 0;    //神経症傾向
            let Extraversion = 0;   //外向性

            //valは入力された値を持ってくるための変数
            //開放性を計算
            let val= parseInt(document.querySelector(`input[name="q1"]:checked`).value);   //これでq1のvalueを取得。
            Openness += val;

            val = parseInt(document.querySelector(`input[name="q6"]:checked`).value);       //これでq6のvalueを取得。
            Openness += val;

            console.log("Openness:" + Openness);   //テスト用

            //勤勉性を計算
            val = parseInt(document.querySelector(`input[name="q2"]:checked`).value);   
            Diligence += val;

            val = parseInt(document.querySelector(`input[name="q7"]:checked`).value);       
            Diligence += val;

            console.log("Diligence:" + Diligence);   //テスト用
        
            //神経症傾向を計算
            val = parseInt(document.querySelector(`input[name="q3"]:checked`).value);   
            Neuroticism += val;

            val = parseInt(document.querySelector(`input[name="q8"]:checked`).value);       
            Neuroticism += val;

            console.log("Neuroticism:" + Neuroticism);   //テスト用

            //外向性を計算
            val = parseInt(document.querySelector(`input[name="q4"]:checked`).value);   
            Extraversion += val;

            val = parseInt(document.querySelector(`input[name="q5"]:checked`).value);       
            Extraversion += val;

            console.log("Extraversion:" + Extraversion);   //テスト用

            //性格タイプを決定する関数
            let personality = decisionPersonality(Openness, Diligence, Neuroticism, Extraversion);  //性格を格納するpersonality

            document.getElementById("personality-result").textContent = personality;
        });

        //性格タイプを決める関数
        function decisionPersonality(Openness, Diligence, Neuroticism, Extraversion) {
            let max = 0;            //最大得点を記録
            let personality = "";   //性格タイプの文字列を格納する
            let ifSameNumber1 = ""; //もし点数が同じだった時にその性格を記録
            let ifSameNumber2 = "";
            let ifSameNumber3 = "";

            max = Math.max(Openness, Diligence, Neuroticism, Extraversion); //最大値を計算

            //どれが最大値なのかを探す
            if(max === Openness) {  
                personality = "開放性";
            }

            if(max === Diligence) {
                if(personality !== "") { //もしpersonalityに何かしら入ってる=最大値が同じだったらその性格をifSameNumber1に記録
                    ifSameNumber1 = "勤勉性"

                } else {
                    personality = "勤勉性";     
                }
            }  

            if(max === Neuroticism) {
            
             if(personality !== "") {
                
                if(ifSameNumber1 !== "") {  //もしpersonalityにもifSameNumber1にも何か入ってたらifSameNumber2へ
                    ifSameNumber2 = "神経症傾向";

                } else {
                    ifSameNumber1 = "神経症傾向";
                }

            } else {
                personality = "神経症傾向";
            }
        }

            if(max === Extraversion) {
                if(personality !== "") {
                    if(ifSameNumber1 !== "") {
                        if(ifSameNumber2 !== "") {
                            ifSameNumber3 = "外向性";
                        } else {
                            ifSameNumber2 = "外向性";
                        }

                    } else {
                        ifSameNumber1 = "外向性";
                    }

                } else {
                    personality = "外向性";
                }
            }

            //同数があった時の変数ifSafeNumberに何か入ってた場合
            if(ifSameNumber1 !== "") {

                //最大値の性格をカウントする変数
                let count = 2;

                if(ifSameNumber2 !== "") {
                    count++;
                }

                if(ifSameNumber3 !== "") {
                    count++;
                }

                //ここで乱数　1~countまでの乱数を生成
                var random = Math.floor(Math.random() * count) + 1;

                console.log(random);

                if(random === 1) {
                    return personality;
                } else if(random === 2) {
                    return ifSameNumber1;
                } else if(random === 3) {
                    return ifSameNumber2;
                } else if(random === 4) {
                    return ifSameNumber3;
                }

            //同数がなかった時の場合
            } else {
                return personality;
            }
        }
        
        //登録しました！を表示するための関数
function showMessage() {
	document.getElementById("message").style.display = "block";
}
        