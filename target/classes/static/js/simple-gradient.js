class SimpleGradient {
    constructor(options = {}) {
        this.colors = options.colors || [
            [62, 35, 255],   // 蓝紫色
            [60, 255, 242],  // 青色
            [255, 35, 98]    // 粉红色
        ];
        this.speed = options.speed || 0.002;
        this.angle = 0;
        
        this.container = options.container || document.body;
        this.canvas = document.createElement('canvas');
        this.ctx = this.canvas.getContext('2d');
        
        this.container.appendChild(this.canvas);
        
        this.resize();
        window.addEventListener('resize', () => this.resize());
        
        this.animate = this.animate.bind(this);
        this.animate();
    }
    
    resize() {
        this.canvas.width = this.container.offsetWidth;
        this.canvas.height = this.container.offsetHeight;
    }
    
    animate() {
        const { width, height } = this.canvas;
        const ctx = this.ctx;
        
        // 清除画布
        ctx.clearRect(0, 0, width, height);
        
        // 创建渐变
        this.angle += this.speed;
        const gradient = ctx.createLinearGradient(
            width * Math.cos(this.angle) * 0.5 + width * 0.5,
            height * Math.sin(this.angle) * 0.5 + height * 0.5,
            width * Math.cos(this.angle + Math.PI) * 0.5 + width * 0.5,
            height * Math.sin(this.angle + Math.PI) * 0.5 + height * 0.5
        );
        
        // 添加颜色
        this.colors.forEach((color, i) => {
            const stop = i / (this.colors.length - 1);
            gradient.addColorStop(stop, `rgb(${color[0]}, ${color[1]}, ${color[2]})`);
        });
        
        // 填充背景
        ctx.fillStyle = gradient;
        ctx.fillRect(0, 0, width, height);
        
        requestAnimationFrame(this.animate);
    }
} 