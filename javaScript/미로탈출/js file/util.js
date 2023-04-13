function setTarget(){
    //반딧불의 새로운 좌표를 마우스를 이용하여 설정
    //마우스를 누를 때 좌표를 구하여 targetX, targetY값을 설정하자
    console.log("마우스 x좌표는 ", event.clientX, "마우스 y좌표는 ", event.clientY);
    targetX=event.clientX;
    targetY=event.clientY;
}


function getRandom(n){
return parseInt(Math.random()*n);

}
/*범위가 있는 랜덤값------------------------------------------
min=시작값
max=끝값
//시작값이 5이고, 끝값이 8이라면 Math.random()에 몇을 곱해야하나
max-min+1*/

function getRandomwithRange(min, max){
   var result = min+ parseInt(Math.random()*(max-min+1))
    return result;

}

/*-------------------------------------------------
게임 개발 시 사용될 충돌체크함수 정의
---------------------------------------------------*/
function collisionCheck(me, you){
    //충돌여부 판단 > 조건들을 모두 변수로 빼고, &&로 묶어버리자 >결과 전체가 &&으로 연결되어야함 > 호출하면 리턴을 가지고 감
    // 움직일 나=me / 상대방= you
        let result1=(me.y+me.height >=you.y) && (me.x+me.width >= you.x) //북서쪽
        let result2=(me.x+me.width >= you.x) && (me.y <= you.y + you.height); //남서쪽
        let result3=(me.x <= you.x+you.width) && (me.y+me.height >= you.y); //북동
        let result4=(me.x <= you.x+you.width) && (me.y <= you.y+you.height);//남동

        return result1 && result2 && result3 && result4;
}

/*-------------------------------------------------
날짜 관련 문자열 처리
날짜 두자리로 처리하는 함수
n이 10보다 작으면 앞에 '0'문자열 부착
---------------------------------------------------*/
function getDateString(n){
    let str=n;
    if(n<10){
        str="0"+str; //누적
    }
    return str;
}