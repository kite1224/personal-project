class  Mainbox{
    constructor(container, x,y,width,height,stepX,stepY){
        this.container=container; //주인공의 div가 붙을 부모요소
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.stepX=stepX;
        this.stepY=stepY;

        this.img=document.createElement("img");
        this.img.src="./images/dog.gif";
        this.img.style.position="absolute";
        this.img.style.left=this.x+"px";
        this.img.style.top=this.y+"px";
        this.img.style.width=this.width+"px";
        this.img.style.height=this.height+"px";

        this.container.appendChild(this.img);
    }

    tick(){
        
        let cell=blockArray[this.stepY][this.stepX]; //객체의 좌표
        console.log(this.stepX,this.stepY,cell);
        
        this.x=parseInt(cell.img.style.left);
        this.y=parseInt(cell.img.style.top);
        
    }
    render(){
        this.img.style.left=this.x+"px";
        this.img.style.top=this.y+"px";
    }
   

}