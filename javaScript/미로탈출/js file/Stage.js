class Stage{
    constructor(container,x,y,width,height,type){
        this.container=container;
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.type=type;

        this.img=document.createElement("img");
        this.img.style.width=this.width+"px";
        this.img.style.height=this.height+"px";
        this.img.style.position="absolute";
        this.img.style.left=this.x+"px";
        this.img.style.top=this.y+"px";
        this.img.style.border="1px solid black";

        switch(this.type){
            case 0: this.img.src ="./images/floor2.png"; break;
            case 1:this.img.src="./images/wall.png"; break;
            case 2:this.img.src="./images/item.png";
                         itemArray.push(this);break;
            case 3: this.img.src="./images/Home.png";break;
        }

        this.container.appendChild(this.img);

    }
}