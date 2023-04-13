class Shirt extends GameObject{
    constructor(container,src,x,y,width,height,velX,velY){
        super(container,src,x,y,width,height,velX,velY);
        this.img.addEventListener("click",()=>{
            //console.log("클릭",this.src);
            flag3 = false;
            addImage(this.src);
        });
    }
    tick(){
        if(this.x < -250){
            this.x=(shirtArray.length-1)*250;
        }
        this.x+=this.velX;
        this.y+=this.velY;
    }
}