class Enemy{
    constructor(container,src, x,y,width,height,velX,velY){
        this.container=container;
        this.img=document.createElement("img");;
        this.src=src;
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.velX=velX;
        this.velY=velY;

        this.img.src=this.src;

    //스타일 적용
        this.img.style.position="absolute";
        this.img.style.left=this.x+"px";
        this.img.style.top=this.y+"px";
        this.img.style.width=this.width+"px";
        this.img.style.height=this.height+"px";

    //부착
        this.container.appendChild(this.img);
    }
    //사물의 동작에 대한 표현은 메서드로 처리한다(함수)
    tick(){        
        this.x+=this.velX
        this.y+=this.velY
    }
    render(){
        this.img.style.left=this.x+"px";
        this.img.style.top=this.y+"px";
        
        this.removeWithEnemy();
    }
    removeWithEnemy(){
        let result = collisionCheck(this,meBox);
            //충돌한경우
        
        if(result){
            //1) 화면에서 제거
            try{
                
                detailArea.removeChild(chanceArray[0].img); //detailArea에 사과소멸
                chanceArray.splice(chanceArray[0],1);
              
                this.container.removeChild(this.img); //stage에서 나죽고 
                let index=enemyArray.indexOf(this); //적군이 어디에 있는지 조사 후 삭제
                enemyArray.splice(index,1);
            }catch(e){
                console.log(e);
            }
        }
        
    }
}