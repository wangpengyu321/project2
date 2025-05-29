/**
 * 波浪渐变背景动画
 * 实现类似CurveGradientBg效果的波浪渐变动画
 */
class WavesBg {
    constructor(options) {
        this.options = Object.assign({
            container: null,
            colors: [
                [62, 35, 255],  // 蓝紫色
                [60, 255, 242], // 青色
                [255, 35, 98]   // 粉红色
            ],
            waves: 3,
            speed: 0.004,
            amplitude: 0.7,
            frequency: 0.005
        }, options);

        if (!this.options.container) {
            console.error('WavesBg: container is required');
            return;
        }

        // 创建canvas元素
        this.canvas = document.createElement('canvas');
        this.ctx = this.canvas.getContext('2d');
        this.options.container.appendChild(this.canvas);

        // 监听窗口大小变化
        window.addEventListener('resize', this.resize.bind(this));
        this.resize();

        // 动画参数
        this.points = [];
        this.tick = 0;

        // 初始化波浪点
        this.initPoints();

        // 开始动画
        this.animate = this.animate.bind(this);
        this.animate();
    }

    resize() {
        const dpr = window.devicePixelRatio || 1;
        const width = this.options.container.offsetWidth;
        const height = this.options.container.offsetHeight;
        
        this.width = width;
        this.height = height;
        
        this.canvas.width = width * dpr;
        this.canvas.height = height * dpr;
        this.canvas.style.width = `${width}px`;
        this.canvas.style.height = `${height}px`;
        
        this.ctx.scale(dpr, dpr);
        
        // 重新初始化点
        this.initPoints();
    }

    initPoints() {
        const { waves, frequency, amplitude } = this.options;
        const width = this.width;
        const height = this.height;
        
        this.points = [];
        
        for (let w = 0; w < waves; w++) {
            const points = [];
            const baseY = height / 2;
            const spread = height * 0.7;
            const offset = Math.random() * 100;
            
            for (let i = 0; i <= width; i += 5) {
                points.push({
                    x: i,
                    y: baseY + Math.sin(i * frequency + offset) * (amplitude * spread),
                    originY: baseY,
                    offset: offset
                });
            }
            
            this.points.push(points);
        }
    }

    getGradient(ctx, wave, opacity = 1) {
        const { colors } = this.options;
        const minMax = this.getPointsMinMax(wave);
        
        // 创建渐变
        const gradient = ctx.createLinearGradient(0, minMax.min, 0, minMax.max);
        
        // 添加颜色
        colors.forEach((color, i) => {
            const stop = i / (colors.length - 1);
            const alpha = opacity;
            gradient.addColorStop(stop, `rgba(${color[0]}, ${color[1]}, ${color[2]}, ${alpha})`);
        });
        
        return gradient;
    }

    getPointsMinMax(points) {
        let min = Infinity;
        let max = -Infinity;
        
        points.forEach(point => {
            if (point.y < min) min = point.y;
            if (point.y > max) max = point.y;
        });
        
        return { min, max };
    }

    updatePoints() {
        const { speed, frequency, amplitude } = this.options;
        const height = this.height;
        const spread = height * 0.7;
        
        this.tick += speed;
        
        this.points.forEach((wave, waveIndex) => {
            const waveSpeed = speed * (1 + waveIndex * 0.2); // 每个波的速度略有不同
            wave.forEach(point => {
                // 更新y值, 创建波动效果
                const newY = point.originY + Math.sin(point.x * frequency + point.offset + this.tick) * (amplitude * spread);
                point.y = newY;
            });
        });
    }

    drawWave(ctx, points, opacity) {
        const gradient = this.getGradient(ctx, points, opacity);
        
        ctx.beginPath();
        ctx.moveTo(points[0].x, points[0].y);
        
        // 使用曲线绘制波浪形状
        for (let i = 0; i < points.length - 1; i++) {
            const curr = points[i];
            const next = points[i + 1];
            const midX = (curr.x + next.x) / 2;
            const midY = (curr.y + next.y) / 2;
            
            ctx.quadraticCurveTo(curr.x, curr.y, midX, midY);
        }
        
        // 封闭路径
        ctx.lineTo(this.width, this.height);
        ctx.lineTo(0, this.height);
        ctx.closePath();
        
        // 填充波浪
        ctx.fillStyle = gradient;
        ctx.fill();
    }

    animate() {
        // 清除画布
        this.ctx.clearRect(0, 0, this.width, this.height);
        
        // 更新点位置
        this.updatePoints();
        
        // 绘制每个波浪
        this.points.forEach((wave, index) => {
            const opacity = 0.6 - (index * 0.15); // 每个波浪透明度不同
            this.drawWave(this.ctx, wave, opacity);
        });
        
        // 循环动画
        requestAnimationFrame(this.animate);
    }
} 